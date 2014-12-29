package com.gyh.digou.app;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.gyh.digou.Data;
import com.gyh.digou.bean.LoginInfo;
import com.gyh.digou.log.LogcatHelper;


public class MyApp extends Application {
	public static LoginInfo info=null;
	
	
	/*public void reLogin()
	{
		
		
		AjaxParams params=new AjaxParams();
		params.put("user_name","zertul999");
		params.put("password","123456");
		FinalHttp fh=new FinalHttp();
		fh.post(Tools.getBaseUrl()+"?app=member&act=api_login",params,new AjaxCallBack<String>(){

			@Override
			public void onFailure(Throwable t, int errorNo,
					String strMsg) {
				Toast.makeText(getApplicationContext(),strMsg,Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onSuccess(String t) {
				System.out.println(t);
				Gson gson=new Gson();
			
				LoginInfo info=gson.fromJson(t, LoginInfo.class);
				
				//MyApp app=((MyApp)getActivity().getApplication());
				
				setInfo(info);
				Toast.makeText(getApplicationContext(),info.getErrMsg(),Toast.LENGTH_SHORT).show();
				
			}
			
		});
		
		
	}*/
	public boolean isFirstUse()
	{
		
		FileInputStream fis = null;
		
		try {
			fis = openFileInput("first");
			//info=(LoginInfo) ois.readObject();
			//ois.close();
			fis.close();
		}catch (Exception e) {
			if(null!=fis)
			{
				try {
					fis.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			return true;
		}
		return false;
		
		
	}
	
	public void saveFirst(String s)
	{
		FileOutputStream fos = null;
		try
		{
			fos=openFileOutput("first", 0);
			fos.write(s.getBytes());
			fos.close();
		}catch(Exception e)
		{
			if(null!=null)
			{
				try {
					fos.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	/*public boolean isLogin()
	{
		
		ObjectInputStream ois;
		
		try {
			ois = new ObjectInputStream(openFileInput("login"));
			info=(LoginInfo) ois.readObject();
			ois.close();
		}catch (Exception e) {
			return false;
		}
		return true;
	}*/
	
	
	//先要判断islogin
	/*public LoginInfo getInfo() {
		ObjectInputStream ois;
		LoginInfo info=null;
		try {
			ois = new ObjectInputStream(openFileInput("login"));
			info=(LoginInfo) ois.readObject();
			ois.close();
			
			
			
			
		} catch (StreamCorruptedException e) {
			
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
		
			e.printStackTrace();
		}
		
		
		return info;
	}*/

	
	
	
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	
	private void InitLocation(){
		
		mLocClient = new LocationClient(this);
		//mLocClient.
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
	}
	
	
	
	public  void saveInfo(LoginInfo info)
	{
		
		
		
				try {
					ObjectOutputStream oos=new ObjectOutputStream(openFileOutput("login",Context.MODE_PRIVATE));
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
	
	public boolean readInfo()
	{
		
		ObjectInputStream ois;
		
		try {
			ois = new ObjectInputStream(openFileInput("login"));
			info=(LoginInfo) ois.readObject();
			ois.close();
		}catch (Exception e) {
			return false;
		}
		return true;
		
	}
	
	
	@Override
	public void onCreate() {
		super.onCreate();
	
		LogcatHelper.getInstance(this).start();
		
		
		SDKInitializer.initialize(this);
		
		if(readInfo())
		{
			Data.info=info;
		}
		
		//startService(new Intent(this,GouliangService.class));
		
	}
	
	Handler handler;
	
	public void setHandler(Handler handler)
	{
		this.handler=handler;
		InitLocation();
	}
	public void cancelHandler()
	{
		mLocClient.stop();
	}
	
	Runnable run=null;
	public void Gouliang(final BDLocation location)
	{
		
		
		Log.d("Gouliang", "lat="+location.getLatitude()+"lng="+location.getLongitude());
		
		if(null==run)
		{
			run=new Runnable()
			{
	
				@Override
				public void run() {
					
						final AjaxParams params=new AjaxParams();
						params.put("page", "1");
						params.put("pageSize","150");
						params.put("lng",location.getLongitude()+"");
						params.put("lat",location.getLatitude()+"");
						params.put("user_id",Data.info.getData().getUser_id());
						params.put("distance", "50000");
						params.put("point", "10");
						
						FinalHttp fh=new FinalHttp();
						fh.post(Data.getStoreListUrl(), params,new AjaxCallBack<String>() {
		
							@Override
							public void onSuccess(String t) {							
								
								Log.d("Gouliang", params.toString());
								Log.d("Gouliang",t);
								
								try {
									JSONObject json_result=new JSONObject(t);
									
									Toast.makeText(MyApp.this, json_result.getString("ErrMsg"), Toast.LENGTH_SHORT).show();
									
								} catch (JSONException e) {
									
									e.printStackTrace();
								}
								
								
							}
		
							@Override
							public void onFailure(Throwable t, int errorNo,
									String strMsg) {
								
							}
						
						
						});
					}
			
			};
		}
			
		handler.postDelayed(run, 2000);
		
		
	}
	
	
		
	
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if(location!=null)
			{
				Message msg=new Message();
				msg.obj=location;
				handler.handleMessage(msg);
				//Gouliang(location);
			}
			
		}

		@Override
		public void onReceivePoi(BDLocation arg0) {
			
		}


	}
	
	
	
	
}
