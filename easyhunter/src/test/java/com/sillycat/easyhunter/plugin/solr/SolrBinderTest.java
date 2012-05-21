package com.sillycat.easyhunter.plugin.solr;

import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;
import org.junit.Test;

import com.sillycat.easyhunter.model.User;

public class SolrBinderTest {

	@Test
	public void createDoc() {
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", System.currentTimeMillis());
		doc.addField("name", "SolrInputDocument");
		doc.addField("age", 22, 2.0f);

		doc.addField("like", new String[] { "music", "book", "sport" });
		
		doc.put("address", new SolrInputField("guangzhou"));
		
		doc.setField("sex", "man");
		doc.setField("remark", "china people", 2.0f);

		System.out.println(doc);
	}
	
	@Test
    public void docAndBean4Binder() {
        SolrDocument doc = new SolrDocument();
        doc.addField("id", 456);
        doc.addField("name", "SolrInputDocument");
        
        doc.addField("likes", new String[] { "music", "book", "sport" });
        
        doc.put("address", "guangzhou");
        
        doc.setField("sex", "man");
        doc.setField("remark", "china people");
        
        DocumentObjectBinder binder = new DocumentObjectBinder();
        User user = new User();
        user.setId(222);
        user.setName("JavaBean");
        user.setLike(new String[] { "music", "book", "sport" });
        user.setAddress("guangdong");
        
        System.out.println(doc);
        // User ->> SolrInputDocument
        System.out.println(binder.toSolrInputDocument(user));
        // SolrDocument ->> User
        System.out.println(binder.getBean(User.class, doc));
        
        SolrDocumentList list = new SolrDocumentList();
        list.add(doc);
        list.add(doc);
        //SolrDocumentList ->> List
        System.out.println(binder.getBeans(User.class, list));
    }
	
	@Test
    public void docMethod() {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", System.currentTimeMillis());
        doc.addField("name", "SolrInputDocument");
        doc.addField("age", 23, 1.0f);
        doc.addField("age", 22, 2.0f);
        doc.addField("age", 24, 0f);
        
        System.out.println("keyset: " + doc.entrySet());
        System.out.println(doc.get("age"));
        //排名有用，类似百度竞价排名
        doc.setDocumentBoost(2.0f);
        System.out.println(doc.getDocumentBoost());
        System.out.println(doc.getField("name"));
        System.out.println(doc.getFieldNames());//keys
        System.out.println(doc.getFieldValues("age"));
        System.out.println(doc.getFieldValues("id"));
        System.out.println(doc.values());
    }

}
