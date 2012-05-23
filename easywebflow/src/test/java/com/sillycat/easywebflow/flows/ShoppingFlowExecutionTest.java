package com.sillycat.easywebflow.flows;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.springframework.binding.mapping.Mapper;
import org.springframework.binding.mapping.MappingResults;
import org.springframework.webflow.config.FlowDefinitionResource;
import org.springframework.webflow.config.FlowDefinitionResourceFactory;
import org.springframework.webflow.core.collection.LocalAttributeMap;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.engine.EndState;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.test.MockExternalContext;
import org.springframework.webflow.test.MockFlowBuilderContext;
import org.springframework.webflow.test.execution.AbstractXmlFlowExecutionTests;

import com.sillycat.easywebflow.model.Cart;
import com.sillycat.easywebflow.model.Product;
import com.sillycat.easywebflow.service.CartService;
import com.sillycat.easywebflow.service.ProductService;

public class ShoppingFlowExecutionTest extends AbstractXmlFlowExecutionTests {

	private CartService cartService;

	private ProductService productService;
	
	private Product product;
	
	private Cart cart;

	protected void setUp() {
		cartService = EasyMock.createMock(CartService.class);
		productService = EasyMock.createMock(ProductService.class);
		product = mockProduct();
		cart = new Cart();
	}

	protected FlowDefinitionResource getResource(
			FlowDefinitionResourceFactory resourceFactory) {
		return resourceFactory
				.createFileResource("d:/work/easy/easywebflow/src/main/resources/flows/shopping.xml");
	}

	protected void configureFlowBuilderContext(
			MockFlowBuilderContext builderContext) {
		builderContext.registerBean("cartService", cartService);
		builderContext.registerBean("productService", productService);
	}

	public void testStartShoppingFlow() {
		List<Product> products = mockProducts();
		EasyMock.expect(productService.getProducts()).andReturn(products);
		EasyMock.replay(productService);
		MutableAttributeMap input = new LocalAttributeMap();
		MockExternalContext context = new MockExternalContext();
		startFlow(input, context);
		assertCurrentStateEquals("viewCart");
		assertResponseWrittenEquals("viewCart", context);
		assertNotNull(this.getViewScope().get("products"));
		EasyMock.verify(productService);
	}
	
	private Flow createMockAddToCartSubflow() {
	    Flow mockAddToCartSubflow = new Flow("addToCart");
	    mockAddToCartSubflow.setInputMapper(new Mapper() {
	        public MappingResults map(Object source, Object target) {
	            // assert that 1L was passed in as input
	            //assertEquals(1, ((AttributeMap) source).get("productId"));
	            return null;
	        }
	    });
	    // immediately return the bookingConfirmed outcome so the caller can respond
	    new EndState(mockAddToCartSubflow, "productAdded");
	    return mockAddToCartSubflow;
	}

	private List<Product> mockProducts() {
		List<Product> items = new ArrayList<Product>();
		items.add(new Product(1,"mockProduct1",100));
		items.add(new Product(2,"mockProduct2",200));
		items.add(new Product(3,"mockProduct3",300));
		return items;
	}

	private Product mockProduct() {
		Product item = new Product(1, "mockProduct", 100);
		return item;
	}
}
