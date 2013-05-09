// Require.js allows us to configure shortcut alias
require.config({
  waitSeconds : 120, //make sure it is enough to load all gmaps scripts
  paths: {
    jquery: 'lib/jquery/jquery',
    underscore: 'lib/underscore-min',
    backbone: 'lib/backbone-min',
    text: '//cdnjs.cloudflare.com/ajax/libs/require-text/2.0.5/text',
    async: 'lib/require/async'
  }
});

require([
  'App',
], function(App){
  App.initialize();
});
