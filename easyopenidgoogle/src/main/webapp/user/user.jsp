<html>
	<head>
		<title>User Demo</title>
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
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
	    $("#resultLog").html("Result: " + data.personName + " status:" + status);
	}
	
	});
	
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
