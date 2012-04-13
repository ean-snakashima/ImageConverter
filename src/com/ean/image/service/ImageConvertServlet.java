package com.ean.image.service;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;

import com.ean.image.core.ConvertRequestEntity;
import com.ean.image.core.ImageConverter;

public class ImageConvertServlet extends HttpServlet {
	private static final long serialVersionUID = 4908984244958958417L;

	static{
		System.setProperty("jmagick.systemclassloader","false");
	}
	
	private String localDir;
	
	public void init(){
		ServletConfig config = getServletConfig();
		localDir = config.getInitParameter("local_dir");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		ConvertRequestEntity requestEntity = toConvertRequestEntity(req);

		MagickImage originalImage = null;
		try {
			originalImage = getMagickImage(req);
		} catch (MagickException e) {
			e.printStackTrace();
		}
		
		if(originalImage == null){
			// Empty Image
			return;
		}
		
		byte[] newImage = new ImageConverter().convert(requestEntity, originalImage);
		
		if(newImage != null){
			resp.getOutputStream().write(newImage);
		}else{
			// Empty Image
		}
	}
	
	private ConvertRequestEntity toConvertRequestEntity(HttpServletRequest req){
		String maxHeightString = (String)req.getParameter("height");
		String maxWidthString = (String)req.getParameter("width");
		int height = Integer.parseInt(maxHeightString);
		int width = Integer.parseInt(maxWidthString);
		ConvertRequestEntity requestEntity = new ConvertRequestEntity(height, width);
		
		String qualityString = (String)req.getParameter("quality");
		if(qualityString != null && qualityString.isEmpty() == false){
			int quality = Integer.parseInt(qualityString);
			requestEntity.setQuality(quality);
		}
		return requestEntity;
	}
	
	private MagickImage getMagickImage(HttpServletRequest req) throws IOException, MagickException{
		String pathString = (String)req.getParameter("path");
		MagickImage originalImage = null; 
		if(isLocalStorage(pathString)){
			originalImage = getImage(new File(localDir + pathString));
		}else{
			originalImage = getImage(new URL(pathString));
		}
		return originalImage;
	}
	
	private boolean isLocalStorage(String path){
		return path != null && path.toLowerCase().startsWith("http") == false;
	}
	
	private MagickImage getImage(File file)throws IOException, MagickException{
		BufferedImage image = ImageIO.read(file);
		return getImage(image);
	}
	
	private MagickImage getImage(URL url)throws IOException, MagickException{
		BufferedImage image = ImageIO.read(url);
		return getImage(image);
	}
	
	private MagickImage getImage(BufferedImage image)throws IOException, MagickException{
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		OutputStream outputStream = new BufferedOutputStream(byteArrayOutputStream);
		
		try {
			ImageIO.write(image, "jpg", outputStream);
			
			ImageInfo imageInfo = new ImageInfo();
			MagickImage magickImage =  new MagickImage(imageInfo, byteArrayOutputStream.toByteArray());
			magickImage.getDimension().setSize(image.getWidth(), image.getHeight());
			return magickImage;
		} finally {
			outputStream.close();
			byteArrayOutputStream.close();
			
		}
	}


}
