package com.gyh.digou.gouwuche;

import java.util.ArrayList;
import java.util.Iterator;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.gyh.digou.Data;
import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.util.Tools;
import com.gyh.digou.wode.maijia.DizhiAdapter;

public class MakeOrderChooseAddressActivity extends Activity {

	
	ArrayList<JSONObject>  warrlists;
	ListView list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.makeorder_chooseaddress);
		
		LinearLayout layout_addAddress=(LinearLayout) findViewById(R.id.makeorder_chooseaddress_layout_addaddress);
		
		list=(ListView) findViewById(R.id.makeorder_chooseaddress_list);
		
		
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				Intent data=new Intent();
				data.putExtra("address",warrlists.get(arg2).toString());
				setResult(0x123, data);
				
				MakeOrderChooseAddressActivity.this.finish();
				
				
			}
		});
		adapter = new DizhiAdapter(
				MakeOrderChooseAddressActivity.this);

		list.setAdapter(adapter);
		initData();
	
		
		
	}
	
	
	
	DizhiAdapter adapter;
	
	
	public void initData()
	{
		
		
		AjaxParams params = new AjaxParams();
		
		params.put("token", Data.getInfo().getData().getToken());

		FinalHttp fh = new FinalHttp();
		fh.post(Tools.getBaseUrl() + "?app=my_address&act=api_address", params,
				new AjaxCallBack<String>() {

					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						Toast.makeText(MakeOrderChooseAddressActivity.this, strMsg,
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
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	
	
	
	
}
