'use strict';
angular.module('leanbitSupportTicketSystemApp')
	.factory('workitemDb', function ($http) {
		var serviceUrl = 'http://localhost:8080/ticketsystem/workitem';
		$http.defaults.headers.common['X-Requested-With'];
		return {
			getAll: function () {
				return $http.get(serviceUrl + '/getall');
			},
			add: function (workitem) {
				return $http.post(serviceUrl, workitem);
			},
			remove: function (workitemName) {
				return $http.delete(serviceUrl + '/' + workitemName);
			},
			update: function (workitem) {
				return $http.put(serviceUrl, workitem);
			},
			addToUser: function (workitem, userID) {
				return $http.put(serviceUrl + '/addtouser/' + userID, workitem);
			}
		};
	});