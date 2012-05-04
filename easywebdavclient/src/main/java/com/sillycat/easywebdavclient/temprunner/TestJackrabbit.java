package com.sillycat.easywebdavclient.temprunner;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.jackrabbit.webdav.DavConstants;
import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.MultiStatus;
import org.apache.jackrabbit.webdav.MultiStatusResponse;
import org.apache.jackrabbit.webdav.client.methods.DavMethod;
import org.apache.jackrabbit.webdav.client.methods.MkColMethod;
import org.apache.jackrabbit.webdav.client.methods.PropFindMethod;
import org.apache.jackrabbit.webdav.client.methods.PutMethod;

public class TestJackrabbit {

	public static void main(String[] args) throws IOException, DavException {
		HttpClient client = new HttpClient();
		Credentials creds = new UsernamePasswordCredentials("admin", "admin");
		client.getState().setCredentials(AuthScope.ANY, creds);

		DavMethod mkCol = new MkColMethod(
				"http://192.168.1.71:4502/crx/repository/crx.default/content/dam/testfolder");
		client.executeMethod(mkCol);
		System.out.println("mkcol testfolder:" + mkCol.getStatusCode() + " "
				+ mkCol.getStatusText());

		PutMethod put = new PutMethod(
				"http://192.168.1.71:4502/crx/repository/crx.default/content/dam/testfolder/testimg.jpg");
		RequestEntity requestEntity = new InputStreamRequestEntity(
				new FileInputStream("img12.jpg"));
		put.setRequestEntity(requestEntity);
		client.executeMethod(put);
		System.out.println("put image file:" + put.getStatusCode() + " "
				+ put.getStatusText());

		DavMethod find = new PropFindMethod(
				"http://192.168.1.71:4502/crx/repository/crx.default/content/dam/testfolder",
				DavConstants.PROPFIND_ALL_PROP, DavConstants.DEPTH_1);
		client.executeMethod(find);
		MultiStatus multiStatus = find.getResponseBodyAsMultiStatus();
		MultiStatusResponse[] responses = multiStatus.getResponses();
		System.out.println("Folders and files:");
		for (int i = 0; i < responses.length; i++) {
			System.out.println(responses[i].getHref());
		}
	}

}
