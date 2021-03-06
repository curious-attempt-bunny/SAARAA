package com.rhok.saaraa;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    
//	    final Spinner categories = initSpinner(R.id.category, CATEGORIES);
//	    //Spinner fireCategories = initSpinner(R.id.fireCategory, FIRE_CATEGORIES);
//	    final Spinner severityLevel = initSpinner(R.id.severityLevel, SEVERITY_LEVEL);
//	    final CheckBox peopleTrapped = (CheckBox) findViewById(R.id.peopleTrapped);
//	    final EditText whatDoYouSee = (EditText) findViewById(R.id.whatDoYouSee);
//	    LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//	    final TextView textLocation = (TextView) findViewById(R.id.textLocation);
//	    final Button addPhotoButton = (Button) findViewById(R.id.addPhoto);
//	    final Button submitButton = (Button) findViewById(R.id.submitButton);
//	    final EditText locationText = (EditText) findViewById(R.id.location);
//
//		  TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//		  final String cellNumber = telephonyManager.getLine1Number();
//	    
//	    categories.setOnItemSelectedListener(new OnItemSelectedListener() {
//	    	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//				//String toast = "The category is " +
//		        //	parent.getItemAtPosition(position).toString();
//				//toast(parent, toast);
//				toast(getCurrentFocus(), "Your cell# is "+cellNumber);
//			}
//			
//	    	public void onNothingSelected(AdapterView<?> arg0) {
//			}
//		});
//
//	    // TwiPic API Key: 44ca4886a1d4f4ab1003a6a109f8f2a8
//	    
//	    final Location[] currentLocation = new Location[1];
//	    // Define a listener that responds to location updates
//	    LocationListener locationListener = new LocationListener() {
//	        public void onLocationChanged(Location location) {
//	        	if (currentLocation[0] == null) {
//	        		toast(getCurrentFocus(), "Geo-location determined");
//		        	textLocation.setText("What's your location? (optional, we have your GPS location)");
//	        	}
//	        	// Called when a new location is found by the network location provider.
//	        	currentLocation[0] = location;
//	        }
//
//	        public void onStatusChanged(String provider, int status, Bundle extras) {}
//
//	        public void onProviderEnabled(String provider) {}
//
//	        public void onProviderDisabled(String provider) {}
//	      };
//
//	    addPhotoButton.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				Intent intent = new Intent(WelcomeActivity.this, CaptureActivity.class);
//		        startActivity(intent);
//			}
//		});
//	      
//	    // Register the listener with the Location Manager to receive location updates
//	    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//	    
//        submitButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Create a new HttpClient and Post Header
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpPost httppost = new HttpPost("http://saaraa.heroku.com/reports");
//                JSONObject json = new JSONObject();
//                try {
//                	// "2011-06-04 16:58:04 -0700"
//                	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
//                	Date now = new Date();
//                	String dateString = sdf.format(now);
//                	json.put("captured", dateString);
//                	JSONObject reporter = new JSONObject();
//                	reporter.put("phone", cellNumber);
//                	json.put("reporter", reporter);
//                	json.put("category", categories.getSelectedItem().toString());
//                	json.put("severity", severityLevel.getSelectedItem().toString());
//                	String peopleTrappedString = new String();
//                	JSONObject peopleTrappedJSON = new JSONObject();
//                	peopleTrappedJSON.put("peopletrapped", peopleTrapped.isChecked());
//                	json.put("metadata", peopleTrappedJSON);
//                	json.put("description", whatDoYouSee.getText());
//                	JSONObject loc = new JSONObject();
//                	boolean hasLoc = false;
//                	String currentAddress = locationText.getText().toString();
//                	if ( currentAddress.length() > 0 ) {
//                		loc.put("address", currentAddress);
//                		hasLoc = true;
//                	}
//                	if ( currentLocation[0] != null ) {
//                		double currentLatitude = currentLocation[0].getLatitude();
//                		double currentLongitude = currentLocation[0].getLongitude();
//                		loc.put("latitude", currentLatitude);
//                		loc.put("longitude", currentLongitude);
//                		hasLoc = true;
//                	}
//                	if ( hasLoc ) 
//                		json.put("location", loc);
//                	// added for debugging check on JSON string
//                	String x = json.toString();
//                	StringEntity se = new StringEntity( json.toString());  
//                    httppost.setEntity(se);
//
//                    // Execute HTTP Post Request
//                    HttpResponse response = httpclient.execute(httppost);
//                    int i = 1;
//                    HttpParams params = response.getParams();
//                    if ( response.getStatusLine().getStatusCode() != 200 ) {
//                    	// todo: handle errors
//                    }
//                    int j = i + 1;
//                } catch (ClientProtocolException e) {
//                    throw new RuntimeException(e);
//                } catch (IOException e) {
//                	throw new RuntimeException(e);
//                } catch (JSONException e) {
//                	throw new RuntimeException(e);
//				}
//
//                toast(v, "Sent report to server");
//            }
//        });

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