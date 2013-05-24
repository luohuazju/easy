define([
  'jquery',
  'underscore',
  'backbone',
  'text!template/location/LocationTemplate.html'
], function($, _, Backbone, htmlTemplate) {

  var user = {
	"name": "sillycat",
  	"age": 32,
  	"type" : "working"
  };
	
  var LocationView = Backbone.View.extend({
    el: $("#content"),
    
    render: function(){
      window.logger.debug("I am going to hit the Location Page.");
      var template = _.template(htmlTemplate, { user: user });
      this.$el.html(template);
    }
  
  });

  return LocationView;
  
});
