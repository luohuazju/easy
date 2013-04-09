package com.sillycat.easyrestclientandroid.activity.impl;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.sillycat.easyrestclientandroid.R;
import com.sillycat.easyrestclientandroid.activity.AbstractCameraActivity;

public class VideoCameraActivity extends AbstractCameraActivity {

	protected static final String TAG = VideoCameraActivity.class
			.getSimpleName();

	public static final int VIDEO_REQUEST = 10003;

	private static final String VIDEO_STORAGE_KEY = "viewvideo";

	private VideoView videoView;

	private static int currentPosition = 0;

	private Uri videoUri;

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			Log.d(TAG, "entering the call back...");
			videoUri = data.getData();
			videoView.setVideoURI(videoUri);
			videoView.requestFocus();
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "entering the onCreate...");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_camera);
		videoView = (VideoView) findViewById(R.id.video_window);

		Button vidBtn = (Button) findViewById(R.id.video_start);
		setBtnListenerOrDisable(vidBtn, mTakeVidOnClickListener,
				MediaStore.ACTION_VIDEO_CAPTURE);
		
		MediaController controller = new MediaController(this);
		videoView.setMediaController(controller);
		videoView.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				videoView.setBackgroundColor(Color.argb(0, 0, 255, 0));
			}
		});
		videoView.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				Toast.makeText(VideoCameraActivity.this, "The End.",
						Toast.LENGTH_LONG).show();
			}
		});
		
	}

	protected void onResume() {
		Log.d(TAG, "entering the onResume...");
		super.onResume();
		videoView.seekTo(currentPosition);
		videoView.start();
	}

	protected void onStop() {
		Log.d(TAG, "entering the onStop...");
		super.onStop();
		videoView.pause();
		currentPosition = videoView.getCurrentPosition();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d(TAG, "entering the onKeyDown...");
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finish();
			return true;
		}
		return false;
	}

	Button.OnClickListener mTakeVidOnClickListener = new Button.OnClickListener() {
		public void onClick(View v) {
			Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
			startActivityForResult(takeVideoIntent, VIDEO_REQUEST);
		}
	};

	protected void onSaveInstanceState(Bundle outState) {
		Log.d(TAG, "entering the onSaveInstanceState...");
		outState.putParcelable(VIDEO_STORAGE_KEY, videoUri);
		super.onSaveInstanceState(outState);
	}

	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		Log.d(TAG, "entering the onRestoreInstanceState...");
		super.onRestoreInstanceState(savedInstanceState);
		videoUri = savedInstanceState.getParcelable(VIDEO_STORAGE_KEY);
		videoView.setVideoURI(videoUri);
		videoView.requestFocus();

	}

}
