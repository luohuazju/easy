package com.sillycat.easyrestclientandroid.activity.impl;

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

		Product[] items_arr = new Product[5];
		for (int i = 0; i < items.size(); i++) {
			items_arr[i] = new Product();
			items_arr[i] = items.get(i);
		}

		ProductsListAdapter adapter = new ProductsListAdapter(this,
				android.R.layout.simple_list_item_1, items_arr);

		View footerView = ((LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.products_list_footer, null, false);
		this.getListView().addFooterView(footerView);

		this.setListAdapter(adapter);

		this.getListView().setOnScrollListener(new OnScrollListener() {

			// useless here, skip!
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			// dumdumdum
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

				// what is the bottom iten that is visible
				int lastInScreen = firstVisibleItem + visibleItemCount;

				// is the bottom item visible & not loading more already ? Load
				// more !
				if ((lastInScreen == totalItemCount) && !(loadingMore)) {
					// Thread thread = new Thread(null, loadMoreListItems);
					// thread.start();
					currentPage = currentPage + 1;
				}
			}
		});
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
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
				}
				return dao.pagination(1, pageSize);
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
