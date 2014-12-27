package com.gyh.digou;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.bean.LoginInfo;
import com.gyh.digou.util.Tools;
import com.gyh.digou.wode.ZhuCe;
import com.gyh.digou.wode.maijia.Mimagenggai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.login);
		
		
		
		Button login_btn=(Button)findViewById(R.id.login_btn);
		final EditText edit_username=(EditText)findViewById(R.id.login_username);
		final EditText edit_password=(EditText)findViewById(R.id.login_password);
		
		
		
		RadioButton button2=(RadioButton)findViewById(R.id.zhaohuimima);
		
		
		findViewById(R.id.base_title_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				LoginActivity.this.finish();
				
			}
		});
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent intent=new Intent(LoginActivity.this, Mimagenggai.class);
				startActivity(intent);
				
				
			}
		});
		
		
		
		RadioButton button=(RadioButton)findViewById(R.id.zhuce);
		
		button.setOnClickListener(new OnClickListener() {
			
		
			public void onClick(View arg0) {
				Intent intent=new Intent(LoginActivity.this, ZhuCe.class);
				startActivity(intent);
				
			}
		});
		
	
		login_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String password=edit_password.getText().toString();
				String user_name=edit_username.getText().toString();
				
				AjaxParams params=new AjaxParams();
				params.put("user_name",user_name);
				params.put("password",password);
				FinalHttp fh=new FinalHttp();
				fh.post(Tools.getBaseUrl()+"?app=member&act=api_login",params,new AjaxCallBack<String>(){

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						Toast.makeText(LoginActivity.this,strMsg,Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(String t) {
						System.out.println(t);
						try
						{
							Gson gson=new Gson();
						
							LoginInfo info=gson.fromJson(t, LoginInfo.class);
							Data.info=info;
							MyApp app=((MyApp)LoginActivity.this.getApplication());
							
							app.saveInfo(info);
							
							//Data.setInfo(info);
							Toast.makeText(LoginActivity.this,info.getErrMsg(),Toast.LENGTH_SHORT).show();
							LoginActivity.this.finish();
						}catch(JsonSyntaxException e)
						{
							
							e.printStackTrace();
							
						}
						
					}
					
				});
			}
		});
	}

	@Override
	protected void onDestroy() {
	
		super.onDestroy();
	}

	@Override
	protected void onPause() {
	
		super.onPause();
		
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		
		
	}

	@Override
	protected void onStop() {
		super.onStop();
		
		
	}

	
	
	
	
}
