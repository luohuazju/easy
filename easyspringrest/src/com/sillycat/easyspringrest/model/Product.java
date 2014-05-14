package com.sillycat.easyspringrest.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class Product {
	
	private Long id;
	private String productName;
	private String productLink;
	private String desn;
	
	//XmlTransient means that we will ignore in generated XML
	@XmlTransient
	@JsonIgnore
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@XmlElement(name="name")
	@JsonProperty("name")
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getProductLink() {
		return productLink;
	}
	
	public void setProductLink(String productLink) {
		this.productLink = productLink;
	}
	
	public String getDesn() {
		return desn;
	}
	
	public void setDesn(String desn) {
		this.desn = desn;
	}
	
}
