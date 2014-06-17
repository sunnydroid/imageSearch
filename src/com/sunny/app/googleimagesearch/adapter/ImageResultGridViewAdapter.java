package com.sunny.app.googleimagesearch.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.loopj.android.image.SmartImage;
import com.loopj.android.image.SmartImageView;
import com.sunny.app.googleimagesearch.R;
import com.sunny.app.googleimagesearch.dataModel.ImageResult;

public class ImageResultGridViewAdapter extends ArrayAdapter<ImageResult> {
	
	public ImageResultGridViewAdapter(Context context, List<ImageResult> imageResults) {
		super(context, R.layout.item_image_view, imageResults);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageResult imageInfo = this.getItem(position);
		SmartImageView ivImage;
		if(convertView == null) {
			LayoutInflater infaltor =  LayoutInflater.from(getContext());
			ivImage = (SmartImageView) infaltor.inflate(R.layout.item_image_view, parent, false);
		} else {
			ivImage = (SmartImageView) convertView;
			ivImage.setImageUrl(imageInfo.getTbUrl());
		}
		
		return ivImage;
	}
	
	

}
