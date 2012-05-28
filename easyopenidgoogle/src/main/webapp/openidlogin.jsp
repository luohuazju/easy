<html>
  <head>
    <title>Open ID Login</title>
  </head>

  <body>
  	<br />
  	<p>Your principal object is....: <%= request.getUserPrincipal() %></p><br />
    <h3>Please Enter Your OpenID Identity</h3>
    <form name="f1" action="j_spring_openid_security_check" method="POST">
      <table>
        <tr>
        	<td>OpenID Identity:</td>
        	<td><input type='text' name='openid_identifier' value='https://www.google.com/accounts/o8/id'/></td></tr>
        <tr><td colspan='2'><input name="submit" type="submit"></td></tr>
        <tr><td colspan='2'><input name="reset" type="reset"></td></tr>
      </table>
    </form>

	<br />
	<br />
	<h3>Please Enter Your System User Name</h3>
    <form name="f2" action="j_spring_security_check" method="POST">
      <table>
        <tr>
        	<td>User Name:</td>
        	<td><input id="j_username" type='text' name='j_username' style="width:150px" /></td>
        </tr>
        <tr>
        	<td>Password: </td>
        	<td><input id="j_password" type='password' name='j_password' style="width:150px" /></td>
        </tr>
        <tr><td colspan='2'><input name="submit" type="submit"></td></tr>
        <tr><td colspan='2'><input name="reset" type="reset"></td></tr>
      </table>
    </form>
  </body>
</html>
