package com.sillycat.easyrestclientandroid.activity.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sillycat.easyrestclientandroid.R;
import com.sillycat.easyrestclientandroid.activity.AbstractCameraActivity;
import com.sillycat.easyrestclientandroid.util.factory.AlbumStorageDirFactory;
import com.sillycat.easyrestclientandroid.util.factory.impl.BasicAlbumDirFactoryImpl;
import com.sillycat.easyrestclientandroid.util.factory.impl.FroyoAlbumDirFactoryImpl;

public class PhotoCameraActivity extends AbstractCameraActivity {

	protected static final String TAG = PhotoCameraActivity.class
			.getSimpleName();

	private static final String BITMAP_STORAGE_KEY = "viewbitmap";
	private static final String ALBUM_NAME = "photo_demo";
	private static final String JPEG_FILE_PREFIX = "IMG_";
	private static final String JPEG_FILE_SUFFIX = ".jpg";

	public static final int BIG_PHOTO_REQUEST = 10001;

	public static final int SMALL_PHOTO_REQUEST = 10002;

	private ImageView photoView;
	private Bitmap photoBitmap;

	private String currentPhotoPath;

	private AlbumStorageDirFactory storageDirFactory = null;

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case BIG_PHOTO_REQUEST: {
			if (resultCode == RESULT_OK) {
				if (currentPhotoPath != null) {
					setPic();  //SCALA the picture
					galleryAddPic(); //send the picture announcement
					currentPhotoPath = null;
				}
			}
			break;
		} // big photo

		case SMALL_PHOTO_REQUEST: {
			if (resultCode == RESULT_OK) {
				Bundle extras = data.getExtras();
				photoBitmap = (Bitmap) extras.get("data");
				photoView.setImageBitmap(photoBitmap);
				photoView.setVisibility(View.VISIBLE);
			}
			break;
		} // small photo
		}
	}

	Button.OnClickListener mTakePicOnClickListener = new Button.OnClickListener() {
		public void onClick(View v) {
			File f = null;
			Intent takePictureIntent = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);
			try {
				f = setUpPhotoFile();
				currentPhotoPath = f.getAbsolutePath();
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(f));
				startActivityForResult(takePictureIntent, BIG_PHOTO_REQUEST);
			} catch (IOException e) {
				Log.d(TAG, e.getMessage());
				f = null;
				currentPhotoPath = null;
			}
		}
	};

	Button.OnClickListener mTakePicSmallOnClickListener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent takePictureIntent = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(takePictureIntent, SMALL_PHOTO_REQUEST);
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_camera);

		photoView = (ImageView) findViewById(R.id.photo_picture);

		Button picBtn = (Button) findViewById(R.id.photo_big);
		setBtnListenerOrDisable(picBtn, mTakePicOnClickListener,
				MediaStore.ACTION_IMAGE_CAPTURE);

		Button picSBtn = (Button) findViewById(R.id.photo_small);
		setBtnListenerOrDisable(picSBtn, mTakePicSmallOnClickListener,
				MediaStore.ACTION_IMAGE_CAPTURE);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			storageDirFactory = new FroyoAlbumDirFactoryImpl();
		} else {
			storageDirFactory = new BasicAlbumDirFactoryImpl();
		}
	}

	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(BITMAP_STORAGE_KEY, photoBitmap);
		super.onSaveInstanceState(outState);
	}

	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		photoBitmap = savedInstanceState.getParcelable(BITMAP_STORAGE_KEY);
		photoView.setImageBitmap(photoBitmap);
	}

	private String getAlbumName() {
		return ALBUM_NAME;
	}

	private File getAlbumDir() {
		File storageDir = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			storageDir = storageDirFactory.getAlbumStorageDir(getAlbumName());
			if (storageDir != null) {
				if (!storageDir.mkdirs()) {
					if (!storageDir.exists()) {
						Log.d(TAG, "failed to create directory");
						return null;
					}
				}
			}
		} else {
			Log.d(TAG, "External storage is not mounted READ/WRITE.");
		}
		return storageDir;
	}

	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
		File albumF = getAlbumDir();
		File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX,
				albumF);
		return imageF;
	}

	private File setUpPhotoFile() throws IOException {
		File f = createImageFile();
		currentPhotoPath = f.getAbsolutePath();
		return f;
	}

	private void setPic() {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
		int targetW = photoView.getWidth();
		int targetH = photoView.getHeight();

		/* Get the size of the image */
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
		int scaleFactor = 1;
		if ((targetW > 0) && (targetH > 0)) {
			scaleFactor = Math.min(photoW / targetW, photoH / targetH);
		}

		/* Set bitmap options to scale the image decode target */
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
		Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

		/* Associate the Bitmap to the ImageView */
		photoView.setImageBitmap(bitmap);
		photoView.setVisibility(View.VISIBLE);
	}

	private void galleryAddPic() {
		Intent mediaScanIntent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File f = new File(currentPhotoPath);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		this.sendBroadcast(mediaScanIntent);
	}



}
