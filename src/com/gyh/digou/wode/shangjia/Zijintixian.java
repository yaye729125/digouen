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
import com.gyh.digou.app.MyApp;
import com.gyh.digou.util.Tools;
import com.gyh.digou.wode.maijia.MessageAdapter;
import com.gyh.digou.wode.maijia.Wodexiaoxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Zijintixian extends Activity{
	ListView listView;
	 TextView zijiyue_text;
	 EditText zijin_tixian_ed1;
	 RadioButton tixian_mingxi;
	 RadioButton querentixian_butt1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zijintixian);
	 listView=(ListView) findViewById(R.id.zijin_tixian_list);
	 
	zijiyue_text=(TextView) findViewById(R.id.zijiyue_text);
	 zijin_tixian_ed1=(EditText) findViewById(R.id.zijin_tixian_ed1);
	 querentixian_butt1=(RadioButton) findViewById(R.id.querentixian_butt1);
	 querentixian_butt1.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			
		String zijin=	zijin_tixian_ed1.getText().toString();
		if(zijin.length()<1){
			Toast.makeText(Zijintixian.this, "请输入提现金额",
					Toast.LENGTH_SHORT).show();
		}
		if(zijin.length()>=1){
		
			
			AjaxParams params = new AjaxParams();
			
			params.put("token",Data.info.getData().getToken());
			params.put("funds", zijin);
			

			FinalHttp fh = new FinalHttp();
			fh.post(Tools.getBaseUrl() + "?app=my_pay&act=api_withdraw",
					params, new AjaxCallBack<String>() {

						public void onFailure(Throwable t, int errorNo,
								String strMsg) {
							Toast.makeText(Zijintixian.this, strMsg,
									Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onSuccess(String t) {
							 System.out.println("---20-------"+t+"----280-----80------------");
                                  try {
									JSONObject jsonObject=new JSONObject(t);
									String ErrNum=	jsonObject.getString("ErrNum");
									String ErrMsg=	jsonObject.getString("ErrMsg");	
									if(ErrNum.equals("0")){				
									Toast.makeText(Zijintixian.this, ErrMsg, 0).show();
									Intent intent=new Intent(Zijintixian.this, Zijintixian.class);
									startActivity(intent);
									
									}		
									if(ErrNum.equals("1")){				
										Toast.makeText(Zijintixian.this, ErrMsg, 0).show();	
										}	
									
									
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						}

					});
			
			
			
			
			
		}
			
			
		}
	});
	 
	 tixian_mingxi=(RadioButton) findViewById(R.id.tixian_mingxi);
	 
	 tixian_mingxi.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			  Intent intent=new Intent(Zijintixian.this, Yinghang.class);
			  startActivity(intent);
			
		}
	});
	
		
		
		AjaxParams params = new AjaxParams();
		
		params.put("token",Data.info.getData().getToken());
		

		FinalHttp fh = new FinalHttp();
		fh.post(Tools.getBaseUrl() + "?app=my_pay&act=api_withdraw_list",
				params, new AjaxCallBack<String>() {

					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						Toast.makeText(Zijintixian.this, strMsg,
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(String t) {
						 System.out.println("---20-------"+t+"----280-----80------------");

               try {
				JSONObject jsonObject=new JSONObject(t);
				
			JSONObject jsonObject2=	jsonObject.getJSONObject("data");
			String paysum=jsonObject2.getString("paysum");
			zijiyue_text.setText(paysum);
			ArrayList<JSONObject> arrayLists=new ArrayList<JSONObject>();
			
			
			JSONArray array=jsonObject2.getJSONArray("list");
				for(int x=0;x<array.length();x++){
				JSONObject jsonObject3=	array.getJSONObject(x);
				String funds=jsonObject3.getString("funds");
				String detail=jsonObject3.getString("detail");
				String time=jsonObject3.getString("time");
				arrayLists.add(jsonObject3);
				System.out.println("jjsksjd----"+funds+detail+time);
				ZijinListAdapter adapter1=new ZijinListAdapter(Zijintixian.this, arrayLists);
				 listView.setAdapter(adapter1);
				}
			
				
			
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
               
					}

				});
		
		
	}

}
