'use strict';
angular.module('leanbitSupportTicketSystemApp')
	.factory('usersDb', function ($http) {
		var serviceUrl = 'http://localhost:8080/ticketsystem/user';
		return {
			getAll: function () {
				return $http.get(serviceUrl + '/team/leanbit');
			},
			update: function (user) {
				return $http.put(serviceUrl, user);
			},
			login: function (userName, password) {
				return $http.get(serviceUrl + '/login/' + userName + '/' + password);
			},
			logout: function (token) {
				return $http.delete(serviceUrl + '/logout/'+token);
			}

		};
	});