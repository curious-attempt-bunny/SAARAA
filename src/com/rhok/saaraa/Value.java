package com.rhok.saaraa;

import org.json.JSONException;
import org.json.JSONObject;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class Value {

	private final String key;
	private View source;

	public Value(String key) {
		this.key = key;
	}

	public void setSource(View source) {
		this.source = source;
	}

	public void populate(JSONObject json) throws JSONException {
		String[] parts = key.split("\\.");
		for(int i=0; i<parts.length-1; i++) {
			if (!json.has(parts[i])) {
				JSONObject subObject = new JSONObject();
				json.put(parts[i], subObject);
			}
			json = json.getJSONObject(parts[i]);
		}
		json.put(parts[parts.length-1], getValue());
	}

	private Object getValue() {
		if (source instanceof EditText) {
			return ((EditText)source).getText();
		}
		return ((Spinner)source).getSelectedItem().toString();
	}

}
