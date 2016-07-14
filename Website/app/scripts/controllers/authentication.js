angular.module('leanbitSupportTicketSystemApp')
	.controller('LoginCtrl', function ($scope, usersDb, $location) {
		function onError(res) {
			alert('Username or password!');
			console.log($scope.token);
			console.log('Error', res);
		}

		$scope.userName;
		$scope.password;

		$scope.login = function () {
			usersDb.login($scope.userName, $scope.password)
				.then(function (res) {
					$scope.token = res.data;
					console.log(res);
					window.localStorage['token'] = $scope.token;
					$location.path('/main');
					$location.replace();
				}, onError);;
		};
		$scope.logout = function () {
			usersDb.logout(window.localStorage['token']);
			window.localStorage['token'] = "";
			$location.path('/login');
			$location.replace();
		}
	});