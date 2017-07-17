package com.diocn;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageDownload {
	
	    public static void main(String[] args) {
//	        String url = "http://localhost:8080/image/touxiang.png";
//	        downloadPicture(url);
	    	downloadPicture("http://icon.smartisan.com/drawable/com.smile.gifmaker/ic_launcher.png","com.smile.gifmaker.png");
	    }
	    //链接url下载图片
	    public static void downloadPicture(String urlList , String file) {
	        URL url = null;
	        int imageNumber = 0;

	        try {
	            url = new URL(urlList);
	            DataInputStream dataInputStream = new DataInputStream(url.openStream());

//	            String imageName =  "F:/test.jpg";
	            String imageName =  "G:/我的下载/锤子图标/"+file;

	            FileOutputStream fileOutputStream = new FileOutputStream(new File(imageName));
	            ByteArrayOutputStream output = new ByteArrayOutputStream();

	            byte[] buffer = new byte[1024];
	            int length;

	            while ((length = dataInputStream.read(buffer)) > 0) {
	                output.write(buffer, 0, length);
	            }
	            byte[] context=output.toByteArray();
	            fileOutputStream.write(output.toByteArray());
	            dataInputStream.close();
	            fileOutputStream.close();
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
