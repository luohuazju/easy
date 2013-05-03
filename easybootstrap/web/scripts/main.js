require([ "jquery", "jquery.plugin1", "jquery.plugin2" ], function($) {
	// the jquery.alpha.js and jquery.beta.js plugins have been loaded.
	$(function() {
		$('body').sayhello1().sayhello2();
	});
});