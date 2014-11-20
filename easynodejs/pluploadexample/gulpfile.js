/*jslint node: true */
'use strict';

//gulp &  plugins
var gulp = require('gulp');
var jshint = require('gulp-jshint');
var mocha = require('gulp-mocha');
var nodemon = require('gulp-nodemon');
var gutil = require('gulp-util');
 
gulp.task('default', ['lint', 'test', 'serve', 'watch']);

 
var paths = {
  main    : 'app.js',
  tests   : 'test/**/*.js',
  sources : [ '**/*.js', '!node_modules/**']
};
 
// lint all of our js source files
gulp.task('lint', function (){
  return gulp.src(paths.sources)
    .pipe(jshint())
    .pipe(jshint.reporter('default'));
});
 
// run mocha tests
gulp.task('test', function (){
  return gulp.src(paths.tests, {read: false})
  .pipe(mocha({reporter: 'list'}))
  .on('error', gutil.log);
});
 
//run app using nodemon
gulp.task('serve', [], function(){
  return nodemon({
    script: 'app.js',
    watch   : paths.sources
  });
});
 
// will watch client files and rebuild on change
gulp.task('watch', function(){
  gulp.watch(paths.sources, ['lint']);
});

 


