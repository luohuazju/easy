define([
  'jquery',
  'Config',
  'log4javascript',
  'log4javascript_custom'
], function($, Config) {
	function Log4j(){
	}
	
	Log4j.prototype.init = function(){
		var config = new Config();
		
		if(config.getLogEnable() == 1){
			log4javascript.setEnabled(true);
		}else{
			log4javascript.setEnabled(false);
		}
		
		var logger = log4javascript.getLogger(); 
		
		if(config.getLogLevel() == 'debug'){
			logger.setLevel(log4javascript.Level.DEBUG);
		}else if(config.getLogLevel() == 'all'){
			logger.setLevel(log4javascript.Level.ALL);
		}else if(config.getLogLevel() == 'info'){
			logger.setLevel(log4javascript.Level.INFO);
		}else if(config.getLogLevel() == 'error'){
			logger.setLevel(log4javascript.Level.ERROR);
		}
		
		var consoleAppender = new log4javascript.BrowserConsoleAppender();
		
		//%d{HH:mm:ss,SSS} %l{s:l} %-5p - %m{1}%n
		//%d{HH:mm:ss} %-5p - %m%n
		var simpleLayout = new log4javascript.PatternLayout("%d{HH:mm:ss,SSS} %l{s:l} %-5p - %m  %n ");  
		consoleAppender.setLayout(simpleLayout); 
		
		logger.addAppender(consoleAppender);
		
		window.logger = logger;
	};
	
	
	
	return Log4j;
	
});