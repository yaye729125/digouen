package com.gyh.digou.wode.shangjia;



import java.util.ArrayList;
import java.util.Iterator;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gyh.digou.Data;
import com.gyh.digou.R;

import com.gyh.digou.shouye.MyListView;
import com.gyh.digou.util.Tools;
import com.gyh.digou.wode.DingdanAdapter;
import com.gyh.digou.wode.DingdanXiangqing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;



public class ShangjiaDingdangunaliActivity extends Activity{
	
	
	
	public void getData(String mode)
	{
		
		AjaxParams params1 = new AjaxParams();
		/*MyApp myApp = (MyApp)ShangjiaDingdangunaliActivity.this.getApplication();
		String token = myApp.getInfo().getData().getToken();*/

		params1.put("token",Data.info.getData().getToken());
		params1.put("type", "all");

		
		FinalHttp fh1 = new FinalHttp();
		fh1.post(Tools.getBaseUrl() + "?app=seller_order&act=api_orders",
				params1, new AjaxCallBack<String>() {

					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						Toast.makeText(ShangjiaDingdangunaliActivity.this, strMsg,
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(String t) {
						 System.out.println("0---qwwwww-------"+t+"--");
						 try {

								JSONObject jsonObject = new JSONObject(t);
								String ErrNum = jsonObject.getString("ErrNum");
								String ErrMsg = jsonObject.getString("ErrMsg");
								JSONObject datajson = jsonObject
										.getJSONObject("data");
								// String total= datajson.getString("total");

								JSONArray listJson = datajson
										.getJSONArray("list");
								/*ArrayList<JSONObject> warrlist1 = new ArrayList<JSONObject>();
								
								Iterator<String> it = listJson.keys();
								while (it.hasNext()) {
									String key = it.next();
									JSONObject object = listJson.getJSONObject(key);
									warrlist1.add(object);

								}*/
							
								adapter.setData(listJson);
								
						

							} catch (Exception e) {
								// TODO: handle exception

							}

						}

					});	
		
		
		
	}
	MyListView listView;
	DingdanAdapter adapter;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dingdanguanli);

		listView=(MyListView)findViewById(R.id.dingdanguanli_list);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				try {
					Intent intent=new Intent(ShangjiaDingdangunaliActivity.this,DingdanXiangqing.class);
					
					
					intent.putExtra("order_id", adapter.getData().getJSONObject(arg2).getString("order_id"));
					startActivity(intent);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		
		 
		adapter=new DingdanAdapter(this);
		listView.setAdapter(adapter);
		getData("all");
		 
		 
		 
	}
	public void on1(View v)
	{
		
		getData("all");
		
	}
	public void on2(View v)
	{
		
		getData("pending");
		
		
	}
	public void on3(View v)
	{
		
		getData("accepted");
		
	}
	public void on4(View v)
	{
		
		getData("finished");
		
	}
	public void on5(View v)
	{
		
		getData("canceled");
		
	}		
			
			
			/*
		
			
	
		
				 AjaxParams params2 = new AjaxParams();
			
					params2.put("token", token);
					params2.put("type", "pending");

					
					FinalHttp fh2 = new FinalHttp();
					fh2.post(Tools.getBaseUrl() + "?app=seller_order&act=api_orders",
							params2, new AjaxCallBack<String>() {

								public void onFailure(Throwable t, int errorNo,
										String strMsg) {
									Toast.makeText(ShangjiaDingdangunaliActivity.this, strMsg,
											Toast.LENGTH_SHORT).show();
								}

								@Override
								public void onSuccess(String t) {
									 System.out.println("0---qwwwww-------"+t+"--");
									 
									
									 
									 
									 try {

											JSONObject jsonObject = new JSONObject(t);
											String ErrNum = jsonObject.getString("ErrNum");
											String ErrMsg = jsonObject.getString("ErrMsg");
											JSONObject datajson = jsonObject
													.getJSONObject("data");
											// String total= datajson.getString("total");

											JSONObject listJson = datajson
													.getJSONObject("list");
											 warrlist2 = new ArrayList<JSONObject>();
											
											Iterator<String> it = listJson.keys();
											while (it.hasNext()) {
												String key = it.next();
												JSONObject object = listJson.getJSONObject(key);
												warrlist2.add(object);

											
											}

											System.out.println("-33-11"+warrlist2);
										
											YiyueqianDingDaniAdapter adapter=new YiyueqianDingDaniAdapter(ShangjiaDingdangunaliActivity.this, warrlist2);
									           listView2.setAdapter(adapter);						
										

										} catch (Exception e) {
											// TODO: handle exception

										}

									}

								});	
		
	
				 AjaxParams params3 = new AjaxParams();

					params3.put("token", token);
					params3.put("type", "accepted");

					
					FinalHttp fh3 = new FinalHttp();
					fh3.post(Tools.getBaseUrl() + "?app=seller_order&act=api_orders",
							params3, new AjaxCallBack<String>() {

								public void onFailure(Throwable t, int errorNo,
										String strMsg) {
									Toast.makeText(ShangjiaDingdangunaliActivity.this, strMsg,
											Toast.LENGTH_SHORT).show();
								}

								@Override
								public void onSuccess(String t) {
									 System.out.println("0---qwwwww-------"+t+"--");
									 
									
									 
									 
									 try {

											JSONObject jsonObject = new JSONObject(t);
											String ErrNum = jsonObject.getString("ErrNum");
											String ErrMsg = jsonObject.getString("ErrMsg");
											JSONObject datajson = jsonObject
													.getJSONObject("data");
									

											JSONObject listJson = datajson
													.getJSONObject("list");
											 warrlist3 = new ArrayList<JSONObject>();
											
											Iterator<String> it = listJson.keys();
											while (it.hasNext()) {
												String key = it.next();
												JSONObject object = listJson.getJSONObject(key);
												warrlist3.add(object);

											
											
											}

											System.out.println("-33-11"+warrlist3);
//						 
											YiyueqianDingDaniAdapter adapter=new YiyueqianDingDaniAdapter(ShangjiaDingdangunaliActivity.this, warrlist3);
									           listView3.setAdapter(adapter);						
															
											
										

										} catch (Exception e) {
											

										}

									}

								});	
				  
		
				 AjaxParams params4 = new AjaxParams();

					params4.put("token", token);
					params4.put("type", "finished");

					
					FinalHttp fh4 = new FinalHttp();
					fh4.post(Tools.getBaseUrl() + "?app=seller_order&act=api_orders",
							params4, new AjaxCallBack<String>() {

								public void onFailure(Throwable t, int errorNo,
										String strMsg) {
									Toast.makeText(ShangjiaDingdangunaliActivity.this, strMsg,
											Toast.LENGTH_SHORT).show();
								}

								@Override
								public void onSuccess(String t) {
									 System.out.println("333333333333333333-------"+t+"--");
									 
									
									 
									 
									 try {

											JSONObject jsonObject = new JSONObject(t);
											String ErrNum = jsonObject.getString("ErrNum");
											String ErrMsg = jsonObject.getString("ErrMsg");
											JSONObject datajson = jsonObject
													.getJSONObject("data");
											// String total= datajson.getString("total");

											JSONObject listJson = datajson
													.getJSONObject("list");
											 warrlist4 = new ArrayList<JSONObject>();
											
											Iterator<String> it = listJson.keys();
											while (it.hasNext()) {
												String key = it.next();
												JSONObject object = listJson.getJSONObject(key);
												warrlist4.add(object);

											
											
											}

											System.out.println("-33-11"+warrlist4);
//						 
									
											YiyueqianDingDaniAdapter adapter=new YiyueqianDingDaniAdapter(ShangjiaDingdangunaliActivity.this, warrlist4);
									           listView4.setAdapter(adapter);								
											
										

										} catch (Exception e) {
											

										}

									}

								});	
			
				 
				 AjaxParams params5 = new AjaxParams();

					params5.put("token", token);
					params5.put("type", "canceled");

					
					FinalHttp fhq = new FinalHttp();
					fhq.post(Tools.getBaseUrl() + "?app=seller_order&act=api_orders",
							params5, new AjaxCallBack<String>() {

								public void onFailure(Throwable t, int errorNo,
										String strMsg) {
									Toast.makeText(ShangjiaDingdangunaliActivity.this, strMsg,
											Toast.LENGTH_SHORT).show();
								}

								@Override
								public void onSuccess(String t) {
									 System.out.println("333333333333333333-------"+t+"--");
									 
									
									 
									 
									 try {

											JSONObject jsonObject = new JSONObject(t);
											String ErrNum = jsonObject.getString("ErrNum");
											String ErrMsg = jsonObject.getString("ErrMsg");
											JSONObject datajson = jsonObject
													.getJSONObject("data");
											// String total= datajson.getString("total");

											JSONObject listJson = datajson
													.getJSONObject("list");
											 warrlist5 = new ArrayList<JSONObject>();
											
											Iterator<String> it = listJson.keys();
											while (it.hasNext()) {
												String key = it.next();
												JSONObject object = listJson.getJSONObject(key);
												warrlist5.add(object);

											
											
											}

											System.out.println("-33-11"+warrlist5);
//						 
										
											YiyueqianDingDaniAdapter adapter=new YiyueqianDingDaniAdapter(ShangjiaDingdangunaliActivity.this, warrlist5);
									           listView5.setAdapter(adapter);								
													
											
											
										

										} catch (Exception e) {
											

										}

									}

								});	
				  
		
				
		 listView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
			try {
				String id=	warrlist1.get(arg2).getString("order_id");
				
				Intent intent=new Intent(ShangjiaDingdangunaliActivity.this, ShangjiaDingdanxianxi.class);
				intent.putExtra("id", id);
				startActivity(intent);
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
				
				
			}
		});	
		 listView2.setOnItemClickListener(new OnItemClickListener() {
			 
			 @Override
			 public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					 long arg3) {
				 
				 try {
					 String id=	warrlist2.get(arg2).getString("order_id");
					 
					 Intent intent=new Intent(ShangjiaDingdangunaliActivity.this, ShangjiaDingdanxianxi.class);
					 intent.putExtra("id", id);
					 startActivity(intent);
					 
					 
				 } catch (JSONException e) {
					 // TODO Auto-generated catch block
					 e.printStackTrace();
				 }
				 
				 
				 
			 }
		 });	
		 listView3.setOnItemClickListener(new OnItemClickListener() {
			 
			 @Override
			 public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					 long arg3) {
				 
				 try {
					 String id=	warrlist3.get(arg2).getString("order_id");
					 
					 Intent intent=new Intent(ShangjiaDingdangunaliActivity.this, ShangjiaDingdanxianxi.class);
					 intent.putExtra("id", id);
					 startActivity(intent);
					 
					 
				 } catch (JSONException e) {
					 // TODO Auto-generated catch block
					 e.printStackTrace();
				 }
				 
				 
				 
			 }
		 });	
		 listView4.setOnItemClickListener(new OnItemClickListener() {
			 
			 @Override
			 public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					 long arg3) {
				 
				 try {
					 String id=	warrlist4.get(arg2).getString("order_id");
					 
					 Intent intent=new Intent(ShangjiaDingdangunaliActivity.this, ShangjiaDingdanxianxi.class);
					 intent.putExtra("id", id);
					 startActivity(intent);
					 
					 
				 } catch (JSONException e) {
					 // TODO Auto-generated catch block
					 e.printStackTrace();
				 }
				 
				 
				 
			 }
		 });	
		 listView5.setOnItemClickListener(new OnItemClickListener() {
			 
			 @Override
			 public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					 long arg3) {
				 
				 try {
					 String id=	warrlist5.get(arg2).getString("order_id");
					 
					 Intent intent=new Intent(ShangjiaDingdangunaliActivity.this, ShangjiaDingdanxianxi.class);
					 intent.putExtra("id", id);
					 startActivity(intent);
					 
					 
				 } catch (JSONException e) {
					 // TODO Auto-generated catch block
					 e.printStackTrace();
				 }
				 
				 
				 
			 }
		 });	
		
	}
	
	public void on1(View v) {
		listView1.setVisibility(v.VISIBLE);
		listView2.setVisibility(v.INVISIBLE);
		listView3.setVisibility(v.INVISIBLE);
		listView4.setVisibility(v.INVISIBLE);
		listView5.setVisibility(v.INVISIBLE);

	}
	public void on2(View v) {
		listView1.setVisibility(v.INVISIBLE);
		listView3.setVisibility(v.INVISIBLE);
		listView4.setVisibility(v.INVISIBLE);
		listView2.setVisibility(v.VISIBLE);
		listView5.setVisibility(v.INVISIBLE);
		
	}
	public void on3(View v) {
		listView1.setVisibility(v.INVISIBLE);
		listView3.setVisibility(v.VISIBLE);
		listView4.setVisibility(v.INVISIBLE);
		listView2.setVisibility(v.INVISIBLE);
		listView5.setVisibility(v.INVISIBLE);
		
	}
	public void on4(View v) {
		listView1.setVisibility(v.INVISIBLE);
		listView3.setVisibility(v.INVISIBLE);
		listView4.setVisibility(v.VISIBLE);
		listView2.setVisibility(v.INVISIBLE);
		listView5.setVisibility(v.INVISIBLE);
		
	}
	public void on5(View v) {
		listView1.setVisibility(v.INVISIBLE);
		listView3.setVisibility(v.INVISIBLE);
		listView5.setVisibility(v.VISIBLE);
		listView2.setVisibility(v.INVISIBLE);
		listView4.setVisibility(v.INVISIBLE);
		
	}*/
	
	

}
