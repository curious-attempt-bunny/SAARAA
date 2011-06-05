package com.rhok.saaraa;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
	private String imageShackAPIKey = "57AFJOQW33e358bbe3e44578402635508a6236ca";
	
    private RelativeLayout container;
	private List<Item> items;
	private Map<String, Runnable> mapValueToAction;
	private List<String> options;
	private List<String> imageFilenames = new ArrayList<String>();
	private List<String> imageUrls = new ArrayList<String>();

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
        
	    addPhotoButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(FormActivity.this, CaptureActivity.class);
		        startActivityForResult(intent, 0);
			}
		});
	      
        add(addPhotoButton);
        
        Button submitButton = new Button(this);
        submitButton.setText("Submit Report");
        
        submitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				submitImages();
			}
		});

        add(submitButton);
	}
	
	protected void submitImages() {
		for (String filename : imageFilenames) {
			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost("http://www.imageshack.us/upload_api.php");
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
	            params.add(new BasicNameValuePair("key", imageShackAPIKey));
	            params.add(new BasicNameValuePair("rembar", "1"));
	            params.add(new BasicNameValuePair("public", "0"));
	            
	            StringBody stringBody = new StringBody(params.toString());
	            
	            FileBody bin = new FileBody(new File(filename), "image/jpeg");
	            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);  
	            reqEntity.addPart("fileupload", bin);
	            reqEntity.addPart("", stringBody);
	            
	            httppost.setEntity(reqEntity);
	            
                Log.i("IMAGE_UPLOAD", "uploading " + filename);

	            HttpResponse response = httpclient.execute(httppost);  
	            HttpEntity resEntity = response.getEntity();  
	            if (resEntity != null) {
	            	String responseString = EntityUtils.toString(resEntity);
	                Log.i("IMAGE_UPLOAD", responseString);
	                
	                String url = responseString.split("<image_link>")[1].split("</image_link>")[0];
	                imageUrls.add(url);
	            }
			}
			catch (Exception e) {
				Log.e("IMAGE_UPLOAD", e.getMessage());
			}
		}
		
		for (String url : imageUrls) {
			Log.i("IMAGES", url);
		}
	}

	private void addSpinner() {
		Spinner spinner = new Spinner(this);
		List<String> selections = new ArrayList<String>();
//		selections.add("Select one...");
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		imageFilenames.add(data.getStringExtra("com.rhok.saaraa.FileName"));
	}

}
