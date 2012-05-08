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
	
	//private static final String WEBDAV_URL = "http://localhost:8080/netstore-webdav/shared/";
	
	private static final String WEBDAV_URL = "http://localhost:8080/easywebdavserver/repository/default/";

	/** 
     * @param args 
	 * @throws IOException 
     */  
    public static void main(String[] args) throws IOException {  
        Sardine sardine = SardineFactory.begin("admin", "admin");  
        //Sardine sardine = SardineFactory.begin();  
        
        sardine.disableCompression();
        sardine.disablePreemptiveAuthentication();
        
        if (sardine.exists(WEBDAV_URL)) {  
            System.out.println(WEBDAV_URL + " folder exists");  
        }  
        
        if(!sardine.exists( WEBDAV_URL + "testfolder/")){
        	sardine.createDirectory( WEBDAV_URL + "testfolder/");  
        	System.out.println("testfolder not folder exists");  
        }
        
        InputStream fis = new FileInputStream(new File("d://1.txt"));  
        sardine.put( WEBDAV_URL + "testfolder/3.txt", fis);  
        
        @SuppressWarnings("deprecation")
		List<DavResource> resources = sardine.getResources(WEBDAV_URL + "testfolder/");  
        for (DavResource res : resources)  
        {  
             System.out.println(res); // calls the .toString() method.  
        }  
    }  
	
	
}
