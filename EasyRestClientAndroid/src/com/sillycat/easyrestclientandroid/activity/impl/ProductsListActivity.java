package com.sillycat.easyrestclientandroid.activity.impl;

import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.sillycat.easyrestclientandroid.activity.AbstractAsyncListActivity;
import com.sillycat.easyrestclientandroid.adapter.impl.ProductsListAdapter;
import com.sillycat.easyrestclientandroid.dao.ProductDAO;
import com.sillycat.easyrestclientandroid.dao.mock.ProductMockDAOImpl;
import com.sillycat.easyrestclientandroid.model.Product;

public class ProductsListActivity extends AbstractAsyncListActivity {

	protected static final String TAG = ProductsListActivity.class
			.getSimpleName();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void onStart() {
		super.onStart();
		new DownloadStatesTask().execute();
	}

	public void refreshStates(List<Product> items) {
		if (items == null || items.isEmpty()) {
			return;
		}
		ProductsListAdapter adapter = new ProductsListAdapter(this, items);
		setListAdapter(adapter);
	}

	private class DownloadStatesTask extends
			AsyncTask<Void, Void, List<Product>> {
		@Override
		protected void onPreExecute() {
			// before the network request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected List<Product> doInBackground(Void... params) {
			try {
				ProductDAO dao = new ProductMockDAOImpl();
				return dao.all();
			} catch (Exception e) {
				Log.e(TAG, e.getMessage(), e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Product> result) {
			// hide the progress indicator when the network request is complete
			dismissProgressDialog();

			// return the list of states
			refreshStates(result);
		}
	}

}
