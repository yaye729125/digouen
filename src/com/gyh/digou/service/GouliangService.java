package com.gyh.digou.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.gyh.digou.Data;
import com.gyh.digou.R;
import com.gyh.digou.util.NetworkUtil;
import com.gyh.digou.wode.maijia.WodeGouliangDetailActivity;

public class GouliangService extends Service{

	
	
	
	
	public interface OnLocationChangeListener
	{
		
		public void OnLocationChange(BDLocation location);
		
		
	}
	
	OnLocationChangeListener onLocationListener;
	
	public void setOnLocationChangeListener(OnLocationChangeListener onLocationListener)
	{
		this.onLocationListener=onLocationListener;
	}
	
	private static final String Tag="GouliangService";
	
	private IBinder binder=new GouliangService.LocalBinder();
	
	@Override
	public IBinder onBind(Intent arg0) {
		return binder;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);  
		InitLocation();
		getGouLiang();
		Log.d(Tag, "onCreate");
	}


	
	
	
	/** 
     * 创建通知 
     */  
    private void setUpNotification() {  
        int icon = R.drawable.digouicon;  
        CharSequence tickerText = "ttt";  
        long when = System.currentTimeMillis();  
        mNotification = new Notification(icon, tickerText, when);  
  
        // 放置在"正在运行"栏目中  
        mNotification.flags = Notification.FLAG_ONGOING_EVENT;  
  
        RemoteViews contentView = new RemoteViews(this.getPackageName(), R.layout.gouliang_notification);  
        contentView.setTextViewText(R.id.gouliang_notification_tv,"");  
        // 指定个性化视图  
        mNotification.contentView = contentView;  
  
       Intent intent = new Intent(this, WodeGouliangDetailActivity.class);  
        PendingIntent contentIntent = PendingIntent.getActivity(GouliangService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);  
        // 指定内容意图  
        mNotification.contentIntent = contentIntent;  
        //mNotificationManager.notify(NOTIFY_ID, mNotification);  
        
       
		
		
		
    }  
	
	
    private static  int NOTIFY_ID =0x11;  
	
	
	
    
    private void getGouLiang()
    {
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				
				while(true)
				{
					
					
					if(Data.isLogin()&&!(null==getLocation()))
					{
						
						List<NameValuePair> list_params=new ArrayList<NameValuePair>();
						list_params.add(new BasicNameValuePair("page", "1"));
						list_params.add(new BasicNameValuePair("pageSize", "150"));
						list_params.add(new BasicNameValuePair("lng", getLocation().getLongitude()+""));
						list_params.add(new BasicNameValuePair("lat", getLocation().getLatitude()+""));
						list_params.add(new BasicNameValuePair("user_id", Data.getInfo().getData().getUser_id()));
						list_params.add(new BasicNameValuePair("distance", "50000"));
						list_params.add(new BasicNameValuePair("point", "1"));
						
						
						
						
						String result=NetworkUtil.post(Data.getStoreListUrl(), list_params);
						
						//Log.d("Gouliang", params.toString());
						Log.d("Gouliang",result);
						
						try {
							JSONObject json_result=new JSONObject(result);
							
							RemoteViews contentView = mNotification.contentView;  
		                    contentView.setTextViewText(R.id.gouliang_notification_tv,json_result.getString("ErrMsg")); 
		                    mNotificationManager.notify(NOTIFY_ID, mNotification); 
							//Toast.makeText(GouliangService.this, json_result.getString("ErrMsg"), Toast.LENGTH_SHORT).show();
						//	handler.sendEmptyMessage(0x122);
						} catch (JSONException e) {
							
							e.printStackTrace();
						}
						try {
							Thread.sleep(20000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
				
				
			}
		}).start();
    	
    	
    	
    }
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(Tag, "onDestroy");
	}
	@Override
	public void onRebind(Intent intent) {
		// TODO Auto-generated method stub
		super.onRebind(intent);
	}
	
	
	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		
		Log.d(Tag, "onStart");
		super.onStart(intent, startId);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return START_STICKY;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}
	
	
	private NotificationManager mNotificationManager;  
    private Notification mNotification;  
	
	
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	ExecutorService exec;
	public void InitLocation(){
		setUpNotification();
		mLocClient = new LocationClient(getApplicationContext());
		//mLocClient.
		
		exec = Executors.newCachedThreadPool();  
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
	}


	//定义内容类继承Binder
    public class LocalBinder extends Binder{
        //返回本地服务
    	public GouliangService getService(){
            return GouliangService.this;
        }
    }
	
   
    Runnable run=new Runnable()
	{

  

		@Override
		public void run() {
			
				final AjaxParams params=new AjaxParams();
				params.put("page", "1");
				params.put("pageSize","150");
				params.put("lng",getLocation().getLongitude()+"");
				params.put("lat",getLocation().getLatitude()+"");
				params.put("user_id",Data.getInfo().getData().getUser_id());
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
							
							RemoteViews contentView = mNotification.contentView;  
		                    contentView.setTextViewText(R.id.gouliang_notification_tv,json_result.getString("ErrMsg")); 
		                    mNotificationManager.notify(NOTIFY_ID++, mNotification); 
							//Toast.makeText(GouliangService.this, json_result.getString("ErrMsg"), Toast.LENGTH_SHORT).show();
							handler.sendEmptyMessage(0x122);
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
    
	
	
	
	Callback callback=new Callback() {
		
		@Override
		public boolean handleMessage(Message arg0) {
			
			handler.postDelayed(run, 60000);
			return false;
		}
	};
	Handler handler=new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
		
			if(Data.isLogin())
				handler.postDelayed(run, 60000);
			
			super.handleMessage(msg);
		}
		
		
	};
	
    //Runnable run=null;
	public void Gouliang(final BDLocation location)
	{
		
		
		final AjaxParams params=new AjaxParams();
		params.put("page", "1");
		params.put("pageSize","150");
		params.put("lng",location.getLongitude()+"");
		params.put("lat",location.getLatitude()+"");
		params.put("user_id",Data.getInfo().getData().getUser_id());
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
					
					RemoteViews contentView = mNotification.contentView;  
                    contentView.setTextViewText(R.id.gouliang_notification_tv,json_result.getString("ErrMsg")); 
                    mNotificationManager.notify(NOTIFY_ID, mNotification); 
					//Toast.makeText(GouliangService.this, json_result.getString("ErrMsg"), Toast.LENGTH_SHORT).show();
					
				} catch (JSONException e) {
					
					e.printStackTrace();
				}
				
				
			}

			@Override
			public void onFailure(Throwable t, int errorNo,
					String strMsg) {
				
			}
		
		
		});
		
		
		
		
		
		
		
		
		//run
		//run.
		//exec.execute(run);
		//Log.d("Gouliang", "lat="+location.getLatitude()+"lng="+location.getLongitude());
		
		/*if(null==run)
		{*/
			 /*Runnable run=new Runnable()
			{
	
				@Override
				public void run() {
					
						final AjaxParams params=new AjaxParams();
						params.put("page", "1");
						params.put("pageSize","150");
						params.put("lng",location.getLongitude()+"");
						params.put("lat",location.getLatitude()+"");
						params.put("user_id",Data.getInfo().getData().getUser_id());
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
									
									Toast.makeText(GouliangService.this, json_result.getString("ErrMsg"), Toast.LENGTH_SHORT).show();
									
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
			
			};*/
		
		
		
		/*if(null==gouliangThread)
		{
			gouliangThread=new Thread(run);
			gouliangThread.start();	
		}else
		{
			
			
		}*/
		//handler.postDelayed(run, 2000);
		
		
	}
	Thread gouliangThread;
    
	public BDLocation location;
	
	
	
    public BDLocation getLocation() {
		return location;
	}
	public void setLocation(BDLocation location) {
		this.location = location;
	}


	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if(location!=null)
			{
				if(null==getLocation())
				{
					setLocation(location);
					onLocationListener.OnLocationChange(location);
					/*if(Data.isLogin())
						handler.sendEmptyMessage(0x122);*/
					/*if(Data.isLogin())
						exec.execute(run);*/
					
				}else
				{
					if(!(getLocation().hashCode()==location.hashCode()))
					{
						
						onLocationListener.OnLocationChange(location);
						setLocation(location);
						/*if(Data.isLogin())
							handler.sendEmptyMessage(0x122);*/
						/*if(Data.isLogin())
							
							exec.execute(run);*/
					}
				}
				//setLocation(location);
			
			}
				
			
		}

		@Override
		public void onReceivePoi(BDLocation arg0) {
			
		}


	}
}
