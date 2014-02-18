'use strict';

var lrSnippet = require('grunt-contrib-livereload/lib/utils').livereloadSnippet;

var mountFolder = function (connect, dir) {
	  return connect.static(require('path').resolve(dir));
};

module.exports = function (grunt) {
	  // load all grunt tasks
	require('matchdep').filterDev('grunt-*').forEach(grunt.loadNpmTasks);
	
	grunt.initConfig({
	    blenderbox: {
	        app: 'app',
	        dist: 'dist',
	        test: 'test'
	    },
	    
	    watch: {
	      livereload: {
	        files: [
	          '<%= blenderbox.app %>/*.html',
	          '{<%= blenderbox.app %>}/css/{,*/}*.css',
	          '{<%= blenderbox.app %>}/js/{,*/}*.js',
	          '<%= blenderbox.app %>/img/{,*/}*.{png,jpg,jpeg,webp}',
	          '{<%= blenderbox.app %>}/templates/{,*/}*.html'
	        ],
	        tasks: ['livereload']
	      }
	    },
	      
	    connect: {
	        options: {
	          port: 9000,
	          hostname: '0.0.0.0'
	        },
	        livereload: {
	          options: {
	            middleware: function (connect) {
	              return [
	                lrSnippet, 
	                mountFolder(connect, 'app')
	              ];
	            }
	          }
	        },
	        dist: {
	          options: {
	            middleware: function (connect) {
	              return [
	                mountFolder(connect, 'dist')
	              ];
	            }
	          }
	        }
	    },
	    
	    clean: {
	      dist: ['<%= blenderbox.dist %>/*']
	    },

    });
	
	grunt.renameTask('regarde', 'watch');
	
    grunt.registerTask('server', function (target) {

	    grunt.task.run([
	       'livereload-start',
	       'connect:livereload',
	       'watch'
	    ]);
	});
	
	grunt.registerTask('default', 'server');
};
