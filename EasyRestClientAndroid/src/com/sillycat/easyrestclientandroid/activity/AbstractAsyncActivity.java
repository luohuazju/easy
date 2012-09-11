package com.sillycat.easyrestclientandroid.activity;

import com.sillycat.easyrestclientandroid.mainframe.MainApplication;

import android.app.Activity;
import android.app.ProgressDialog;

public abstract class AbstractAsyncActivity extends Activity implements AsyncActivityInterface
{
	protected static final String TAG = AbstractAsyncActivity.class.getSimpleName();

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
}
