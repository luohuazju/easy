package com.sillycat.easyrestclientandroid.activity;

import android.app.Application;

public interface AsyncActivityInterface {
	
	public Application getApplicationContext();

	//loading progress
	public void showLoadingProgressDialog();

	public void showProgressDialog(CharSequence message);

	public void dismissProgressDialog();
}
