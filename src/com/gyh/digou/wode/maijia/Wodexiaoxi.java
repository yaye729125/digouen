package com.gyh.digou.wode.maijia;

import java.util.ArrayList;

import java.util.Iterator;


import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONObject;


import com.gyh.digou.Data;
import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;

import com.gyh.digou.util.Tools;

import android.app.Activity;

import android.os.Bundle;

import android.view.View;

import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class Wodexiaoxi extends Activity implements OnCheckedChangeListener{
	//ListView listview1, listview2, listview3;
	
	
	ListView listView;
	// Button button1,button2,button3;
	//private ArrayList<JSONObject> commerList = new ArrayList<JSONObject>();

	
	AjaxParams params = new AjaxParams();
	
	
	public void getData(String mode)
	{
		
		
		
		
		
		
		MyApp app = (MyApp) Wodexiaoxi.this.getApplication();
		params.put("token",Data.getInfo().getData().getToken());
		params.put("pattern",mode);

		FinalHttp fh = new FinalHttp();
		fh.post(Tools.getBaseUrl() + "?app=message&act=api_list_message",
				params, new AjaxCallBack<String>() {

					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						Toast.makeText(Wodexiaoxi.this, strMsg,
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(String t) {
						 System.out.println("---80-------"+t+"-----80-----80------------");

						try {

							JSONObject jsonObject = new JSONObject(t);
							String ErrNum = jsonObject.getString("ErrNum");
							String ErrMsg = jsonObject.getString("ErrMsg");
							JSONObject datajson = jsonObject
									.getJSONObject("data");
							// String total= datajson.getString("total");

							JSONObject listJson = datajson
									.getJSONObject("list");
							ArrayList<JSONObject> warrlist = new ArrayList<JSONObject>();
							// warrlist.add((JSONObject) object);
							Iterator<String> it = listJson.keys();
							while (it.hasNext()) {
								String key = it.next();
								JSONObject object = listJson.getJSONObject(key);
								warrlist.add(object);
							}

						

							adapter.setData(warrlist);

						} catch (Exception e) {
							e.printStackTrace();
							adapter.clear();

						}

					}

				});
		
	}
	MessageAdapter adapter ;
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wodexiaoxi_en);

		listView=(ListView) findViewById(R.id.wodexiaoxi_list);

		
		
		adapter = new MessageAdapter(
				Wodexiaoxi.this);
		
		listView.setAdapter(adapter);
		RadioGroup radioGroup=(RadioGroup) findViewById(R.id.wodexiaoxi_radiogroup);
		radioGroup.setOnCheckedChangeListener(this);
		getData("newpm");
		
		/*params.put("pattern", "privatepm");
		//FinalHttp fh = new FinalHttp();
		fh.post(Tools.getBaseUrl() + "?app=message&act=api_list_message",
				params, new AjaxCallBack<String>() {

					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						Toast.makeText(Wodexiaoxi.this, strMsg,
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(String t) {
						 System.out.println("---8-------"+t+"-----8-----8------------");

						try {

							JSONObject jsonObject = new JSONObject(t);
							String ErrNum = jsonObject.getString("ErrNum");
							String ErrMsg = jsonObject.getString("ErrMsg");
							JSONObject datajson = jsonObject
									.getJSONObject("data");
							// String total= datajson.getString("total");

							JSONObject listJson = datajson
									.getJSONObject("list");
							ArrayList<JSONObject> warrlist = new ArrayList<JSONObject>();
							// warrlist.add((JSONObject) object);
							Iterator<String> it = listJson.keys();
							while (it.hasNext()) {
								String key = it.next();
								JSONObject object = listJson.getJSONObject(key);
								warrlist.add(object);
							}

							MessageAdapter adapter = new MessageAdapter(
									Wodexiaoxi.this, warrlist);

							listview2.setAdapter(adapter);

						} catch (Exception e) {
							// TODO: handle exception

						}

					}

				});
		
		
		
		
		
		
		params.put("pattern", "systempm");
		//FinalHttp fh = new FinalHttp();
		fh.post(Tools.getBaseUrl() + "?app=message&act=api_list_message",
				params, new AjaxCallBack<String>() {

					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						Toast.makeText(Wodexiaoxi.this, strMsg,
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(String t) {
						 System.out.println("---0-------"+t+"-----0-----0------------");

						try {

							JSONObject jsonObject = new JSONObject(t);
							String ErrNum = jsonObject.getString("ErrNum");
							String ErrMsg = jsonObject.getString("ErrMsg");
							JSONObject datajson = jsonObject
									.getJSONObject("data");
							// String total= datajson.getString("total");

							JSONObject listJson = datajson
									.getJSONObject("list");
							ArrayList<JSONObject> warrlist = new ArrayList<JSONObject>();
							// warrlist.add((JSONObject) object);
							Iterator<String> it = listJson.keys();
							while (it.hasNext()) {
								String key = it.next();
								JSONObject object = listJson.getJSONObject(key);
								warrlist.add(object);
							}

							MessageAdapter adapter = new MessageAdapter(
									Wodexiaoxi.this, warrlist);

							listview3.setAdapter(adapter);

						} catch (Exception e) {
							// TODO: handle exception

						}

					}

				});
		*/
		
		
		
		

	}
	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		
		switch(arg1)
		{
			case R.id.wodexiaoxi_radiobtn_newpm:
				
				getData("newpm");
				
			break;
			case R.id.wodexiaoxi_radiobtn_privatepm:
				getData("privatepm");
				break;
			case R.id.wodexiaoxi_radiobtn_syspm:
			getData("systempm");
			break;
		
	
		}
		
		
	}

	/*public void on1(View v) {
		listview1.setVisibility(v.VISIBLE);
		listview2.setVisibility(v.INVISIBLE);
		listview3.setVisibility(v.INVISIBLE);

	}*/

	/*public void on2(View v) {
		listview1.setVisibility(v.INVISIBLE);
		listview2.setVisibility(v.VISIBLE);
		listview3.setVisibility(v.INVISIBLE);

	}

	public void on3(View v) {
		listview1.setVisibility(v.INVISIBLE);
		listview2.setVisibility(v.INVISIBLE);
		listview3.setVisibility(v.VISIBLE);

	}*/

}
