// Filename: router.js
define([
  'jquery',
  'underscore',
  'backbone',
  'view/AboutView',
  'view/HomeView',
  'view/MapView'
], function($, _, Backbone, AboutView, HomeView, MapView) {
  
  var Router = Backbone.Router.extend({
    routes: {
      // Define some URL routes  
      'home'  : 'showHome',
      'map'   : 'showMap',
      'about' : 'showAbout'
    },
  
    initialize: function(){
       window.logger.debug("init the router, and make backbone start.");
       Backbone.history.start();
    },
    
    showHome: function(){
    	window.logger.debug("Entering the showHome Page!");
      	new HomeView().render();
    },
    
    showMap: function(){
    	window.logger.debug("Entering the showMap Page!");
    	new MapView().render();
    },
    
    showAbout: function(){
    	window.logger.debug("Entering the showAbout Page!");
    	new AboutView().render();
    }
  });
  
  return Router;
});
