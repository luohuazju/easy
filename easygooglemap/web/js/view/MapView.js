define([
  'jquery',
  'underscore',
  'backbone',
  'text!template/map/StateMapTemplate.html',
  'common/googlemap/MapBackBone'
], function($, _, Backbone, htmlTemplate, MapBackBone) {

  var MapView = Backbone.View.extend({
    el: $("#content"),
    
    render: function(){
      window.logger.debug("I am going to the controller, render to stateMapTemplate");
      this.$el.html(htmlTemplate);
      jQuery(document).ready(function($) {
    	  (new MapBackBone()).initialize();
      });
    }
  
  });

  return MapView;
  
});
