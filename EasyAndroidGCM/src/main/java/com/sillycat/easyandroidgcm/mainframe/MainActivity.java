package com.sillycat.easyandroidgcm.mainframe;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sillycat.easyandroidgcm.R;
import com.sillycat.easyandroidgcm.activity.impl.GCMDemoActivity;

import java.util.List;

public class MainActivity extends ListActivity {

	protected static final String TAG = MainActivity.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// load the contents in XML files
		String[] options = getResources().getStringArray(R.array.main_options);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, options);

		setListAdapter(arrayAdapter);
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent();

		switch (position) {
		case 0:
			intent.setClass(this, GCMDemoActivity.class);
			if (isIntentSafe(intent)) {
				startActivity(intent);
			}
			break;
		default:
			break;
		}
	}

	private boolean isIntentSafe(Intent intent) {
		boolean flag = false;
		PackageManager manager = this.getPackageManager();
		List<ResolveInfo> activities = manager.queryIntentActivities(intent, 0);
		if (activities != null && activities.size() > 0) {
			Log.d(TAG, "The intent is save to invoke.");
			flag = true;
		} else {
			Log.d(TAG, "No where to find the match activity.");
		}
		return flag;
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.options_menu_all, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		boolean result = true;
		try {
			switch (item.getItemId()) {
			case R.id.item_gcm_demo:
				startActivity(new Intent(this, GCMDemoActivity.class));
				return true;
			default:
				return super.onOptionsItemSelected(item);
			}
		} catch (Exception error) {
			Log.d(TAG, "About_onOptionsItemSelected failed");
		}
		return result;
	}
}
