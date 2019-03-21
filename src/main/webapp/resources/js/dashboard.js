var dashboard = angular.module("dashboard", ['sportal', 'ui.bootstrap.datetimepicker'])


    .controller('MapCtrl', function ($scope, sportFieldService, $filter, sessionService, userService, translator, eventFormService, eventService, userService, $rootScope, $window) {
        var sportFields;
        var user;
        $scope.hide = false;
       
        sportFieldService.findAll().$promise.then(function (result) {
            sportFields = result.sportFieldList;
            localStorage.setItem("sportFields", JSON.stringify(sportFields));
            
            initAutocomplete();
        });



        $scope.show = function (sportFieldId) {
         
            $scope.showSportField = true;
            $scope.$apply(function () {
            user = $rootScope.user;
                $scope.sportField = $filter('filter')(sportFields, {
                    id: sportFieldId
                })[0];
                console.log($scope.sportField.events);
                $scope.sportField.events.forEach(function (entry) {
                    if(Array.isArray(entry.date)){
                    entry.date = eventFormService.convertArrayToDate(entry.date);
                    entry.endDate = eventFormService.convertArrayToDate(entry.endDate);
//                       entry = translator.translate(entry);
                    }
                });
            });




        }

        $scope.addEvent = function (event) {

            event.date = eventFormService.convertDateToArray(event.date);
            event.endDate = eventFormService.convertDateToArray(event.endDate);
            eventService.add($scope.sportField.id, event);
            $scope.event = null;
        }

        $scope.compare = function () {

            $scope.event.endDate.setFullYear($scope.event.date.getFullYear(), $scope.event.date.getMonth(), $scope.event.date.getDate());
            let diff = ($scope.event.endDate - $scope.event.date);
            $scope.diffMins = diff / 60000;


        }

        $scope.searchByTime = function (timeCriteria) {
           
            return function (item) {
                if (validateUser(user, item)) {

                    if (timeCriteria == null) {
                        if(days_between(item.date,new Date()) >= 0){
                        return item;}
                    } else if (days_between(item.date, new Date()) <= timeCriteria && days_between(item.date, new Date())  >= 0) {
                        return item;
                    }
                }
            };
        }

        $scope.searchToday = function () {
            $scope.timeCriteria = 0;
        }

        $scope.searchSevenDays = function () {
            $scope.timeCriteria = 7;
        }

        $scope.searchThirtyDays = function () {
            $scope.timeCriteria = 30;
        }

        $scope.searchAll = function () {
            $scope.timeCriteria = null;
        }

        $scope.newEvent = function (sportFieldId) {
            $window.location.href = '/newEvent/' + sportFieldId;
        }

        $scope.redirectToEvent = function (eventId) {
            $window.location.href = '/event/' + eventId;
        }

        function validateUser(user, event) {
            var toReturn = true;
            if (user.age > event.maxAge || user.age < event.minAge || user.gender !== event.gender) {
                toReturn = false;
            }

            return toReturn;
        }

        function days_between(date1, date2) {
            if(date1<date2){return -1;}
            // The number of milliseconds in one day
            var ONE_DAY = 1000 * 60 * 60 * 24

            // Convert both dates to milliseconds
            var date1_ms = date1.getTime()
            var date2_ms = date2.getTime()

            // Calculate the difference in milliseconds
            var difference_ms = Math.abs(date1_ms - date2_ms)

            // Convert back to days and return
            return Math.round(difference_ms / ONE_DAY)

        }
        
        function changeColors(){
            
        }


    })
