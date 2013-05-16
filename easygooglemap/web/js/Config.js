define([
  'jquery'
], function($) {
	function Config(){
		this.logLevel = 'all'; //info, debug, all
		this.logEnable = 1;
	}
	
	Config.prototype.setLogLevel = function(logLevel){
		this.logLevel = logLevel;
	};
	
	Config.prototype.getLogLevel = function(){
		return this.logLevel;
	};
	
	Config.prototype.setLogEnable = function(logEnable){
		this.logEnable = logEnalbe;
	};
	
	Config.prototype.getLogEnable = function(){
		return this.logEnable;
	};
	
	return Config;
});