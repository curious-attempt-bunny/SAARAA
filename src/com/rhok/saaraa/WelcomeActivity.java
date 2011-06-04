package com.rhok.saaraa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class WelcomeActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);

	    final Spinner categories = initSpinner(R.id.category, CATEGORIES);
	    //Spinner fireCategories = initSpinner(R.id.fireCategory, FIRE_CATEGORIES);
	    final Spinner severityLevel = initSpinner(R.id.severityLevel, SEVERITY_LEVEL);
	    final EditText whatDoYouSee = (EditText) findViewById(R.id.whatDoYouSee);
	    
	    categories.setOnItemSelectedListener(new OnItemSelectedListener() {
	    	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String toast = "The category is " +
		        	parent.getItemAtPosition(position).toString();
				toast(parent, toast);
			}
			
	    	public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	    
	    final Button button = (Button) findViewById(R.id.submitButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Create a new HttpClient and Post Header
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://saaraa.heroku.com/reports");
                JSONObject json = new JSONObject();
                try {
                	json.put("Event Category", categories.getSelectedItem().toString());
                	json.put("Severity Level", severityLevel.getSelectedItem().toString());
                	json.put("What do you see", whatDoYouSee.getText());
                	String x = json.toString();
                	StringEntity se = new StringEntity( json.toString());  
                    httppost.setEntity(se);

                    // Execute HTTP Post Request
                    HttpResponse response = httpclient.execute(httppost);
                } catch (ClientProtocolException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                	throw new RuntimeException(e);
                } catch (JSONException e) {
                	throw new RuntimeException(e);
				}

                toast(v, "Sent report to server");
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