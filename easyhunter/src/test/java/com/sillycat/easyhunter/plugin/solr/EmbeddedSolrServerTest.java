package com.sillycat.easyhunter.plugin.solr;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.core.CoreContainer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class EmbeddedSolrServerTest {

	EmbeddedSolrServer server = null;

	@Before
	public void init() {
		System.setProperty("solr.solr.home",
				"D:\\book\\solr\\apache-solr-3.6.0\\example\\solr");
		CoreContainer.Initializer initializer = new CoreContainer.Initializer();
		CoreContainer coreContainer = null;
		try {
			coreContainer = initializer.initialize();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		server = new EmbeddedSolrServer(coreContainer, "");
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
