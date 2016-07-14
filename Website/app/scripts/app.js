'use strict';

/**
 * @ngdoc overview
 * @name leanbitSupportTicketSystemApp
 * @description
 * # leanbitSupportTicketSystemApp
 *
 * Main module of the application.
 */
angular
  .module('leanbitSupportTicketSystemApp', [
    'ngAnimate',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'ui.sortable'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/main', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .when('/login', {
        templateUrl: 'views/login.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .when('/newitem', {
        templateUrl: 'views/newitem.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .otherwise({
        redirectTo: '/login'
      });
  });