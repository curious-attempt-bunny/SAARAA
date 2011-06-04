package com.rhok.saaraa;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class WelcomeActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);

	    Spinner categories = initSpinner(R.id.category, CATEGORIES);
	    //Spinner fireCategories = initSpinner(R.id.fireCategory, FIRE_CATEGORIES);
	    initSpinner(R.id.severityLevel, SEVERITY_LEVEL);
	    
	    categories.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String toast = "The category is " +
		        	parent.getItemAtPosition(position).toString();
				toast(parent, toast);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	    
	    final Button button = (Button) findViewById(R.id.submitButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                toast(v, "Send report to server");
            }
        });

	}

	private Spinner initSpinner(int id, String[] selections) {
		Spinner spinner = (Spinner) findViewById(id);
	    ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, selections);
	    spinner.setAdapter(adapter);
		return spinner;
	}

    private void toast(View parent, String toast) {
		Toast.makeText(parent.getContext(), toast, Toast.LENGTH_LONG).show();
	}

	private static final String[] CATEGORIES = new String[] {
        "Fire", "Flood", "Gas Leak", "Down Wire", "Severe Weather", "Earthquake", "Other"
    };

	private static final String[] FIRE_CATEGORIES = new String[] {
        "Building", "Grass/Forest", "Vechicle", "Boat / Marina / Dock", "Other"
    };
	
	private static final String[] SEVERITY_LEVEL = new String[] {
        "Green", "Yellow", "Red"
    };
}