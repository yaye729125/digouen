package com.gyh.digou.wode.maijia;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONObject;

import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.util.Tools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class FenleiActivity extends Activity{
	
	ListView listView1,listView2,listView3;
	String nn;
	ArrayList<JSONObject> li;
	 String child;
	 ArrayList<JSONObject> arrlis;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.de_01);
		
		 listView1=(ListView) findViewById(R.id.log_listView1);
		 listView2=(ListView) findViewById(R.id.log_listView2);
		 listView3=(ListView) findViewById(R.id.log_listView3);
		
		AjaxParams params = new AjaxParams();
		
//		params.put("token", token);
//		params.put("pattern", "");

		FinalHttp fh = new FinalHttp();
		fh.post(Tools.getBaseUrl() + "?app=category&act=api_store",
				params, new AjaxCallBack<String>() {

					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						Toast.makeText(FenleiActivity.this, strMsg,
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(String t) {
						 System.out.println("---7-0-------"+t+"-----70-----70------------");

					
						 try {
							 
							 JSONObject jsonObject=new JSONObject(t); 
						         String name=jsonObject.getString("data");	
						     JSONArray array=new JSONArray(name);    
					  li=new ArrayList<JSONObject>();
					            for(int c=0;c<array.length();c++){
				     	         JSONObject bb=	(JSONObject) array.get(c);	
				     	      nn=     bb.getString("children");
				               	li.add(bb);
				               	
				               	
				             FenleiAdapter adapter=new FenleiAdapter(FenleiActivity.this, li);  	
				             	
				               	listView1.setAdapter(adapter);	
				              	System.out.println("-=-=0"+nn);
				              	
					}
						
						} catch (Exception e) {
							// TODO: handle exception
						}
						

				}

				});
		
		listView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				
				listView1.setVisibility(arg0.INVISIBLE);
				listView2.setVisibility(arg0.VISIBLE);
				listView3.setVisibility(arg0.VISIBLE);
				try {
					
					 arrlis=new ArrayList<JSONObject>();
					JSONArray jsonArray=new JSONArray(nn);
					for(int i=0;i<jsonArray.length();i++){
				JSONObject jsonObject=	jsonArray.getJSONObject(i);	
			   child=	jsonObject.getString("children");
				//System.out.println("-9-987"+child+"-");
				          arrlis.add(jsonObject);
				          FenleiAdapter adapter=new FenleiAdapter(FenleiActivity.this, arrlis);  	
			             	
			               	listView2.setAdapter(adapter);	
					}
		
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			
				
			              
			}
		});
		
		listView2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
			String name=	arrlis.get(arg2).toString();
			//Toast.makeText(FenleiActivity.this, name, 0).show();
				
				try {
					
					
					JSONObject jsonObject=new JSONObject(name);
				String vv=	jsonObject.getString("cate_name");
					
				Intent intent=new Intent(FenleiActivity.this, Shenqingshanjia.class);
				
				intent.putExtra("cate", vv);
				startActivity(intent);
				finish();
				
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			
//				listView1.setVisibility(arg0.VISIBLE);
//				listView2.setVisibility(arg0.INVISIBLE);
//				listView3.setVisibility(arg0.VISIBLE);
//				
//				
//				
//				
//				try {
//					ArrayList<JSONObject> ara=new ArrayList<JSONObject>();
//					
//					 JSONArray array=new JSONArray(child);
//					for(int x=0;x<array.length();x++){
//					JSONObject jsonObject=	  array.getJSONObject(x);
//					ara.add(jsonObject);
//					FenleiAdapter adapter=new FenleiAdapter(FenleiActivity.this, ara);  	
//	             	
//	               	listView3.setAdapter(adapter);	
//	              
//	               	System.out.println("---------------------8-3"+ara);
//					}
//					
//					
//					
//					
//					
//					
//					
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
			
				       
			}
		});
		
		
          
	}

}
