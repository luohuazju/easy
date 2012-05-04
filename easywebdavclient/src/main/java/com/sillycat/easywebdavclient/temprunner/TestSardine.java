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
        Sardine sardine = SardineFactory.begin("admin", "admin");  
          
        if (sardine.exists("http://192.168.1.71:4502/crx/repository/crx.default/content/dam/")) {  
            System.out.println("/content/dam folder exists");  
        }  
          
        sardine.createDirectory("http://192.168.1.71:4502/crx/repository/crx.default/content/dam/testfolder/");  
          
        InputStream fis = new FileInputStream(new File("img12.jpg"));  
        sardine.put("http://192.168.1.71:4502/crx/repository/crx.default/content/dam/testfolder/img12.jpg", fis);  
          
        @SuppressWarnings("deprecation")
		List<DavResource> resources = sardine.getResources("http://192.168.1.71:4502/crx/repository/crx.default/content/dam/testfolder/");  
        for (DavResource res : resources)  
        {  
             System.out.println(res); // calls the .toString() method.  
        }  
    }  
	
	
}
