package com.gyh.digou.wode.maijia;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Handler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.google.gson.Gson;
import com.gyh.digou.Data;
import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.bean.LoginInfo;
import com.gyh.digou.util.NetworkUtil;
import com.gyh.digou.util.Tools;

import android.app.Activity;
import android.content.Intent;
import android.net.rtp.RtpStream;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class WodeGouLiangActivity extends Activity{
	
	//String jieguo;
	//TableRow tabrow;
	TextView shuangdianshengyu_text;
	//EditText tuisong_editText1;
	//Button baocun_button1;
	//String tuisongxinxi;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wodegouliang);
		
		
		RelativeLayout layout_chongzhi=(RelativeLayout) findViewById(R.id.wodegouliang_chongzhi);
		
		
		
		//tabrow=(TableRow) findViewById(R.id.shuangdianchongzhi_tab);
		layout_chongzhi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(WodeGouLiangActivity.this, Shuangdianchongzhi.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			}
		});
	
		shuangdianshengyu_text=(TextView) findViewById(R.id.wodegouliang_tv_shengyu_content);
		getData();
		
		findViewById(R.id.wodegouliang_layout_detail).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(WodeGouLiangActivity.this,WodeGouliangDetailActivity.class));
				
			}
		});
	} 
	
	
	
	public void  getData()
	{
		
		new Thread(new Runnable(){
			JSONObject dataObject;
			@Override
			public void run() {
				List<NameValuePair> params=new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("token",Data.info.getData().getToken()));
				final String result=NetworkUtil.post(Tools.getBaseUrl()+"?app=my_point&act=api_point", params);
				System.out.println(result+"------------100"); 
				
				JSONObject jsonObject = null;
				try{
				jsonObject = new JSONObject(result);
				dataObject = jsonObject.getJSONObject("data"); 
				WodeGouLiangActivity.this.runOnUiThread(new Runnable(){

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
