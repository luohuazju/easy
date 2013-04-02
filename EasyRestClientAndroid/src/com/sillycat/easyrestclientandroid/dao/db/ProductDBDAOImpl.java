package com.sillycat.easyrestclientandroid.dao.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sillycat.easyrestclientandroid.dao.ProductDAO;
import com.sillycat.easyrestclientandroid.model.Product;

public class ProductDBDAOImpl implements ProductDAO {

	private BasicDBHelper helper;

	public ProductDBDAOImpl(BasicDBHelper helper) {
		this.helper = helper;
	}

	public List<Product> all() {
		List<Product> items = new ArrayList<Product>();
		synchronized (DBGuard.class) {
			SQLiteDatabase db = null;
			Cursor cursor = null;
			try {
				db = helper.getReadableDatabase();
				cursor = db.query(Product.TABLE_NAME, new String[] {
						Product.COLUMN_NAME_PRODUCT_DESN,
						Product.COLUMN_NAME_PRODUCT_ID,
						Product.COLUMN_NAME_PRODUCT_IMAGE_URL,
						Product.COLUMN_NAME_PRODUCT_NAME,
						Product.COLUMN_NAME_PRODUCT_PRICE }, null, null, null,
						null, null);
				if (cursor != null && cursor.getColumnCount() > 0) {
					cursor.moveToFirst();
					while (cursor.getPosition() != cursor.getCount()) {
						items.add(getItem(cursor));
						cursor.moveToNext();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (cursor != null) {
					cursor.close();
				}
				if (db != null) {
					db.close();
				}
			}
		}
		return items;
	}

	public Product get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Product> pagination(int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Product insert(Product product) {
		synchronized (DBGuard.class) {
			SQLiteDatabase db = null;
			try {
				db = helper.getWritableDatabase();
				Long id = db.insert(Product.TABLE_NAME,
						Product.COLUMN_NAME_PRODUCT_NAME, getValues(product));
				product.setProductId(id);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (db != null) {
					db.close();
				}
			}
			return product;
		}
	}

	@Override
	public boolean deleteById(Long productId) {
		synchronized (DBGuard.class) {
			SQLiteDatabase db = null;
			try {
				db = helper.getReadableDatabase();
				db.delete(Product.TABLE_NAME, "id = ?",
						new String[] { productId + "" });
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (db != null) {
					db.close();
				}
			}
		}
		return false;
	}

	private ContentValues getValues(Product item) {
		ContentValues values = new ContentValues();
		values.put(Product.COLUMN_NAME_PRODUCT_DESN, item.getProductDesn());
		values.put(Product.COLUMN_NAME_PRODUCT_ID, item.getProductId());
		values.put(Product.COLUMN_NAME_PRODUCT_IMAGE_URL,
				item.getProductImageURL());
		values.put(Product.COLUMN_NAME_PRODUCT_NAME, item.getProductName());
		values.put(Product.COLUMN_NAME_PRODUCT_PRICE, item.getProductPrice());
		return values;
	}

	private Product getItem(Cursor cursor) {
		Product item = new Product();
		item.setProductDesn(cursor.getString(0));
		item.setProductId(cursor.getLong(1));
		item.setProductImageURL(cursor.getString(2));
		item.setProductName(cursor.getString(3));
		item.setProductPrice(cursor.getString(4));
		return item;
	}

	@Override
	public Product update(Product product) {
		Product item = null;
		synchronized (DBGuard.class) {
			SQLiteDatabase db = null;
			try {
				db = helper.getWritableDatabase();
				db.update(Product.TABLE_NAME, getValues(product), "id=?",
						new String[] { "" + product.getProductId() });
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (db != null) {
					db.close();
				}
			}
		}
		return item;
	}

}
