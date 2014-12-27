package com.gyh.digou.wode.shangjia;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.google.gson.Gson;
import com.gyh.digou.Data;
import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.bean.Goods;
import com.gyh.digou.bean.IImages;
import com.gyh.digou.bean.ItemDetailInfo;
import com.gyh.digou.bean.LoginInfo;
import com.gyh.digou.fenlei.CateCommercialAdapter;
import com.gyh.digou.fenlei.NestRadioGroup;
import com.gyh.digou.fenlei.NestRadioGroup.OnCheckedChangeListener;
import com.gyh.digou.util.NetworkUtil;
import com.gyh.digou.util.Tools;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

public class Shangpinguanli extends Activity implements OnCheckedChangeListener{
	
	
	ListView shangpingguanli_listView;
	RadioButton tianjia_shangping;
	ArrayList<JSONObject> warrlist;
	
	
	
	
	
	
	
	PullToRefreshListView pullToRefreshListView;
	//ListView list;
	CateCommercialAdapter adapter;
	
	
	boolean is_refresh=false;
	
	
	
	//传递参数 
	String store_id;
	
	
	private ArrayList<JSONObject> commerList=new ArrayList<JSONObject>();
	NestRadioGroup fenLeiFragment_RadioGroup;
	//ProgressDialog pd;
	AjaxParams params_cur;
	
	String url_xp=Data.getStorexpApiUrl();
	String url_jg=Data.getStorejgApiUrl();
	String url_rx=Data.getStorerxApiUrl();
	String url_tj=Data.getStoretjApiUrl();
	
	String url_cur;
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		url_cur=url_tj;
		params_cur=new AjaxParams("store_id",store_id,"page","1","pageSize","150");
		//params_cur=params_tj;
		//initBaseData();
		getNetWorkData(url_tj);
	}




	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent=getIntent();
		store_id=intent.getStringExtra("store_id");
		setContentView(R.layout.shangpinguanli);
		
		pullToRefreshListView=(PullToRefreshListView) findViewById(R.id.shangpingguanli_listView);
		shangpingguanli_listView=pullToRefreshListView.getRefreshableView();
		
		
		pullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				
				
				is_refresh=true;
				getNetWorkData(url_cur);
				
			}
		});
		
		
		
		
		fenLeiFragment_RadioGroup = (NestRadioGroup) findViewById(R.id.shangpingguanli_radiogroup);
		fenLeiFragment_RadioGroup.setOnCheckedChangeListener(this);
		login();
		adapter=new CateCommercialAdapter(this);
		
		shangpingguanli_listView.setAdapter(adapter);
		
		
		
		
		tianjia_shangping=(RadioButton) findViewById(R.id.tianjia_shangping);
		
		
		
	
		
		
		
		tianjia_shangping.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View arg0) {
				
				Intent intent=new Intent(Shangpinguanli.this,TianJiaShangPinActivity.class);
				startActivity(intent);
				
				
				
				
			}
		});
		
		shangpingguanli_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				try {
					Intent intent=new Intent(Shangpinguanli.this,ShangjiaEditCommerActivity.class);
				
					intent.putExtra("goods_id",commerList.get(arg2-1).getString("goods_id"));
					
					startActivity(intent);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//editCommer(arg2);
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
	
	
	
	




	public String getToken()
	{
		
		return MyApp.info.getData().getToken();
	}
	
	
	
	
	public void login()
	{
		
		AjaxParams params=new AjaxParams();
		params.put("user_name","zertul999");
		params.put("password","123456");
		FinalHttp fh=new FinalHttp();
		fh.post(Tools.getBaseUrl()+"?app=member&act=api_login",params,new AjaxCallBack<String>(){

			@Override
			public void onFailure(Throwable t, int errorNo,
					String strMsg) {
				Toast.makeText(getApplicationContext(),strMsg,Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onSuccess(String t) {
				System.out.println(t);
				Gson gson=new Gson();
			
				LoginInfo info=gson.fromJson(t, LoginInfo.class);
				MyApp.info=info;
				Toast.makeText(getApplicationContext(),info.getErrMsg(),Toast.LENGTH_SHORT).show();
				
			}
			
		});
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
