package com.ean.image.core;

import java.io.IOException;

import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;

public class ImageConverter {
	public byte[] convert(ConvertRequestEntity request, MagickImage image) throws IOException{
		
		try {
			int imgWidth = (int)image.getDimension().getWidth();
			int imgHeight = (int)image.getDimension().getHeight();
			
			int reqWidth = request.getWidth();
			int reqHeight = request.getHeight();
			
			int width = imgWidth < reqWidth ? imgWidth : reqWidth;
			int height = imgHeight < reqHeight ? imgHeight : reqHeight;
			
			double rationWidth = (double)width / imgWidth;
			double rationHeight = (double)height / imgHeight;
			
			if(rationWidth < rationHeight){
				height = (int)(rationWidth * imgHeight);
			}else{
				width = (int)(rationHeight * imgWidth);
			}
			
			MagickImage resized = image.scaleImage(width, height);
			ImageInfo imageInfo = new ImageInfo();
			imageInfo.setSize(width + "x" + height);
			
			int quality = request.getQuality();
			if(quality > -1){
				imageInfo.setQuality(quality);
			}
			return resized.imageToBlob(imageInfo);
		} catch (MagickException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		
		long start = System.currentTimeMillis();
		
		for(int i=0; i<10; i++)
		new ImageConverter().convert(new ConvertRequestEntity(30, 30), new MagickImage(new ImageInfo("C:\\temp\\IMG_0975.JPG")));
		
		
		long end = System.currentTimeMillis();
		
		System.out.println((end - start) + "msec");
		
//		String src = "C:\\temp\\IMG_0975.JPG";
//		String dst = "C:\\temp\\IMG_0975x.jpg";
//		MagickImage image = new MagickImage(new ImageInfo(src));
//		int width  = (int) image.getDimension().getWidth() / 2;
//		int height = (int) image.getDimension().getHeight() / 2;
//		MagickImage resized = image.scaleImage(width, height);
//		resized.setFileName(dst);
//		resized.writeImage(new ImageInfo());
//		
//		System.out.println("done");
		
		
	}

}
