var exec = require("child_process").exec;
var querystring = require("querystring");
var fs = require("fs");
var formidable = require("formidable");

function start(response) {
  console.log("Request handler 'start' was called.");
  
  var body = '<html>'+
    '<head>'+
    '<meta http-equiv="Content-Type" content="text/html; '+
    'charset=UTF-8" />'+
    '</head>'+
    '<body>'+
    '<form action="/upload" method="post" enctype="multipart/form-data">'+
    '<input type="text" name="title" value="hello" /> <br/>'+
    '<input type="file" name="upload" multiple="multiple">' + 
    '<input type="submit" value="Upload File" />'+
    '</form>'+
    '</body>'+
    '</html>';

    response.writeHead(200, {"Content-Type": "text/html"});
    response.write(body);
    response.end();
}

function upload(response, request) {
  console.log("Request handler 'upload' was called.");

  var form = new formidable.IncomingForm();
  console.log("about to parse");
  form.parse(request, function(error, fields, files){  
     console.log("parsing done");     

     /* Possible error on windows system:
        tried to rename to an already existing file */
     fs.rename(files.upload.path, "/tmp/test.png", function(err) {
       if(err){
         fs.unlink("/tmp/test.png");
         fs.rename(files.upload.path, "/tmp/test.png");
       }
     });
     response.writeHead(200, {"Content-Type": "text/html"});
     response.write("received image:<br />");
     response.write(fields.title + "<br />");
     response.write("<img src='/show' />");
     response.end();
  
  });
}

function show(response){
  console.log("Request handler 'show' was called.");
  fs.readFile("/tmp/test.png", "binary", function(error, file){
     if(error){
        response.writeHead(500, {"Content-Type": "text/plain"});
        response.write(error + "\n");
        response.end();
     }else {
        response.writeHead(200, {"Content-Type": "image/png"});
        response.write(file,"binary");
        response.end();
     }
  });

}

exports.start = start;
exports.upload = upload;
exports.show = show;
