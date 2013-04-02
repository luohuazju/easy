package com.sillycat.easyrestclientandroid.dao.db;

import com.sillycat.easyrestclientandroid.model.Product;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BasicDBHelper extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;

	public static final String DATABASE_NAME = "sillycat.db";

	public BasicDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Product.SQL_CREATE);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(Product.SQL_DROP);
		onCreate(db);
	}
	
}
