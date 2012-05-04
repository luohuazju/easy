package com.sillycat.easywebdavclient.temprunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.googlecode.sardine.DavResource;
import com.googlecode.sardine.Sardine;
import com.googlecode.sardine.SardineFactory;

public class TestSardine {

	/** 
     * @param args 
	 * @throws IOException 
     */  
    public static void main(String[] args) throws IOException {  
        //Sardine sardine = SardineFactory.begin("admin", "admin");  
        Sardine sardine = SardineFactory.begin();  
        
        sardine.disableCompression();
        sardine.disablePreemptiveAuthentication();
        
        if (sardine.exists("http://localhost:8080/netstore-webdav/shared/")) {  
            System.out.println("shared folder exists");  
        }  
        
        if(!sardine.exists("http://localhost:8080/netstore-webdav/shared/testfolder/")){
        	sardine.createDirectory("http://localhost:8080/netstore-webdav/shared/testfolder/");  
        	System.out.println("testfolder not folder exists");  
        }
        
        InputStream fis = new FileInputStream(new File("d://1.txt"));  
        sardine.put("http://localhost:8080/netstore-webdav/shared/testfolder/2.txt", fis);  
        
        @SuppressWarnings("deprecation")
		List<DavResource> resources = sardine.getResources("http://localhost:8080/netstore-webdav/shared/testfolder/");  
        for (DavResource res : resources)  
        {  
             System.out.println(res); // calls the .toString() method.  
        }  
    }  
	
	
}
