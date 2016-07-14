/*'use strict';
angular.module('leanbitSupportTicketSystemApp')
	.controller('UserCtrl', function ($scope, usersDb) {
		function onError(res) {
			console.log('Error', res);
		}

		function getUsers() {
			usersDb.getAll()
				.then(function (res) {
					$scope.users = res.data;
					console.dir(users);
				}, onError);
		}

		getUsers();
	});*/