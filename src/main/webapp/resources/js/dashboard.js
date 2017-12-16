var dashboard = angular.module("dashboard", ['sportal','ui.bootstrap.datetimepicker'])
.factory("eventService", function($resource, $http){
    "use strict";
    var service = {};
   
    service.add = function(sportFieldId, event, success, failure){
        console.log(sportFieldId);
        console.log(event);
        var Event = $resource("/api/events/"+sportFieldId);
        Event.save({}, event, success, failure);
    };
    
    return service; 
})
.service("eventFormService", function(){
    this.convertDateToArray = function(date){
        return [
            date.getFullYear(),
            date.getMonth(),
            date.getDate(),
            date.getHours(),
            date.getMinutes()
        ];
    }  
})
.controller('MapCtrl', function($scope, sportFieldService, $filter, sessionService, userService, translator, eventFormService, eventService){
	var sportFields;
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
            $scope.sportField = translator.translate($scope.sportField);
		});
        console.log($scope.sportField);
	}
    
    $scope.addEvent = function(event){
        
        event.date = eventFormService.convertDateToArray(event.date);
        event.endDate = eventFormService.convertDateToArray(event.endDate);
        eventService.add($scope.sportField.id, event);
        console.log(event);
        $scope.event = null;
    }
    
    $scope.compare = function(){
     
        $scope.event.endDate.setFullYear($scope.event.date.getFullYear(), $scope.event.date.getMonth(), $scope.event.date.getDate());
        let diff = ($scope.event.endDate - $scope.event.date);
        $scope.diffMins = diff / 60000;

        
    }
   

})
