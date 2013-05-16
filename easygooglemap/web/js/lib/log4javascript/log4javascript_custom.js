define([ 
 'log4javascript' 
 ],function() {
	log4javascript.PatternLayout.prototype.format = function(loggingEvent) {
		var regex = /%(-?[0-9]+)?(\.?[0-9]+)?([acdflmMnpr%])(\{([^\}]+)\})?|([^%]+)/;
		var formattedString = "";
		var result;
		var searchString = this.pattern;
 
		// Cannot use regex global flag since it doesn't work with exec in IE5
		while ((result = regex.exec(searchString))) {
			var matchedString = result[0];
			var padding = result[1];
			var truncation = result[2];
			var conversionCharacter = result[3];
			var specifier = result[5];
			var text = result[6];
 
			// Check if the pattern matched was just normal text
			if (text) {
				formattedString += "" + text;
			} else {
				// Create a raw replacement string based on the conversion
				// character and specifier
				var replacement = "";
				switch(conversionCharacter) {
//BEGIN -----------------------Additional Option to output the calling file name and line number of the logging call.
					case "l": //Location						
						var isChrome = navigator.userAgent.indexOf("Chrome") !== -1;
						if(isChrome){
							//do someting else
							var stack = new Error().stack;
							var lineAccessingLogger = stack.split("\n")[8];
							
							var funcBegin = lineAccessingLogger.indexOf("at ") + 3;
							var resourceBegin = lineAccessingLogger.indexOf(" (") + 2;
							
							
							var functionName = funcBegin < resourceBegin ? lineAccessingLogger.substring(funcBegin,resourceBegin-2) : null;
							
							var resourceLoc;
							if(functionName){
								resourceLoc = lineAccessingLogger.substring(resourceBegin,lineAccessingLogger.length-1);								
							}else{
								functionName = "(anonymous)";
								resourceLoc = lineAccessingLogger.substring(funcBegin);
							}
							
							var colIdx = resourceLoc.lastIndexOf(":");
							var column = parseInt(resourceLoc.substring(colIdx+1),10);
							var lineIdx = resourceLoc.lastIndexOf(":",colIdx-1);
							var line = parseInt(resourceLoc.substring(lineIdx+1,colIdx),10);
							
							var resource = resourceLoc.substring(0,lineIdx);
							var lastSegmentIdx = resource.lastIndexOf("/");
							
							var lastSegment = resource.substring(lastSegmentIdx+1);							
							
							/*
							 var resultObject = {
									r : resource,
									l : line,
									c : column,
									f : functionName,
									s : lastSegment
							};
							*/							
 
							var spec = "s:l";
							if(specifier)spec = specifier;
							
							var specresult = [];
							var priorNum = "";
							for ( var int = 0; int < spec.length; int++) {
								var l = spec[int];
								var num = parseInt(l,10);
								if(num > -1 ){
									priorNum += l;
									continue;
								}else{
									if(priorNum.length >0){
										specresult.push(parseInt(priorNum,10));
										priorNum = "";
									}
									specresult.push(l);
								}
							}							
							if(priorNum.length >0)
								specresult.push(parseInt(priorNum,10));
							spec = specresult;						
							
							for ( var int = 0; int < spec.length; int++) {
								var optNum = spec[int+1];								
								switch(spec[int]){
								case "s":
									replacement += lastSegment;
									break;
								case "r":
									var string = resource;
									if(typeof optNum === "number"){
										string = string.substring(string.length-optNum);
										spec.splice(int+1,1);
									}
									replacement += string;
									break;									
								case "l":
									replacement += line;
									break;
								case "c":
									replacement += column;
									break;
								case "f":									
									var string = functionName;
									if(typeof optNum === "number"){
										string = string.substring(string.length-optNum);
										spec.splice(int+1,1);
									}
									replacement += string;
									break;
									break;
								default:									
									replacement += spec[int];
								};								
							}
						}else{
							throw "can only use this method on google chrome";
						}						
						break;
//END -----------------------Additional Option to output the calling file name and line number of the logging call.						
					case "a": // Array of messages
					case "m": // Message
						var depth = 0;
						if (specifier) {
							depth = parseInt(specifier, 10);
							if (isNaN(depth)) {
								handleError("PatternLayout.format: invalid specifier '" +
									specifier + "' for conversion character '" + conversionCharacter +
									"' - should be a number");
								depth = 0;
							}
						}
						var messages = (conversionCharacter === "a") ? loggingEvent.messages[0] : loggingEvent.messages;
						for (var i = 0, len = messages.length; i < len; i++) {
							if (i > 0 && (replacement.charAt(replacement.length - 1) !== " ")) {
								replacement += " ";
							}
							if (depth === 0) {
								replacement += messages[i];
							} else {
								replacement += formatObjectExpansion(messages[i], depth);
							}
						}
						break;
					case "c": // Logger name
						var loggerName = loggingEvent.logger.name;
						if (specifier) {
							var precision = parseInt(specifier, 10);
							var loggerNameBits = loggingEvent.logger.name.split(".");
							if (precision >= loggerNameBits.length) {
								replacement = loggerName;
							} else {
								replacement = loggerNameBits.slice(loggerNameBits.length - precision).join(".");
							}
						} else {
							replacement = loggerName;
						}
						break;
					case "d": // Date
						var dateFormat = log4javascript.PatternLayout.ISO8601_DATEFORMAT;
						if (specifier) {
							dateFormat = specifier;
							// Pick up special cases
							if (dateFormat == "ISO8601") {
								dateFormat = log4javascript.PatternLayout.ISO8601_DATEFORMAT;
							} else if (dateFormat == "ABSOLUTE") {
								dateFormat = log4javascript.PatternLayout.ABSOLUTETIME_DATEFORMAT;
							} else if (dateFormat == "DATE") {
								dateFormat = log4javascript.PatternLayout.DATETIME_DATEFORMAT;
							}
						}
						// Format the date
						replacement = (new log4javascript.SimpleDateFormat(dateFormat)).format(loggingEvent.timeStamp);
						break;
					case "f": // Custom field
						if (this.hasCustomFields()) {
							var fieldIndex = 0;
							if (specifier) {
								fieldIndex = parseInt(specifier, 10);
								if (isNaN(fieldIndex)) {
									handleError("PatternLayout.format: invalid specifier '" +
										specifier + "' for conversion character 'f' - should be a number");
								} else if (fieldIndex === 0) {
									handleError("PatternLayout.format: invalid specifier '" +
										specifier + "' for conversion character 'f' - must be greater than zero");
								} else if (fieldIndex > this.customFields.length) {
									handleError("PatternLayout.format: invalid specifier '" +
										specifier + "' for conversion character 'f' - there aren't that many custom fields");
								} else {
									fieldIndex = fieldIndex - 1;
								}
							}
							replacement = this.customFields[fieldIndex].value;
						}
						break;
					case "n": // New line
						replacement = "\r\n";
						break;
					case "p": // Level
						replacement = loggingEvent.level.name;
						break;
					case "r": // Milliseconds since log4javascript startup
						replacement = "" + loggingEvent.timeStamp.getDifference(applicationStartDate);
						break;
					case "%": // Literal % sign
						replacement = "%";
						break;
					default:
						replacement = matchedString;
						break;
				}
				// Format the replacement according to any padding or
				// truncation specified
				var l;
 
				// First, truncation
				if (truncation) {
					l = parseInt(truncation.substr(1), 10);
					var strLen = replacement.length;
					if (l < strLen) {
						replacement = replacement.substring(strLen - l, strLen);
					}
				}
				// Next, padding
				if (padding) {
					if (padding.charAt(0) == "-") {
						l = parseInt(padding.substr(1), 10);
						// Right pad with spaces
						while (replacement.length < l) {
							replacement += " ";
						}
					} else {
						l = parseInt(padding, 10);
						// Left pad with spaces
						while (replacement.length < l) {
							replacement = " " + replacement;
						}
					}
				}
				formattedString += replacement;
			}
			searchString = searchString.substr(result.index + result[0].length);
		}
		return formattedString;
	};
	
	function formatObjectExpansion(obj, depth, indentation) {
		var objectsExpanded = [];

		function doFormat(obj, depth, indentation) {
			var i, j, len, childDepth, childIndentation, childLines, expansion,
				childExpansion;

			if (!indentation) {
				indentation = "";
			}

			function formatString(text) {
				var lines = splitIntoLines(text);
				for (var j = 1, jLen = lines.length; j < jLen; j++) {
					lines[j] = indentation + lines[j];
				}
				return lines.join(newLine);
			}

			if (obj === null) {
				return "null";
			} else if (typeof obj == "undefined") {
				return "undefined";
			} else if (typeof obj == "string") {
				return formatString(obj);
			} else if (typeof obj == "object" && array_contains(objectsExpanded, obj)) {
				try {
					expansion = toStr(obj);
				} catch (ex) {
					expansion = "Error formatting property. Details: " + getExceptionStringRep(ex);
				}
				return expansion + " [already expanded]";
			} else if ((obj instanceof Array) && depth > 0) {
				objectsExpanded.push(obj);
				expansion = "[" + newLine;
				childDepth = depth - 1;
				childIndentation = indentation + "  ";
				childLines = [];
				for (i = 0, len = obj.length; i < len; i++) {
					try {
						childExpansion = doFormat(obj[i], childDepth, childIndentation);
						childLines.push(childIndentation + childExpansion);
					} catch (ex) {
						childLines.push(childIndentation + "Error formatting array member. Details: " +
							getExceptionStringRep(ex) + "");
					}
				}
				expansion += childLines.join("," + newLine) + newLine + indentation + "]";
				return expansion;
            } else if (Object.prototype.toString.call(obj) == "[object Date]") {
                return obj.toString();
			} else if (typeof obj == "object" && depth > 0) {
				objectsExpanded.push(obj);
				expansion = "{" + newLine;
				childDepth = depth - 1;
				childIndentation = indentation + "  ";
				childLines = [];
				for (i in obj) {
					try {
						childExpansion = doFormat(obj[i], childDepth, childIndentation);
						childLines.push(childIndentation + i + ": " + childExpansion);
					} catch (ex) {
						childLines.push(childIndentation + i + ": Error formatting property. Details: " +
							getExceptionStringRep(ex));
					}
				}
				expansion += childLines.join("," + newLine) + newLine + indentation + "}";
				return expansion;
			} else {
				return formatString(toStr(obj));
			}
		}
		return doFormat(obj, depth, indentation);
	}
});