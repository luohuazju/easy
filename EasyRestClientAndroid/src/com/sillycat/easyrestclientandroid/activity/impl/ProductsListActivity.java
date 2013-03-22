package com.sillycat.easyrestclientandroid.activity.impl;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import com.sillycat.easyrestclientandroid.R;
import com.sillycat.easyrestclientandroid.activity.AbstractAsyncListActivity;
import com.sillycat.easyrestclientandroid.adapter.impl.ProductsListAdapter;
import com.sillycat.easyrestclientandroid.dao.ProductDAO;
import com.sillycat.easyrestclientandroid.dao.mock.ProductMockDAOImpl;
import com.sillycat.easyrestclientandroid.model.Product;

public class ProductsListActivity extends AbstractAsyncListActivity {

	protected static final String TAG = ProductsListActivity.class
			.getSimpleName();

	int pageSize = 5;

	int currentPage = 1;

	boolean loadingMore = false;

	ProductsListAdapter adapter;

	List<Product> items;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		items = new ArrayList<Product>();

		adapter = new ProductsListAdapter(this,
				android.R.layout.simple_list_item_1, items);

		setListAdapter(adapter);

	}

	public void onStart() {
		super.onStart();
		new DownloadStatesTask().execute(currentPage);
	}

	public void refreshStates(List<Product> items) {

		if (items == null || items.isEmpty()) {
			return;
		}

		for (int i = 0; i < items.size(); i++) {
			adapter.add(items.get(i));
		}
		// Update the Application title
		setTitle("Products List with " + String.valueOf(adapter.getCount())
				+ " items");

		// Tell to the adapter that changes have been made, this will cause the
		// list to refresh
		adapter.notifyDataSetChanged();

		this.getListView().setOnScrollListener(new OnScrollListener() {

			// useless here, skip!
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			// dumdumdum
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

				// what is the bottom iten that is visible
				int lastInScreen = firstVisibleItem + visibleItemCount;

				Log.d(TAG, "firstVisibleItem = " + firstVisibleItem + " visibleItemCount = " + visibleItemCount + " totalItemCount = " + totalItemCount);
				if ((lastInScreen == totalItemCount) && !(loadingMore)) {
					// Thread thread = new Thread(null, loadMoreListItems);
					// thread.start();
					currentPage = currentPage + 1;
					new DownloadStatesTask().execute(currentPage);
				}
			}
		});
		// Done loading more.
		loadingMore = false;
	}

	private class DownloadStatesTask extends
			AsyncTask<Integer, Void, List<Product>> {
		@Override
		protected void onPreExecute() {
			loadingMore = true;
			// before the network request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected List<Product> doInBackground(Integer... params) {
			try {
				Log.d(TAG, "Hitting the current page params = " + params[0]);
				ProductDAO dao = new ProductMockDAOImpl();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
				}
				return dao.pagination(params[0], pageSize);
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
