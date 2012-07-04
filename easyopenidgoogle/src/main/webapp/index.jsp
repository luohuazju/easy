<html>
<body>
	<h2>Hello World!</h2>
	<br />
	<p>
		Your principal object is....:
		<%=request.getUserPrincipal()%></p>
	<br />
	<a href="user/user.jsp">user page</a>
	<br />
	<a href="super/super.jsp">super page</a>
	<br />
	<a href="admin/admin.jsp">admin page</a>
	<br />
	<br />
	<a href="./j_spring_security_logout" id="application_logout">Logout Application</a> <br />
	<form action="./j_spring_security_logout" name="logoutForm" id="logoutFormId" method="post" style="display:none;">
	</form>
	<a href="#" id="google_logout" onclick="logout()">Logout Google</a> <br />
	<iframe id="logout_iframe" style="display:none;"> </iframe>
	
	<script type="text/javascript">
		function logout() {
			//setTimeout("logoutApplication()",2000);
			iframe = document.getElementById('logout_iframe');
			iframe.src = 'https://www.google.com/accounts/Logout';
			if (iframe.attachEvent) {
				iframe.attachEvent("onload", function() {
					logoutApplication();
				});
			} else {
				iframe.onload = function() {
					logoutApplication();
				};
			}
		}
		
		function logoutApplication(){
			//alert("1");
			//var app_logout_button = document.getElementById('application_logout');
			//alert(app_logout_button);
			//if(app_logout_button != null){
			//	app_logout_button.click();
			//}
			//alert("asdfasdf");
			document.getElementById("logoutFormId").submit();
		}
	</script>

</body>
</html>
