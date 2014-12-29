package com.gyh.digou.wode;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gyh.digou.Data;
import com.gyh.digou.MainActivity;
import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.bean.LoginInfo;
import com.gyh.digou.util.Tools;
import com.gyh.digou.wode.maijia.Mimagenggai;

public class LoginFragment extends Fragment {

	
	Context mContext;
	MainActivity activity;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		mContext=activity=(MainActivity) getActivity();
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view=inflater.inflate(R.layout.login,container,false);
		
		Button login_btn=(Button) view.findViewById(R.id.login_btn);
		final EditText edit_username=(EditText) view.findViewById(R.id.login_username);
		final EditText edit_password=(EditText) view.findViewById(R.id.login_password);
		
		
		
		RadioButton button2=(RadioButton) view.findViewById(R.id.zhaohuimima);
		
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent intent=new Intent(getActivity(), Mimagenggai.class);
				startActivity(intent);
				
				
				
				
				
				
			}
		});
		
		
		
		RadioButton button=(RadioButton) view.findViewById(R.id.zhuce);
		
		button.setOnClickListener(new OnClickListener() {
			
		
			public void onClick(View arg0) {
				Intent intent=new Intent(getActivity(), ZhuCe.class);
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
						Toast.makeText(getActivity(),strMsg,Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(String t) {
						
						
						System.out.println(t);
						Gson gson=new Gson();
					
						
						
						//Data.getAddCartUrl();
						LoginInfo info=gson.fromJson(t, LoginInfo.class);
						
						
						if(info.ErrNum.equals("0"))
						{
							/*app.setInfo(info);
							Data.setInfo(info);*/
							Data.info=info;
							MyApp app=((MyApp)getActivity().getApplication());
							app.saveInfo(info);
							//Data.saveInfo(mContext, info);
							//Data.setToken(info.getData().getToken());
							Toast.makeText(getActivity(),info.getErrMsg()+"  userType="+Data.getUserType()+"  store_id="+info.getData().getStore_id(),Toast.LENGTH_SHORT).show();
						}else
						{
							Toast.makeText(getActivity(),info.getErrMsg(),Toast.LENGTH_SHORT).show();
						}
					}
					
				});
			}
		});
		return view;
	}

	
	
	
	
}
