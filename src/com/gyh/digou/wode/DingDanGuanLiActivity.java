package com.gyh.digou.wode;


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

import com.gyh.digou.shouye.MyListView;
import com.gyh.digou.util.Tools;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class DingDanGuanLiActivity extends Activity implements OnCheckedChangeListener
{
	MyListView listView;
	PullToRefreshListView pullToRefreshListView;
	DingdanAdapter adapter;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dingdanguanli);
		
		listView=(MyListView)findViewById(R.id.dingdanguanli_list);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				try {
					Intent intent=new Intent(DingDanGuanLiActivity.this,DingdanXiangqing.class);
					
					
					intent.putExtra("order_id", adapter.getData().get(arg2).getString("order_id"));
					startActivity(intent);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		
		
		adapter=new DingdanAdapter(this);
		listView.setAdapter(adapter);
		getData("all");
		
		
	}
	public void on1(View v)
	{
		
		getData("all");
		
	}
	public void on2(View v)
	{
		
		getData("pending");
		
		
	}
	public void on3(View v)
	{
		
		getData("accepted");
		
	}
	public void on4(View v)
	{
		
		getData("finished");
		
	}
	public void on5(View v)
	{
		
		getData("canceled");
		
	}
	ArrayList<JSONObject> warrlist;
	public void getData(String mode)
	{
		
		

	     	AjaxParams params = new AjaxParams();
			
			params.put("token", Data.getInfo().getData().getToken());		
			params.put("type", mode);
			
			FinalHttp fh = new FinalHttp();
			fh.post(Tools.getBaseUrl() + "?app=buyer_order&act=api_orders",
					params, new AjaxCallBack<String>() {

						public void onFailure(Throwable t, int errorNo,
								String strMsg) {
							Toast.makeText(DingDanGuanLiActivity.this, strMsg,
									Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onSuccess(String t) {
							 System.out.println("---89-------"+t+"--");
							// pullToRefreshListView.onRefreshComplete();
							try {

								JSONObject jsonObject = new JSONObject(t);
								String ErrNum = jsonObject.getString("ErrNum");
								String ErrMsg = jsonObject.getString("ErrMsg");
								JSONObject datajson = jsonObject
										.getJSONObject("data");
								// String total= datajson.getString("total");

								JSONObject listJson = datajson
										.getJSONObject("orders");
								 warrlist = new ArrayList<JSONObject>();
								
								Iterator<String> it = listJson.keys();
								while (it.hasNext()) {
									String key = it.next();
									JSONObject object = listJson.getJSONObject(key);
									warrlist.add(object);
								}
								//System.out.println("-33-11"+warrlist1);
							
							adapter.setData(warrlist);
							//YiyueqianDingDaniAdapter adapter=new YiyueqianDingDaniAdapter(DingDanGuanLiActivity.this, warrlist);
							
						//	DingdanXiangqingAdapter adapter2 =new DingdanXiangqingAdapter(DingDanGuanLiActivity.this, warrlists);
							//listView.setAdapter(adapter);
							//	listView1.setAdapter(adapter2);

							} catch (Exception e) {
								e.printStackTrace();
								//pullToRefreshListView.onRefreshComplete();
								// TODO: handle exception

							}

						}

					});	

			
			
		
			
	     
	}
	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
		
		
	
	
	
}

	
	

