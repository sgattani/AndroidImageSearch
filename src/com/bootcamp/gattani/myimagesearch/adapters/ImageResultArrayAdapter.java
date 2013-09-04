package com.bootcamp.gattani.myimagesearch.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.bootcamp.gattani.myimagesearch.R;
import com.bootcamp.gattani.myimagesearch.models.ImageResult;
import com.loopj.android.image.SmartImageView;

public class ImageResultArrayAdapter extends ArrayAdapter<ImageResult> {

	public ImageResultArrayAdapter(Context context, List<ImageResult> images) {
		super(context, R.layout.item_image_result,images);
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageResult imageMeta = this.getItem(position);
		SmartImageView ivImage;
		if(convertView == null){
			LayoutInflater inflator = LayoutInflater.from(getContext());
			ivImage = (SmartImageView)inflator.inflate(R.layout.item_image_result, parent, false);
		} else {
			ivImage = (SmartImageView) convertView;
			ivImage.setImageResource(android.R.color.transparent);
		}
		ivImage.setImageUrl(imageMeta.getThumbUrl());	
		return ivImage;
	}

}
