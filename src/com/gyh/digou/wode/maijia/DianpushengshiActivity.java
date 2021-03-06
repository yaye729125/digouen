package com.gyh.digou.wode.maijia;

import java.util.ArrayList;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONObject;

import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.util.Tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DianpushengshiActivity extends Activity{
	public static final String MY_NEW_LIFEFORM="NEW_LIFEFORM";
	DiquAdapter adapter;
	 ArrayList<JSONObject> arrayList;
	 ArrayList<JSONObject> lis;
	 ListView listView1,listView2,listView3;
	 String tt;
	 String opo;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.de_01);
		 listView1=(ListView) findViewById(R.id.log_listView1);
		 listView2=(ListView) findViewById(R.id.log_listView2);
		 listView3=(ListView) findViewById(R.id.log_listView3);
		
		
		
		  AjaxParams params=new AjaxParams();
			

		params.put("region_id","0");
			
		FinalHttp fh=new FinalHttp();
		
    fh.post(Tools.getBaseUrl()+"?app=mlselection&act=api_region",params,new AjaxCallBack<String>(){	       
			public void onFailure(Throwable t, int errorNo,
					String strMsg) {
		//	Toast.makeText(DizhiBianji.this,strMsg,Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onSuccess(String t) {	
				System.out.println("===20=="+t+"====");	
				
				try {
				
					JSONObject jsonObject=new JSONObject(t);
			JSONArray array=jsonObject.getJSONArray("data");
			JSONObject jsonObject2 = null;
			for(int i=0;i<array.length();i++){ 
						
				  jsonObject2 = (JSONObject)array.get(i); 
			
				AdressInfo adressInfo=new AdressInfo();
				adressInfo.setRegion_id(jsonObject2.getString("region_id"));
				adressInfo.setRegion_id(jsonObject2.getString("region_name"));
				adressInfo.setRegion_id(jsonObject2.getString("parent_id"));
				adressInfo.setRegion_id(jsonObject2.getString("sort_order"));
				adressInfo.setRegion_id(jsonObject2.getString("children"));
			       String name=jsonObject2.getString("children");	
			       String names=   jsonObject2.getString("region_name");
			       String cc=jsonObject2.getString("sort_order");
			       
			     System.out.println("--"+name+"---89898989898989899");	 
			     System.out.println("--"+names+"---89898989898989899");	
			     System.out.println("--"+cc+"---89898989898989899");	
			     
			JSONArray array2=new JSONArray(name);  
			
			   arrayList=new ArrayList<JSONObject>();  
			for(int x=0;x<array2.length();x++){ 			
				  JSONObject jsonObject3 = (JSONObject)array2.get(x);
	
			String kk=jsonObject3.getString("region_name");		    
			  System.out.println("++"+kk+"---89898989898989899");
			  
			
			  arrayList.add(jsonObject3);
			  
			  
			  
			   adapter=new DiquAdapter(DianpushengshiActivity.this, arrayList); 
			   
			   listView1.setAdapter(adapter);
			   
			   
			}
			     
			   listView1.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					
					listView1.setVisibility(arg0.INVISIBLE);
					listView2.setVisibility(arg0.VISIBLE);
					listView3.setVisibility(arg0.VISIBLE);
					String name=	arrayList.get(arg2).toString();
					
					
					
					try {
						
						

						JSONObject jsonObject=new JSONObject(name);
						String names=jsonObject.getString("children");
						
						JSONArray jsonArray=new JSONArray(names);
						
						 lis=new ArrayList<JSONObject>();
						for(int d=0;d<jsonArray.length();d++){
						JSONObject js=	(JSONObject) jsonArray.get(d);
						
					String	  region_name=  js.getString("region_name");
						  
						  lis.add(js);
						}
						
						
						adapter=new DiquAdapter(DianpushengshiActivity.this, lis); 
						   
						   listView2.setAdapter(adapter);
						 
						  
						
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					
					
					
				}
					
				
			});
			   
			   
			   listView2.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {					
					
			               String oo=lis.get(arg2).toString();
			               
			               try {
							
			            	   
			            	   JSONObject jsonObject=new JSONObject(oo);
			            	   
			            	tt=   jsonObject.getString("region_name");
			            	   
			            	   
			            	   
						} catch (Exception e) {
							// TODO: handle exception
						}
			               
//			               
//			               final Intent intent=new Intent(MY_NEW_LIFEFORM);  
//			               intent.putExtra("msgContent", tt);
//			               sendBroadcast(intent);		               
//			               finish();
			             Intent intent=new Intent(DianpushengshiActivity.this, Shenqingshanjia.class);			             
			             intent.putExtra("diqu", tt);
			               startActivity(intent);
			               finish();
					//Toast.makeText(DianpushengshiActivity.this, tt, 0).show();
					
				}
			});
			   
			   
			}			
					
			
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				
		
			}
		
			});

		
	}

}
