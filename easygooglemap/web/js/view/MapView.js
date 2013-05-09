define([
  'jquery',
  'underscore',
  'backbone',
  'text!template/map/StateMapTemplate.html',
  'async!http://maps.google.com/maps/api/js?client=gme-digby&sensor=false&v=3'
], function($, _, Backbone, htmlTemplate) {

  var MapView = Backbone.View.extend({
    el: $("#content"),
    
    render: function(){
      console.log("I am going to the controller, render to stateMapTemplate");
      this.$el.html(htmlTemplate);
    }
  
  });

  return MapView;
  
});
