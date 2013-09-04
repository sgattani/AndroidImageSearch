package com.bootcamp.gattani.myimagesearch.models;

import java.io.Serializable;

public class JsonImageRequestFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5409133245958084305L;
	
	private String imgsz;
	private String imgcolor;
	private String imgtype;
	private String as_sitesearch;
	
	public JsonImageRequestFilter(String imgsz, String imgcolor,
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
			builder.append(imgsz);
		}
		if (imgcolor != null) {
			builder.append("&imgcolor=");
			builder.append(imgcolor);
		}
		if (imgtype != null) {
			builder.append("&imgtype=");
			builder.append(imgtype);
		}
		if (as_sitesearch != null) {
			builder.append("&as_sitesearch=");
			builder.append(as_sitesearch);
		}
		return builder.toString();
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
			return new JsonImageRequestFilter(imageSize,imageColor, imageType, site);
		}
		
	}
	
}
