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

	    Spinner spinner = (Spinner) findViewById(R.id.category);
	    ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, GENRES);
	    spinner.setAdapter(adapter);
	    
	    spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
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

    private void toast(View parent, String toast) {
		Toast.makeText(parent.getContext(), toast, Toast.LENGTH_LONG).show();
	}

	private static final String[] GENRES = new String[] {
        "Fire", "Flood", "Gas Leak", "Down Wire", "Severe Weather", "Earthquake", "Other"
    };

}