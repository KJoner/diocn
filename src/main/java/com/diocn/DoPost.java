package com.diocn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class DoPost {

	//发送一个GET请求,参数形式key1=value1&key2=value2...
    public static String post(String path,String params) throws Exception{
        HttpURLConnection httpConn=null;
        BufferedReader in=null;
        PrintWriter out=null;
        try {
            URL url=new URL(path);
            httpConn=(HttpURLConnection)url.openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            httpConn.setRequestProperty("Content-Type", "application/json");
            
            //发送post请求参数
            out=new PrintWriter(httpConn.getOutputStream());
            out.println(params);
            out.flush();
             
            //读取响应
            if(httpConn.getResponseCode()==HttpURLConnection.HTTP_OK){
                StringBuffer content=new StringBuffer();
                String tempStr="";
                in=new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
                while((tempStr=in.readLine())!=null){
                    content.append(tempStr);
                }
                return content.toString();
            }else{
                throw new Exception("请求出现了问题!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            in.close();
            out.close();
            httpConn.disconnect();
        }
        return null;
    }
    @Test
    public void test(){
    	try {
			String str = post("http://setting.smartisan.com/app/icon/","[{\"package\":\""+"com.smile.gifmaker"+"\"}]");
			System.out.println(str);
			String[] arr = str.split("\"logo\":\"");
			if(arr.length>1){
				String[] arr2 = arr[1].split("\",\"md5\":");
				System.out.println(arr2[0]);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
