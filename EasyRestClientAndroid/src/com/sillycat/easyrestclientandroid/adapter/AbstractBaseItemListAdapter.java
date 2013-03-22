package com.sillycat.easyrestclientandroid.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

public abstract class AbstractBaseItemListAdapter<T> extends ArrayAdapter<T> {

	public AbstractBaseItemListAdapter(Context context, int textViewResourceId,
			T[] objects) {
		super(context, textViewResourceId, objects);
	}

	

}
