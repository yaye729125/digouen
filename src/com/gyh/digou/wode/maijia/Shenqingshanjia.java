package com.gyh.digou.wode.maijia;


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
import com.gyh.digou.app.MyApp;
import com.gyh.digou.util.Tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Shenqingshanjia extends Activity{
	ArrayList<JSONObject>  li;
	ArrayList<JSONObject>  lis;
	ArrayList<JSONObject>  arrlis;
	ArrayList<JSONObject> warrlist;
	String   nn;
	String child;
	ArrayList<JSONObject>  arrayList;
	DiquAdapter adapter;
	String dianpuleixingid;
	String dianpuleixing;
	String dianpufenlei;
	String dianpufenleiid;
	String dianpushengshi;
	
	FenleiAdapter adapters;
	
	EditText dianzhuming_editText,dianpuming_editText,shengfenzhen_editText
	,dianpudizhi_editText,youzhen_editText,dianhua_editText;
	CheckBox xieyi_checkBox1;
	RadioButton dianpufenlei_button,dianpuleixing_button,dianpushengshi_button;
	
	//Spinner dianpufenlei_spinner,dianpuleixing_spinner1,dianpushengshi_spinner1;
	Button tijio_button1,xieyi_button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shenqingshangjia);
		dianzhuming_editText=(EditText) findViewById(R.id.dianzhuming_editText);
		shengfenzhen_editText=(EditText) findViewById(R.id.shengfenzhenhaoma_editText);
		dianpuming_editText=(EditText) findViewById(R.id.dianpuming_editText);
		dianpufenlei_button=(RadioButton)findViewById(R.id.dianpufenlei_button);
		dianpudizhi_editText=(EditText) findViewById(R.id.dianpudizhi_editText);
		youzhen_editText=(EditText) findViewById(R.id.youzhenbianma_editText);
		dianhua_editText=(EditText) findViewById(R.id.dianhuahaoma_editText);
		
		dianpuleixing_button=(RadioButton) findViewById(R.id.dianpuleixing_button);
		dianpushengshi_button=(RadioButton) findViewById(R.id.dianpushengshi_button);
		xieyi_checkBox1=(CheckBox) findViewById(R.id.xieyi_checkbox);
		xieyi_button=(Button) findViewById(R.id.xieyi_button);
		
		
		
//		AjaxParams params = new AjaxParams();
//		MyApp myApp = (MyApp) Shenqingshanjia.this.getApplication();
//		String token = myApp.getInfo().getData().getToken();
//		MyApp app = (MyApp) Shenqingshanjia.this.getApplication();
////		params.put("token", token);
////		params.put("pattern", "");
//
//		FinalHttp fh = new FinalHttp();
//		fh.post(Tools.getBaseUrl() + "?app=category&act=api_store",
//				params, new AjaxCallBack<String>() {
//
//					public void onFailure(Throwable t, int errorNo,
//							String strMsg) {
//						Toast.makeText(Shenqingshanjia.this, strMsg,
//								Toast.LENGTH_SHORT).show();
//					}
//
//					@Override
//					public void onSuccess(String t) {
//						 System.out.println("---8-0-------"+t+"-----80-----80------------");
//
//					
//						 try {
//							 
//							 JSONObject jsonObject=new JSONObject(t); 
//						         String name=jsonObject.getString("data");	
//						     JSONArray array=new JSONArray(name);    
//					         li=new ArrayList<JSONObject>();
//					            for(int c=0;c<array.length();c++){
//				     	         JSONObject bb=	(JSONObject) array.get(c);	
//				     	      
//				               	li.add(bb);
//				               	
//				               	
//				               	System.out.println("-=-=0"+li);
//					}
//						
//						
//							 
//							
//						} catch (Exception e) {
//							// TODO: handle exception
//						}
//						
//						 
//						 
//						 
//
//				}
//
//				});
//		
//		
//	
		
		

		
		
		
		
		AjaxParams paramsss = new AjaxParams();
//		MyApp myApp = (MyApp) FenleiActivity.this.getApplication();
//		String token = myApp.getInfo().getData().getToken();
//		MyApp app = (MyApp) FenleiActivity.this.getApplication();
//		params.put("token", token);
//		params.put("pattern", "");

		FinalHttp fhss = new FinalHttp();
		fhss.post(Tools.getBaseUrl() + "?app=category&act=api_store",
				paramsss, new AjaxCallBack<String>() {

					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						Toast.makeText(Shenqingshanjia.this, strMsg,
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
				               	
				               	
//				             FenleiAdapter adapter=new FenleiAdapter(Shenqingshanjia.this, li);  	
//				             	
//				               	listView1.setAdapter(adapter);	
//				              	System.out.println("-=-=0"+nn);
				              	
					}
						
						} catch (Exception e) {
							// TODO: handle exception
						}
						

				}

				});
		
		
		
		
		dianpufenlei_button.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			
			
			
			FenleiAdapter adapters=new FenleiAdapter(Shenqingshanjia.this, li); 
			
			 AlertDialog.Builder builder = new Builder(Shenqingshanjia.this);
				builder.setTitle("店铺分类");
		//
					View log = Shenqingshanjia.this.getLayoutInflater().inflate(R.layout.de_01, null);
					builder.setView(log);
					final AlertDialog alertDialogs = builder.create();
					alertDialogs.show();
					overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			ListView vi=	(ListView) log.findViewById(R.id.log_listView1);
			vi.setAdapter(adapters);
			
			
			vi.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					
					alertDialogs.dismiss();
					
					try {
						
						 arrlis=new ArrayList<JSONObject>();
						JSONArray jsonArray=new JSONArray(nn);
						for(int i=0;i<jsonArray.length();i++){
					JSONObject jsonObject=	jsonArray.getJSONObject(i);	
				   child=	jsonObject.getString("children");
					//System.out.println("-9-987"+child+"-");
					          arrlis.add(jsonObject);
					          FenleiAdapter adapter=new FenleiAdapter(Shenqingshanjia.this, arrlis);  	
				             	
				               
					          AlertDialog.Builder builder = new Builder(Shenqingshanjia.this);
								builder.setTitle("店铺分类");
						//
									View log = Shenqingshanjia.this.getLayoutInflater().inflate(R.layout.de_01, null);
									builder.setView(log);
									final AlertDialog alertDialog = builder.create();
									alertDialog.show();
									overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
							ListView vis=	(ListView) log.findViewById(R.id.log_listView1);
							vis.setAdapter(adapter);          
					          
					          
							vis.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									
									
									String name=	arrlis.get(arg2).toString();
									//Toast.makeText(FenleiActivity.this, name, 0).show();
										
										try {
											
											
											JSONObject jsonObject=new JSONObject(name);
										dianpufenlei=	jsonObject.getString("cate_name");
										 dianpufenleiid=	jsonObject.getString("cate_id");
											
										dianpufenlei_button.setText(dianpufenlei);
										
										Toast.makeText(Shenqingshanjia.this, dianpufenleiid, 0).show();
										} catch (Exception e) {
											// TODO: handle exception
										}
									
										alertDialog.dismiss();
									
									
									
									
								}
							})    ;      
					          
					          
						}
			
						
					} catch (Exception e) {
						// TODO: handle exception
					}
					
		
				}
			});
			
			
			
			
//			
	//		Intent intent=new Intent(Shenqingshanjia.this, FenleiActivity.class);
//			Bundle bundle=new Bundle();
//		//	intent.putExtra("shuju", li);
////			bundle.putString("shuju", li);
////			intent.putExtras(bundle);
	//		startActivity(intent);			
	//		finish();
			
			
		}
	});
		
		
		
		
		
		
		AjaxParams paramt = new AjaxParams();
		
//		params.put("token", token);
//		params.put("pattern", "");

		FinalHttp fh = new FinalHttp();
		fh.post(Tools.getBaseUrl() + "?app=apply&act=api_sgrade",
				paramt, new AjaxCallBack<String>() {

					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						Toast.makeText(Shenqingshanjia.this, strMsg,
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(String t) {
						 System.out.println("===---==8-==-------"+t+"-----810-----810------------");

						 try {
							
							 
							 JSONObject jsonObject=new JSONObject(t);	 
						JSONObject jsonObject2=jsonObject.getJSONObject("data");
							 
						 warrlist = new ArrayList<JSONObject>();
						// warrlist.add((JSONObject) object);
						Iterator<String> it = jsonObject2.keys();
						while (it.hasNext()) {
							String key = it.next();
							JSONObject object = jsonObject2.getJSONObject(key);
							warrlist.add(object);
						}
							 
							 
							 
							 
						} catch (Exception e) {
							// TODO: handle exception
						}
						 
					 
						 
						 
				}

				});
		
		
	
		
		dianpuleixing_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
		  LeixingAdapter		adaptery=new LeixingAdapter(Shenqingshanjia.this, warrlist); 
				AlertDialog.Builder builder = new Builder(Shenqingshanjia.this);
				builder.setTitle("选择店铺类型");
		//
					View log = Shenqingshanjia.this.getLayoutInflater().inflate(R.layout.de_01, null);
					builder.setView(log);
					final AlertDialog alertDialog = builder.create();
					alertDialog.show();
					//overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			ListView viy=	(ListView) log.findViewById(R.id.log_listView1);
			viy.setAdapter(adaptery);
				
			viy.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					
				   try {
					 dianpuleixing=	warrlist.get(arg2).getString("grade_name");
					 dianpuleixingid=	warrlist.get(arg2).getString("grade_id");	
					dianpuleixing_button.setText(dianpuleixing);			
					Toast.makeText(Shenqingshanjia.this, dianpuleixingid, 0).show();				
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
				   alertDialog.dismiss();
					
				}
			});
				
			}
		});
		
		
		
//		dianpushengshi_button.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
				
				
//				Intent intent=new Intent(Shenqingshanjia.this, DianpushengshiActivity.class);
//				startActivity(intent);
//				finish();
				
				
			
				AjaxParams paramss=new AjaxParams();
//				MyApp myApp=(MyApp)Shenqingshanjia.this.getApplication();
//				String token=myApp.getInfo().getData().getToken();
//				MyApp app=(MyApp) Shenqingshanjia.this.getApplication();

			paramss.put("region_id","0");
				
			FinalHttp fhs=new FinalHttp();
			
	      fhs.post(Tools.getBaseUrl()+"?app=mlselection&act=api_region",paramss,new AjaxCallBack<String>(){	       
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
				  
				  
				  
				   adapter=new DiquAdapter(Shenqingshanjia.this, arrayList); 
				   
				   dianpushengshi_button.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						 AlertDialog.Builder builder = new Builder(Shenqingshanjia.this);
						builder.setTitle("选择省市");
				//
							View log = Shenqingshanjia.this.getLayoutInflater().inflate(R.layout.de_01, null);
							builder.setView(log);
							final AlertDialog alertDialog = builder.create();
							alertDialog.show();
							overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
					ListView vi=	(ListView) log.findViewById(R.id.log_listView1);
					vi.setAdapter(adapter);
							
							
					
					vi.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							alertDialog.dismiss();
							
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
							DiquAdapter diquAdapter=new DiquAdapter(Shenqingshanjia.this, lis);  
							  AlertDialog.Builder builder = new Builder(Shenqingshanjia.this);
								builder.setTitle("选择省市");
						//
									View log = Shenqingshanjia.this.getLayoutInflater().inflate(R.layout.de_01, null);
									builder.setView(log);
									 final AlertDialog alertDialog = builder.create();
									alertDialog.show();
									overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
							ListView vis=	(ListView) log.findViewById(R.id.log_listView1);
							vis.setAdapter(diquAdapter);	  
							  
						
					vis.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							String ss=lis.get(arg2).toString();
							try {
								
								JSONObject jsonObjects=new JSONObject(ss);
								 dianpushengshi=	jsonObjects.getString("region_name");
								
								Toast.makeText(Shenqingshanjia.this, dianpushengshi, 0).show();
								dianpushengshi_button.setText(dianpushengshi);
							} catch (Exception e) {
								// TODO: handle exception
							}
							alertDialog.dismiss();
							
						}
					})	;	
							
				
							
							
							
						} catch (Exception e) {
							// TODO: handle exception
						}
						
						
						
							//Toast.makeText(DizhiBianji.this, name, 20).show();
						}
					});
					
					
					
					
					}
				})  ; 
				   
				   
			
				   
				   
			
				     
				     
				}  
				      
				     
				}			
						
				
				
						
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					
			
				}
			
				});

				
				
				
				
				
				
				
				
				
			//}
		//});
		
		
		
//
//		Intent intents=getIntent();
//		String text=intents.getStringExtra("diqu");
//		dianpushengshi_button.setText(text);
		
		
		Intent intent1=getIntent();
	String text2=intent1.getStringExtra("cate");
		
	dianpufenlei_button.setText(text2);
		
//
//		youzhen_editText=(EditText) findViewById(R.id.youzhen_editText);
//		dianhua_editText=(EditText) findViewById(R.id.dianhua_editText);
//		dianpufenlei_spinner=(Spinner) findViewById(R.id.dianpufenlei_spinner);
//		dianpuleixing_spinner1=(Spinner) findViewById(R.id.dianpuleixing_spinner1);
//		dianpushengshi_spinner1=(Spinner) findViewById(R.id.dianpushengshi_spinner1);
//		//xieyi_checkBox1=(CheckBox) findViewById(R.id.xieyi_checkBox1);

		tijio_button1=(Button) findViewById(R.id.tijio_button1);

		
		
	  tijio_button1.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
		String dianzhuming=	dianzhuming_editText.getText().toString();
			shengfenzhen_editText.getText().toString();
	String dianpuming=dianpuming_editText.getText().toString();
			dianpufenlei_button.getText().toString();
			dianpudizhi_editText.getText().toString();
			youzhen_editText.getText().toString();
		//Double double1=	dianpushengshi*1E;
			
		String dianhua=	dianhua_editText.getText().toString();
		Toast.makeText(Shenqingshanjia.this, dianhua,
				Toast.LENGTH_SHORT).show();
			AjaxParams params = new AjaxParams();
			
			params.put("token",Data.info.getData().getToken());
			params.put("sgrade_id", dianpuleixingid);
			params.put("store_name", dianpuming);
			params.put("owner_name", dianzhuming);
			params.put("tel", "18615773227");
			params.put("cate_id", dianpufenleiid);
			params.put("lng", "134");
			params.put("lat", "29");

			FinalHttp fh = new FinalHttp();
			fh.post(Tools.getBaseUrl() + "?app=apply&act=api_reply",
					params, new AjaxCallBack<String>() {

						public void onFailure(Throwable t, int errorNo,
								String strMsg) {
							Toast.makeText(Shenqingshanjia.this, strMsg,
									Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onSuccess(String t) {
							 System.out.println("---810-------"+t+"-----810-----810------------");
							 Toast.makeText(Shenqingshanjia.this, t,
										Toast.LENGTH_SHORT).show();
//							try {
	//
//								JSONObject jsonObject = new JSONObject(t);
//								String ErrNum = jsonObject.getString("ErrNum");
//								String ErrMsg = jsonObject.getString("ErrMsg");
//								JSONObject datajson = jsonObject
//										.getJSONObject("data");
//								// String total= datajson.getString("total");
	//
//								JSONObject listJson = datajson
//										.getJSONObject("list");
//								ArrayList<JSONObject> warrlist = new ArrayList<JSONObject>();
//								// warrlist.add((JSONObject) object);
//								Iterator<String> it = listJson.keys();
//								while (it.hasNext()) {
//									String key = it.next();
//									JSONObject object = listJson.getJSONObject(key);
//									warrlist.add(object);
//								}
	//
//								MessageAdapter adapter = new MessageAdapter(
//										Shenqingshanjia.this, warrlist);
	//
//								listview1.setAdapter(adapter);
	//
//							} catch (Exception e) {
//								// TODO: handle exception
	//
//							}
	//
					}

					});
			
			
			
			
		}
	});
		
		
		
	}
	
	

}


 
