<html>
	<head>
		<title>User Demo</title>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script src="../resources/components/json/json2.js"></script>
<script type="text/javascript">

$(function() {
	
	$("#GetAjax").click(function() {
	    var theId = $.trim($("#theId").val());
	    $.ajax({
	          type: "GET",
	          url: "../service/person/" + theId,
	          contentType: "application/json",
	          cache: false,
	          success: onSuccess
	    });
	});
	
	function onSuccess(data,status)
	{
		if (HasErrors(data)){ 
			return; 
		}
	    $("#resultLog").html("Result: " + data.personName + " status:" + status);
	}
	
	});
	
	function HasErrors(data) { 
		var data_str = JSON.stringify(data);
		alert(data_str);
		// check for redirect to login page 
		if (data_str.search(/j_spring_security_check/i) != -1) { 
			top.location.href = './openidlogin.jsp'; 
		    return true; 
		} 
		// check for IIS error page 
		if (data_str.search(/Internal Server Error/) != -1) { 
		    alert('Server Error.'); 
		    return true; 
		} 
		// check for our custom error handling page 
		if (data_str.search(/Error.jsp/) != -1) { 
		    alert('An error occurred on the server. The Technical Support Team has been provided with the error details.'); 
		    return true; 
		} 
		return false; 
	} 

	
</script>	
	</head>
<body>
<h2>Hello User!</h2><br />
<p>Your principal object is....: <%= request.getUserPrincipal() %></p><br />
<br />
<h1>AJAX DEMO</h1><br />
<input type="text" id="theId" name="theId" value="1" /><br />
<button id="GetAjax">GetAjax</button><br />
<div id="resultLog">test</div>
<br />
<a href="../index.jsp">Home</a>
<br />



</body>
</html>
