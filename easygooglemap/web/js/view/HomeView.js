define([
  'jquery',
  'underscore',
  'backbone',
  'text!template/home/HomeTemplate.html'
], function($, _, Backbone, htmlTemplate) {

  var HomeView = Backbone.View.extend({
    el: $("#content"),
    
    render: function(){
      console.log("I am going to render to HomeView");
      this.$el.html(htmlTemplate);
    }
  
  });

  return HomeView;
  
});
