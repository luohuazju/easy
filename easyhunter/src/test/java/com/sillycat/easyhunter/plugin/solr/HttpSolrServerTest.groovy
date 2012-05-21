@Grab(group='org.apache.solr', module='solr-solrj', version='1.4.1')
@Grab(group='org.slf4j', module='slf4j-jdk14', version='1.5.5')

import org.apache.solr.client.solrj.impl.HttpSolrServer

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