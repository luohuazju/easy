'use strict';

/**
 * @ngdoc function
 * @name yosampleApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the yosampleApp
 */
angular.module('yosampleApp')
  .controller('AboutCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
