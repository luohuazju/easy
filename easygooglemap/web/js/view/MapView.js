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
      console.log("I am going to the controller, render to stateMapTemplate");
      this.$el.html(htmlTemplate);
      MapBackBone.initialize();
    }
  
  });

  return MapView;
  
});
