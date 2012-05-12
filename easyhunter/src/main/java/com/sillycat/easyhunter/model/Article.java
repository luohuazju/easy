package com.sillycat.easyhunter.model;

import java.io.Serializable;
import java.io.StringReader;
import java.util.Date;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.FieldInfo.IndexOptions;

import com.sillycat.easyhunter.common.StringUtil;
import com.sillycat.easyhunter.plugin.lucene.LuceneObject;

public class Article implements LuceneObject, Serializable {

	private static final long serialVersionUID = 797661575680671369L;

	private String id;

	private String title;

	private String author;

	private Date gmtCreate;

	private String content;

	private String websiteURL;

	public Document buildindex() {
		Document doc = new Document();
		if (StringUtil.isNotBlank(this.getId())) {
			Field idField = new Field("id", this.getId(), Field.Store.YES,
					Field.Index.NOT_ANALYZED_NO_NORMS);
			idField.setIndexOptions(IndexOptions.DOCS_ONLY);
			doc.add(idField);
		}
		if (StringUtil.isNotBlank(this.getTitle())) {
			doc.add(new Field("title", this.getTitle(), Field.Store.YES,
					Field.Index.ANALYZED_NO_NORMS));
		}
		if (StringUtil.isNotBlank(this.getAuthor())) {
			doc.add(new Field("author", this.getAuthor(), Field.Store.YES,
					Field.Index.ANALYZED_NO_NORMS));
		}
		if (StringUtil.isNotBlank(this.getWebsiteURL())) {
			doc.add(new Field("websiteURL", this.getWebsiteURL(),
					Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
		}
		if (this.getGmtCreate() != null) {
			NumericField gmtCreate_Field = new NumericField("gmtCreate");
			gmtCreate_Field.setLongValue(this.getGmtCreate().getTime());
			doc.add(gmtCreate_Field);
		}
		if (StringUtil.isNotBlank(this.getContent())) {
			doc.add(new Field("content", new StringReader(this.getContent())));
		}
		return doc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWebsiteURL() {
		return websiteURL;
	}

	public void setWebsiteURL(String websiteURL) {
		this.websiteURL = websiteURL;
	}

}
