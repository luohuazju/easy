package com.sillycat.easyhunter.plugin.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.SolrParams;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HttpSolrServerTest {

	private HttpSolrServer server;

	private static final String DEFAULT_URL = "http://localhost:8983/solr/";

	@Before
	public void init() {
		server = new HttpSolrServer(DEFAULT_URL);
		server.setSoTimeout(1000); // socket read timeout 
		server.setConnectionTimeout(100); 
		server.setDefaultMaxConnectionsPerHost(100); 
		server.setMaxTotalConnections(100); 
		server.setFollowRedirects(false); // defaults to false 
		// allowCompression defaults to false. 
		// Server side must support gzip or deflate for this to have any effect. 
		server.setAllowCompression(true); 
		server.setMaxRetries(1); // defaults to 0.  > 1 not recommended. 
		
		 
		//sorlr J 目前使用二进制的格式作为默认的格式。对于solr1.2的用户通过显示的设置才能使用XML格式。
		server.setParser(new XMLResponseParser());
	}

	@After
	public void destory() {
		server = null;
		System.runFinalization();
		System.gc();
	}

	public final void fail(Object o) {
		System.out.println(o);
	}

	@Test
	public void server() {
		fail(server);
	}

	@Test
	public void query() {
		String query = "name:DDR";
		SolrParams params = new SolrQuery(query);
		try {
			QueryResponse response = server.query(params);

			SolrDocumentList list = response.getResults();
			for (int i = 0; i < list.size(); i++) {
				fail(list.get(i));
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	}

}
