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
import com.gyh.digou.wode.DindanxiangxiAdapter;
import com.gyh.digou.wode.DingdanXiangqing;
import com.gyh.digou.wode.maijia.MessageAdapter;
import com.gyh.digou.wode.maijia.Wodexiaoxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
public class ShangjiaDingdanxianxi extends Activity{
	TextView textView1,textView2,textView3,textView4,textView5,textView6,textView7;
	ListView listView;
	 Button button;
	 RadioButton button2;
	 LinearLayout layout;
	 String order_id;
	 String invoice_no;
	 DindanxiangxiAdapter adapter;
protected void onCreate(Bundle savedInstanceState) {
	
	super.onCreate(savedInstanceState);
	setContentView(R.layout.dingdanxiangqing1);
	
	 listView=(ListView) findViewById(R.id.xiangqing_list);
	
	 
	 
	 adapter=new DindanxiangxiAdapter(ShangjiaDingdanxianxi.this);
	 listView.setAdapter(adapter);
	 
	textView1=(TextView) findViewById(R.id.hao);
    textView2=(TextView) findViewById(R.id.day);
    textView3=(TextView) findViewById(R.id.dianpu);
    textView4=(TextView) findViewById(R.id.money);
    textView5=(TextView) findViewById(R.id.name);
    textView6=(TextView) findViewById(R.id.phone);
    textView7=(TextView) findViewById(R.id.adress);
    button=(Button) findViewById(R.id.butts);
    button2=(RadioButton) findViewById(R.id.xiangqing_fanhui);
    layout=(LinearLayout) findViewById(R.id.liner);
    button2.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			finish();
			
		}
	});
    button.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			
			
			AjaxParams params = new AjaxParams();
			
			params.put("token", Data.getInfo().getData().getToken());
			params.put("invoice_no", invoice_no);
			params.put("order_id", order_id);

			
			
			
			FinalHttp fh = new FinalHttp();
			fh.post(Tools.getBaseUrl() + "?app=seller_order&act=api_shipped",
					params, new AjaxCallBack<String>() {

						public void onFailure(Throwable t, int errorNo,
								String strMsg) {
						
						}

						@Override
						public void onSuccess(String t) {
							
							try {
								JSONObject jsonObject=new JSONObject(t);
								
							String num=	jsonObject.getString("ErrNum");
							
							if(num.equals("0")){
								  Toast.makeText(ShangjiaDingdanxianxi.this, "发货成功", 0).show();
								  finish();
							}
							if(num.equals("1")){
								Toast.makeText(ShangjiaDingdanxianxi.this, "发货失败", 0).show();
							}
								
								
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
						

						}

					});
			
			
			
			
			
		}
		
	});
    
    
    
    
	     Intent intent=getIntent();
	   String id=   intent.getStringExtra("id");
	
	   
	   
	   AjaxParams paramsq = new AjaxParams();
		

		paramsq.put("token", Data.getInfo().getData().getToken());
		paramsq.put("order_id", id);

		
		FinalHttp fhq = new FinalHttp();
		fhq.post(Tools.getBaseUrl() + "?app=seller_order&act=api_view",
				paramsq, new AjaxCallBack<String>() {

					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						Toast.makeText(ShangjiaDingdanxianxi.this, strMsg,
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(String t) {
						 System.out.println("456-------"+t+"--");
						try {
							JSONObject jsonObject=new JSONObject(t);
						JSONObject jsonObject2=jsonObject.getJSONObject("data");
						String hao=jsonObject2.getString("order_sn");
						String time=jsonObject2.getString("add_time");
						 order_id=jsonObject2.getString("order_id");
						String money=jsonObject2.getString("order_amount");
						String buyer=jsonObject2.getString("buyer_name");
						String status=jsonObject2.getString("status");
						 invoice_no=jsonObject2.getString("invoice_no");
						
					String data=jsonObject2.getString("data");
					JSONObject jsonObject3=new JSONObject(data);
				JSONObject jsonObject4=	jsonObject3.getJSONObject("order_extm");
				String maijiaming=jsonObject4.getString("consignee");
				String phone_mob=jsonObject4.getString("phone_mob");
				String phone_tel=jsonObject4.getString("phone_tel");
				String address=jsonObject4.getString("address");
				  textView5.setText(maijiaming);
				  textView6.setText(phone_mob);
				  textView7.setText(address);
			JSONObject jsonObject5=jsonObject3.getJSONObject("goods_list");
		     	ArrayList<JSONObject> warrlist = new ArrayList<JSONObject>();
		
			  Iterator<String> it = jsonObject5.keys();
			   while (it.hasNext()) {
				String key = it.next();
				JSONObject object = jsonObject5.getJSONObject(key);
				warrlist.add(object);
				
			}
			adapter.setData(warrlist);
				
						textView1.setText(hao);
						textView2.setText(time);	
						textView3.setText(buyer);
						textView4.setText(money);
					if(status.equals("11")){
						//button.setText("等待买家付款");
						button2.setText("待付款");
						layout.removeView(button);
					
					TextView textView=new TextView(ShangjiaDingdanxianxi.this);
					
					textView.setText("等待买家付款");
					textView.setTextSize(30);
					
					layout.addView(textView);
						
						
						
					}
					if(status.equals("20")){
						button.setText("确认发货");
						button2.setText("待发货");
					}
					if(status.equals("40")){
						//button.setText("交易完成");
						button2.setText("已完成");
						
						layout.removeView(button);
						
						TextView textView=new TextView(ShangjiaDingdanxianxi.this);
						
						textView.setText("交易完成");
						textView.setTextSize(30);
						textView.setGravity(Gravity.CENTER);
						
						layout.addView(textView);
						
						
					}
					if(status.equals("0")){
						//button.setText("交易已取消");
						button2.setText("已取消");
                           layout.removeView(button);
						
						TextView textView=new TextView(ShangjiaDingdanxianxi.this);
						
						textView.setText("交易已取消");
						textView.setTextSize(30);
						
						layout.addView(textView);
					
					}
					if(status.equals("30")){
						button2.setText("待收货");
						layout.removeView(button);
						TextView textView=new TextView(ShangjiaDingdanxianxi.this);
						textView.setText("等待买家确认收货");
						textView.setTextSize(30);
						layout.addView(textView);
						
					}
							
							
							
							
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						  
										 								 
				}

		});	
	 
}
}
