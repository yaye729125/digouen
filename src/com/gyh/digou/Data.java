package com.gyh.digou;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.net.Uri;

import com.gyh.digou.bean.LoginInfo;



 /**
  * 
  * 静态数据类
 * @author lbc
 *
 */
public class Data {

	
	
	
	public static String user="user";
	public static String shoper="shoper";
	
	public static String getUserType()
	{
		
		//System.out.println(info);
		
		if(null==Data.info)
		{
			return "user";
		}else
		{
			if(null==Data.info.getData().getStore_id())
			{
				return "user";
			}else
			{
				String store_id=Data.info.getData().getStore_id();
				if(store_id.equals("")||store_id.equals("null"))
				{
					
					return "user";
					
				}
				return "shoper";
			}
		}
		
		
		
		
	}
	
	
	
	
	public static Uri cameraUri;
	
	public static void setUri(Uri cameraUri)
	{
		Data.cameraUri=cameraUri;
	}
	public static Uri getUri()
	{
		return cameraUri;
	}
	
	//public static String token;
	
	public static LoginInfo info=null;
	
	
	
	
	/*public static LoginInfo getInfo() {
		return Data.info;
	}



	public static void setInfo(LoginInfo info) {
		Data.info = info;
	}*/

	
	/*public static void saveInfo(final Context mContext,final LoginInfo info)
	{
		setInfo(info);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					ObjectOutputStream oos=new ObjectOutputStream(mContext.openFileOutput("login",Context.MODE_PRIVATE));
					oos.writeObject(info);
					oos.flush();
					oos.close();
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
				
			}
		}).start();
		
		
		
	}*/
	
	public static boolean isLogin()
	{
		
		
		return !(null==info);
	}


	/*public static String getToken() {
		return token;
	}



	public static void setToken(String token) {
		Data.token = token;
	}*/



	public static String getStorexpApiUrl(){
		return getBaseUrl()+"?app=store&act=api_get_new_goods";
	}
	
	
	
	public static String getStorerxApiUrl(){
		return getBaseUrl()+"?app=store&act=api_get_hot_sale_goods";
	}
	public static String getStoretjApiUrl(){
		return getBaseUrl()+"?app=store&act=api_get_recommended_goods";
	}
	public static String getStorejgApiUrl(){
		return getBaseUrl()+"?app=store&act=api_get_price_goods";
	}
	public static String getBaseUrl()
	{
		
		return "http://www.cddego.com/api.php";
		
	}
	
	public static String getStoreInfoUrl()
	{
		return getBaseUrl()+"?app=member&act=api_store_info";
	}
	
	public static String getGoodsDetailUrl()
	{
		return getBaseUrl()+"?app=goods&act=api_goods_detail";
	}
	
	public static String getMakeOrderUrl()
	{
		return getBaseUrl()+"?app=order&act=api_order";
	}
	
	
	public static String getSearchItemUrl()
	{
		return getBaseUrl()+"?app=search&act=api_goods";
	}
	
	
	public static String getStoreListUrl()
	{
		return getBaseUrl()+"?app=search&act=api_store";
	}
	
	public static String getGouliangDetailUrl()
	{
		return getBaseUrl()+"?app=my_point&act=api_point";
	}
	public static String getAddCartUrl()
	{
		return getBaseUrl()+"?app=cart&act=api_add";
	}
	
	public static String getUpdateCartNum()
	{
			return getBaseUrl()+"?app=cart&act=api_update";
	}
	public static String getCartUrl()
	{
		return getBaseUrl()+"?app=cart&act=api_cart";
	}
	
	public static String getDropCartUrl()
	{
		return getBaseUrl()+"?app=cart&act=api_drop";
	}
	
	public static String getshakeUrl()
	{
		return getBaseUrl()+"?app=cart&act=api_cart_shake";
	}
			
	public static String getUploadImageUrl()
	{
		return getBaseUrl()+"?app=apply&act=api_upload_image";
	}
}
