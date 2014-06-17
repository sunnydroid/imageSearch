package com.sunny.app.googleimagesearch;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sunny.app.googleimagesearch.adapter.ImageResultGridViewAdapter;
import com.sunny.app.googleimagesearch.dataModel.ImageResult;

public class SearchActivity extends Activity {

	final String GOOGLE_IMAGE_SEARCH_API = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&q=";
	
	EditText etSearch;
	Button btnSearch;
	GridView gvSearchResult;
	List<ImageResult> resultList;
	ImageResultGridViewAdapter gViewAdapter;
	String filterQuery;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupView();
		/*
		 * Create on click listener for grid view item to open 
		 * full image
		 */
		gvSearchResult.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult clickedImageResult = resultList.get(position);
				i.putExtra("url", clickedImageResult.getFullUrl());
				startActivity(i);
			}
			
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/* Inflate the custome menu created */
		getMenuInflater().inflate(R.menu.activity_settings, menu);
		return true;
	}

	private void setupView() {
		etSearch = (EditText) findViewById(R.id.etQuery);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		gvSearchResult = (GridView) findViewById(R.id.gvSearchResult);
		resultList = new ArrayList<>();
		/* create grid view adapter which maps image result to grid view
		 * and attach it to the grid view
		 */
		gViewAdapter = new ImageResultGridViewAdapter(this, resultList);
		gvSearchResult.setAdapter(gViewAdapter);
	}
	
	public void onSettingsClick(MenuItem mi) {
		Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
		Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
		startActivityForResult(i, 20);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		/* First check result code */
		if(resultCode == RESULT_OK) {
			if(requestCode == 20) {
				String result = data.getStringExtra("query");
				filterQuery = result;
				Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	/**
	 * Asynchronous search 
	 * @param v
	 */
	public void onImageSearch(View v) {
		String queryString = etSearch.getText().toString();
		StringBuilder sb = new StringBuilder();
		sb.append(GOOGLE_IMAGE_SEARCH_API);
		sb.append(Uri.encode(queryString));
		if(filterQuery != null) {
			sb.append("&");
			sb.append(filterQuery);
		}
		
		AsyncHttpClient asyncClient = new AsyncHttpClient();
		asyncClient.get(sb.toString(), new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(JSONObject response) {
				JSONArray imageJsonResults = null;
				try {
					imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
					resultList.clear();
					gViewAdapter.clear();
					resultList = ImageResult.fromJSONArray(imageJsonResults);
					Log.d("DEBUG", resultList.toString());
					gViewAdapter.addAll(resultList);
				} catch(JSONException je) {
					Log.d("ERROR", "Exception occured while fetching results: " + je.getMessage());
					je.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(Throwable t, JSONObject response) {
				t.printStackTrace();
			}
		});
	}
}
