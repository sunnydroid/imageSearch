package com.sunny.app.googleimagesearch.dataModel;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult {
	private String tbUrl;
	private String fullUrl;
	
	
	public ImageResult(JSONObject jsonResult) {
		try {
			tbUrl = jsonResult.getString("tbUrl");
			fullUrl = jsonResult.getString("url");
		} catch (JSONException je) {
			this.tbUrl = null;
			this.fullUrl = null;
		}
	}
	/**
	 * @return the tbUrl
	 */
	public String getTbUrl() {
		return tbUrl;
	}
	/**
	 * @param tbUrl the tbUrl to set
	 */
	public void setTbUrl(String tbUrl) {
		this.tbUrl = tbUrl;
	}
	/**
	 * @return the fullUrl
	 */
	public String getFullUrl() {
		return fullUrl;
	}
	/**
	 * @param fullUrl the fullUrl to set
	 */
	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Url: " + this.fullUrl + "Thumbnail: " + this.tbUrl );
		return sb.toString();
	}
	
	public static List<ImageResult> fromJSONArray(JSONArray jsonArray) throws JSONException {
		List<ImageResult> imageResults = new ArrayList<>();
		for(int i = 0 ; i < jsonArray.length(); i++) {
			ImageResult imageResult = new ImageResult(jsonArray.getJSONObject(i));
			imageResults.add(imageResult);
		}
		
		return imageResults;
	}
	
}
