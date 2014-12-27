package com.gyh.digou.wode.shangjia;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.util.NetworkUtil;
import com.gyh.digou.util.Tools;
import com.gyh.digou.wode.maijia.Shuangdianchongzhi;
import com.gyh.digou.wode.maijia.WodeGouLiangActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WodeGouLiangEnActivity extends Activity {
	TextView shuangdianshengyu_text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.wodegouliang_en);
		
		
		RelativeLayout layout_chongzhi=(RelativeLayout) findViewById(R.id.wodegouliang_chongzhi);
		
		
		
		//tabrow=(TableRow) findViewById(R.id.shuangdianchongzhi_tab);
		layout_chongzhi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(WodeGouLiangEnActivity.this, Shuangdianchongzhi.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			}
		});
	
		shuangdianshengyu_text=(TextView) findViewById(R.id.wodegouliangen_tv_shengyu_content);
		getData();
	}

	@Override
	protected void onDestroy() {
	
		super.onDestroy();
		
		
		
		
	}

	
	public void  getData()
	{
		
		new Thread(new Runnable(){
			JSONObject dataObject;
			@Override
			public void run() {
				List<NameValuePair> params=new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("token",MyApp.info.getData().getToken()));
				final String result=NetworkUtil.post(Tools.getBaseUrl()+"?app=my_point&act=api_point", params);
				System.out.println(result+"------------100"); 
				
				JSONObject jsonObject = null;
				try{
				jsonObject = new JSONObject(result);
				dataObject = jsonObject.getJSONObject("data"); 
				WodeGouLiangEnActivity.this.runOnUiThread(new Runnable(){

					@Override
					public void run() {
						try {
							shuangdianshengyu_text.setText(dataObject.getString("point"));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					}});	

				      
				}catch (JSONException e) {
					e.printStackTrace();
				}catch (NumberFormatException e) {
					e.printStackTrace();
				}
				
			}
			
		}).start();
		
		
		
		
	}
	
	
	
}
