package com.bootcamp.gattani.myimagesearch;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
	private JsonImageRequestFilter filter;
	
	ArrayAdapter<String> imageSizeArrayAdapter;
	ArrayAdapter<String> colorFilterArrayAdapter;	
	ArrayAdapter<String> imageTypeArrayAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter_results);
		discoverElements();
		setupSpinnerItems();
	}

	@SuppressLint("DefaultLocale")
	public void onApplyClick(View v){
		String imgSize = StringUtils.isBlank(String.valueOf(sImageSize.getSelectedItem())) ? null : String.valueOf(sImageSize.getSelectedItem()).toLowerCase();
		String colorFilter = StringUtils.isBlank(String.valueOf(sColorFilter.getSelectedItem())) ? null : String.valueOf(sColorFilter.getSelectedItem()).toLowerCase();
		String imgType = StringUtils.isBlank(String.valueOf(sImageType.getSelectedItem())) ? null : String.valueOf(sImageType.getSelectedItem()).toLowerCase();
		
		if("none".equals(imgSize)){
			imgSize = null;
		}
		
		if("none".equals(colorFilter)){
			colorFilter = null;
		}

		if("none".equals(imgType)){
			imgType = null;
		}
		
		String siteFilter = StringUtils.isBlank(String.valueOf(etSiteFilter.getText())) ? null : String.valueOf(etSiteFilter.getText()).toLowerCase();

		filter = JsonImageRequestFilter.getJsonImageRequestFilterBuilder()
				.filterByImageSize(imgSize)
				.filterByImageDominantColor(colorFilter)
				.filterByImageType(imgType)
				.filterBySite(siteFilter)
				.build();
		
		Intent i = new Intent();
		i.putExtra("filter", filter);
		setResult(Activity.RESULT_OK, i);
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.filter_results, menu);
		return true;
	}
	
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
