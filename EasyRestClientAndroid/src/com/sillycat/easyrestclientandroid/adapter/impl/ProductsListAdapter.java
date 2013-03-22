package com.sillycat.easyrestclientandroid.adapter.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sillycat.easyrestclientandroid.R;
import com.sillycat.easyrestclientandroid.adapter.AbstractBaseItemListAdapter;
import com.sillycat.easyrestclientandroid.model.Product;

public class ProductsListAdapter extends AbstractBaseItemListAdapter<Product> {

	private final LayoutInflater _layoutInflater;

	public ProductsListAdapter(Context context, int textViewResourceId,
			Product[] objects) {
		super(context, textViewResourceId, objects);
		this._layoutInflater = LayoutInflater.from(context);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Product item = getItem(position);

		View view = convertView;

		if (view == null) {
			view = _layoutInflater.inflate(R.layout.products_list_item, parent,
					false);
		}

		TextView t1 = (TextView) view.findViewById(R.id.product_name);
		t1.setText(item.getProductName());

		TextView t2 = (TextView) view.findViewById(R.id.product_price);
		t2.setText(item.getProductPrice());

		TextView t3 = (TextView) view.findViewById(R.id.product_desn);
		t3.setText(item.getProductDesn());

		ImageView t4 = (ImageView) view.findViewById(R.id.product_image);
		URL url = null;
		Bitmap bmp = null;
		try {
			url = new URL(item.getProductImageURL());
			bmp = BitmapFactory.decodeStream(url.openConnection()
					.getInputStream());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		t4.setImageBitmap(bmp);
		return view;
	}

}
