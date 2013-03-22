package com.sillycat.easyrestclientandroid.dao.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sillycat.easyrestclientandroid.dao.ProductDAO;
import com.sillycat.easyrestclientandroid.model.Product;

public class ProductMockDAOImpl implements ProductDAO{
	
	private Map<Integer, Product> map = null;

	public void init(){
		if(map == null){
			map = new HashMap<Integer, Product>();
			Product t1 = new Product();
			t1.setProductDesn("This basket ball is buying from Nike store, it is great. You can try to play it anytime");
			t1.setProductId(1);
			t1.setProductImageURL("http://www.buybuybaby.com/assets/product_images/65/197556200420c.jpg");
			t1.setProductName("Basket Ball");
			t1.setProductPrice("$19.99");
			
			Product t2 = new Product();
			t2.setProductDesn("This is really a reliable car that you can daily drive to anywhere.");
			t2.setProductId(2);
			t2.setProductImageURL("http://www.buybuybaby.com/assets/product_images/65/197555200419c.jpg");
			t2.setProductName("Honda Civic 2002 EX");
			t2.setProductPrice("$4,450.00");
			
			Product t3 = new Product();
			t3.setProductDesn("Jeep Compass looks big in China, but there are a lot of big cars in Austin. So Jeep looks smaller in Austin.");
			t3.setProductId(3);
			t3.setProductImageURL("http://www.buybuybaby.com/assets/product_images/65/19767118913674p.jpg");
			t3.setProductName("Jeep Compass");
			t3.setProductPrice("$20,000.00");
			
			Product t4 = new Product();
			t4.setProductDesn("Red Lobster is really a nice place. They cook good food there.");
			t4.setProductId(4);
			t4.setProductImageURL("http://www.buybuybaby.com/assets/product_images/65/10420916320617p.jpg");
			t4.setProductName("Red Lobster");
			t4.setProductPrice("$90.00");
			
			Product t5 = new Product();
			t5.setProductDesn("Rudy's has great BBQ.");
			t5.setProductId(5);
			t5.setProductImageURL("http://www.buybuybaby.com/assets/product_images/65/17045718227525p.jpg");
			t5.setProductName("Rudy's");
			t5.setProductPrice("$50.00");
			
			Product t6 = new Product();
			t6.setProductDesn("Freebird, you will feel you can choose anything you like there as a free bird. Haha.");
			t6.setProductId(6);
			t6.setProductImageURL("http://www.buybuybaby.com/assets/product_images/65/20661740165295p.jpg");
			t6.setProductName("Freebird");
			t6.setProductPrice("$10.00");
			
			Product t7 = new Product();
			t7.setProductDesn("Freebird, you will feel you can choose anything you like there as a free bird. Haha.");
			t7.setProductId(7);
			t7.setProductImageURL("http://www.buybuybaby.com/assets/product_images/65/20661740165295p.jpg");
			t7.setProductName("Freebird");
			t7.setProductPrice("$10.00");
			
			Product t8 = new Product();
			t8.setProductDesn("Freebird, you will feel you can choose anything you like there as a free bird. Haha.");
			t8.setProductId(8);
			t8.setProductImageURL("http://www.buybuybaby.com/assets/product_images/65/20661740165295p.jpg");
			t8.setProductName("Freebird");
			t8.setProductPrice("$10.00");
			
			map.put(t1.getProductId(), t1);
			map.put(t2.getProductId(), t2);
			map.put(t3.getProductId(), t3);
			map.put(t4.getProductId(), t4);
			map.put(t5.getProductId(), t5);
			map.put(t6.getProductId(), t6);
			map.put(t7.getProductId(), t7);
			map.put(t8.getProductId(), t8);
		}
	}
	
	@Override
	public List<Product> all() {
		if(map == null){
			init();
		}
		List<Product> all = new ArrayList<Product>(map.values());
		return all;
	}

	@Override
	public Product get(Integer id) {
		if(map == null){
			init();
		}
		return map.get(id);
	}

	public List<Product> pagination(int currentPage, int pageSize){
		List<Product> pages = null;
		if(map == null){
			init();
		}
		int start = (currentPage-1) * pageSize + 1;
		int end = currentPage * pageSize;
		if(end > map.size()){
			end = map.size();
		}
		pages = new ArrayList<Product>();
		for(int i = start;i <= end; i++){
			pages.add(map.get(i));
		}
		return pages;
	}
	
	public int size(){
		if(map == null){
			init();
		}
		return map.size();
	}
	
	
}
