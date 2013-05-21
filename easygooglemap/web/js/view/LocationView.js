define([
  'jquery',
  'underscore',
  'backbone',
  'text!template/location/LocationTemplate.html'
], function($, _, Backbone, htmlTemplate) {

  var LocationView = Backbone.View.extend({
    el: $("#content"),
    
    render: function(){
      window.logger.debug("I am going to hit the Location Page.");
      this.$el.html(htmlTemplate);
    }
  
  });

  return LocationView;
  
});
