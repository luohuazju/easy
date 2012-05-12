package com.sillycat.easyhunter.plugin.lucene;

import java.util.List;

import org.apache.lucene.search.ScoreDoc;

public interface LuceneService {
	
	public List<ScoreDoc> search(String key, String search);
	
	public void buildIndex(List<LuceneObject> list, boolean isCreat);

}
