package com.sunny.app.googleimagesearch;

import com.loopj.android.image.SmartImageView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ImageDisplayActivity extends Activity {

	SmartImageView ivImageResultFull;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		ivImageResultFull = (SmartImageView) findViewById(R.id.ivResultFull);
		String fullUrl = getIntent().getStringExtra("url");
		ivImageResultFull.setImageUrl(fullUrl);
	}
}
