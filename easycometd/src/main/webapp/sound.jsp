<html>
<head>
<TITLE>test</TITLE>
</head>
<body>
	<embed id="devUnknown"   
       src="http://localhost:8080/easycometd/resources/sound/test.wav"  
       width="0"  
       height="0"  
       loop="false"   
       autostart="false">  
	</embed>  
  	<input type="button" name="button" value="button" onclick="playSound('devUnknown');" />
<script type="text/javascript">  
function playSound(id)  
{  
  var node=document.getElementById(id);  
  if(node!=null)  
  {  
     node.Play();  
  }  
}  
</script>  
</body>
</html>
