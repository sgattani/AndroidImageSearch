package com.bootcamp.gattani.myimagesearch.models;

import java.io.Serializable;

import android.net.Uri;

import com.bootcamp.gattani.myimagesearch.connectors.GImageJsonConnector;

public class JsonImageRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6098591771794976196L;
	
	private String baseUrl;
	private String q;
	private int start = 0;
	private int rsz = 8;
	private String v;
	private JsonImageRequestFilter filter;
	
	public JsonImageRequest(String baseUrl, String query, int start, int resultSize) {
		super();
		this.baseUrl = baseUrl;
		this.q = query;
		this.start = start;
		this.rsz = resultSize;
		this.v = "1.0";
	}
	
	/**
	 * @return the baseUrl
	 */
	public String getBaseUrl() {
		return baseUrl;
	}
	
	/**
	 * @return the query
	 */
	public String getQuery() {
		return q;
	}
	
	/**
	 * @return the index
	 */
	public int getStart() {
		return start;
	}
	
	/**
	 * @return the rsz
	 */
	public int getRsz() {
		return rsz;
	}
	
	/**
	 * @return the version??
	 */
	public String getVersion() {
		return v;
	}
	
	/**
	 * @param v the version to set
	 */
	public void setVersion(String v) {
		this.v = v;
	}
	
	/**
	 * @param start the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}
	
	/**
	 * @param filter 
	 */
	public void setFilter(JsonImageRequestFilter filter){
		this.filter = filter;
	}

	/**
	 * Clear the filter
	 */
	public void clearFilter(){
		this.filter = null;
	}
	
	/**
	 * returns the filter
	 * @return
	 */
	public JsonImageRequestFilter getFilter(){
		return this.filter;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (baseUrl != null) {
			builder.append(baseUrl);
			builder.append("?");
		}
		if (v != null) {
			builder.append("v=");
			builder.append(v);
			builder.append("&");
		}
		
		builder.append("start=");
		builder.append(start);
		builder.append("&");
		builder.append("rsz=");
		builder.append(rsz);
		builder.append("&");

		if (q != null) {
			builder.append("q=");
			builder.append(Uri.encode(q));
		}
		
		if(filter != null){
			builder.append(filter.toString());
		}
		
		return builder.toString();
	}
	
	public static JsonRequestBuilder getImageRequestBuilder(){
		return new JsonRequestBuilder();
	}
		
	public static class JsonRequestBuilder {
		private String baseUrl;
		private String query;
		private int index;
		private int rsz;

		public JsonRequestBuilder createGoogleImageRequest(){
			this.baseUrl = GImageJsonConnector.IMG_REQUEST_BASE_URL;
			return this;
		}
		
		public JsonRequestBuilder forQueryString(String queryString){
			this.query = queryString;
			return this;
		}

		public JsonRequestBuilder startingAtIndex(int index){
			this.index = index;
			return this;
		}

		public JsonRequestBuilder withResultSize(int resultSize){
			this.rsz = resultSize;
			return this;
		}
		
		public JsonImageRequest build(){
			return new JsonImageRequest(baseUrl, query, index, rsz);
		}

	}
}
