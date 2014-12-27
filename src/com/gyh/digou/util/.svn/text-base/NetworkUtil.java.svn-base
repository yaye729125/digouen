package com.gyh.digou.util;

import java.util.ArrayList;
import java.util.List;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetworkUtil {

	public static String post(String url,List<NameValuePair> params)
	{
		HttpPost httpRequest = new HttpPost(url);
		
		String result =null;
		
		try {
			
			
			httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
			
			//httpResponse.
			HttpEntity entity=httpResponse.getEntity();
			result = EntityUtils.toString(entity,"utf-8");
			
			
		}catch (Exception e) {
			result="error";
		}
		
		return result;
		
	}
	
	
	
	
	
	
	public static boolean isNetworkAvailable(Context context) {   
        ConnectivityManager cm = (ConnectivityManager) context   
                .getSystemService(Context.CONNECTIVITY_SERVICE);   
        if (cm == null) {  
        	
        }
        else {
        	//如果仅仅是用来判断网络连接则可以使用 cm.getActiveNetworkInfo().isAvailable();  
            NetworkInfo[] info = cm.getAllNetworkInfo();   
            if (info != null) {   
                for (int i = 0; i < info.length; i++) {   
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {   
                        return true;   
                    }   
                }   
            }   
        	
        	//return cm.getActiveNetworkInfo().isAvailable();
        }   
        return false;   
    }
	
	
	
	
}
