//require([ "jquery", "jquery.plugin2", "jquery.plugin1" ], function($) {

var t1 = "asdfasdf";

$.fn.sayhello1 = function() {
    return this.append('<p>Sillycat, go and go!</p>' + t1);
};

//});