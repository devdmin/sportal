var sportal = angular.module('sportal', ['ngResource'])

.factory("sessionService", function($http, $window){
  var session = {};
   session.login = function(data){
     return $http.post("/login", "username=" + data.username +
           "&password=" + data.password, {
               headers: {'Content-Type': 'application/x-www-form-urlencoded'}
           } ).then(function(data) {
            $window.location.href = '/';
               localStorage.setItem("session", {});
           }, function(data) {
               alert("Błąd logowania");
           });
   };
   session.logout = function(data){
     localStorage.removeItem("session");
   };
  session.isLoggedIn = function(){
     return localStorage.getItem("session") !== null;
   };

  return session;
})

.factory("userService", function($resource, $http){
    var service = {};
    service.findAll = function(){
        var Users = $resource("/api/users");
        return Users.get();
    }
    return service;
})

.controller('AppCtrl', function AppCtrl ($scope){
    
})

.controller('RegisterCtrl', function($scope){
      $scope.register = function(){
        console.log('X ' + JSON.stringify($scope.user));
          console.log((new Date().getFullYear())-$scope.user.year);
      }
})

.controller('UsersCtrl', function($scope, userService){
    userService.findAll().$promise.then(function(data){
        console.log(data.userList);
        angular.forEach(data.userList,function(value,index){
            console.log(value.username + ": " + index);
       });
    });
})


.controller('LoginCtrl', function($scope, $http, sessionService){
  $scope.login = function(){
        sessionService.login($scope.user)
      }
});


