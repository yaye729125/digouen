package com.gyh.digou.itemdetail.shop;

import java.util.ArrayList;
import java.util.Iterator;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gyh.digou.Data;
import com.gyh.digou.R;
import com.gyh.digou.fenlei.CateCommercialAdapter;
import com.gyh.digou.fenlei.NestRadioGroup;
import com.gyh.digou.fenlei.NestRadioGroup.OnCheckedChangeListener;
import com.gyh.digou.itemdetail.ItemDetailActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class ShopActivity extends Activity implements OnCheckedChangeListener{

	PullToRefreshListView pullToRefreshListView;
	ListView list;
	CateCommercialAdapter adapter;
	
	
	boolean is_refresh=false;
	
	
	
	//传递参数 
	String store_id;
	
	
	private ArrayList<JSONObject> commerList=new ArrayList<JSONObject>();
	NestRadioGroup fenLeiFragment_RadioGroup;
	ProgressDialog pd;
	AjaxParams params_cur;
	
	String url_xp=Data.getStorexpApiUrl();
	String url_jg=Data.getStorejgApiUrl();
	String url_rx=Data.getStorerxApiUrl();
	String url_tj=Data.getStoretjApiUrl();
	
	String url_cur;
	
	TextView store_name;
	ImageView store_logo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.shop);
		
		Intent intent=getIntent();
		store_id=intent.getStringExtra("store_id");
		
		store_name=(TextView) findViewById(R.id.shop_name);
		store_logo=(ImageView) findViewById(R.id.shop_logo);
		
		
		
		pullToRefreshListView=(PullToRefreshListView) findViewById(R.id.shop_list);
		list=pullToRefreshListView.getRefreshableView();
		
		
		pullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				
				
				is_refresh=true;
				getNetWorkData(url_cur);
				
			}
		});
		
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				
				//ShopActivity.this.finish();
				
				Intent intent=new Intent(ShopActivity.this,ItemDetailActivity.class);
				try {
					intent.putExtra("goods_id",commerList.get(arg2-1).getString("goods_id"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				startActivity(intent);
				
			}
		});
		
		
		fenLeiFragment_RadioGroup = (NestRadioGroup) findViewById(R.id.shop_radiogroup);
		fenLeiFragment_RadioGroup.setOnCheckedChangeListener(this);
	
		adapter=new CateCommercialAdapter(this);
		list.setAdapter(adapter);
		
		url_cur=url_tj;
		params_cur=new AjaxParams("store_id",store_id,"page","1","pageSize","150");
		//params_cur=params_tj;
		initBaseData();
		getNetWorkData(url_tj);
		
		
	}
	public void initBaseData()
	{
		
		AjaxParams params=new AjaxParams("store_id",store_id);
		FinalHttp fh3 = new FinalHttp();
		fh3.post(Data.getStoreInfoUrl(), params,new AjaxCallBack<String>() {
			
			@Override
			public void onLoading(long count, long current) {
			
				super.onLoading(count, current);
			}

			@Override
			public void onSuccess(String t) {
				
				
				Log.d("shopactivity",t);
				
				
				try {
					JSONObject json_result=new JSONObject(t);
					
					JSONObject json_data=json_result.getJSONObject("data");
					
					
					store_name.setText(json_data.getString("store_name"));
					
					
					
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		
	}
	
	public void getNetWorkData(String url)
	{
		FinalHttp fh3 = new FinalHttp();
		fh3.post(url, params_cur,new AjaxCallBack<String>() {
			
			@Override
			public void onLoading(long count, long current) {
			
				super.onLoading(count, current);
			}

			@Override
			public void onSuccess(String t) {
				Log.d("shopactivity---",t);
				try {
					
					JSONObject json_result=new JSONObject(t);
					JSONObject json_cate= json_result.getJSONObject("data");
					JSONObject json_catelist=json_cate.getJSONObject("list");
					@SuppressWarnings("unchecked")
					Iterator<String> keys=json_catelist.keys();
					commerList.clear();
					while(keys.hasNext())
					{
						String key=keys.next();
						JSONObject json_commer=json_catelist.getJSONObject(key);
						commerList.add(json_commer);
					}
					
					
					if(pd!=null)
					{
						if(pd.isShowing())
							pd.cancel();
					}
					
					pullToRefreshListView.onRefreshComplete();
					if(!(commerList.size()==0))
					{
						adapter.setData(commerList);
						
						//handler.sendEmptyMessage(0x1);
					}else
					{
						//list.setEmptyView(search_empty_view);
					}
					
				}catch (Exception e) {
					//pd.cancel();
					//list.removeAllViewsInLayout();
					//list.setEmptyView(search_empty_view);
					e.printStackTrace();
				}
		
			}
		});
		
	}
	
	
	
	
	
	
	@Override
	protected void onDestroy() {
	
		super.onDestroy();
		
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}


	@Override
	public void onCheckedChanged(NestRadioGroup group, int checkedId) {
		switch(checkedId)
		{
		case R.id.shop_radiobtn_tj:
			
			url_cur=url_tj;
			break;
		case R.id.shop_radiobtn_xl:
			url_cur=url_rx;
			
			break;
		case R.id.shop_radiobtn_jg:
			url_cur=url_jg;
			break;
		case R.id.shop_radiobtn_xp:
			url_cur=url_xp;
			break;
		}
		getNetWorkData(url_cur);
	}


	
}
