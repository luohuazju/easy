'use strict';

/**
 * @ngdoc function
 * @name yosampleApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the yosampleApp
 */
angular.module('yosampleApp')
  .controller('MainCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
