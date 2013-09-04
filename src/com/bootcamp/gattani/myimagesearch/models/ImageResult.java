package com.bootcamp.gattani.myimagesearch.models;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult implements Serializable {
	/**
	 * generated uid
	 */
	private static final long serialVersionUID = -8439469124836428576L;
	private static final String KEY_FULL_URL = "url";
	private static final String KEY_THUMB_NAIL_URL = "tbUrl";
	
	private String fullUrl;
	private String thumbUrl;
	
	public ImageResult(JSONObject json){
		try{
			this.fullUrl = json.getString(KEY_FULL_URL);
			this.thumbUrl = json.getString(KEY_THUMB_NAIL_URL);
		} catch(JSONException jE){
			this.fullUrl = null;
			this.thumbUrl = null;
		}
	}
	
	/**
	 * @return the fullUrl
	 */
	public String getFullUrl() {
		return fullUrl;
	}
	/**
	 * @return the thumbUrl
	 */
	public String getThumbUrl() {
		return thumbUrl;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ImageResult [");
		if (fullUrl != null) {
			builder.append("fullUrl=");
			builder.append(fullUrl);
			builder.append(", ");
		}
		if (thumbUrl != null) {
			builder.append("thumbUrl=");
			builder.append(thumbUrl);
		}
		builder.append("]");
		return builder.toString();
	}

	public static ArrayList<ImageResult> fromJSONArray(JSONArray array){
		ArrayList<ImageResult> results = new ArrayList<ImageResult>();
		for(int i = 0; i < array.length(); i++ ){
			try {
				results.add(new ImageResult(array.getJSONObject(i)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return results;
	}
}
