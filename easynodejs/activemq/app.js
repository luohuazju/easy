var stompit = require('stompit');

var connectOptions = {
  'host': 'localhost',
  //'host': '52.8.216.163',
  'port': 61613,
  'timeout': 6000,
  'connectHeaders':{
    'host': '/',
    //'login': 'username',
    //'passcode': 'password',
    'heart-beat': '5000,5000'
  }
};

stompit.connect(connectOptions, function(error, client) {

  if (error) {
    console.log('connect error ' + error.message);
    return;
  }
  
  var sendHeaders = {
    'destination': '/queue/dwh_datawarehouse',
    'content-type': 'text/plain'
  };
  
  var frame = client.send(sendHeaders);
  frame.write('hello from nodejs' + new Date());
  frame.end();
  client.disconnect();
});