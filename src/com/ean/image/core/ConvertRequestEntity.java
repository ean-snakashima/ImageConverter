package com.ean.image.core;

public class ConvertRequestEntity {
	public static enum IMG_TYPE {NONE, JPG, PNG}
	public static enum TYPE { CROP, RESIZE}
	
	private final int width;
	private final int height;
	private IMG_TYPE imageType = IMG_TYPE.JPG;
	private TYPE type = TYPE.RESIZE;
	private int quality = -1;

	public ConvertRequestEntity(int width, int hight) {
		super();
		this.width = width;
		this.height = hight;
	}

	public IMG_TYPE getImageType() {
		return imageType;
	}

	public void setImageType(IMG_TYPE imageType) {
		this.imageType = imageType;
	}

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
}
