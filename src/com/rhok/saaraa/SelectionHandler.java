package com.rhok.saaraa;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public abstract class SelectionHandler implements OnItemSelectedListener {
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		String item = parent.getSelectedItem().toString();
		onSelected(item);
	}
	
	protected abstract void onSelected(String item);

	public void onNothingSelected(AdapterView<?> arg0) {
	}
}
