"use strict";

var _ = require('underscore');

_.merge = function(dest) {

    // Get the objects to merge
    var objs = Array.prototype.slice.call(arguments, 1);

    // Loop through each object
    for (var num in objs) {
        var src = objs[num];

        if (src) {
            // Loop through each key in the object
            for (var key in src) {
                var value = src[key];

                // Check if we should overwrite the entire value, or recurse into the objects
                if (dest[key] && dest[key] instanceof Object && value instanceof Object && !(dest[key] instanceof Array)) {
                    dest[key] = this.merge(dest[key], value);
                } else {
                    dest[key] = value;
                }
            }
        }
    }

    return dest;
}

module.exports = _;