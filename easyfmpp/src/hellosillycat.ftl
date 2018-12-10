<html>
	<head>
		<title>fmpp generate html</title>
	</head>
	<body>
		<h1>Welcome ${user}</h1>
		<p>Happy workday in ${work}</p>

		<table border=1>
  		<tr><#list csv.headers as h><th>${h}</#list>
		<#list csv as row>
  		<tr><#list csv.headers as h><td>${(row[h]!"N/A")?string}</#list>
		</#list>
	</body>
</html>