package com.bootcamp.gattani.myimagesearch;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.bootcamp.gattani.myimagesearch.models.JsonImageRequestFilter;

public class FilterResultsActivity extends Activity {
	private static List<String> image_sizes = new ArrayList<String>();
	private static List<String> color_filters = new ArrayList<String>();
	private static List<String> image_types = new ArrayList<String>();
	private static String KEY_GOOGLE_IMAGE_FILTER = "FilterResultsActivity.googleImageFilter";
	
	static {
		image_sizes.add("None");
		image_sizes.add("Icon");
		image_sizes.add("Small");
		image_sizes.add("Medium");
		image_sizes.add("Large");
		image_sizes.add("XLarge");
		image_sizes.add("XXLarge");
		image_sizes.add("Huge");
		
		color_filters.add("None");
		color_filters.add("Black");
		color_filters.add("Blue");
		color_filters.add("Brown");
		color_filters.add("Gray");
		color_filters.add("Green");
		color_filters.add("Orange");
		color_filters.add("Pink");
		color_filters.add("Purple");
		color_filters.add("Red");
		color_filters.add("Teal");
		color_filters.add("White");
		color_filters.add("Yellow");
		
		image_types.add("None");
		image_types.add("Face");
		image_types.add("Photo");
		image_types.add("Clipart");
		image_types.add("Lineart");
	}

	private Spinner sImageSize;
	private Spinner sColorFilter;
	private Spinner sImageType;
	private EditText etSiteFilter;

	ArrayAdapter<String> imageSizeArrayAdapter;
	ArrayAdapter<String> colorFilterArrayAdapter;	
	ArrayAdapter<String> imageTypeArrayAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter_results);
		discoverElements();
		setupSpinnerItems();
		setupViewFromSavedPrefs();
	}

	@Override
	protected void onResume(){
		setupViewFromSavedPrefs();
		super.onResume();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.filter_results, menu);
		return true;
	}

	/**
	 * Constructs a new filter, saves it and returns it to listening activity
	 * @param v
	 */
	public void onApplyClick(View v){
		String imgSize = sImageSize.getSelectedItem().toString();
		String colorFilter = sColorFilter.getSelectedItem().toString();
		String imgType = sImageType.getSelectedItem().toString();
		String siteFilter = String.valueOf(etSiteFilter.getText()).toString();

		JsonImageRequestFilter filter = JsonImageRequestFilter.getJsonImageRequestFilterBuilder()
				.filterByImageSize(imgSize)
				.filterByImageDominantColor(colorFilter)
				.filterByImageType(imgType)
				.filterBySite(siteFilter)
				.build();
		
		//save the new filter
		saveFilter(getSharedPreferences(KEY_GOOGLE_IMAGE_FILTER, MODE_PRIVATE), filter);
		
		//send it to the initiating activity
		Intent i = new Intent();
		i.putExtra("filter", filter);
		setResult(Activity.RESULT_OK, i);
		finish();
	}

	/**
	 * Constructs a new NULL filter, saves it and returns it to listening activity
	 * @param v
	 */
	public void onClearClick(View v){
		JsonImageRequestFilter filter = null;
		//save the new filter
		saveFilter(getSharedPreferences(KEY_GOOGLE_IMAGE_FILTER, MODE_PRIVATE), filter);
				
		//send it to the initiating activity
		Intent i = new Intent();
		i.putExtra("filter", filter);
		setResult(Activity.RESULT_OK, i);
		finish();
	}
	
	/** view and persistence helpers*/
	
	private void setupViewFromSavedPrefs(){
		SharedPreferences pref = getSharedPreferences(KEY_GOOGLE_IMAGE_FILTER, MODE_PRIVATE);
		JsonImageRequestFilter savedFilter = getSavedFilter(pref);
		setupViewWithFilter(savedFilter);	
	}

	private void setupViewWithFilter(JsonImageRequestFilter filter){
		if(filter == null){
			sImageSize.setSelection(0, true);
			sColorFilter.setSelection(0, true);
			sImageType.setSelection(0, true);
			etSiteFilter.setText("");
			
		} else {
			sImageSize.setSelection(imageSizeArrayAdapter.getPosition(filter.getImgsz()), true);
			sColorFilter.setSelection(colorFilterArrayAdapter.getPosition(filter.getImgcolor()), true);
			sImageType.setSelection(imageTypeArrayAdapter.getPosition(filter.getImgtype()), true);
			etSiteFilter.setText(filter.getAs_sitesearch());
		}
	}

	private JsonImageRequestFilter getSavedFilter(SharedPreferences pref){
		if(pref == null){
			return null;
		}
		//pull values from shared preferences
		String imageColor = pref.getString(JsonImageRequestFilter.KEY_IMAGE_COLOR, "None");
		String imageType = pref.getString(JsonImageRequestFilter.KEY_IMAGE_TYPE, "None");
		String imageSize = pref.getString(JsonImageRequestFilter.KEY_IMAGE_SIZE, "None");
		String imageSite = pref.getString(JsonImageRequestFilter.KEY_AS_SITE, "");
		
		JsonImageRequestFilter savedFilter = JsonImageRequestFilter.getJsonImageRequestFilterBuilder()
													.filterByImageDominantColor(imageColor)
													.filterByImageSize(imageSize)
													.filterByImageType(imageType)
													.filterBySite(imageSite)
													.build();
		
		return savedFilter;
	}

	private void saveFilter(SharedPreferences pref, JsonImageRequestFilter filter){
		if(pref == null){
			return;
		}
		//save preferences
		SharedPreferences.Editor edt = pref.edit();
		edt.putString(JsonImageRequestFilter.KEY_IMAGE_COLOR, filter != null ? filter.getImgcolor() : "None");
		edt.putString(JsonImageRequestFilter.KEY_IMAGE_TYPE, filter != null ? filter.getImgtype() : "None");
		edt.putString(JsonImageRequestFilter.KEY_IMAGE_SIZE, filter != null ? filter.getImgsz() : "None");
		edt.putString(JsonImageRequestFilter.KEY_AS_SITE, filter != null ? filter.getAs_sitesearch() : "");
		edt.commit();
	}
	
	/** Initial setup helpers*/
	
	private void discoverElements(){
		sImageSize = (Spinner)findViewById(R.id.sImageSize);
		sColorFilter = (Spinner)findViewById(R.id.sColorFilter);
		sImageType = (Spinner)findViewById(R.id.sImageType);
		etSiteFilter = (EditText)findViewById(R.id.etSiteFilter);
	}
	
	private void setupSpinnerItems(){
		imageSizeArrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, image_sizes);
		imageSizeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sImageSize.setAdapter(imageSizeArrayAdapter);
		
		colorFilterArrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, color_filters);
		colorFilterArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sColorFilter.setAdapter(colorFilterArrayAdapter);
		
		imageTypeArrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, image_types);
		imageTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sImageType.setAdapter(imageTypeArrayAdapter);
	}

}
