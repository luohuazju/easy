"use strict";

var express = require('express');
var bodyParser = require('body-parser');  
var log4js = require('log4js');

var app = express();
app.use(bodyParser());
//app.use(bodyParser.json({ type: 'application/vnd.api+json' }))
app.use(bodyParser.json());

log4js.configure(__dirname +'/config/log/log4js.json');

var bug_module = require('./bug/route');
var logger = log4js.getLogger("root");

app.use(log4js.connectLogger(logger));
app.use('/bugs', bug_module);
 
app.set('port', process.env.PORT || 3001);
app.listen(app.get('port'));

logger.debug("Running nodeJS on port" + app.get("port") + " !");

