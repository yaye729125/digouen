package com.gyh.digou.wode.maijia;

import java.util.ArrayList;
import java.util.Iterator;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.gyh.digou.Data;
import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.itemdetail.ItemDetailActivity;
import com.gyh.digou.itemdetail.shop.ShopActivity;
import com.gyh.digou.util.Tools;

public class Wodeshoucang extends Activity {
	 ListView listView1,listView2;
	// ArrayList<JSONObject> warrlistst;
	
	 
	 
	 public void getStoreData()
	 {
		 
		 
		 AjaxParams paramys = new AjaxParams();
//			MyApp myApp = (MyApp) this.getApplication();
//			String token = myApp.getInfo().getData().getToken();
//			MyApp app = (MyApp) Wodeshoucang.this.getApplication();
			paramys.put("token",Data.getInfo().getData().getToken());
			paramys.put("type", "store");

			FinalHttp fhy = new FinalHttp();
			fhy.post(Tools.getBaseUrl() + "?app=my_favorite&act=api_favorite",
					paramys, new AjaxCallBack<String>() {

						public void onFailure(Throwable t, int errorNo,
								String strMsg) {
							Toast.makeText(Wodeshoucang.this, strMsg,
									Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onSuccess(String t) {							
						 System.out.println("---811-------"+t+"-----811-----811------------");

				      		
						 
						 try {
							JSONObject jsonObject=new JSONObject(t);
						JSONObject jsonObject2=jsonObject.getJSONObject("data");
					 JSONObject jsonObject3=	jsonObject2.getJSONObject("store");			
					 ArrayList<JSONObject>  warrlistst = new ArrayList<JSONObject>();					
						Iterator<String> it = jsonObject3.keys();
						while (it.hasNext()) {
							String key = it.next();
							JSONObject object = jsonObject3.getJSONObject(key);
							warrlistst.add(object);
						}
							
						
							
							
							
							adapterStore.setData(warrlistst);
							
							
							
							
							
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
						}

					});
		 
	 }
	 
	 
	/* public String getToken()
	 {
		 MyApp myApp = (MyApp) this.getApplication();
		 String token = myApp.getInfo().getData().getToken();
			
		 return token;
		 
	 }*/
	 public void getGoodsData()
	 {
		 
		 
		 
		 AjaxParams params = new AjaxParams();
			
			params.put("token",Data.getInfo().getData().getToken());
			params.put("type", "goods");

			FinalHttp fh = new FinalHttp();
			fh.post(Tools.getBaseUrl() + "?app=my_favorite&act=api_favorite",
					params, new AjaxCallBack<String>() {

						public void onFailure(Throwable t, int errorNo,
								String strMsg) {
							Toast.makeText(Wodeshoucang.this, strMsg,
									Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onSuccess(String t) {							
						 System.out.println("---81-------"+t+"-----810-----810------------");

						 try {
							 JSONObject jsonObject=new JSONObject(t);	
							JSONObject jsonObject2=jsonObject.getJSONObject("data");
							JSONObject jsonObject3=	jsonObject2.getJSONObject("goods");
							ArrayList<JSONObject> warrlist = new ArrayList<JSONObject>();
							Iterator<String> it = jsonObject3.keys();
							while (it.hasNext()) {
								String key = it.next();
								JSONObject object = jsonObject3.getJSONObject(key);
								warrlist.add(object);
							}
							 
					
							adapterGoods.setData(warrlist);
						
						} catch (Exception e) {
							// TODO: handle exception
						}
						 
				      		
							 
						}

					});
		 
		 
		 
		 
	 }
	 ShoucangStoreAdapter adapterStore;
	 ShoucangGoodsAdapter adapterGoods;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wodeshoucang);
		
		listView1=(ListView) findViewById(R.id.list_shoucang_1);
	     listView2=(ListView) findViewById(R.id.list_shoucang_2);
	     
	     
	     
	     	adapterGoods=new ShoucangGoodsAdapter(Wodeshoucang.this);
			
			
			listView1.setAdapter(adapterGoods);	
			
			
			adapterStore=new ShoucangStoreAdapter(Wodeshoucang.this);
			listView2.setAdapter(adapterStore);	
			
			
			listView1.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					try {
					Intent intent=new Intent(Wodeshoucang.this,ItemDetailActivity.class);
					
						intent.putExtra("goods_id",adapterGoods.getData().get(arg2).getString("goods_id"));
					
					startActivity(intent);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			listView2.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					
					try {
						
						Intent intent=new Intent(Wodeshoucang.this,ShopActivity.class);
						
						intent.putExtra("store_id",adapterStore.getData().get(arg2).getString("store_id"));
						
						startActivity(intent);
						
						
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					
				}
			});
			getGoodsData();
	}
           
	public void on1(View v) {
		getGoodsData();
		listView1.setVisibility(v.VISIBLE);
		listView2.setVisibility(v.INVISIBLE);
		
		
		

	}
	public void on2(View v) {
		getStoreData();
		listView1.setVisibility(v.INVISIBLE);
		listView2.setVisibility(v.VISIBLE);
		
		
	}

	
	
}
