package com.gyh.digou.faxian;

import java.util.Iterator;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.gyh.digou.MainActivity;
import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.itemdetail.shop.ShopActivity;
import com.gyh.digou.mytoast.CustomToast;
import com.gyh.digou.service.GouliangService;
import com.gyh.digou.service.GouliangService.OnLocationChangeListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class FaXianFragement extends Fragment {

	public static FaXianFragement ff;
	Context mContext;
	MainActivity mainActivity;
	private static final int REQUEST_OPEN_LOC_SETTING = 0x210;

	public static FaXianFragement getInstance() {
		if (ff == null) {
			ff = new FaXianFragement();
		}
		return ff;
	}

	//PullToRefreshListView pullToRefreshListView;
	ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.faxian_fragment, container, false);

		listView = (ListView) view
				.findViewById(R.id.faxian_list);
		//listView = pullToRefreshListView.getRefreshableView();
		adapter = new FaxianListBaseAdapter(mContext);
		listView.setAdapter(adapter);
		listView.setOnScrollListener(new MyScrollListenter());
		
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				try {
				Intent intent=new Intent(mContext,ShopActivity.class);
				
				
					intent.putExtra("store_id",adapter.getData().getJSONObject(arg2).getString("store_id"));
				
				startActivity(intent);
				
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		return view;
	}

	boolean flag_scroll = false;

	class MyScrollListenter implements OnScrollListener {

		@Override
		public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {

		}

		@Override
		public void onScrollStateChanged(AbsListView arg0, int arg1) {

			if (arg1 == OnScrollListener.SCROLL_STATE_IDLE) {
				flag_scroll = false;
			} else {
				flag_scroll = true;
			}
		}

	}

	
	Handler handler=new Handler();
	// CustomToast toast;
	

	public void initData(BDLocation location) {

		final JSONArray list = new JSONArray();
		AjaxParams params = new AjaxParams();
		params.put("order", "area");
		params.put("lng", location.getLongitude() + "");
		params.put("lat", location.getLatitude() + "");
		// params.put("recommend_id","20");
		FinalHttp fh = new FinalHttp();
		fh.post("http://www.cddego.com/api.php?app=search&act=api_store",
				params, new AjaxCallBack<String>() {

					@Override
					public void onLoading(long count, long current) {
						super.onLoading(count, current);
					}

					@Override
					public void onSuccess(String t) {
						mainActivity.hideDialog();
						Log.d("faxianfragment----", t);

						JSONObject jsonObject = null;

						try {

							jsonObject = new JSONObject(t);
							JSONObject dataObject = jsonObject
									.getJSONObject("data");

							String total = dataObject.getString("total");
							int count = Integer.parseInt(total);

							JSONObject listObject = (JSONObject) dataObject
									.get("stores");
							Iterator itr = listObject.keys();
							while (itr.hasNext()) {
								String key = (String) itr.next();
								JSONObject numObject = (JSONObject) listObject
										.get(key);
								list.put(numObject);

							}
							adapter.setData(list);
							flag_scroll=true;
							flag_load=false;
							//getData = false;
						} catch (Exception e) {
							e.printStackTrace();

						}
					}
				});

	}

	FaxianListBaseAdapter adapter;
	LayoutInflater myInflater;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		mContext = mainActivity = (MainActivity) getActivity();
		myInflater = LayoutInflater.from(mContext);
		flag_scroll=false;
		flag_load=true;
		mainActivity.showDialog();
		
		Log.d("onResume", "");
		// ((MyApp)mainActivity.getApplication()).setHandler(handler);
		bindService();
		// mLocationClient = ((MyApp)activity.getApplication()).mLocationClient;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {

		super.onStart();
	}

	Location loc;
	LocationManager lm;

	/*Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.obj != null) {
				bdLocation = (BDLocation) msg.obj;
				if (getData)
					initData();
			}
		}

	};*/

	//boolean getData = false;

	private LocationClient mLocationClient;
	BDLocation bdLocation;

	public BDLocation getBdLocation() {
		return bdLocation;
	}

	public void setBdLocation(BDLocation bdLocation) {
		this.bdLocation = bdLocation;
	}

	@Override
	public void onResume() {

		super.onResume();
		

	}
	Runnable initDataRun=new Runnable() {
		
		@Override
		public void run() {
			
			
			if(!(null==getBdLocation()))
			{
					final JSONArray list = new JSONArray();
					AjaxParams params = new AjaxParams();
					params.put("order", "area");
					params.put("lng", getBdLocation().getLongitude() + "");
					params.put("lat", getBdLocation().getLatitude() + "");
					// params.put("recommend_id","20");
					FinalHttp fh = new FinalHttp();
					fh.post("http://www.cddego.com/api.php?app=search&act=api_store",
					params, new AjaxCallBack<String>() {

						@Override
						public void onLoading(long count, long current) {
							super.onLoading(count, current);
						}

						@Override
						public void onSuccess(String t) {
							mainActivity.hideDialog();
							Log.d("faxianfragment----", t);

							JSONObject jsonObject = null;

							try {

								jsonObject = new JSONObject(t);
								JSONObject dataObject = jsonObject
										.getJSONObject("data");

								String total = dataObject.getString("total");
								int count = Integer.parseInt(total);

								JSONObject listObject = (JSONObject) dataObject
										.get("stores");
								Iterator itr = listObject.keys();
								while (itr.hasNext()) {
									String key = (String) itr.next();
									JSONObject numObject = (JSONObject) listObject
											.get(key);
									list.put(numObject);

								}
								adapter.setData(list);
								flag_scroll=true;
								//getData = false;
							} catch (Exception e) {
								e.printStackTrace();

							}
						}
					});
			}
			
		}
	};
	
	boolean flag_load=true;
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
			
			//service.InitLocation();
			service.setOnLocationChangeListener(new OnLocationChangeListener() {
				
				@Override
				public void OnLocationChange(final BDLocation location) {
					
					
					if(flag_load)
					{
						//setBdLocation(location);
						initData(location);
					}
					/*if (!flag_scroll) {
						
						setBdLocation(location);
						handler.postDelayed(
						initDataRun, 10000);
					
					}
					Log.d("serviceConnection", "lat="+location.getLatitude()+"  lng="+location.getLongitude());*/
					
				}
			});
			//listenLoc();
			
			//Log.d("serviceConnection", service.location.toString());

		}
	};

	
	private void listenLoc()
	{
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true)
				{
					
					if(!(null==service.getLocation()))
					{
						BDLocation loc=service.getLocation();
						Log.d("serviceConnection", "lat="+loc.getLatitude()+"  lng="+loc.getLongitude());
						
					}
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
		}).start();
		
	}
	private void bindService() {

		Log.d("bindService", "");
		Intent intent = new Intent(mContext, GouliangService.class);
		mContext.getApplicationContext().bindService(intent, serviceConnection,
				Context.BIND_AUTO_CREATE);

	}

	private void unBindService() {

		Log.d("unBindService", "");
		mContext.getApplicationContext().unbindService(serviceConnection);
	}

	/*
	 * public void showToast() { toast=new CustomToast(mContext);
	 * toast.setGravity(Gravity.CENTER, 0, 0); View
	 * toastview=myInflater.inflate(R.layout.mytoast,null);
	 * //base_toast_tv=(TextView) view.findViewById(R.id.mytoast_tv);
	 * //bar=(ProgressBar) view.findViewById(R.id.mytoast_progerssbar);
	 * toast.setView(toastview); toast.show(-1); }
	 * 
	 * public void hideToast() { if(toast!=null) { if(toast.isShowing())
	 * toast.hide(); } }
	 */
	public void openSetting() {

		/*
		 * // 判断GPS是否正常启动 if
		 * (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
		 * Toast.makeText(mContext, "请开启GPS导航...", Toast.LENGTH_SHORT).show();
		 * // 返回开启GPS导航设置界面 Intent intent = new
		 * Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		 * startActivityForResult(intent,REQUEST_OPEN_LOC_SETTING); //return;
		 * }else {
		 * 
		 * 
		 * 
		 * }
		 */

	}

	//GouliangService gouliangService = null;

	@Override
	public void onPause() {

		super.onPause();
		Log.d("onPause", "");
		// 退出时销毁定位
		// mLocClient.stop();
		// lm.removeUpdates(llistener);
		// lm.setTestProviderEnabled(provider, false);
		
		/*
		 * getData=false;
		 * ((MyApp)mainActivity.getApplication()).cancelHandler();
		 */

	}

	@Override
	public void onStop() {

		super.onStop();
	}

	@Override
	public void onDestroyView() {
		//unBindService();
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		flag_scroll=true;
		flag_load=false;
		mainActivity.hideDialog();
		unBindService();
		//unBindService();
		super.onDestroy();
	}

	@Override
	public void onDetach() {

		super.onDetach();
	}

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);
	}
}
