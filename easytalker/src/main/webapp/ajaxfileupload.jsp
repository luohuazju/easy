<html>
	<head>
		<title>AjaxFileUpload Demo</title>
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
		<script type="text/javascript" src="./resources/component/ajaxfileupload/2.1/ajaxfileupload.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	$("#filemaps").change(function(){
		var file_upl = document.getElementById('filemaps'); 
		var realpath = getPath(file_upl);
		$("#fileurl").val(realpath);
	});
});

function userBrowser(){
    var browserName=navigator.userAgent.toLowerCase();
    if(/msie/i.test(browserName) && !/opera/.test(browserName)){
        return "IE";
    }else if(/firefox/i.test(browserName)){
        return "Firefox";
    }else if(/chrome/i.test(browserName) && /webkit/i.test(browserName) && /mozilla/i.test(browserName)){
        return "Chrome";
    }else if(/opera/i.test(browserName)){
        return "Opera";
    }else if(/webkit/i.test(browserName) &&!(/chrome/i.test(browserName) && /webkit/i.test(browserName) && /mozilla/i.test(browserName))){
        return "Safari";
    }else{
        return "unKnow";
    }
}

function getPathIE(obj){
	obj.select();
	return document.selection.createRange().text;
}

function getPathFFSecurity(obj){
	try { 
	    netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect"); 
	}catch (e) { 
	    alert('Unable to access local files due to browser security settings. To overcome this, follow these steps: (1) Enter "about:config" in the URL field; (2) Right click and select New->Boolean; (3) Enter "signed.applets.codebase_principal_support" (without the quotes) as a new preference name; (4) Click OK and try loading the file again.'); 
	   	if(obj.files){
       		return obj.files.item(0).name;
      	}
	 	return; 
	    }
	return obj.value;
}

function extractFilename(path) {
	  if (path.substr(0, 12) == "C:\\fakepath\\")
	    return path.substr(12); // modern browser
	  var x;
	  x = path.lastIndexOf('/');
	  if (x >= 0) // Unix-based path
	    return path.substr(x+1);
	  x = path.lastIndexOf('\\');
	  if (x >= 0) // Windows-based path
	    return path.substr(x+1);
	  return path; // just the filename
}

function getPath(obj){
  	if(obj){
    	//if (window.navigator.userAgent.indexOf("MSIE")>=1){
    	if(userBrowser() == 'IE'){
       		return getPathIE(obj);
     	//}else if(window.navigator.userAgent.indexOf("Firefox")>=1){
     	}else if(userBrowser() == 'Firefox'){
     		return getPathFFSecurity(obj);
     	}else if(userBrowser() == 'Chrome'){
     		return extractFilename(obj.value);
     	}
    	return obj.value;
   	}
}

function ajaxFileUpload(){
			$.ajaxFileUpload(
            {
                url:'fileupload.do',           
                secureuri:false,
                fileElementId:'filemaps',                 
                dataType: 'json',                           
                success: function (data, status)           
                {      
                	console.info(data);
                    $('#result').html('Success to Add');
                },
                error: function (data, status, e)          
                {
                    $('#result').html('Fail to Add');
                }
            }                   
		  );
}
</script>

	</head>
<body>

<h2>AjaxFileUpload Demo</h2>

<form method="post" action="fileupload.do" enctype="multipart/form-data">  
	<input id="fileurl" type="text" class="langtext" readonly="readonly"/>
	<input type="file" id="filemaps" name="filemaps" value="upload"/> 
    <input type="button" value="Submit" onclick="ajaxFileUpload()"/>
</form> 

<div id="result"></div>

</body>
</html>
