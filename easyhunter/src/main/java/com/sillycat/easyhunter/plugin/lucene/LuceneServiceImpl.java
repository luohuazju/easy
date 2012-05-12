package com.sillycat.easyhunter.plugin.lucene;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
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
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

import com.sillycat.easyhunter.common.StringUtil;

public class LuceneServiceImpl implements LuceneService {

	protected final Log log = LogFactory.getLog(getClass());

	private Analyzer analyzer = null;

	private static final String INDEX_PATH = "D:\\lucene\\index";

	private String indexPath;

	public void init() {
		analyzer = new CJKAnalyzer(Version.LUCENE_36);
	}

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
	public List<ScoreDoc> search(String key, String search) {
		IndexSearcher searcher = null;
		ScoreDoc[] hits = null;
		Directory dir = null;
		try {
			dir = FSDirectory.open(new File(this.getIndexPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		IndexReader reader = null;
		try {
			reader = IndexReader.open(dir);
		} catch (CorruptIndexException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		searcher = new IndexSearcher(reader);

		QueryParser parser = new QueryParser(Version.LUCENE_36, key, analyzer);
		Query query = null;
		try {
			query = parser.parse(search);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// 5 pages
		TopDocs results = null;
		try {
			results = searcher.search(query, 5 * 10);
			hits = results.scoreDocs;
		} catch (IOException e) {
			e.printStackTrace();
		}

		// total pages
		int numTotalHits = results.totalHits;
		try {
			hits = searcher.search(query, numTotalHits).scoreDocs;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Arrays.asList(hits);
		// Document doc = searcher.doc(hits[i].doc);
	}

	/**
	 * 建立索引
	 * 
	 * @param list
	 *            要建立索引的list
	 * @param memory
	 *            true 内存中建立索引，false 配置的路径上存放索引
	 */
	public void buildIndex(List<LuceneObject> list, boolean isCreat){
		Directory dir = null;
		try {
			dir = FSDirectory.open(new File(this.getIndexPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
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

		IndexWriter writer = null;
		try {
			writer = new IndexWriter(dir, iwc);
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Iterator<LuceneObject> iterator = list.iterator();
		Document doc = null;
		LuceneObject bo = null;
		while (iterator.hasNext()) {
			bo = (LuceneObject) iterator.next();
			doc = bo.buildindex();
			if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
				try {
					writer.addDocument(doc);
				} catch (CorruptIndexException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				Term term = new Term("id", doc.get("id"));
				try {
					writer.updateDocument(term, doc);
				} catch (CorruptIndexException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			writer.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
