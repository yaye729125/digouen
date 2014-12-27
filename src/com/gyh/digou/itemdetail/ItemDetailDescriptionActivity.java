package com.gyh.digou.itemdetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.webkit.WebSettings;
import android.webkit.WebSettings.ZoomDensity;

import com.gyh.digou.R;
import com.gyh.digou.itemdetail.view.ProgressWebView;

public class ItemDetailDescriptionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.item_detail_description);
		Intent intent=getIntent();
		
		String data=intent.getStringExtra("description");
		StringBuilder sb=new StringBuilder(data);
		///sb.insert(0,"<html>");
		//sb.insert(data.length()-1,"<\\/html>");
		ProgressWebView webView=(ProgressWebView) findViewById(R.id.item_detail_description_webview);
		//webView.setWebChromeClient(new chromeClient()); 
		//webView.getSettings().set
		//webView.setInitialScale(59);
		
		WebSettings settings=webView.getSettings();
		/*DisplayMetrics metrics = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(metrics);
	    int mDensity = metrics.densityDpi;
	    if (mDensity <= 120) {
	    settings.setDefaultZoom(ZoomDensity.CLOSE);
	    }else if (mDensity <= 160) {
	    settings.setDefaultZoom(ZoomDensity.MEDIUM);
	    }else if (mDensity >= 240) {
	    settings.setDefaultZoom(ZoomDensity.FAR);
	    }*/
		//settings.setDefaultZoom(ZoomDensity.)
		
		settings.setUseWideViewPort(true);
		//webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.);
	   settings.setLoadWithOverviewMode(true); 
	    //settings.setBuiltInZoomControls(true);
		webView.loadData(data,"text/html","utf-8");
		
	}

	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	
	
	
	
}
