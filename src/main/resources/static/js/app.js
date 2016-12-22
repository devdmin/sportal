var sportal = angular.module('sportal', []);



sportal.controller('AppCtrl', function AppCtrl ($scope){
    
});

sportal.controller('RegisterCtrl', ['$scope', function($scope){
      $scope.submit = function(){
        console.log('Submit');
      }
}]);