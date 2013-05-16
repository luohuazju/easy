// Require.js allows us to configure shortcut alias
require.config({
  waitSeconds : 120, //make sure it is enough to load all gmaps scripts
  shim: {
	    underscore: {
	      exports: '_'
	    },
	    backbone: {
	      deps: ['underscore', 'jquery'],
	      exports: 'Backbone'
	    }
  },
  paths: {
    jquery: '//cdnjs.cloudflare.com/ajax/libs/jquery/1.8.2/jquery.min',
    underscore: '//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.4.1/underscore-min',
    backbone: '//cdnjs.cloudflare.com/ajax/libs/backbone.js/0.9.2/backbone-min',
    text: '//cdnjs.cloudflare.com/ajax/libs/require-text/2.0.5/text',
    async: 'lib/require/async',
    log4javascript: '//cdnjs.cloudflare.com/ajax/libs/log4javascript/1.4.3/log4javascript',
    json2: '//cdnjs.cloudflare.com/ajax/libs/json2/20121008/json2',
    log4javascript_custom: 'lib/log4javascript/log4javascript_custom'
  }
});

require([
  'Router',
  'Config',
  'Log4j',
  'json2'
], function(Router, Config, Log4j){
	
	new Log4j().init();
	
	var IndexPageView = Backbone.View.extend({
		el: "#content",
		router: null,
		
		initialize: function(){
			window.logger.debug("initialize the IndexPageView and whole system.", "Good Luck.");
			window.logger.info("Config Start==================================");
			window.logger.info(JSON.stringify(new Config()));
			window.logger.info("Config End====================================");
			this.router = new Router();
		}
	});
	
	new IndexPageView();
	
});
