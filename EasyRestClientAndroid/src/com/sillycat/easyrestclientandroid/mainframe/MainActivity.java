package com.sillycat.easyrestclientandroid.mainframe;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sillycat.easyrestclientandroid.R;
import com.sillycat.easyrestclientandroid.activity.impl.DemoActivity;
import com.sillycat.easyrestclientandroid.activity.impl.GetOnePersonActivity;
import com.sillycat.easyrestclientandroid.activity.impl.PersonListActivity;
import com.sillycat.easyrestclientandroid.activity.impl.ProductsListActivity;

public class MainActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //load the contents in XML files
        String[] options = getResources().getStringArray(R.array.main_options);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, options);
		
		setListAdapter(arrayAdapter);
    }

    protected void onListItemClick(ListView l, View v, int position, long id) 
	{
		Intent intent = new Intent();
		
		switch(position) 
		{
			case 0:
				intent.setClass(this, PersonListActivity.class);
				startActivity(intent);
				break;
			case 1:
				intent.setClass(this, GetOnePersonActivity.class);
				startActivity(intent);
				break;
			case 2:
				intent.setClass(this, DemoActivity.class);
				startActivity(intent);
				break;
			case 3:
				intent.setClass(this, ProductsListActivity.class);
				startActivity(intent);
				break;
			case 4:
				break;
	      	default:
	      		break;
		}
	}
}
