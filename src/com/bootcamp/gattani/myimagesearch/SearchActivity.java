package com.bootcamp.gattani.myimagesearch;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.bootcamp.gattani.myimagesearch.adapters.ImageResultArrayAdapter;
import com.bootcamp.gattani.myimagesearch.connectors.GImageJsonConnector;
import com.bootcamp.gattani.myimagesearch.models.ImageResult;
import com.bootcamp.gattani.myimagesearch.models.JsonImageRequest;
import com.bootcamp.gattani.myimagesearch.models.JsonImageRequestFilter;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {
	private static final int FILTER_REQUEST_ID = 101;
	private GridView gvSearchResults;
	private Button btnSearch;
	private EditText etSearchString;
	private ImageResultArrayAdapter imageAdapter;
	
	private List<ImageResult> imageResults;
	private JsonImageRequest imgRequest;
	private JsonImageRequestFilter imgFilter;
	
	private static boolean stopLoading = false;
	
	private Toast loading;
	private Toast eor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupElements();
		setOnImageClickListener();
		setupScrolling();
		imageResults = new ArrayList<ImageResult>();
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvSearchResults.setAdapter(imageAdapter);
		
		loading = Toast.makeText(getBaseContext(), "loading ...", Toast.LENGTH_SHORT);
		eor = Toast.makeText(getBaseContext(), "End of Results", Toast.LENGTH_SHORT);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent i = new Intent(getBaseContext(), FilterResultsActivity.class);
			startActivityForResult(i, FILTER_REQUEST_ID);
			break;
		default:
			break;
		}
		return true;
	}

	/* click handlers **/
	public void onSearchButtonClick(View v){
		String searchString = etSearchString.getText().toString();
		imgRequest = JsonImageRequest.getImageRequestBuilder()
						.createGoogleImageRequest()
						.forQueryString(searchString)
						.startingAtIndex(0)
						.withResultSize(8)
						.build();

		if(imgFilter != null){
			imgRequest.setFilter(imgFilter);
		}
		
		//get new images
		getImages(true);
		
		//force hide the keyboard
		hideSoftKeyBoard();
	}
	       
    private void setOnImageClickListener(){
    	gvSearchResults.setOnItemClickListener( new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position, long rowId) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);
			}
		});
    }
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == FILTER_REQUEST_ID) {
			if (resultCode == Activity.RESULT_OK) {
				imgFilter = (JsonImageRequestFilter)data.getSerializableExtra("filter");
			}
			
			if(imgRequest != null){
				if(imgFilter != null){
					imgRequest.setFilter(imgFilter);					
				} else {
					imgRequest.clearFilter();
				}

				//disable scroll loading till we refetch images
				stopLoading = true;
				
				//clear previous images here since we are applying filter
				getImages(true);
			}
		}
	}

    private void setupScrolling(){
    	gvSearchResults.setOnScrollListener(new OnScrollListener() {
    	    private int lastFirstVisibleItem;
    	    private boolean isScrollingUp = false;

    		@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			      if(scrollState == 0) 
			    	  Log.i("a", "scrolling stopped...");

			      if (view.getId() == gvSearchResults.getId()) {
			    	  final int currentFirstVisibleItem = gvSearchResults.getFirstVisiblePosition();
			    	  if (currentFirstVisibleItem > lastFirstVisibleItem) {
			    		  isScrollingUp = false;
			    		  Log.i("a", "scrolling down...");
			    	  } else if (currentFirstVisibleItem < lastFirstVisibleItem) {
			    		  isScrollingUp = true;
			    		  Log.i("a", "scrolling up...");
			    	  }

			    	  lastFirstVisibleItem = currentFirstVisibleItem;
			      }
    		}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				int lastInScreen = firstVisibleItem + visibleItemCount;
				if((lastInScreen == totalItemCount) && totalItemCount > 0 && !stopLoading && !isScrollingUp){
					getNextImages();
				}				
			}
		});
    }
    
    private void getImages(final boolean clear){
		//redo the search
		GImageJsonConnector.getInstance().getAsync(imgRequest, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response){
				if(clear) {
					imageAdapter.clear();
				}
				populateImageResults(response);
			}
		});
		
		if(StringUtils.isNotBlank(imgRequest.getQuery())){
			Toast.makeText(this, "Searching for " + imgRequest.getQuery() + " ...", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "Please enter search term", Toast.LENGTH_SHORT).show();			
		}
    }
    
    private void getNextImages(){
    	GImageJsonConnector.getInstance().getNextPageAsync(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response){
				populateImageResults(response);
			}			
		});
    	
		if (!stopLoading) loading.show(); else loading.cancel();	
    }
    
    /**
     * Populates the image results from json response
     * @param response
     */
    private void populateImageResults(JSONObject response){
    	JSONArray imageJsonResults = null;
    	try{
    		imageJsonResults = response.getJSONObject("responseData")
    							.getJSONArray("results");
    		imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
    		Log.d("DEBUG", imageResults.toString());
    		stopLoading = false;
    	} catch(JSONException e){
			stopLoading = true;
			loading.cancel(); 
			eor.show();
    	}
    }

    
	/* helpers **/
	
	/**
	 * Sets up the activity view elements
	 */
	private void setupElements(){
		gvSearchResults = (GridView)findViewById(R.id.gvSearchResults);
		btnSearch = (Button)findViewById(R.id.btnSearch);
		etSearchString = (EditText)findViewById(R.id.etSearchString);
	}

	/**
	 * Hides soft keyboard
	 */
    private void hideSoftKeyBoard(){
    	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    	imm.hideSoftInputFromWindow(etSearchString.getWindowToken(), 0);
    }

}
