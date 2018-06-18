var dashboard = angular.module("dashboard", ['sportal','ui.bootstrap.datetimepicker'])


.controller('MapCtrl', function($scope, sportFieldService, $filter, sessionService, userService, translator, eventFormService, eventService, userService){
	var sportFields;
    var user;
	$scope.hide = false;
	sportFieldService.findAll().$promise.then(function (result) {
		sportFields = result.sportFieldList;
		localStorage.setItem("sportFields",  JSON.stringify(sportFields));
		initAutocomplete();
	});
   
	
    $scope.show = function(sportFieldId){
		$scope.hide = false;
		$scope.$apply(function () {
			$scope.sportField = $filter('filter')(sportFields, {id: sportFieldId})[0];
            userService.currentUser().$promise.then(function(data){
               user = translator.translate(data);                 
	         });
		});
        google.charts.load('current', {
            packages: ['timeline'], 'language': 'pl'
        }).then(function () {
        var container = document.getElementById('timeline');
        var chart = new google.visualization.Timeline(container);
        var dataTable = new google.visualization.DataTable();

             var options = {
          hAxis: {
            format: 'dd.MM HH:mm'
          }
        };
            
        var dates = [];
        $scope.sportField.events.forEach(function(entry) {
            entry = translator.translate(entry);
            entry.date = new Date(eventFormService.convertArrayToDate(entry.date));
            entry.endDate = new Date(eventFormService.convertArrayToDate(entry.endDate));
            if(entry.date > new Date() && days_between(new Date(), entry.date) < 3){
                dates.push(['', entry.date, entry.endDate]);
            }
        });
            
        dataTable.addColumn({ type: 'string', id: 'id' });
        dataTable.addColumn({ type: 'date', id: 'Start' });
        dataTable.addColumn({ type: 'date', id: 'End' });
        dataTable.addRows(dates);
            
        chart.draw(dataTable, options);    
        });
        
	}
    
    $scope.addEvent = function(event){
        
        event.date = eventFormService.convertDateToArray(event.date);
        event.endDate = eventFormService.convertDateToArray(event.endDate);
        eventService.add($scope.sportField.id, event);
        $scope.event = null;
    }
    
    $scope.compare = function(){
     
        $scope.event.endDate.setFullYear($scope.event.date.getFullYear(), $scope.event.date.getMonth(), $scope.event.date.getDate());
        let diff = ($scope.event.endDate - $scope.event.date);
        $scope.diffMins = diff / 60000;

        
    }
    
    $scope.searchByTime = function( timeCriteria ) {
        return function( item ) {
            if(validateUser(user, item)){
            if (timeCriteria == null){ 
                return item;
            }else if(days_between(item.date, new Date())<=timeCriteria){
                return item;
            }
            }
        };
    }
    
    $scope.searchToday = function(){
         $scope.timeCriteria = 0;
    }
    
    $scope.searchSevenDays = function(){
         $scope.timeCriteria = 7;
    }
    
    $scope.searchThirtyDays = function(){
         $scope.timeCriteria = 30;
    }
    
    $scope.searchAll = function(){
        $scope.timeCriteria = null;
    }
        
    function validateUser(user, event){
        var toReturn = true;
        
        if(user.age > event.maxAge || user.age < event.minAge || user.gender !== event.gender){
            toReturn = false;
        }
        
        return toReturn;
    }
   
    function days_between(date1, date2) {

    // The number of milliseconds in one day
    var ONE_DAY = 1000 * 60 * 60 * 24

    // Convert both dates to milliseconds
    var date1_ms = date1.getTime()
    var date2_ms = date2.getTime()

    // Calculate the difference in milliseconds
    var difference_ms = Math.abs(date1_ms - date2_ms)

    // Convert back to days and return
    return Math.round(difference_ms/ONE_DAY)

    }

   
})
