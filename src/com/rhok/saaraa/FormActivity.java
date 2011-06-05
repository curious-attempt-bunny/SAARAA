package com.rhok.saaraa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;

public class FormActivity extends Activity {
    private RelativeLayout container;
	private List<Item> items;
	private Map<String, Runnable> mapValueToAction;
	private List<String> options;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        container = new RelativeLayout(this);
        container.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT));

        setContentView(container);

        items = new ArrayList<Item>();
        mapValueToAction = new LinkedHashMap<String, Runnable>();
        options = new ArrayList<String>();
        
		Item item = new Item();
		items.add(item);

		addEventCategory();
	}

	private void addEventCategory() {
		label("Event Category");
        addOption("Fire", new Runnable() { public void run() { addFireCategory(); } });
        addSpinner();
//		spinner("Fire", "Flood", "Gas Leak", "Downed Power Line", "Severe Weather", "Earthquake", "Zombie Apocalypse", "Other");
	}

	private void addFireCategory() {
		label("Fire Category");
		addOption("Building", new Runnable() { public void run() { addBuildingType(); } });
		addOption("Grass/Forest", new Runnable() { public void run() { addLocation(); } });
		addOption("Vehicle", new Runnable() { public void run() { addLocation(); } });
		addOption("Boat / Marina / Dock", new Runnable() { public void run() { addLocation(); } });
		addOption("Other", new Runnable() { public void run() { addLocation(); } });
        addSpinner();
	}
	
	private void addBuildingType() {
		label("Building Type");
		addOption("Public", new Runnable() { public void run() { addBuildings(
				"Fire station",
		        "Police",
		        "Hospital",
		        "School",
		        "Library",
		        "Community center",
		        "Services (gas, power, water)",
		        "Other"); } });
		addOption("Private", new Runnable() { public void run() { addBuildings(
				"Warehouse",
		        "Office - single level",
		        "Office - high rise"); } });
		addOption("Residential", new Runnable() { public void run() { addBuildings(
				"Single family house",
		        "Apartment building",
		        "High rise"); } });
		addSpinner();
	}
	
	private void addBuildings(String... buildings) {
		label("Building");
		Runnable action = new Runnable() {
			public void run() {
				addLocation();
			}
		};
        for(String building : buildings) {
        	addOption(building, action);
        }
        addSpinner();
	}
	
	private void addLocation() {
		label("Where are you?");
		
		EditText textEdit = new EditText(this);
        add(textEdit);
        
        Button addPhotoButton = new Button(this);
        addPhotoButton.setText("Add Photo");
        add(addPhotoButton);
        
        Button submitButton = new Button(this);
        submitButton.setText("Submit Report");
        add(submitButton);
	}
	
	private void addSpinner() {
		Spinner spinner = new Spinner(this);
		List<String> selections = new ArrayList<String>();
		selections.add("Select one...");
		selections.addAll(options);
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, selections.toArray(new String[]{}));
	    spinner.setAdapter(adapter);
	    final int here = items.size();
	    final Map<String, Runnable> map = mapValueToAction;
	    spinner.setOnItemSelectedListener(new SelectionHandler() {
			protected void onSelected(String selection) {
				unwindTo(here);
				if (map.containsKey(selection)) {
					Item item = new Item();
					items.add(item);
					
					map.get(selection).run();
				}
			}
		});
		add(spinner);
		mapValueToAction = new HashMap<String, Runnable>(); 
		options = new ArrayList<String>();
	}

	private void addOption(String value, Runnable runnable) {
		mapValueToAction.put(value, runnable);
		options.add(value);
	}
	
	private int unwindTo(int parent) {
		while(items.size() > parent) {
			Item item = items.remove(items.size()-1);	
			for(View view : item.getViews()) {
				container.removeView(view);
				index--;
			}
		}
		
		return items.size();
	}

	private TextView label(String text) {
		TextView label = new TextView(this);
        label.setText(text);
        add(label);
		return label;
	}

	int index = 1;
	
	private void add(final View child) {
		LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		if (index > 1) {
			layoutParams.addRule(RelativeLayout.BELOW, index);
		}
		index++;
		child.setId(index);
		
		container.addView(child, layoutParams);
		items.get(items.size()-1).add(child);
	}
}
