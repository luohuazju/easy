var express = require('express');
var bodyParser = require('body-parser');  

var app = express();
app.use(bodyParser());
app.use(bodyParser.json({ type: 'application/vnd.api+json' }))

var bug_module = require('./bug/route');

app.use('/bugs', bug_module);
 
app.set('port', process.env.PORT || 3001);
app.listen(app.get('port'));
console.log('Running nodeJS on port ' + app.get('port') + ' !');