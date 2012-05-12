package com.sillycat.easyhunter.model;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Hits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sillycat.easyview.core.model.User;
import com.sillycat.easyview.core.service.LuceneManager;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"file:src/test/resources/test-context.xml" }) 
public class ArticleLuceneServiceTest {

	@Autowired  
    @Qualifier("personService")  
	private LuceneManager luceneManager;


	@Test
	public void dumy() {
		Assert.assertTrue(true);
	}

	@Test
	public void search() throws Exception {
		luceneManager.init();
		@SuppressWarnings("rawtypes")
		List list = new ArrayList();
		User t1 = new User();
		t1.setId(Integer.valueOf("1"));
		t1.setLogonName("中文1");
		t1.setEmail("中文1@126.com");
		User t2 = new User();
		t2.setId(Integer.valueOf("2"));
		t2.setLogonName("中英文2");
		t2.setEmail("中英文2@126.com");
		list.add(t1);
		list.add(t2);
		//luceneManager.buildIndex(list,true);
		//Hits results = luceneManager.search("logonName", "中文1",true);
		luceneManager.buildIndex(list,false);
		Hits results = luceneManager.search("logonName", "中文1",false);
		assertEquals(1, results.length());

		Document doc = results.doc(0);
		assertEquals("中文1", doc.getField("logonName").stringValue());
		assertEquals("中文1@126.com", doc.getField("email").stringValue());

		//results = luceneManager.search("logonName", "中",true);
		results = luceneManager.search("logonName", "中",false);
		assertEquals(2, results.length());

	}
	
	
}
