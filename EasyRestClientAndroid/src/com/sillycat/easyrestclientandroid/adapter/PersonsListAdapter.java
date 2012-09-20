package com.sillycat.easyrestclientandroid.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sillycat.easyapi.rest.json.Person;
import com.sillycat.easyrestclientandroid.R;

public class PersonsListAdapter extends BaseAdapter {

	private List<Person> _persons;
	
	private final LayoutInflater _layoutInflater;

	public PersonsListAdapter(Context context, List<Person> _persons) {
		this._persons = _persons;
		this._layoutInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return _persons.size();
	}

	public Person getItem(int position) {
		if (_persons != null && _persons.size() > position) {
			return _persons.get(position);
		}
		return null;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Person person = getItem(position);

		View view = convertView;

		if (view == null) {
			view = _layoutInflater.inflate(R.layout.persons_list_item, parent,
					false);
		}

		TextView t = (TextView) view.findViewById(R.id.person_name);
		t.setText(person.getPersonName());

		return view;
	}

}
