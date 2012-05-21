package com.sillycat.easyhunter.plugin.solr

import org.apache.solr.client.solrj.SolrQuery
import org.apache.solr.client.solrj.SolrServerException
import org.apache.solr.client.solrj.impl.HttpSolrServer
import org.apache.solr.client.solrj.response.QueryResponse
import org.apache.solr.common.SolrDocumentList
import org.apache.solr.common.params.SolrParams

class HttpSolrServerGroovyTest {

	static main(args) {
		String url = "http://localhost:8983/solr"
	HttpSolrServer server = new HttpSolrServer( url );

	String query = "name:DDR";
	SolrParams params = new SolrQuery(query);
	try {
		QueryResponse response = server.query(params);

		SolrDocumentList list = response.getResults();
		for (int i = 0; i < list.size(); i++) {
			println (list.get(i));
		}
	} catch (SolrServerException e) {
		e.printStackTrace();
	}

	println 'done'
	}

}
