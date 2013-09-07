package com.bootcamp.gattani.myimagesearch.models;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import android.annotation.SuppressLint;
import android.util.Log;

@SuppressLint("DefaultLocale")
public class JsonImageRequestFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5409133245958084305L;
	
	private String imgsz;
	private String imgcolor;
	private String imgtype;
	private String as_sitesearch;
	
	public static final String KEY_IMAGE_SIZE = "imgsz";
	public static final String KEY_IMAGE_COLOR = "imgcolor";
	public static final String KEY_IMAGE_TYPE = "imgtype";
	public static final String KEY_AS_SITE = "as_sitesearch";
	
	//should only be constructed using the builder
	private JsonImageRequestFilter(String imgsz, String imgcolor,
			String imgtype, String as_sitesearch) {
		super();
		this.imgsz = imgsz;
		this.imgcolor = imgcolor;
		this.imgtype = imgtype;
		this.as_sitesearch = as_sitesearch;
	}

	/**
	 * @return the imgsz
	 */
	public String getImgsz() {
		return imgsz;
	}
	
	/**
	 * @param imgsz the imgsz to set
	 */
	public void setImgsz(String imgsz) {
		this.imgsz = imgsz;
	}
	
	/**
	 * @return the imgcolor
	 */
	public String getImgcolor() {
		return imgcolor;
	}
	
	/**
	 * @param imgcolor the imgcolor to set
	 */
	public void setImgcolor(String imgcolor) {
		this.imgcolor = imgcolor;
	}
	
	/**
	 * @return the imgtype
	 */
	public String getImgtype() {
		return imgtype;
	}
	
	/**
	 * @param imgtype the imgtype to set
	 */
	public void setImgtype(String imgtype) {
		this.imgtype = imgtype;
	}
	
	/**
	 * @return the as_sitesearch
	 */
	public String getAs_sitesearch() {
		return as_sitesearch;
	}
	
	/**
	 * @param as_sitesearch the as_sitesearch to set
	 */
	public void setAs_sitesearch(String as_sitesearch) {
		this.as_sitesearch = as_sitesearch;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (imgsz != null) {
			builder.append("&imgsz=");
			builder.append(imgsz.toLowerCase());
		}
		if (imgcolor != null) {
			builder.append("&imgcolor=");
			builder.append(imgcolor.toLowerCase());
		}
		if (imgtype != null) {
			builder.append("&imgtype=");
			builder.append(imgtype.toLowerCase());
		}
		if (as_sitesearch != null) {
			builder.append("&as_sitesearch=");
			builder.append(as_sitesearch);
		}
		String filter = builder.toString();
		Log.d("DEBUG", filter);
		
		if(StringUtils.isBlank(filter)) filter = null;
		
		return filter;
	}
	
	public static JsonImageRequestFilterBuilder getJsonImageRequestFilterBuilder(){
		return new JsonImageRequestFilterBuilder();
	}
	
	public static class JsonImageRequestFilterBuilder {
		private String imageSize;
		private String imageColor;
		private String imageType;
		private String site;
		
		public JsonImageRequestFilterBuilder filterByImageSize(String size){
			imageSize = size;
			return this;
		}
		
		public JsonImageRequestFilterBuilder filterByImageDominantColor(String color){
			imageColor = color;
			return this;
		}
		
		public JsonImageRequestFilterBuilder filterByImageType(String type){
			imageType = type;
			return this;
		}
		
		public JsonImageRequestFilterBuilder filterBySite(String site){
			this.site = site;
			return this;
		}
		
		public JsonImageRequestFilter build(){
			imageSize = !isBlankOrNone(imageSize) ? imageSize : null;
			imageColor = !isBlankOrNone(imageColor) ? imageColor : null;
			imageType = !isBlankOrNone(imageType) ? imageType : null;
			site = !StringUtils.isBlank(site) ? site : null;

			
			if(imageSize == null && imageColor == null && imageType == null && site == null){
				return null;
			} else {
				return new JsonImageRequestFilter(imageSize,imageColor, imageType, site);				
			}
		}

		private boolean isBlankOrNone(String input){
			if(StringUtils.isBlank(input) || "none".equalsIgnoreCase(input)){
				return true;
			}
			
			return false;
		}
	}
	
}
