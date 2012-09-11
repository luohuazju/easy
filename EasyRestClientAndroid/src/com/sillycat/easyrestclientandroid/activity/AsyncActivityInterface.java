package com.sillycat.easyrestclientandroid.activity;

import com.sillycat.easyrestclientandroid.mainframe.MainApplication;

public interface AsyncActivityInterface {
	
	public MainApplication getApplicationContext();

	//loading progress
	public void showLoadingProgressDialog();

	public void showProgressDialog(CharSequence message);

	public void dismissProgressDialog();
}
