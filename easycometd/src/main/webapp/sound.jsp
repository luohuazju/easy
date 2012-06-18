<html> 
    <head> 
        <TITLE>test</TITLE> 
        <script language="JavaScript"> 
            function play(){ 
                alert('1'); 
                var sound = 'http://localhost:8080/easycometd/resources/sound/test.wav';            
                document.all.bgss.src=sound; 
            } 
        </script> 
    </head> 
    <body> 
        <bgsound id="bgss" loop="false" autostart="false"> 
        <div>
             <input type="button" name="button" value="button" onclick="play();" />
        </div> 
    </body> 
</html> 