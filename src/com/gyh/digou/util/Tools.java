package com.gyh.digou.util;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Tools {

	
	public static String token="30a7XpGfBg8XXWbxR6iJwuAHc83kgW2OH/DcmVhi/kCIfC6VTy/uYiNpSc70gMN6AtFegNKztISetR20PHrqkGikCsJHxANgI5i+0tUaacKidaL6YzA1XyGdZKrrQqGQzQs5A+krqxgkpzqXmuzT2cKVrdzSE7lUVnMgmKKkR7g9jgfJIsR22w4uJ9TrtSNb9W4";
	
	
	
	
	private static String app_dir="digou";
	
	public static File makeImageDir(String name)
	{
		
		
		File dir=new File(Environment.getExternalStorageDirectory().getPath().toString()+File.separator+app_dir);
		
		
		if(!dir.exists())
			dir.mkdirs();
		
		
		
		return new File(dir, name);
		
		
	}
	public  static String getAddTime(String string) {
		
		
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return format.format(new Date(Long.parseLong(string)*1000));
		
		//return null;
	}
	
	 public static void setListViewHeightBasedOnChildren(ListView listView) {  
	        ListAdapter listAdapter = listView.getAdapter();   
	        if (listAdapter == null) {  
	            return;  
	        }  
	  
	        int totalHeight = 0;  
	        for (int i = 0; i < listAdapter.getCount(); i++) {  
	            View listItem = listAdapter.getView(i, null, listView);  
	            listItem.measure(0, 0);  
	            totalHeight += listItem.getMeasuredHeight();  
	        }  
	  
	        ViewGroup.LayoutParams params = listView.getLayoutParams();  
	        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));  
	        listView.setLayoutParams(params);  
	    }  
	public static String getToken()
	{
		return token;
	}
	public static void setToken(String token)
	{
		Tools.token=token;
	}
	
	
	public static String getBaseUrl()
	{
		
		return "http://www.cddego.com/api.php";
		
	}
	public static void saveToken(Context mContext,String token)
	{
		SharedPreferences share=mContext.getSharedPreferences("def",0);
		SharedPreferences.Editor ed=share.edit().putString("token",token);
		ed.commit();
	}
	public static String getToken(Context mContext)
	{
		SharedPreferences share=mContext.getSharedPreferences("def",0);
		return share.getString(token,null);
	}
	
	/**
	 * 根据手机分辨率从dp转成px
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f) - 15;
	}
	
	public static boolean hasSdcard(){
		String state = Environment.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED)){
			return true;
		}else{
			return false;
		}
	}
	
	
}
