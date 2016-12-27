var sportal = angular.module('sportal', []);



sportal.controller('AppCtrl', function AppCtrl ($scope){
    
});

sportal.controller('RegisterCtrl', function($scope){
      $scope.register = function(){
        console.log('X ' + JSON.stringify($scope.user));
          console.log((new Date().getFullYear())-$scope.user.year);
      }
});

sportal.controller('UsersCtrl', function($scope, $http){
    $scope.data = {};
   
    $http.get("http://localhost:8080/api/users")
                .success(function(data){
        $scope.data.userList = data;
        console.log(data);
    })
                .error(function(error){
        $scope.data.error = error;
    });
});