<html>
  <head>
    <title>Open ID Login</title>
  </head>

  <body onload="document.f.j_username.focus();">
    <h3>Please Enter Your OpenID Identity</h3>

    <form name="f" action="j_spring_openid_security_check" method="POST">
      <table>
        <tr>
        	<td>OpenID Identity:</td>
        	<td><input type='text' name='openid_identifier' value='https://www.google.com/accounts/o8/id'/></td></tr>
        <tr><td colspan='2'><input name="submit" type="submit"></td></tr>
        <tr><td colspan='2'><input name="reset" type="reset"></td></tr>
      </table>
    </form>

  </body>
</html>
