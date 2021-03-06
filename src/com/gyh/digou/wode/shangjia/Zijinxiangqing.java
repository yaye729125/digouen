package com.gyh.digou.wode.shangjia;

import java.util.ArrayList;
import java.util.Iterator;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import com.gyh.digou.Data;
import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.util.Tools;

import android.app.Activity;
import android.content.Intent;
import android.graphics.RadialGradient;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Zijinxiangqing extends Activity{
	ListView listView;
	TextView textView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zijinguanli);
		 textView=(TextView) findViewById(R.id.ziji_zonge);
		listView=(ListView) findViewById(R.id.zijin_list);
		RadioButton button=(RadioButton) findViewById(R.id.zijin_tixian);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent intent=new Intent(Zijinxiangqing.this, Zijintixian.class);				
				startActivity(intent);
				
				
				
				
				
				
				
				
			}
		});
		
		
		
		AjaxParams params = new AjaxParams();
		
		params.put("token", Data.info.getData().getToken());
		

		FinalHttp fh = new FinalHttp();
		fh.post(Tools.getBaseUrl() + "?app=my_pay&act=api_pay",
				params, new AjaxCallBack<String>() {

					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						Toast.makeText(Zijinxiangqing.this, strMsg,
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(String t) {
						 System.out.println("---434343-------"+t+"-----");

						 
						 try {
							JSONObject jsonObject=new JSONObject(t);
							
						JSONObject jsonObject2=	jsonObject.getJSONObject("data");
						String zonge=	jsonObject2.getString("paysum");
						
						textView.setText(zonge);
					
						JSONObject jsonObject3=jsonObject2.getJSONObject("list");
						
						ArrayList<JSONObject> warrlist = new ArrayList<JSONObject>();
					
						Iterator<String> it = jsonObject3.keys();
						while (it.hasNext()) {
							String key = it.next();
							JSONObject object = jsonObject3.getJSONObject(key);
							warrlist.add(object);
							
							ZijinAdapter adapter=new ZijinAdapter(Zijinxiangqing.this, warrlist);
							listView.setAdapter(adapter);
							System.out.println(warrlist+"--");
							
						}	
							
							
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 
						
					}

				});
		
	}

}
