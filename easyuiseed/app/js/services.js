define([
  'jquery',
  'jqueryMobile',
  'angular',
  'angularResource',
  'angularRoute',
  'underscore',
  'services',
  'controllers'
], function( $ ) {

var module1 = angular.module('TodoService', ['ngResource']).factory('Todo', ['$resource', function ($resource) {
    var Todo = $resource('/api/todo/:todoId', {}, {
        update: { method: 'PUT'}
    });
    return Todo;
}]);

});