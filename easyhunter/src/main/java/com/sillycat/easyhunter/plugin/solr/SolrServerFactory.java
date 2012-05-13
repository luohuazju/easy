package com.sillycat.easyhunter.plugin.solr;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.core.CoreContainer;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.xml.sax.SAXException;

import com.sillycat.easyhunter.common.StringUtil;

public class SolrServerFactory implements FactoryBean<SolrServer>,
		InitializingBean {

	protected final Log log = LogFactory.getLog(getClass());

	private final String DEFAULT_SERVER_URL = "http://localhost:8983/solr/";

	private final String DEFAULT_SOLR_HOME = "D:\\book\\solr\\apache-solr-3.6.0\\example\\solr";

	private final String DEFAULT_SOLR_SERVER_CLASS_NAME = "org.apache.solr.client.solrj.embedded.EmbeddedSolrServer";

	private String serverURL;

	private String solrHome;

	private String solrServerClassName;

	private SolrServer solrServer = null;

	public SolrServer getObject() throws Exception {
		return solrServer;
	}

	public Class<SolrServer> getObjectType() {
		return SolrServer.class;
	}

	public boolean isSingleton() {
		return true;
	}

	public void afterPropertiesSet() throws Exception {
		if ("org.apache.solr.client.solrj.embedded.EmbeddedSolrServer"
				.equalsIgnoreCase(this.getSolrServerClassName())) {
			System.setProperty("solr.solr.home", this.getSolrHome());
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
			EmbeddedSolrServer server = new EmbeddedSolrServer(coreContainer, "");
			solrServer = server;
		} else if ("org.apache.solr.client.solrj.impl.HttpSolrServer"
				.equalsIgnoreCase(this.getSolrServerClassName())) {
			HttpSolrServer server = new HttpSolrServer(this.getServerURL());
			server.setSoTimeout(1000); // socket read timeout
			server.setConnectionTimeout(100);
			server.setDefaultMaxConnectionsPerHost(100);
			server.setMaxTotalConnections(100);
			server.setFollowRedirects(false); // defaults to false
			// allowCompression defaults to false.
			// Server side must support gzip or deflate for this to have any
			// effect.
			server.setAllowCompression(true);
			server.setMaxRetries(1); // defaults to 0. > 1 not recommended.

			// sorlr J 目前使用二进制的格式作为默认的格式。对于solr1.2的用户通过显示的设置才能使用XML格式。
			server.setParser(new XMLResponseParser());
			solrServer =  server;
		}
	}

	public String getServerURL() {
		if (StringUtil.isBlank(serverURL)) {
			serverURL = DEFAULT_SERVER_URL;
		}
		return serverURL;
	}

	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}

	public String getSolrHome() {
		if (StringUtil.isBlank(solrHome)) {
			solrHome = DEFAULT_SOLR_HOME;
		}
		return solrHome;
	}

	public void setSolrHome(String solrHome) {
		this.solrHome = solrHome;
	}

	public String getSolrServerClassName() {
		if (StringUtil.isBlank(solrServerClassName)) {
			solrServerClassName = DEFAULT_SOLR_SERVER_CLASS_NAME;
		}
		return solrServerClassName;
	}

	public void setSolrServerClassName(String solrServerClassName) {
		this.solrServerClassName = solrServerClassName;
	}

}
