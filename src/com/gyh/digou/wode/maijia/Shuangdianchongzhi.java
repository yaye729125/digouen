package com.gyh.digou.wode.maijia;

import java.util.ArrayList;
import java.util.Iterator;

import javax.security.auth.callback.Callback;

import org.json.JSONObject;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.gyh.digou.Data;
import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.util.NetworkUtil;
import com.gyh.digou.util.Tools;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Shuangdianchongzhi extends Activity {
	TextView textView;
	EditText editText;
	Button button;
	
	String s;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shuangdianchongzhi);

		button = (Button) findViewById(R.id.chongzhi_button);
		textView = (TextView) findViewById(R.id.dianshu);
		editText = (EditText) findViewById(R.id.editText1);
		 
		new Thread(new Runnable() {

			@Override
			public void run() {

				editText.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence arg0, int arg1,
							int arg2, int arg3) {
					String	chongzhi = editText.getText().toString();
						

						if (chongzhi.length() != 0) {

							int i = Integer.parseInt(chongzhi);

							int shuan = i * 100;

							s = String.valueOf(shuan);
							textView.setText(s);

						} else {

							textView.setText("0");

						}

					}

					@Override
					public void beforeTextChanged(CharSequence arg0, int arg1,
							int arg2, int arg3) {
						// TODO Auto-generated method stub

					}

					@Override
					public void afterTextChanged(Editable arg0) {
						// textView.setText(chongzhi);

					}
				});

			}
		}).start();
		
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				String	chongzhi = editText.getText().toString();
				
				
					
				
				AjaxParams params = new AjaxParams();
				
				params.put("token",Data.info.getData().getToken());
		
			
				 params.put("num",chongzhi);
				
				
				
				FinalHttp fh = new FinalHttp();
				fh.post(Tools.getBaseUrl() + "?app=my_point&act=api_deposit",
						params, new AjaxCallBack<String>() {

							public void onFailure(Throwable t, int errorNo,
									String strMsg) {
								Toast.makeText(Shuangdianchongzhi.this, strMsg,
										Toast.LENGTH_SHORT).show();
							}

							@Override
							public void onSuccess(String t) {
								 System.out.println("---81-------"+t+"-----81-----81------------");
								 
								 try {
									 JSONObject jsonObject = new JSONObject(t);
										String ErrNum = jsonObject.getString("ErrNum");

										if(ErrNum.equals("0")){
											Toast.makeText(Shuangdianchongzhi.this, "成功", 0).show();
										}else{
											Toast.makeText(Shuangdianchongzhi.this, "失败", 0).show();
										}
										
									 
									 
								} catch (Exception e) {
									// TODO: handle exception
								}
								
								
							}

						});	}
				
				
				
				
				
				
		
		});
		
		
		
		
		
			}
		
	}

