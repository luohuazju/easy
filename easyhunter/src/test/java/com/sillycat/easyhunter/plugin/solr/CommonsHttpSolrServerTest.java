package com.sillycat.easyhunter.plugin.solr;

import java.net.MalformedURLException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.SolrParams;

@SuppressWarnings("deprecation")
public class CommonsHttpSolrServerTest {

	private SolrServer server;
	private SolrServer httpServer;

	private static final String DEFAULT_URL = "http://localhost:8983/solr/";

	//@Before
	public void init() {
		try {
			server = new CommonsHttpSolrServer(DEFAULT_URL);
			httpServer = new CommonsHttpSolrServer(DEFAULT_URL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	//@After
	public void destory() {
		server = null;
		httpServer = null;
		System.runFinalization();
		System.gc();
	}

	public final void fail(Object o) {
		System.out.println(o);
	}

	//@Test
	public void server() {
		fail(server);
		fail(httpServer);
	}

	//@Test
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
