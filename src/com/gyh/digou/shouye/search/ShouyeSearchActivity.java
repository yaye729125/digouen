package com.gyh.digou.shouye.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;

import com.gyh.digou.R;
import com.gyh.digou.fenlei.PullListView;
import com.gyh.digou.itemdetail.ItemDetailActivity;
import com.gyh.digou.util.Tools;

public class ShouyeSearchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		Intent intent=getIntent();
		String sku=intent.getStringExtra("sku");
		setContentView(R.layout.shouye_search);
		//ListView list=(ListView) findViewById(R.id.shouye_search_list);
		
		final EditText search=(EditText) findViewById(R.id.shouye_edit_search);
		//PullListView mBounceLv = (PullListView)findViewById(R.id.shouye_search_list);
		PullListView listView=(PullListView) findViewById(R.id.shouye_search_list);
		
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				try {
				Intent intent=new Intent(ShouyeSearchActivity.this,ItemDetailActivity.class);
				
					intent.putExtra("goods_id",list.get(arg2).getString("goods_id"));
				
				startActivity(intent);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		
		search.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				
				initAdapter(new AjaxParams("keyword",search.getText().toString()));
			}
		});
		adapter=new SearchAdapter(this);
		if(sku==null)
		{
			initAdapter(new AjaxParams("keyword",""));
		}else
		{
			Toast.makeText(this,"sku="+sku,Toast.LENGTH_SHORT).show();
			initAdapter(new AjaxParams("sku",sku));
		}
		listView.setAdapter(adapter);
		//search.setAdapter();
		/*search.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getData()));
	   
			System.out.println("shouyesearchactivity----befor ajax");
			Log.d("shouyesearchactivity----","befor ajax");*/
			
	}
	
	
	
	SearchAdapter adapter;
	
	List<JSONObject> list=new ArrayList<JSONObject>();
	
	
	/*public void initAdapter(boolean flag,String sku)
	{
		
	}*/
	public void initAdapter(AjaxParams params)
	{
		
		
		//AjaxParams params=new AjaxParams("keyword",keyword);
		FinalHttp fh=new FinalHttp();
		fh.post(Tools.getBaseUrl()+"?app=search&act=api_goods",params,new AjaxCallBack<String>()
		{

			@Override
			public void onLoading(long count, long current) {
				
				super.onLoading(count, current);
			}

			@Override
			public void onSuccess(String t) {
				System.out.println("shouyesearchactivity----"+t);
				Log.d("shouyesearchactivity----",t);
				
				try {
					JSONObject json_result=new JSONObject(t);
					JSONObject json_data=json_result.getJSONObject("data");
					JSONObject json_data_list=json_data.getJSONObject("list");
					Iterator<String> itr=json_data_list.keys();
					list.clear();
					while(itr.hasNext())
					{
						String key=itr.next();
						list.add(json_data_list.getJSONObject(key));
					}
					adapter.setData(list);
					
				} catch (JSONException e) {
					
					e.printStackTrace();
				}
				
				
				
				
				
				
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
			}
			
		});
		
		
		
	}
		
	
	    private List<String> getData(){
			
			List<String> data = new ArrayList<String>();
			data.add("测试数据1");
			data.add("测试数据2");
			data.add("测试数据3");
			data.add("测试数据1");
			data.add("测试数据2");
			data.add("测试数据3");
			data.add("测试数据1");
			data.add("测试数据2");
			data.add("测试数据3");
			data.add("测试数据1");
			data.add("测试数据2");
			data.add("测试数据3");
			
			return data;
		}

	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		
		
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	
		return super.onKeyDown(keyCode, event);
	}

	
	
	
}
