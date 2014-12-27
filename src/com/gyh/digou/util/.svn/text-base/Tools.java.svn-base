package com.gyh.digou.util;

import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

public class Tools {

	
	public static String token="30a7XpGfBg8XXWbxR6iJwuAHc83kgW2OH/DcmVhi/kCIfC6VTy/uYiNpSc70gMN6AtFegNKztISetR20PHrqkGikCsJHxANgI5i+0tUaacKidaL6YzA1XyGdZKrrQqGQzQs5A+krqxgkpzqXmuzT2cKVrdzSE7lUVnMgmKKkR7g9jgfJIsR22w4uJ9TrtSNb9W4";
	public static String getBaseUrl()
	{
		
		return "http://www.cddego.com/api.php";
		
	}
	public static String getToken()
	{
		return token;
	}
	public static void setToken(String token)
	{
		Tools.token=token;
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
	public static boolean hasSdcard(){
		String state = Environment.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED)){
			return true;
		}else{
			return false;
		}
	}
	
	
}
