package com.bootcamp.gattani.myimagesearch.connectors;

import android.util.Log;

import com.bootcamp.gattani.myimagesearch.models.JsonImageRequest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class GImageJsonConnector {
	public static final String IMG_REQUEST_BASE_URL =  "https://ajax.googleapis.com/ajax/services/search/images";
	private static GImageJsonConnector instance;
	private JsonImageRequest request;
	private AsyncHttpClient client;
	
	private GImageJsonConnector(){
		client = new AsyncHttpClient();
	}
		
	public static synchronized GImageJsonConnector getInstance(){
		if(instance == null){
			instance = new GImageJsonConnector();
		}
		
		return instance;
	}
	
	public void getAsync(JsonImageRequest req, JsonHttpResponseHandler handler){
		request = req;
		Log.d("DEBUG", request.toString());
		client.get(request.toString(), handler);
	}

	public void getNextPageAsync(JsonHttpResponseHandler handler){
		if(request == null) return;
		Log.d("DEBUG", request.toString());
		request.setStart(request.getStart() + request.getRsz());
		client.get(request.toString(), handler);
	}

	public void getPrevPageAsync(JsonHttpResponseHandler handler){
		if(request == null) return;
		Log.d("DEBUG", request.toString());
		request.setStart(request.getStart() - request.getRsz());
		client.get(request.toString(), handler);
	}
}
