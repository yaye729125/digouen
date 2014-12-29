package com.gyh.digou;



import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;


import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.faxian.FaXianFragement;
import com.gyh.digou.fenlei.FenLeiFragement;
import com.gyh.digou.gouwuche.GouWuCheFragement;
import com.gyh.digou.itemdetail.ItemDetailActivity;
import com.gyh.digou.mytoast.CustomToast;
import com.gyh.digou.mytoast.PixelFormat;
import com.gyh.digou.progerss.CustomProgressDialog;
import com.gyh.digou.service.GouliangService;
import com.gyh.digou.service.GouliangService.OnLocationChangeListener;
import com.gyh.digou.shouye.ShouyeFragment;
import com.gyh.digou.shouye.search.ShouyeSearchActivity;
import com.gyh.digou.util.NetworkUtil;
import com.gyh.digou.util.Tools;
import com.gyh.digou.wode.LoginFragment;
import com.gyh.digou.wode.WoDeFragement;

public class MainActivity extends FragmentActivity implements OnCheckedChangeListener{
	public RadioGroup group;
	
	ShouyeFragment myFragment;
	FenLeiFragement fenLeiFragement;
	FaXianFragement faXianFragement;
	GouWuCheFragement gouWuCheFragement;
	WoDeFragement woDeFragement;
	LoginFragment loginFragment;
	FinalBitmap imageLoader;
	
	private String[] items = new String[]{"从文件中选择","拍照","取消"};
	
	private static final String IMAGE_FILE_NAME = "faceImage.jpg";


	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	public  static final int MAIN_REQUEST_SCAN = 3;

	private static final int REQUEST_OPEN_LOC_SETTING =0x210;
	
	MyApp app;
	CustomToast no_network_toast;
	
	
   CustomProgressDialog dialog;
	    
	    
   
   
   
   GouliangService service;
   ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub
			service = null;
		}

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {

			service = ((GouliangService.LocalBinder) arg1).getService();
			
			service.InitLocation();
			service.setOnLocationChangeListener(new OnLocationChangeListener() {
				
				@Override
				public void OnLocationChange(final BDLocation location) {
					
					/*if (!flag_scroll) {
						
						
						handler.postDelayed(new Runnable() {
							
							@Override
							public void run() {
								
								initData(location);
								
							}
						}, 800);
					
					}*/
					Log.d("serviceConnection", "lat="+location.getLatitude()+"  lng="+location.getLongitude());
					
				}
			});
			//listenLoc();
			
			//Log.d("serviceConnection", service.location.toString());

		}
	};
   
   
   
   
   private void bindService() {

		Log.d("bindService", "");
		Intent intent = new Intent(this, GouliangService.class);
		this.getApplicationContext().bindService(intent, serviceConnection,
				Context.BIND_AUTO_CREATE);

	}

	private void unBindService() {

		Log.d("unBindService", "");
		this.getApplicationContext().unbindService(serviceConnection);
	}
   
   
	public void showDialog()
	    {
	    	
	    	if(dialog==null)
	    	{
	    		dialog=CustomProgressDialog.createDialog(this, R.style.CustomProgressDialog,R.layout.mytoast);
	    		dialog.setMessage("正在加载",R.id.mytoast_tv);
	    		dialog.show();
	    		/*dialog=new CustomProgressDialog(mContex,R.style.basedialog);
	    		dialog.*/
	    	}else
	    	{
	    		if(!dialog.isShowing())
	    			dialog.show();
	    	}
	    	
	    }
	    public void hideDialog()
	    {
	    	if(dialog!=null)
	    	{
	    		dialog.dismiss();
	    	}
	    	
	    }
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
//      requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		
		
		Intent intent=getIntent();
		//String action=intent.getStringExtra("action");
		app=(MyApp) getApplication();
		imageLoader=FinalBitmap.create(this);
		imageLoader.configMemoryCachePercent(30);
		imageLoader.configLoadingImage(R.drawable.item_loading);
		//initFragment();
		//changeFragment(tag_shouye);
		group = (RadioGroup)findViewById(R.id.radioGroup);
		group.setOnCheckedChangeListener(this);
		
		initFirstPage();
		IntentFilter filter=new IntentFilter();
		filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		filter.addAction("com.gyh.digou.gocart");
		registerReceiver(network_change_receiver, filter);//, broadcastPermission, scheduler)
		
		if(!NetworkUtil.isNetworkAvailable(this))
		{
			
		
			no_network_toast=new CustomToast(this);
			View view = LayoutInflater.from(this).inflate(R.layout.no_network_toast, null);
			no_network_toast.setGravity(Gravity.TOP,0,PixelFormat.formatDipToPx(this,50));
			no_network_toast.setView(view);
			no_network_toast.show(-1);
		}
    }
	
	
	long firstTime=-1;
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		switch(keyCode)
        {
        case KeyEvent.KEYCODE_BACK:
        	
        	
             long secondTime = System.currentTimeMillis(); 
              if (secondTime - firstTime > 2000) {                                         //如果两次按键时间间隔大于2秒，则不退出
                  Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show(); 
                  firstTime = secondTime;//更新firstTime
                  return true; 
              } else {    
            	  
            	  myExit();
            	  //两次按键小于2秒时，退出应用
            	  //ActivityManager manager=geta
            	  //getApplication()
              } 
              
              
              
            break;
        }
      return super.onKeyUp(keyCode, event);
	}

	//待改进...
	public void myExit()
	{
		
		android.os.Process.killProcess(android.os.Process.myPid());
		
	}



	public FinalBitmap getImageLoader() {
		return imageLoader;
	}

	//LocationManager lm;
	
	
    

	public void setImageLoader(FinalBitmap imageLoader) {
		this.imageLoader = imageLoader;
	}
	
	
	public void initFirstPage()
	{
		FragmentManager fragementManager = getSupportFragmentManager();
		FragmentTransaction beginTransaction = fragementManager.beginTransaction();
		beginTransaction.replace(R.id.frame,ShouyeFragment.getInstance());
		beginTransaction.commit(); 
	}
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
		
		FragmentManager fragementManager = getSupportFragmentManager();
		FragmentTransaction beginTransaction = fragementManager.beginTransaction();
		switch (checkedId) {
		case R.id.first:
			
			
			beginTransaction.replace(R.id.frame,ShouyeFragment.getInstance());
			beginTransaction.commit(); 
			break;
		case R.id.second:
			beginTransaction.replace(R.id.frame,FenLeiFragement.getInstance());
			beginTransaction.commit(); 
			//changeFragment(tag_fenlei);
			
			break;
		case R.id.third:
			//changeFragment(tag_faxian);
			beginTransaction.replace(R.id.frame,FaXianFragement.getInstance());
			beginTransaction.commit(); 
			break;
		case R.id.fourth:
			if(Data.isLogin())
			{
				beginTransaction.replace(R.id.frame,GouWuCheFragement.getInstance(this,tag_gouwuche));
				beginTransaction.commit(); 
			}else
			{
				beginTransaction.replace(R.id.frame,new LoginFragment());
				beginTransaction.commit(); 
			}
			break;
		case R.id.fifth:
			if(Data.isLogin())
			{
				beginTransaction.replace(R.id.frame,WoDeFragement.getInstance());
				beginTransaction.commit(); 
			}else
			{
				beginTransaction.replace(R.id.frame,new LoginFragment());
				beginTransaction.commit(); 
			}
			break;
		}
	}
	 
	String tag_shouye="shouye";
	String tag_fenlei="fenlei";
	String tag_faxian="faxian";
	String tag_gouwuche="gouwuche";
	String tag_wode="wode";
	String tag_login="login";
	
	
	
	
	//fragment singlestone
	
	
	
	/*private boolean isLogin()
	{
		
		return !(null==Data.getInfo());
		
		
	}*/
	
	/*private boolean isLogin()
	{
		if(((MyApp)getApplication()).getInfo()!=null){
			
			return true;
		}else
		{
			return false;
		}
	}*/
	
	
	
	
    @Override  
    protected void onResume() {  
        super.onResume(); 
        
    }  
      
    @Override  
    protected void onPause() {  
        super.onPause();
        if(no_network_toast!=null)
		{
			if(no_network_toast.isShowing())
			{
				no_network_toast.hide();
			}
	}
    }
    @Override
	protected void onDestroy() {
		
		super.onDestroy();
		
		unregisterReceiver(network_change_receiver);
		
	} 
    
    
    
    
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		//RESULT_CANCELED
		if (resultCode != Activity.RESULT_CANCELED){

			switch (requestCode) {
		
			case IMAGE_REQUEST_CODE:
				startPhotoZoom(data.getData());
				break;
			case CAMERA_REQUEST_CODE:
				if (Tools.hasSdcard()) {

					
					System.out.println("app.getUri()"+Data.getUri());
					startPhotoZoom(Data.getUri());
				}else {
					Toast.makeText(MainActivity.this,"未找到sd卡",
							Toast.LENGTH_LONG).show();
				}
				break;
			
			case RESULT_REQUEST_CODE:
				if (data != null) {
					getImageToView(data);
				}
				break;
			case MAIN_REQUEST_SCAN:
				
				
					getGoodsId(data.getStringExtra("result"));
				
				break;
			case REQUEST_OPEN_LOC_SETTING:
				break;
			
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	private void getGoodsId(String stringExtra){
		
		showDialog();
		final AjaxParams params=new AjaxParams();
		
		
		params.put("sku",stringExtra);
		FinalHttp fh=new FinalHttp();
		fh.post(Data.getSearchItemUrl(), params,new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {
				
				super.onSuccess(t);
				Log.d("getGoodsId onSuccess", params.toString());
				Log.d("getGoodsId onSuccess", "t==="+t);
				
				
				try {
					JSONObject json_result=new JSONObject(t);
					
					
					int ErrNum=json_result.getInt("ErrNum");
					if(ErrNum==0)
					{
						
						
						JSONObject json_list=json_result.getJSONObject("data").getJSONObject("list");
						
						
						
						Iterator itr=json_list.keys();
						JSONObject json_list_item=null;
						while(itr.hasNext())
						{
							String key=(String) itr.next();
							json_list_item=json_list.getJSONObject(key);
						}
						hideDialog();
						Intent intent=new Intent(MainActivity.this,ItemDetailActivity.class);
						intent.putExtra("goods_id",json_list_item.getString("goods_id"));
						startActivity(intent);
						
						
					}else
					{
						Toast.makeText(MainActivity.this, json_result.getString("ErrMsg"),Toast.LENGTH_SHORT).show();
					}
					
				
				} catch (JSONException e) {
					hideDialog();
					Toast.makeText(MainActivity.this, "未发现该商品", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
				
				
				
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				
				super.onFailure(t, errorNo, strMsg);
				
				
			}
		
		
		
		});
		
		
		
	}


	Bitmap headImage;
	
	
	private static String app_head_image="tem_head_image.png";
	
	/**
	 *
	 * 
	 * @param picdata
	 */
	public  void getImageToView(Intent data) {
		
		
		Bundle extras = data.getExtras();
		if (extras != null) {
			headImage = extras.getParcelable("data");
			
			try {
				
				
				//exception to do
				if (Tools.hasSdcard()) {
					imageFile=Tools.makeImageDir(app_head_image);
					/*imageFile=new File(Environment
							.getExternalStorageDirectory(),
							IMAGE_FILE_NAME);*/
					
					FileOutputStream fos=new FileOutputStream(imageFile);
					headImage.compress(Bitmap.CompressFormat.PNG,50,fos);
					
					
					Drawable drawable = new BitmapDrawable(headImage);
					WoDeFragement wd=WoDeFragement.getInstance();
					wd.imv_headImage.setImageDrawable(drawable);
					wd.imv_headImage.setPadding(10, 10, 10, 10);
					uploadPic();
				}else
				{
					
					if(null!=headImage&&!headImage.isRecycled())
					{
						headImage.recycle();
						headImage=null;
						
					}
					
					Toast.makeText(this,"未找到sd卡,上传头像失败",Toast.LENGTH_SHORT).show();
				}
				
				
				/*else{
				
					fos=openFileOutput("image",0);
			}*/
			
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}
			
			
			
			
			
			
		}
	}
	
	
	
	
	
	private  void  uploadPic() {
		AjaxParams params=new AjaxParams();
		
		/*MyApp myApp=(MyApp)MainActivity.this.getApplication();
		String token=myApp.getInfo().getData().getToken();*/
		params.put("token",Data.info.getData().getToken());
		try {
		params.put("image",imageFile);
		
		FinalHttp fh=new FinalHttp();
		fh.post(Data.getUploadImageUrl(),params,new AjaxCallBack<String>()
				{

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						
					}
					@Override
					public void onSuccess(String t) {
						Toast.makeText(MainActivity.this,t, Toast.LENGTH_SHORT).show();
						System.out.println(t);
					}
			
				});
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	BroadcastReceiver network_change_receiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context arg0,Intent arg1) {
			
			String action = arg1.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                Log.d("mark", "网络状态已经改变");
                ConnectivityManager connectivityManager = (ConnectivityManager)      

                                         getSystemService(Context.CONNECTIVITY_SERVICE);
                 NetworkInfo info = connectivityManager.getActiveNetworkInfo();  
                if(info != null && info.isAvailable()) {
                    String name = info.getTypeName();
                    handler.sendEmptyMessage(0x0001);
                } else {
                    Log.d("mark", "没有可用网络");
                    handler.sendEmptyMessage(0x0002);
                }
            }else if(action.equals("com.gyh.digou.gocart"))
            {
            	group.check(R.id.fourth);
            }
			
			
			
			
		}
	};
	
	
	
	Handler handler=new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			Log.d("main--handler","--handleMessage");
			switch(msg.what)
			{
			case 0x0001:
				Log.d("main--handler","--be neted");
				if(no_network_toast!=null)
				{
					if(no_network_toast.isShowing())
						no_network_toast.hide();
				}
				onResume();
				
				break;
			case 0x0002:
				Log.d("main--handler","--be noneted");
				if(no_network_toast!=null)
				{
					Log.d("main--handler","--no_network_toast!=null");
					if(!no_network_toast.isShowing())
					{
						Log.d("main--handler","--!no_network_toast.isShowing()");
						no_network_toast.show(-1);
					}
				}
				
				
				break;
			}
			//network_change_receiver.
			
			
			//msg.
			
			
			
			
			
		}
		
	};
	public void showGetImageDialog() {

		AlertDialog dialog=new AlertDialog.Builder(this)
			
				.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							Intent intentFromGallery = new Intent();
							intentFromGallery.setType("image/*");
							intentFromGallery
									.setAction(Intent.ACTION_GET_CONTENT);
							startActivityForResult(intentFromGallery,
									IMAGE_REQUEST_CODE);
							break;
						case 1:

							Intent intentFromCapture = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							//
							if (Tools.hasSdcard()) {
								imageFile=new File(Environment
										.getExternalStorageDirectory(),
										IMAGE_FILE_NAME);
								cameraUri=Uri.fromFile(imageFile);
								Data.setUri(cameraUri);
								intentFromCapture.putExtra(
										MediaStore.EXTRA_OUTPUT,cameraUri
										//URI.
										);
							Log.d("imageFile---",imageFile.toString());
							startActivityForResult(intentFromCapture,
									CAMERA_REQUEST_CODE);
							}else{
								Toast.makeText(MainActivity.this,"未找到sd卡",Toast.LENGTH_SHORT).show();
							}
							break;
						case 2:
							dialog.cancel();
							break;
						}
					}
				}).create();
		Window window=dialog.getWindow();
		window.setGravity(Gravity.BOTTOM);
		dialog.show();	
				

	}
	
	File imageFile;
	Uri  cameraUri;
	/**
	 *
	 * 
	 * @param uri
	 */
	public  void startPhotoZoom(Uri uri) {

		//Log.d("imageFile---",imageFile.toString());
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, RESULT_REQUEST_CODE);
	}

	
	
	
	
}