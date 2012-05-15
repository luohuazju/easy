package com.sillycat.easyhunter.plugin.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.Assert;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.params.SolrParams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sillycat.easyhunter.model.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test/resources/test-context.xml" })
public class SolrJCRUDTest {

	@Autowired
	@Qualifier("embeddedSolrServer")
	private SolrServer embeddedSolrServer;

	@Test
	public void dumy() {
		Assert.assertTrue(true);
		Assert.assertNotNull(embeddedSolrServer);
	}

	@Test
	public void addDoc() {
		// 创建doc文档
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", 1);
		doc.addField("name", "Solr Input Document");
		doc.addField("manu", "this is SolrInputDocument content");
		try {
			// 添加一个doc文档
			UpdateResponse response = embeddedSolrServer.add(doc);
			System.out.println(embeddedSolrServer.commit());// commit后才保存到索引库
			System.out.println(response);
			System.out.println("querying time:" + response.getQTime());
			System.out.println("Elapsed Time:" + response.getElapsedTime());
			System.out.println("status:" + response.getStatus());
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		query("name:solr");
	}

	@Test
	public void addDocs() {
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", 2);
		doc.addField("name", "Solr Input Documents 1");
		doc.addField("manu", "this is SolrInputDocuments 1 content");
		docs.add(doc);
		doc = new SolrInputDocument();
		doc.addField("id", 3);
		doc.addField("name", "Solr Input Documents 2");
		doc.addField("manu", "this is SolrInputDocuments 3 content");
		docs.add(doc);
		try {
			// add documents
			UpdateResponse response = embeddedSolrServer.add(docs);
			// commit后才保存到索引库
			System.out.println(embeddedSolrServer.commit());
			System.out.println(response);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		query("solr");
	}
	
	@Test
	public void addBean() {
	    //Product需要添加相关的Annotation注解，便于告诉solr哪些属性参与到index中
	    Product p4 = new Product();
	    p4.setId("4");
	    p4.setName("add bean index");
	    p4.setManu("index bean manu");
	    p4.setCat(new String[] { "category1", "catagory2" });
	    
	    try {
	        //添加Index Bean到索引库
	        UpdateResponse response = this.embeddedSolrServer.addBean(p4);
	        System.out.println(this.embeddedSolrServer.commit());//commit后才保存到索引库
	        System.out.println(response);
	    } catch (SolrServerException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    query("name:add bean index");
	}
	
	@Test
	public void addBeans() {
	    Product p6 = new Product();
	    p6.setId("6");
	    p6.setName("add beans index 6");
	    p6.setManu("index beans manu 6");
	    p6.setCat(new String[] { "a", "b" });
	    
	    List<Product> products = new ArrayList<Product>();
	    products.add(p6);
	    
	    Product p5 = new Product();
	    p5.setId("5");
	    p5.setName("add beans index 5");
	    p5.setManu("index beans manu 5");
	    p5.setCat(new String[] { "aaa", "bbbb" });
	    products.add(p5);
	    try {
	        //添加索引库
	        UpdateResponse response = this.embeddedSolrServer.addBeans(products);
	        System.out.println(this.embeddedSolrServer.commit());//commit后才保存到索引库
	        System.out.println(response);
	    } catch (SolrServerException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    query("name:add beans index");
	}

	@Test
	public void queryAll() {
		ModifiableSolrParams params = new ModifiableSolrParams();
		// 查询关键词，*:*代表所有属性、所有值，即所有index
		params.set("q", "*:*");
		// 分页，start=0就是从0开始，，rows=5当前返回5条记录，第二页就是变化start这个值为5就可以了。
		params.set("start", 0);
		params.set("rows", Integer.MAX_VALUE);

		// 排序，，如果按照id 排序，，那么将score desc 改成 id desc(or asc)
		params.set("sort", "score desc");

		// 返回信息 * 为全部 这里是全部加上score，如果不加下面就不能使用score
		params.set("fl", "*,score");

		try {
			QueryResponse response = embeddedSolrServer.query(params);
			SolrDocumentList list = response.getResults();
			for (int i = 0; i < list.size(); i++) {
				SolrDocument docTmp = list.get(i);
				System.out.print(docTmp.get("name") + "------");
				System.out.println(docTmp);
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	}

	private void query(String query) {
		SolrParams params = new SolrQuery(query);
		try {
			QueryResponse response = embeddedSolrServer.query(params);
			SolrDocumentList list = response.getResults();
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	}
}
