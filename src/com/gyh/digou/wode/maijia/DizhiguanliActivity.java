package com.gyh.digou.wode.maijia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;


import com.gyh.digou.Data;
import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.util.Tools;
import com.gyh.digou.wode.LoginFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DizhiguanliActivity extends Activity {
	Button tianjiadizhi_button;
	ListView listView;
	DizhiAdapter adapter;
	ArrayList<JSONObject> warrlists;
	
	
	
	public void getData()
	{
		
		
		AjaxParams params = new AjaxParams();
		
		params.put("token", Data.info.getData().getToken());

		FinalHttp fh = new FinalHttp();
		fh.post(Tools.getBaseUrl() + "?app=my_address&act=api_address", params,
				new AjaxCallBack<String>() {

					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						Toast.makeText(DizhiguanliActivity.this, strMsg,
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(String t) {
                         
						try {

							JSONObject jsonObject = new JSONObject(t);
							String ErrNum = jsonObject.getString("ErrNum");
							String ErrMsg = jsonObject.getString("ErrMsg");
							// String data= jsonObject.getString("data");

							JSONObject listobject = jsonObject
									.getJSONObject("data");
							warrlists = new ArrayList<JSONObject>();

							Iterator<String> it = listobject.keys();
							while (it.hasNext()) {
								String key = it.next();
								JSONObject object = listobject
										.getJSONObject(key);
								warrlists.add(object);
							}

							adapter.setData(warrlists);
						} catch (Exception e) {
							// TODO: handle exception
						}

					}

				});
		
		
		
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dizhiguanli);

		tianjiadizhi_button = (Button) findViewById(R.id.tianjiadizhi_button);
		tianjiadizhi_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(DizhiguanliActivity.this, DizhiBianjiActivity.class);
				startActivity(intent);
				//finish();
			}
		});
		 listView = (ListView) findViewById(R.id.dizhiguanli_listview);
		
		
		
		 adapter = new DizhiAdapter(
					DizhiguanliActivity.this);

			listView.setAdapter(adapter);
		 
		 
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, final View arg1,
					 final int arg2, long arg3) {
				
				   AlertDialog.Builder builder = new Builder(DizhiguanliActivity.this);  
			        builder.setMessage("是否删除?");  
			        builder.setTitle("提示");  
			        builder.setPositiveButton("确认",  
			        new android.content.DialogInterface.OnClickListener() {  
			            @Override  
			            public void onClick(DialogInterface dialog, int which) {  
			        	
			         try {
						String num=warrlists.get(arg2).getString("addr_id");
						Toast.makeText(DizhiguanliActivity.this, num, 0).show();
						
						AjaxParams params = new AjaxParams();
						
						params.put("token", Data.info.getData().getToken());
						params.put("addr_id", num);

						FinalHttp fh = new FinalHttp();
						fh.post(Tools.getBaseUrl() + "?app=my_address&act=api_drop", params,
								new AjaxCallBack<String>() {

									public void onFailure(Throwable t, int errorNo,
											String strMsg) {
										Toast.makeText(DizhiguanliActivity.this, strMsg,
												Toast.LENGTH_SHORT).show();
									}

									@Override
									public void onSuccess(String t) {
										
										
										adapter.notifyDataSetChanged();
										
										
									}

								});
						
						
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			            	
			          
			          
	            }
			           
						
			        });  
			        builder.setNegativeButton("取消",  
			        new android.content.DialogInterface.OnClickListener() {  
			            @Override  
			            public void onClick(DialogInterface dialog, int which) {  
			               
			            }  
			        });  
			        builder.create().show();  
				
				
				
				
				return false;
			}
		});
		
		
		getData();
		
		
		
		

	}

}
