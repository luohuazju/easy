package com.sillycat.easyrestclientandroid.util.factory.impl;

import java.io.File;

import android.os.Environment;

import com.sillycat.easyrestclientandroid.util.factory.AlbumStorageDirFactory;

public class BasicAlbumDirFactoryImpl implements AlbumStorageDirFactory {

	// Standard storage location for digital camera files
	private static final String CAMERA_DIR = "/dcim/";

	public File getAlbumStorageDir(String albumName) {
		return new File(Environment.getExternalStorageDirectory() + CAMERA_DIR
				+ albumName);
	}

}
