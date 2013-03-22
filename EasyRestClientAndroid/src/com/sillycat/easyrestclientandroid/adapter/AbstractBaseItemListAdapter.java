package com.sillycat.easyrestclientandroid.adapter;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

public abstract class AbstractBaseItemListAdapter<T> extends ArrayAdapter<T> {

	public AbstractBaseItemListAdapter(Context context, int textViewResourceId,
			List<T> objects) {
		super(context, textViewResourceId, objects);
	}

}
