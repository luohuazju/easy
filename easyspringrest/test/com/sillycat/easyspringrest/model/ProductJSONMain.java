package com.sillycat.easyspringrest.model;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

public class ProductJSONMain {

	public static void main(String[] args) throws JsonGenerationException,
			JsonMappingException, IOException {
		// create ObjectMapper instance
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);

		// convert Object to json string
		Product item1 = new Product();
		item1.setDesn("desn");
		item1.setId(Long.valueOf(1));
		item1.setProductLink("http://github.com/luohuazju");
		item1.setProductName("Product Name");

		// write to file
		File file = new File("/tmp/product.json");
		objectMapper.writeValue(file, item1);

		FileReader fileReader = new FileReader("/tmp/product.json");
		// convert json string to object
		Product item = objectMapper.readValue(fileReader, Product.class);

		System.out.println("Product Object\n" + item);

	}

}
