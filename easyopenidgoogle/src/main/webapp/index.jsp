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
	<a href="###" id="google_logout" onclick="logout()">Logout Google</a> <br />
	<iframe id="logout_iframe" style="display:none;"> </iframe>
	
	<script type="text/javascript">
		function logout() {
			iframe = document.getElementById('logout_iframe');
			iframe.src = 'https://www.google.com/accounts/Logout';
			if (iframe.attachEvent) {
				iframe.attachEvent("onload", function() {
					document.getElementById('application_logout').click();
				});
			} else {
				iframe.onload = function() {
					document.getElementById('application_logout').click();
				};
			}
		}
	</script>

</body>
</html>
