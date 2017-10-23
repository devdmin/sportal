/*jslint browser: true*/
/*jslint node: true*/
/*global angular,alert*/

var sportal = angular.module('sportal', ['ngResource'])

	.factory("sessionService", function ($http, $window) {
		"use strict";
		var session = {};
		session.login = function (data) {
			return $http.post("/login", "username=" + data.username +
							  "&password=" + data.password, {
					headers: {'Content-Type': 'application/x-www-form-urlencoded'}
				}).then(function (data) {
				$window.location.href = '/dashboard';
				localStorage.setItem("session", {});
			}, function (data) {
				alert("Błąd logowania");
			});
		};
		session.logout = function (data) {
			localStorage.removeItem("session");
            $http.get("/logout");
            $window.location.href = '/';

		};
		session.isLoggedIn = function () {
			return localStorage.getItem("session") !== null;
		};

		return session;
	})

	.factory("userService", function ($resource, $http) {
		"use strict";
		var service = {};
		service.findAll = function () {
			var Users = $resource("/api/users");
			return Users.get();
		};

		service.register = function (user, success, failure) {
			var User = $resource("/api/users");
			User.save({}, user, success, failure);
		};
        service.find = function (username){
            var User = $resource("/api/users/"+username);
            return User.get();
        }
        service.currentUser = function(){
            var User = $resource("/api/users/user");
            return User.get();
        }
		return service;
	})

	.factory("sportFieldService", function ($resource, $http) {
		"use strict";
		var service = {};
		service.add = function (sportField, success, failure) {
			var SportField = $resource("/api/sportField");
			SportField.save({}, sportField, success, failure);
		};
		service.findAll = function () {
			var SportFields = $resource("/api/sportField");
			return SportFields.get();
		};
		return service;
	})
    .factory("translator", function($http){
        "use strict";
        var translator = {};
        translator.translate = function(restObject){

       $http.get('resources/js/translations/pl_lang.json')
            .then(function(res){
           angular.forEach(restObject, function(value, key){
               if(res.data[restObject[key]] != null){
                   restObject[key] = res.data[restObject[key]];
                }
            });
            });

            return restObject;
        }
        return translator;
    })

.controller('AppCtrl', function AppCtrl ($scope) {

})
.controller('UserCtrl', function($scope, $window, userService, sessionService){
    var username = $window.location.pathname.split('/').pop();
    var user = userService.find(username);
    $scope.user = user;
})

.controller('RegisterCtrl', function($scope, sessionService, userService){
	$scope.register = function(){
		var user = $scope.user;
		user.age = (new Date().getFullYear())-$scope.user.year;
		delete user.year;
		userService.register(user,
							 function(returnedData) {
			sessionService.login(user);
		},
							 function() {
			alert("Błąd podczas rejestracji użytkownika");
		});

	};
})

.controller('UsersCtrl', function($scope, userService){
	userService.findAll().$promise.then(function(data){
		console.log(data.userList);
		angular.forEach(data.userList,function(value,index){
			console.log(value.username + ": " + index);
		});
	});
})


.controller('MapCtrl', function($scope, sportFieldService, $filter, sessionService, userService, translator){
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
		console.log($scope.sportField.addingDate);
	}
})


.controller('SportFieldCtrl', function($scope, sportFieldService, $window){
	var sportField;
	$scope.add = function(){
		sportField = $scope.sportField;
		sportField.lat = document.getElementById('lat').value;
		sportField.lng = document.getElementById('lng').value;

		sportFieldService.add(sportField, function(returnedData){
			console.log(returnedData);
		},
							  function(err){
			console.log("err", err);
		}
							 );
		$window.localStorage.clear();
		$window.location.href = '/dashboard';
	}
})

.controller('NavCtrl', function($scope,userService, sessionService){
    userService.currentUser().$promise.then(function(data){
		$scope.userPath = "/user/" + data.username;
        console.log(data.username);
	});

    $scope.logout = function(){
        sessionService.logout();
    }
})

.controller('LoginCtrl', function($scope, sessionService){
	$scope.login = function(){
		sessionService.login($scope.user)
	}
})
.controller('AdminCtrl', function($scope, sportFieldService){
	sportFieldService.findAll().$promise.then(function (result) {
		$scope.sportFields = result.sportFieldList;
		console.log(JSON.stringify($scope.sportFields));
	});

})
;
