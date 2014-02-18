require.config({
	waitSeconds : 120,
    paths: {
      jquery: 'libs/jquery/2.1.0/jquery-2.1.0.min',
      jqueryMobile: 'libs/jquery-mobile/1.4.0/jquery.mobile-1.4.0.min',
      angular: 'libs/angularjs/1.2.11/angular.min',
      angularResource: 'libs/angularjs/1.2.11/angular-resource.min',
      angularRoute: 'libs/angularjs/1.2.11/angular-route.min',
      underscore: 'libs/underscore/1.5.2/underscore-min',
      services: 'services',
      controllers: 'controllers'
    }
});

require([
    'jquery',
    'jqueryMobile',
    'angular',
    'angularResource',
    'angularRoute',
    'underscore',
    'services',
    'controllers'
], function($){


});