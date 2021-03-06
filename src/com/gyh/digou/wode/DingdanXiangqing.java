package com.gyh.digou.wode;



import java.util.ArrayList;
import java.util.Iterator;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.util.Tools;
import com.gyh.digou.wode.maijia.DianpushengshiActivity;
import com.gyh.digou.wode.shangjia.ShangjiaDingdanxianxi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ResourceAsColor")
public class DingdanXiangqing extends Activity{
	TextView textView1,textView2,textView3,textView4,textView5,textView6,textView7;
	ListView listView;
	 Button button;
	 LinearLayout liout;
	 TextView textView;
	 DindanxiangxiAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dingdanxiangqing);
		
		Intent intent=getIntent();
		String order_id=intent.getStringExtra("order_id");
		
		   textView1=(TextView) findViewById(R.id.hao);
		    textView2=(TextView) findViewById(R.id.day);
		    textView3=(TextView) findViewById(R.id.dianpu);
		    textView4=(TextView) findViewById(R.id.money);
		    textView5=(TextView) findViewById(R.id.name);
		    textView6=(TextView) findViewById(R.id.phone);
		    textView7=(TextView) findViewById(R.id.adress);
		    button=(Button) findViewById(R.id.butts);
		    liout=(LinearLayout) findViewById(R.id.liout);
		    
		     textView=new TextView(DingdanXiangqing.this);
		    
		listView=(ListView) findViewById(R.id.xiangqing_list);
		
		
		
		adapter=new DindanxiangxiAdapter(DingdanXiangqing.this);
		listView.setAdapter(adapter);
		
		
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				

				
			}
		});
		
		getData(order_id);
		
		
		  
                 
              
			
			
			
			
			
			
			
			
			
			
			
                 
    	   
      
			
			
	}
	
	
	
	public void getData(String order_id)
	{
		
	
		AjaxParams params = new AjaxParams();
		String token=MyApp.info.getData().getToken();
		/*MyApp myApp = (MyApp) this.getApplication();
		String token = myApp.getInfo().getData().getToken();
		MyApp app = (MyApp) DingdanXiangqing.this.getApplication();*/
		
		params.put("token", token);
		params.put("order_id", order_id);
		//Toast.makeText(DingdanXiangqing.this, order_id, 0).show();
		FinalHttp fh = new FinalHttp();
		fh.post(Tools.getBaseUrl() + "?app=buyer_order&act=api_view",
				params, new AjaxCallBack<String>() {

					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						Toast.makeText(DingdanXiangqing.this, strMsg,
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(String t) {
						 System.out.println("---819-------"+t+"--");
						
				try {
			
				   
					   JSONObject jsonObject=new JSONObject(t);
				   
			           JSONObject jsonObject2=	   jsonObject.getJSONObject("data");
				           
			           String order_sn=jsonObject2.getString("order_sn");
			                String name=jsonObject2.getString("store_name");
			              String add_time=jsonObject2.getString("add_time");
			              String order_amount=jsonObject2.getString("order_amount");
			              String data=jsonObject2.getString("data");
			              String stat=jsonObject2.getString("status");
			              textView2.setText(add_time);
			                textView3.setText(name);
			                textView1.setText(order_sn);
			                textView4.setText(order_amount);
			                
			               if(stat.equals("11")) {
			                
				             button.setText("立即付款");
				             
				             
			                
			               }    
			               if(stat.equals("30")) {
			            	   
			            	   button.setText("确认收货");
			            	   
			               }    
			               if(stat.equals("40")) {
			            	   liout.removeView(button);
			            	  // button.setText("交易成功");
			            	   textView.setText("交易成功");
			            	   textView.setTextSize(30);
			            	   liout.addView(textView);
			            	  // button.setBackgroundColor(R.color.tv_Touming);
			            	   
			               }    
			               if(stat.equals("0")) {
			            	   liout.removeView(button);
			            	   // button.setText("交易成功");
			            	   textView.setText("交易取消");
			            	   textView.setTextSize(30);
			            	   liout.addView(textView);
			            	   // button.setBackgroundColor(R.color.tv_Touming);
			            	   
			               }    
			               if(stat.equals("20")) {
			            	   liout.removeView(button);
			            	   // button.setText("交易成功");
			            	   textView.setText("等待卖家发货");
			            	   textView.setTextSize(30);
			            	   liout.addView(textView);
			            	   // button.setBackgroundColor(R.color.tv_Touming);
			            	   
			               }    
			               if(stat.equals("10")) {
			            	   
			            	 //  button.setText("确认收货");
			            	   
			               }    
				   
			                
			             Toast.makeText(DingdanXiangqing.this, data, 0).show();
			                
			                
			             System.out.println("-0-8-7"+data);
			             
			             
			             JSONObject jsonObject3=new JSONObject(data);
			             
				        JSONObject jsonObject4= jsonObject3.getJSONObject("goods_list");
				             
				        ArrayList<JSONObject> warrlists = new ArrayList<JSONObject>();
						// warrlist.add((JSONObject) object);
						Iterator<String> it = jsonObject4.keys();
						while (it.hasNext()) {
							String key = it.next();
							JSONObject object = jsonObject4.getJSONObject(key);
							warrlists.add(object);
							
						}
					
					adapter.setData(warrlists);
					Tools.setListViewHeightBasedOnChildren(listView);
					JSONObject jsonObjectf=new JSONObject(data);
					
					JSONObject js=	jsonObjectf.getJSONObject("order_extm");
			        String na=	js.getString("consignee");
			        String na1=	js.getString("address");
			        String na2=	js.getString("region_name");
			        String na3=	js.getString("phone_mob");
					
			        textView5.setText(na);
			        textView7.setText(na1+na2);
			        textView6.setText(na3);
					
					
				  } catch (Exception e) {
						
				  }
							 
									 
				}

			});	
		
	}
		
		
	
	
	
	

}
