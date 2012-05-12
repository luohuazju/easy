package com.sillycat.easyhunter.plugin.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.sillycat.easyhunter.common.StringUtil;

public class LuceneServiceImpl implements LuceneService {

	protected final Log log = LogFactory.getLog(getClass());

	private Analyzer analyzer = new CJKAnalyzer(Version.LUCENE_36);

	// default index file path
	private static final String INDEX_PATH = "D:\\lucene\\index";

	private String indexPath;

	/**
	 * 搜索
	 * 
	 * @param key
	 *            要搜索的KEY，比如找context字段 context
	 * @param search
	 *            要搜索的内容，比如找context中出现了 我爱你
	 * @param memory
	 *            true 内存的索引，false 配置的路径的索引
	 */
	public List<Document> search(String key, String search, boolean isMore) {
		IndexSearcher searcher = null;
		IndexReader reader = null;
		ScoreDoc[] hits = null;
		Directory dir = null;
		List<Document> documents = null;
		Query query = null;
		try {
			dir = FSDirectory.open(new File(this.getIndexPath()));
			reader = IndexReader.open(dir);
			searcher = new IndexSearcher(reader);
			QueryParser parser = new QueryParser(Version.LUCENE_36, key,
					analyzer);
			query = parser.parse(search);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		TopDocs results = null;
		int numTotalHits = 0;

		// 5 pages first
		try {
			results = searcher.search(query, 5 * 10);
			hits = results.scoreDocs;
			numTotalHits = results.totalHits;
			if (isMore) {
				// total pages
				hits = searcher.search(query, numTotalHits).scoreDocs;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (hits != null && hits.length > 0) {
			documents = new ArrayList<Document>(hits.length);
		}
		for (int i = 0; i < hits.length; i++) {
			try {
				Document doc = searcher.doc(hits[i].doc);
				documents.add(doc);
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			searcher.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return documents;

	}

	/**
	 * 建立索引
	 * 
	 * @param list
	 *            要建立索引的list
	 * @param memory
	 *            true 内存中建立索引，false 配置的路径上存放索引
	 */
	public void buildIndex(List<LuceneObject> list, boolean isCreat) {
		Directory dir = null;
		IndexWriter writer = null;
		try {
			dir = FSDirectory.open(new File(this.getIndexPath()));
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_36,
					analyzer);
			if (isCreat) {
				// Create a new index in the directory, removing any
				// previously indexed documents:
				iwc.setOpenMode(OpenMode.CREATE);
			} else {
				// Add new documents to an existing index:
				iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			}
			writer = new IndexWriter(dir, iwc);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Iterator<LuceneObject> iterator = list.iterator();
		Document doc = null;
		LuceneObject bo = null;
		try {
			while (iterator.hasNext()) {
				bo = (LuceneObject) iterator.next();
				doc = bo.buildindex();
				if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
					writer.addDocument(doc);
				} else {
					Term term = new Term("id", doc.get("id"));
					writer.updateDocument(term, doc);
				}
			}
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getIndexPath() {
		if (StringUtil.isBlank(indexPath)) {
			indexPath = INDEX_PATH;
		}
		return indexPath;
	}

	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
	}

}
