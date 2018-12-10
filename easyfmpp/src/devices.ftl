<@pp.dropOutputFile />
<#list devices as device>
<@pp.changeOutputFile name=device.deviceName+".html" />
<html>
	<head>  
	  <title>${device.deviceName?cap_first}</title>
	</head>
	<body>
		<h1>${device.deviceName?cap_first}</h1>
  		<p>${device.ip}</p>
	</body>  
</html>
</#list>