package com.sillycat.easyandroidgcm.activity;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.sillycat.easyandroidgcm.R;
import com.sillycat.easyandroidgcm.mainframe.MainApplication;

public abstract class AbstractAsyncListActivity extends ListActivity implements AsyncActivityInterface{

	protected static final String TAG = AbstractAsyncListActivity.class.getSimpleName();

	private ProgressDialog _progressDialog;
	
	private boolean _destroyed = false;

	@Override
	public MainApplication getApplicationContext()
	{
		return (MainApplication) super.getApplicationContext();
	}
	
	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
		_destroyed = true;
	}
	
	public void showLoadingProgressDialog() 
	{
		this.showProgressDialog("Loading. Please wait...");
	}
	
	public void showProgressDialog(CharSequence message)
	{
		if (_progressDialog == null)
		{
			_progressDialog = new ProgressDialog(this);
			_progressDialog.setIndeterminate(true);
		}
		
		_progressDialog.setMessage(message);
		_progressDialog.show();
	}
		
	public void dismissProgressDialog() 
	{
		if (_progressDialog != null && !_destroyed) 
		{
			_progressDialog.dismiss();
		}
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.options_menu_all, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		boolean result = true;
		try {
			switch (item.getItemId()) {
			case R.id.item_gcm_demo:
			//	startActivity(new Intent(this, DemoActivity.class));
				return true;
			default:
				return super.onOptionsItemSelected(item);
			}
		} catch (Exception error) {
			Log.d(TAG, "About_onOptionsItemSelected failed");
		}
		return result;
	}
	
}
