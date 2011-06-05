package com.rhok.saaraa;

import java.util.ArrayList;
import java.util.List;

import android.view.View;

public class Item {

	private List<View> views = new ArrayList<View>();

	public void add(View view) {
		views.add(0, view);
	}

	public List<View> getViews() {
		return views;
	}
}
