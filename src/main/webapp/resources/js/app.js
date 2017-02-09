/*jslint browser: true*/
/*jslint node: true*/
/*global alert*/

var angular, sportal = angular.module('sportal', ['ngResource'])

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
	});

.controller('AppCtrl', function AppCtrl ($scope) {
    
});

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

.controller('MapCtrl', function($scope, sportFieldService, $filter){
	var sportFields;
	$scope.hide = false;
	sportFieldService.findAll().$promise.then(function (result) {
		sportFields = result.sportFieldList;
		console.log(sportFields);
		localStorage.setItem("sportFields",  JSON.stringify(sportFields));
		initAutocomplete();
	});

	$scope.show = function(sportFieldId){

		$scope.hide = false;
		console.log($scope.hide);
		console.log(JSON.stringify($filter('filter')(sportFields, {id: sportFieldId})[0]));
		$scope.$apply(function () {
			$scope.sportField = $filter('filter')(sportFields, {id: sportFieldId})[0];
		});
		console.log($scope.sportField.addingDate);
	}

})

.controller('LoginCtrl', function($scope, sessionService){
	$scope.login = function(){
		sessionService.login($scope.user)
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
;


