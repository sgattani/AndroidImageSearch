package com.bootcamp.gattani.myimagesearch;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;

import com.bootcamp.gattani.myimagesearch.models.ImageResult;
import com.loopj.android.image.SmartImageView;

public class ImageDisplayActivity extends Activity {
	private ImageResult imageResult;
	private SmartImageView ivResult;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		imageResult = (ImageResult)getIntent().getSerializableExtra("result");
		ivResult = (SmartImageView)findViewById(R.id.ivResult);
		ivResult.setImageUrl(imageResult.getFullUrl());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_display, menu);
		return true;
	}

}
