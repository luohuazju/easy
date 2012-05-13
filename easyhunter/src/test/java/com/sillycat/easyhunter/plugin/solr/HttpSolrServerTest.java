package com.sillycat.easyhunter.plugin.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.SolrParams;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HttpSolrServerTest {

	private SolrServer server;

	private static final String DEFAULT_URL = "http://localhost:8983/solr/";

	@Before
	public void init() {
		server = new HttpSolrServer(DEFAULT_URL);
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
