package com.sillycat.easyhunter.plugin.lucene;

import java.util.List;

import org.apache.lucene.document.Document;

public interface LuceneService {

	public List<Document> search(String key, String search, boolean isMore);

	public List<Document> search(String[] keys, String search, boolean isMore);

	public void buildIndex(List<LuceneObject> list, boolean isCreat);

}
