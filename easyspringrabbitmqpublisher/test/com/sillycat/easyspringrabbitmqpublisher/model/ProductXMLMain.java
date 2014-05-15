package com.sillycat.easyspringrabbitmqpublisher.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class ProductXMLMain {

	public static void main(String[] args) {
		Product item1 = new Product();
		item1.setDesn("desn");
		item1.setId(Long.valueOf(1));
		item1.setProductLink("http://github.com/luohuazju");
		item1.setProductName("Product Name");

		try {
			File file = new File("/tmp/product.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Product.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(item1, file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		JAXBContext jaxbContext = null;
		Product item2 = null;
		FileReader fileReader = null;
		try {
			fileReader = new FileReader("/tmp/product.xml");

			jaxbContext = JAXBContext.newInstance(Product.class);
			item2 = (Product) jaxbContext.createUnmarshaller().unmarshal(
					fileReader);
			System.out.println("Item:" + item2);
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
