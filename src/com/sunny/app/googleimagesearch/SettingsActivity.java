package com.sunny.app.googleimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingsActivity extends Activity {
	
	private final String PARAMS_GLUE = "&";
	Spinner spImageSize;
	Spinner spImageColor;
	EditText etImageType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		setupViews();
	}
	
	private void setupViews() {
		spImageSize = (Spinner) findViewById(R.id.spSize);
		spImageColor = (Spinner) findViewById(R.id.spColor);
		etImageType = (EditText) findViewById(R.id.etImageType);
		ArrayAdapter<String> sizeSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.imageSize));
		ArrayAdapter<String> colorSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.imageColor));
		
		spImageSize.setAdapter(sizeSpinnerAdapter);
		spImageColor.setAdapter(colorSpinnerAdapter);
	}
	
	public void onSetFilters(View v) {
		Intent i = new Intent();
		StringBuilder sb = new StringBuilder();
		sb.append("imgcolor=");
		sb.append(spImageColor.getSelectedItem().toString());
		sb.append(PARAMS_GLUE);
		sb.append("imgsz=");
		sb.append(spImageSize.getSelectedItem().toString());
		if(etImageType.getText() != null) {
			sb.append(PARAMS_GLUE);
			sb.append("imgtype=");
			sb.append(etImageType.getText().toString());
		}
		i.putExtra("query", sb.toString());
		setResult(RESULT_OK, i);
		finish();
	}
}
