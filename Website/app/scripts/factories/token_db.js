'use strict';
angular.module('leanbitSupportTicketSystemApp')
	.factory('tokenDb', function ($http) {
		var serviceUrl = 'http://localhost:8080/ticketsystem/token';
		return {
			checkToken: function (token) {
				return $http.get(serviceUrl + '/' + token);
			}
		};
	});