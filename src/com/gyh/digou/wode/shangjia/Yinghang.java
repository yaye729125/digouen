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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Yinghang extends Activity{
	EditText yinhang_ed1,yinhang_ed2,yinhang_ed3,yinhang_ed4,yinhang_ed5,yinhang_ed6;
	String bank_name;
	String bank_code;
	String user_name;
	String unit_name;
	
	String financial_contacts;
	String contacts_tel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yinhang);	
		 yinhang_ed1=(EditText) findViewById(R.id.yinhang_ed1);
		 yinhang_ed2=(EditText) findViewById(R.id.yinhang_ed2);
		 yinhang_ed3=(EditText) findViewById(R.id.yinhang_ed3);
		 yinhang_ed4=(EditText) findViewById(R.id.yinhang_ed4);
		 yinhang_ed5=(EditText) findViewById(R.id.yinhang_ed5);
		 yinhang_ed6=(EditText) findViewById(R.id.yinhang_ed6);
			
		RadioButton yinhang_baocun=(RadioButton) findViewById(R.id.yinhang_baocun);
		
		
		
		AjaxParams params = new AjaxParams();
		
		params.put("token",Data.info.getData().getToken());
		

		FinalHttp fh = new FinalHttp();
		fh.post(Tools.getBaseUrl() + "?app=my_pay&act=api_withdraw_list",
				params, new AjaxCallBack<String>() {

					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						Toast.makeText(Yinghang.this, strMsg,
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(String t) {
						 System.out.println("---20-------"+t+"----280-----80------------");
               try {
				JSONObject jsonObject=new JSONObject(t);
				
			JSONObject jsonObject2=	jsonObject.getJSONObject("data");
		
			JSONObject array=jsonObject2.getJSONObject("bank");
				
				 bank_name=array.getString("bank_name");
				 bank_code=array.getString("bank_code");
				user_name=array.getString("user_name");
				 unit_name=array.getString("unit_name");
				 financial_contacts=array.getString("financial_contacts");
				 contacts_tel=array.getString("contacts_tel");
				 
			System.out.println("dsd--"+bank_name+bank_code+user_name);
			yinhang_ed1.setText(bank_name);
			yinhang_ed2.setText(bank_code);
			yinhang_ed3.setText(user_name);
			yinhang_ed4.setText(unit_name);
			yinhang_ed5.setText(financial_contacts);
			yinhang_ed6.setText(contacts_tel);
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
               
					}

				});
		
		
		
		
		
		
		
		
		
		
		yinhang_baocun.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String de1=yinhang_ed1.getText().toString();
				String de2=yinhang_ed2.getText().toString();
				String de3=yinhang_ed3.getText().toString();
				String de4=yinhang_ed4.getText().toString();
				String de5=yinhang_ed5.getText().toString();
				String de6=yinhang_ed6.getText().toString();
				
				
				
				
				
				
				AjaxParams params = new AjaxParams();
				
				params.put("token", Data.info.getData().getToken());
				params.put("bank_name", de1);
				params.put("bank_code", de2);
				params.put("user_name", de3);
				params.put("unit_name", de4);
				params.put("financial_contacts", de5);
				params.put("contacts_tel", de6);
				
				

				FinalHttp fh = new FinalHttp();
				fh.post(Tools.getBaseUrl() + "?app=my_pay&act=api_bank",
						params, new AjaxCallBack<String>() {

							public void onFailure(Throwable t, int errorNo,
									String strMsg) {
								Toast.makeText(Yinghang.this, strMsg,
										Toast.LENGTH_SHORT).show();
							}

							@Override
							public void onSuccess(String t) {
								 System.out.println("---80-------"+t+"-----80-----80------------");

								 
						try {
							JSONObject jsonObject=new JSONObject(t);
							
						String ErrNum=	jsonObject.getString("ErrNum");
						String ErrMsg=	jsonObject.getString("ErrMsg");	
						if(ErrNum.equals("0")){				
						Toast.makeText(Yinghang.this, ErrMsg, 0).show();	
						}	
						
						
						if(ErrNum.equals("1")){				
							Toast.makeText(Yinghang.this, ErrMsg, 0).show();	
							}	
						
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	 
								 
								

							}

						});
				
			
				
			}
		});
		 
		 
		
		
	}

}
