var newEvent = angular.module("dashboard", ['sportal'])

    .controller('EventCtrl', function ($scope, sportFieldService, $filter, sessionService, userService, translator, eventFormService, eventService, userService) {
        var sportFields;
        var sportFieldId = window.location.pathname.split('/')[2];
        var sportField;
        var todaysEvents;
        sportFieldService.findAll().$promise.then(function (result) {
            sportFields = result.sportFieldList;
            sportField = $filter('filter')(sportFields, {
                id: sportFieldId
            })[0];

        });






        $scope.showEventsByDate = function () {
            var container = document.getElementById('timeline');
            var chart = new google.visualization.Timeline(container);
            var dataTable = new google.visualization.DataTable();
            todaysEvents = [];

            dataTable.addColumn({
                type: 'string',
                id: 'id'
            });
            dataTable.addColumn({
                type: 'date',
                id: 'Start'
            });
            dataTable.addColumn({
                type: 'date',
                id: 'End'
            });
            dataTable.removeRows(0, dataTable.getNumberOfRows());

            sportField.events.forEach(function (entry) {
                if (Array.isArray(entry.date)) {
                    entry.date = new Date(eventFormService.convertArrayToDate(entry.date));
                    entry.endDate = new Date(eventFormService.convertArrayToDate(entry.endDate));
                }
                if (sameDay(entry.date, $scope.event.date)) {
                    todaysEvents.push(entry);
                    dataTable.addRow(['', entry.date, entry.endDate]);

                }
            });

            var options = {
                hAxis: {
                    format: 'HH:mm',
                }
            };



            if (dataTable.getNumberOfRows() > 0) {
                chart.draw(dataTable, options);
                $scope.timeline = true;
                $scope.emptyDay = "";
            } else {
                $scope.timeline = false;
                $scope.emptyDay = "Brak wydarzeń na tym obiekcie w tym dniu";
            }


        };

        $scope.addEvent = function (event) {

            event = processEvent(event);
            if (validateEvent(event)) {
                event.date = eventFormService.convertDateToArray(event.date);
                event.endDate = eventFormService.convertDateToArray(event.endDate);
                eventService.add(sportFieldId, event);

            }
        }

        function validateEvent(event) {
            if (todaysEvents.length > 0) {
                var errors = 0;
                todaysEvents.forEach(function (entry) {
                    if (entry.date < add_minutes(event.date, 1) && entry.endDate > minus_minutes(event.endDate, 1)) {
                        errors = errors + 1;
                    }

                    if (entry.date < event.endDate && entry.date > event.date) {
                        errors = errors + 1;
                    }
                    if (entry.endDate < event.endDate && entry.endDate > event.date) {
                        errors = errors + 1;
                    }
                    if (entry.endDate < event.endDate && entry.date > event.date) {
                        errors = errors + 1;
                    }
                });
                if (errors > 0) {
                    alert("blad");
                    return false;
                }
            }
            return true;
        }

        function processEvent(event) {
            event.date.setHours(event.hour, event.minutes, 0);
            event.endDate = add_minutes(event.date, event.duration);

            delete event.minutes;
            delete event.duration;
            delete event.hour;
            return event;
        }

        function add_minutes(dt, minutes) {
            return new Date(dt.getTime() + minutes * 60000);
        }

        function minus_minutes(dt, minutes) {
            return new Date(dt.getTime() - minutes * 60000);
        }

        function sameDay(d1, d2) {

            return d1.getFullYear() === d2.getFullYear() &&
                d1.getMonth() === d2.getMonth() &&
                d1.getDate() === d2.getDate();
        }
    })

    .controller('EventInfoCtrl', function ($scope, $filter, eventFormService, eventService, sportFieldService, translator, $rootScope) {
        var eventId = window.location.pathname.split('/')[2];
        var sportField;
        var event;

        $scope.nonParticipant = false;
        $scope.wrong = false;
        $scope.participant = false;
        
        sportFieldService.findByEventId(eventId).$promise.then(function (result) {
            sportField = translator.translate(result);
            event = translator.translate($filter('filter')(sportField.events, {
                id: eventId
            })[0]);

            event.date = eventFormService.convertArrayToDate(event.date);
            event.endDate = eventFormService.convertArrayToDate(event.endDate);

            eventService.findAuthor(eventId).$promise.then(function (result) {
                $scope.event.author = result;
            });

            eventService.findParticipants(eventId).$promise.then(function (result) {
                 
                $scope.event.users = result;
                
                
                result.userList.filter(function (field) {
                    if (field.username === $rootScope.user.username) {
                        console.log("XD");
                       
                        $scope.wrong = false;
                        $scope.participant = true;
                        $scope.nonParticipant = false;
                        console.log(field.username === $rootScope.user.username);
                         
                    }                  
                   
                });
                
                if( $scope.participant === false){
                     $scope.nonParticipant = true;
                }
                   
                
            });
            
            $scope.sportField = translator.translate(sportField);
            $scope.event = translator.translate(event);

        });

        $scope.join = function () {
            $scope.participant = !$scope.participant;
             $scope.nonParticipant = false;
            $scope.event.users.userList.push($rootScope.user);
            eventService.join(eventId);
        }

        $scope.signOut = function () {
            $scope.participant = !$scope.participant;
            $scope.nonParticipant = true;
           
            for (var i = 0; i < $scope.event.users.userList.length; i++) {
                
                if ($scope.event.users.userList[i].username === $rootScope.user.username) {
                    $scope.event.users.userList.splice(i, 1);
                }
            }
            eventService.signOut(eventId);
        }

    });
