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
import android.widget.CheckBox;
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
        addOption("Fire", new Runnable() { public void run() {
        	addArePeopleTrapped();
    		addArePeopleInjured();
    		addSeverity( new Runnable() { public void run() { addFireCategory(); } } );
        } });
        addOption("Flood", new Runnable() { public void run() {
        	addArePeopleTrapped();
    		addArePeopleInjured();
    		addSeverity( new Runnable() { public void run() {
    			checkBox("Houses in Flood?");
    			checkBox("Over Roadway?");
    			label("Levee");
    			checkBox("Damage?");
    			checkBox("Overtopping?");
    			checkBox("Failure/Breach?");
    			label("Dam");
    			checkBox("Damage?");
    			checkBox("Overtopping?");
    			checkBox("Failure/Breach?");
    			addLocation();
    		} } );
        } });
        addOption("Gas Leak", new Runnable() { public void run() {
        	addArePeopleTrapped();
    		addArePeopleInjured();
    		addSeverity( new Runnable() { public void run() { 
    			checkBox("Visible or Audible Source?");
    			checkBox("Is There Fire?");
    			checkBox("Are structures threatened?");
    			addLocation();
    		} } );
        } });
        addOption("Downed Power Line", new Runnable() { public void run() {
        	addArePeopleTrapped();
    		addArePeopleInjured();
    		addSeverity( new Runnable() { public void run() { 
    			checkBox("Is There Sparking?");
    			label("Is the Line...");
    			Runnable action = new Runnable() {
    				public void run() {
    					checkBox("Is the Line Touching a Vehicle With People Inside?");
    					addLocation();
    				}
    			};
    			addOption("On the Road", action);
    			addOption("Front Yard", action);
    			addOption("Back Yard", action);
    			addSpinner();
    		} } );
        } });
//        addOption("Severe Weather", new Runnable() { public void run() {
//        	// TODO
//        } });
        addOption("Earthquake", new Runnable() { public void run() {
        	addArePeopleTrapped();
    		addArePeopleInjured();
    		addBuildingType(new Runnable() {
    			public void run() {
    				checkBox("Is There Fire?");
    				addSeverity(new Runnable() {
    					public void run() {
    						checkBox("Are Structures Threatened?");
    						addLocation();
    					}
					} );
    			}
    		});
        } });
        addSpinner();
	}

	protected void addSeverity(Runnable action) {
		label("Severity Level");
		addOption("Green", action);
		addOption("Yellow", action);
		addOption("Red", action);
		addSpinner();
	}

	private void addFireCategory() {
		label("Fire Category");
		addOption("Building", new Runnable() { public void run() { addBuildingType(
				new Runnable() { public void run() { addLocation(); } }
		); } });
		addOption("Grass/Forest", new Runnable() { public void run() { addLocation(); } });
		addOption("Vehicle", new Runnable() { public void run() { addLocation(); } });
		addOption("Boat / Marina / Dock", new Runnable() { public void run() { addLocation(); } });
		addOption("Other", new Runnable() { public void run() { addLocation(); } });
        addSpinner();
	}
	
	private void addArePeopleInjured() {
		String label = "Are People Injured?";
		checkBox(label);
	}

	private void checkBox(String label) {
		CheckBox checkBox = new CheckBox(this);
		checkBox.setText(label);
		add(checkBox);
	}

	private void addArePeopleTrapped() {
		CheckBox checkBox = new CheckBox(this);
		checkBox.setText("Are People Trapped?");
		add(checkBox);
	}

	private void addBuildingType(final Runnable lastAction) {
		label("Building Type");
		addOption("Public", new Runnable() { public void run() { addBuildings( lastAction,
				"Fire station",
		        "Police",
		        "Hospital",
		        "School",
		        "Library",
		        "Community center",
		        "Services (gas, power, water)",
		        "Other"); } });
		addOption("Private", new Runnable() { public void run() { addBuildings( lastAction,
				"Warehouse",
		        "Office - single level",
		        "Office - high rise"); } });
		addOption("Residential", new Runnable() { public void run() { addBuildings( lastAction,
				"Single family house",
		        "Apartment building",
		        "High rise"); } });
		addSpinner();
	}
	
	private void addBuildings(final Runnable lastAction, String... buildings) {
		label("Building");
		Runnable action = new Runnable() {
			public void run() {
				lastAction.run();
			}
		};
        for(String building : buildings) {
        	addOption(building, action);
        }
        addSpinner();
	}
	
	private void addLocation() {
		label("What do you see?");
		
		EditText textEdit = new EditText(this);
        add(textEdit);
        
		label("Where are you?");
		
		textEdit = new EditText(this);
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
		//selections.add("Select one...");
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
