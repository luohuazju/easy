package com.sillycat.easyhunter.plugin.solr;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sillycat.easyhunter.model.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test/resources/test-context.xml" })
public class SolrJAdvancedMethodTest {

	@Autowired
	@Qualifier("embeddedSolrServer")
	private SolrServer solrServer;

	@Test
	public void dumy() {
		Assert.assertTrue(true);
		Assert.assertNotNull(solrServer);
	}

	@Test
	public void systemMethod() {
		System.out.println(solrServer.getBinder());
		try {
			System.out.println(solrServer.optimize());// 合并索引文件，可以优化索引、提供性能，但需要一定的时间
			System.out.println(solrServer.ping());// ping服务器是否连接成功
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void rollback() {
		try {
			Product p299 = new Product();
			p299.setId("299");
			p299.setName("add bean index199");
			p299.setManu("index bean manu199");
			p299.setCat(new String[] { "a199", "b199" });

			UpdateResponse response = solrServer.addBean(p299);
			System.out.println("response: " + response);

			// 回滚掉之前的操作，rollback addBean operation
			System.out.println("rollback: " + solrServer.rollback());
			// 提交操作，提交后无法回滚之前操作；发现addBean没有成功添加索引
			System.out.println("commit: " + solrServer.commit());
			queryAll();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void queryCase() {
		// AND 并且
		SolrQuery params = new SolrQuery("name:apple AND manu:inc");
		// OR 或者
		params.setQuery("name:apple OR manu:apache");
		// 空格 等同于 OR
		params.setQuery("name:server manu:dell");

		// 查询name包含solr apple
		params.setQuery("name:solr,apple");
		// manu不包含inc
		params.setQuery("name:solr,apple NOT manu:inc");

		// 50 <= price <= 200
		params.setQuery("price:[50 TO 200]");
		params.setQuery("popularity:[5 TO 6]");

		// 50 <= price <= 200 AND 5 <= popularity <= 6
		params.setQuery("price:[50 TO 200] AND popularity:[5 TO 6]");
		params.setQuery("price:[50 TO 200] OR popularity:[5 TO 6]");

		// 过滤器查询，可以提高性能, filter 类似多个条件组合，如and
		// params.addFilterQuery("id:VA902B");
		// params.addFilterQuery("price:[50 TO 200]");
		// params.addFilterQuery("popularity:[* TO 5]");
		// params.addFilterQuery("weight:*");
		// 0 < popularity < 6 ，没有等于
		// params.addFilterQuery("popularity:{0 TO 6}");

		// 排序
		params.addSortField("id", ORDER.asc);

		// 分页：start开始页，rows每页显示记录条数
		// params.add("start", "0");
		// params.add("rows", "200");
		// params.setStart(0);
		// params.setRows(200);

		// 设置高亮
		params.setHighlight(true); // 开启高亮组件
		params.addHighlightField("name");// 高亮字段
		params.setHighlightSimplePre("<font color='red'>");
		// 标记，高亮关键字前缀
		params.setHighlightSimplePost("</font>");
		// 后缀
		params.setHighlightSnippets(1);// 结果分片数，默认为1
		params.setHighlightFragsize(1000);// 每个分片的最大长度，默认为100

		// 分片信息
		params.setFacet(true).setFacetMinCount(1).setFacetLimit(5)// 段
				.addFacetField("name")// 分片字段
				.addFacetField("inStock");

		try {
			QueryResponse response = solrServer.query(params);

			List<Product> products = response.getBeans(Product.class);
			for (int i = 0; i < products.size(); i++) {
				System.out.println(products.get(i));
			}

			// 输出查询结果集
			SolrDocumentList list = response.getResults();
			System.out.println("query result nums: " + list.getNumFound());
			for (int i = 0; i < list.size(); i++) {
				SolrDocument doc = list.get(i);
				System.out.println(doc.get("name") + "  " + doc);
			}

			// 输出分片信息
			List<FacetField> facets = response.getFacetFields();
			for (FacetField facet : facets) {
				System.out.println(facet);
				List<Count> facetCounts = facet.getValues();
				for (FacetField.Count count : facetCounts) {
					System.out.println(count.getName() + ": "
							+ count.getCount());
				}
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void facetQueryCase() {
		SolrQuery params = new SolrQuery("*:*");
		// 排序
		params.addSortField("id", ORDER.asc);
		params.setStart(0);
		params.setRows(200);
		// Facet为solr中的层次分类查询
		// 分片信息
		params.setFacet(true).setQuery("*:*").setFacetMinCount(1)
				.setFacetLimit(5)// 段
				// .setFacetPrefix("electronics", "cat")
				.setFacetPrefix("cor")// 查询manu、name中关键字前缀是cor的
				.addFacetField("manu").addFacetField("name");// 分片字段
		try {
			QueryResponse response = solrServer.query(params);
			// 输出查询结果集
			SolrDocumentList list = response.getResults();
			System.out.println("Query result nums: " + list.getNumFound());
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}
			System.out.println("All facet filed result: ");
			// 输出分片信息
			List<FacetField> facets = response.getFacetFields();
			for (FacetField facet : facets) {
				System.out.println(facet);
				List<Count> facetCounts = facet.getValues();
				for (FacetField.Count count : facetCounts) {
					// 关键字 - 出现次数
					System.out.println(count.getName() + ": "
							+ count.getCount());
				}
			}
			System.out.println("Search facet [name] filed result: ");
			// 输出分片信息
			FacetField facetField = response.getFacetField("name");
			List<Count> facetFields = facetField.getValues();
			for (Count count : facetFields) {
				// 关键字 - 出现次数
				System.out.println(count.getName() + ": " + count.getCount());
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void queryHight() {
		SolrQuery params = new SolrQuery();
		params.setQuery("manu:SolrInputDocuments");
		params.setHighlight(true); 
		params.addHighlightField("manu");// 高亮字段
		params.setHighlightSimplePre("<font color='red'>");
		// 标记，高亮关键字前缀
		params.setHighlightSimplePost("</font>");
		// 后缀
		params.setHighlightSnippets(1);// 结果分片数，默认为1
		params.setHighlightFragsize(1000);// 每个分片的最大长度，默认为100
		try {
			QueryResponse response = solrServer.query(params);

			// 输出查询结果集
			SolrDocumentList list = response.getResults();
			System.out.println("query result nums: " + list.getNumFound());
			for (int i = 0; i < list.size(); i++) {
				SolrDocument doc = list.get(i);
				String id = (String) doc.getFieldValue("id");
				if (response.getHighlighting().get(id) != null) {
					List<String> highlightSnippets = response
							.getHighlighting().get(id).get("manu");
					System.out.println("hightlight=" + highlightSnippets);
				}
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	}

	private void queryAll() {
		ModifiableSolrParams params = new ModifiableSolrParams();
		// 查询关键词，*:*代表所有属性、所有值，即所有index
		params.set("q", "*:*");
		// 分页，start=0就是从0开始，，rows=5当前返回5条记录，第二页就是变化start这个值为5就可以了。
		params.set("start", 0);
		params.set("rows", Integer.MAX_VALUE);

		// 排序，，如果按照id 排序，，那么将score desc 改成 id desc(or asc)
		// params.set("sort", "score desc");
		params.set("sort", "id asc");

		// 返回信息 * 为全部 这里是全部加上score，如果不加下面就不能使用score
		params.set("fl", "*,score");

		try {
			QueryResponse response = solrServer.query(params);
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

}
