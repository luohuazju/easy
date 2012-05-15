package com.sillycat.easyhunter.plugin.solr;

import junit.framework.Assert;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.SolrParams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test/resources/test-context.xml" })
public class SolrServerFactoryTest {

	@Autowired
	@Qualifier("httpSolrServer")
	private SolrServer httpSolrServer;

	@Autowired
	@Qualifier("embeddedSolrServer")
	private SolrServer embeddedSolrServer;

	@Test
	public void dumy() {
		Assert.assertTrue(true);
	}

	@Test
	public void embeddedSolrServerQuery() {
		String query = "name:DDR";
		SolrParams params = new SolrQuery(query);

		try {
			QueryResponse response = embeddedSolrServer.query(params);

			SolrDocumentList list = response.getResults();
			for (int i = 0; i < list.size(); i++) {
				Assert.assertNotNull(list.get(i));
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void httpSolrServerQuery() {
		String query = "name:DDR";
		SolrParams params = new SolrQuery(query);

		try {
			QueryResponse response = httpSolrServer.query(params);

			SolrDocumentList list = response.getResults();
			for (int i = 0; i < list.size(); i++) {
				Assert.assertNotNull(list.get(i));
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	}

}
