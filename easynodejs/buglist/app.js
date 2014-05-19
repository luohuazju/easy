var express = require('express');
var bodyParser = require('body-parser');  

var app = express();
app.use(bodyParser());
app.use(bodyParser.json({ type: 'application/vnd.api+json' }))

var bug_module = require('./bug/route');

app.use('/bugs', bug_module);
 
app.listen(3001);
console.log('Running nodeJS on port 3001!');