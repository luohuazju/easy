// Filename: router.js
define([
  'jquery',
  'underscore',
  'backbone',
  'view/AboutView',
  'view/HomeView',
  'view/MapView'
], function($, _, Backbone, AboutView, HomeView, MapView) {
  
  var AppRouter = Backbone.Router.extend({
    routes: {
      // Define some URL routes  
      'home'  : 'showHome',
      'map'   : 'showMap',
      'about' : 'showAbout'
    }
  });
  
  var initialize = function(){
    var app_router = new AppRouter;
    
    app_router.on('route:showHome', function(){
    	console.log("Entering the showHome Page!");
    	new HomeView().render();
    });

    app_router.on('route:showMap', function(){
    	console.log("Entering the showMap Page!");
    	new MapView().render();
    });
    
    app_router.on('route:showAbout', function(){
    	console.log("Entering the showAbout Page!");
    	new AboutView().render();
    });

    Backbone.history.start();
  };
  return { 
    initialize: initialize
  };
});
