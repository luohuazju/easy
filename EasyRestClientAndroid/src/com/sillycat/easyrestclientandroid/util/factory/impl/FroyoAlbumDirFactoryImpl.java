package com.sillycat.easyrestclientandroid.util.factory.impl;

import java.io.File;

import android.os.Environment;

import com.sillycat.easyrestclientandroid.util.factory.AlbumStorageDirFactory;

public class FroyoAlbumDirFactoryImpl implements AlbumStorageDirFactory {

	public File getAlbumStorageDir(String albumName) {
		return new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				albumName);
	}

}
