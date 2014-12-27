package com.gyh.digou.itemdetail.map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviPara;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.gyh.digou.app.MyApp;

public class MapNaviActivity extends Activity implements
	OnGetGeoCoderResultListener {
	
	
	
	
	GeoCoder mSearch = null;
	
	
	LatLng  latlng_des;
	BDLocation loc_src;
	boolean flag=false;
	Handler handler=new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			
			if(latlng_des!=null&&loc_src!=null)
			{
				startNavi();
				
			}else
			{
				switch(msg.what)
				{
				case 0x12:
					
					latlng_des=(LatLng) msg.obj;
					break;
				default:
					
					loc_src=(BDLocation) msg.obj;
					
					break;	
				}
			}
		}
		
	};
	
	
	
	
	/**
	 * 开始导航
	 * 
	 * @param view
	 */
	public void startNavi() {
		
		if(!flag){
			Log.d("startNavi", "flag=true");
			flag=true;
				LatLng pt1 = new LatLng(loc_src.getLatitude(), loc_src.getLongitude());
				//LatLng pt2 = new LatLng(mLat2, mLon2);
				// 构建 导航参数
				NaviPara para = new NaviPara();
				para.startPoint = pt1;
				para.startName = "从这里开始";
				para.endPoint = latlng_des;
				para.endName = "到这里结束";
		
				try {
		
					BaiduMapNavigation.openBaiduMapNavi(para, this);
					finish();
		
				} catch (BaiduMapAppNotSupportNaviException e) {
					e.printStackTrace();
					
					startWebNavi();
					finish();
					Log.d("startWebNavi", "flag=true");
				}
		}
	}

	public void startWebNavi() {
		LatLng pt1 = new LatLng(loc_src.getLatitude(), loc_src.getLongitude());
		//LatLng pt2 = new LatLng(mLat2, mLon2);
		// 构建 导航参数
		//NaviPara para = new NaviPara();
		// 构建 导航参数
		NaviPara para = new NaviPara();
		para.startPoint = pt1;
		para.endPoint = latlng_des;
		BaiduMapNavigation.openWebBaiduMapNavi(para, this);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent=getIntent();
		String address=intent.getStringExtra("address");
		
		((MyApp)getApplication()).setHandler(handler);
		
		// 初始化搜索模块，注册事件监听
				mSearch = GeoCoder.newInstance();
				mSearch.setOnGetGeoCodeResultListener(this);
				mSearch.geocode(new GeoCodeOption().city(
						"成都").address(
						address));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		
		
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		
		
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		
		
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		
		
	}

	@Override
	protected void onStop() {
		super.onStop();
		
		
		
	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
		
			return;
		}
		Message msg=new Message();
		msg.obj=result.getLocation();
		msg.what=0x12;
		
		handler.handleMessage(msg);
		//result.getLocation()
		String strInfo = String.format("纬度：%f 经度：%f",
				result.getLocation().latitude, result.getLocation().longitude);
		
		
		
		
		
	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
		
	}

	
	
	
	
}
