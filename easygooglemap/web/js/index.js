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
    async: 'lib/require/async'
  }
});

require([
  'App',
], function(App){
  App.initialize();
});
