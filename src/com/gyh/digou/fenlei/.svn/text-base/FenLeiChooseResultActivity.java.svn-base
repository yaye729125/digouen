package com.gyh.digou.fenlei;

import java.util.ArrayList;
import java.util.Iterator;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.gyh.digou.R;
import com.gyh.digou.fenlei.NestRadioGroup.OnCheckedChangeListener;
import com.gyh.digou.itemdetail.ItemDetailActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class FenLeiChooseResultActivity extends FragmentActivity implements OnCheckedChangeListener{
	
	
	NestRadioGroup fenLeiFragment_RadioGroup;
	//private Fragment fragment;
	FragmentManager fragmentManager;
	FragmentTransaction transaction ;
	ListView list;
	PullToRefreshListView pullToRefreshListView;
	ProgressDialog pd;
	public static  final int xg_fin=0x1;
	public static  final int xl_fin=0x2;
	public static  final int jg_fin=0x3;
	public static  final int xp_fin=0x4;
	
	
	Handler handler=new Handler()
	{

		@Override
		public void dispatchMessage(Message msg) {
			
			super.dispatchMessage(msg);
		}

		@Override
		public void handleMessage(Message msg) {
			if(is_refresh)
			{
				pullToRefreshListView.onRefreshComplete();
				is_refresh=false;
			}
			
		}

		@Override
		public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
			
			return super.sendMessageAtTime(msg, uptimeMillis);
		}
		
	};
	private int sor_jg_status=-1;//-1表示未选中 0表示up 1表示down
	//boolean sort_jg_up=true;
	private ArrayList<JSONObject> commerList=new ArrayList<JSONObject>();
	ImageView imv_jg_sort;
	
	boolean is_refresh=false;
	public final int xg=0x11;
	public final int xl=0x12;
	public final int jg_up=0x13;
	public final int jg_down=0x14;
	public final int xp=0x15;
	public int cur_cate=-1;
	//ViewPager pager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fenlei_choose_result);
		
		
		Intent intent = getIntent();
		Bundle bundle=intent.getExtras();
		cate_id=bundle.getString("fenlei_search_cate_id");
		pullToRefreshListView=(PullToRefreshListView) findViewById(R.id.fenlei_fragment_choose_result_list);
		list=pullToRefreshListView.getRefreshableView();
		pullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				is_refresh=true;
				getNetWorkData(cur_cate);
				
			}
		});
		
		adapter=new CateCommercialAdapter(this);
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent=new Intent(FenLeiChooseResultActivity.this,ItemDetailActivity.class);
				try {
					intent.putExtra("goods_id",commerList.get(arg2-1).getString("goods_id"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				startActivity(intent);
				
			}
		});
		fenLeiFragment_RadioGroup = (NestRadioGroup) findViewById(R.id.fenlei_fragment_choose_result_radiogroup);
		fenLeiFragment_RadioGroup.setOnCheckedChangeListener(this);
		
		imv_jg_sort=(ImageView) findViewById(R.id.fenlei_fragment_choose_result_imv_jg_sort);
		
		//imv_jg_sort.setImageResource(R.drawable.sort_button_price_up);
		
		RadioButton radiobtn_jg=(RadioButton) findViewById(R.id.fenlei_fragment_choose_result_radiobtn_jiage);
		//radiobtn_jg.co
		//radiobtn_jg.setco
		
		radiobtn_jg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if((sor_jg_status++)%2==0)
				{
					getNetWorkData(jg_up);
					//params_cur=params_jg_up;
					cur_cate=jg_up;
					imv_jg_sort.setImageResource(R.drawable.sort_button_price_up);
					Toast.makeText(FenLeiChooseResultActivity.this,"up",Toast.LENGTH_SHORT).show();
				}else
				{
					Toast.makeText(FenLeiChooseResultActivity.this,"down",Toast.LENGTH_SHORT).show();
					imv_jg_sort.setImageResource(R.drawable.sort_button_price_down);
					cur_cate=jg_down;
				}
				getNetWorkData(cur_cate);
				
			}
		});
		pd=new ProgressDialog(this,R.style.basedialog);
		pd.setCancelable(false);
		pd.show();
		
		getNetWorkData(xg);
		//fenLeiFragment_RadioGroup.
	}
	
	
	CateCommercialAdapter adapter;
	
	String cate_id=null;
	AjaxParams params_xg;
	AjaxParams params_xl;
	AjaxParams params_jg_up;
	AjaxParams params_jg_down;
	AjaxParams params_xp;
	//AjaxParams  params_cur;
	public void initParams()
	{
		params_xg=new AjaxParams();
		
		params_xg.put("cate_id", cate_id);
		//params_cur=params_xg;
		params_xl=new AjaxParams();
		params_xl.put("cate_id", cate_id);
		params_xl.put("order", "sales_desc");
		params_jg_up=new AjaxParams();
		params_jg_up.put("cate_id", cate_id);
		params_jg_up.put("order", "price_asc");
		params_jg_down=new AjaxParams();
		params_jg_down.put("cate_id", cate_id);
		params_jg_down.put("order", "price_desc");
		
		params_xp=new AjaxParams();
		params_xp.put("cate_id", cate_id);
		params_xp.put("order", "add_time_desc");
		
	}
	public void getNetWorkData(int cate)
	{
		AjaxParams params=params_xg;
		
		switch(cate)
		{
		case xg:
			params=params_xg;
			break;
		case xl:
			params=params_xl;
			break;
		case jg_up:
			params=params_jg_up;
			break;
		case jg_down:
			params=params_jg_down;
			break;
		case xp:
			params=params_xp;
			break;
			
		}
		
		FinalHttp fh3 = new FinalHttp();
		fh3.post("http://www.cddego.com/api.php?app=search&act=api_goods", params,new AjaxCallBack<String>() {
			
			@Override
			public void onLoading(long count, long current) {
			
				super.onLoading(count, current);
			}

			@Override
			public void onSuccess(String t) {
				System.out.println(t);
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
					
					if(pd.isShowing())
						pd.cancel();
					adapter.setData(commerList);
					
					handler.sendEmptyMessage(0x1);
					
				}catch (Exception e) {
					e.printStackTrace();
				}
		
			}
		});
		
	}
	@Override  
    protected void onResume() {  
        super.onResume();  
    }  
      
    @Override  
    protected void onPause() {  
        super.onPause();  
    }




	@Override
	public void onCheckedChanged(NestRadioGroup group, int checkedId) {
		
		Log.d("oncheckedchanged","ess");
		if(!pd.isShowing())
			pd.show();
		switch (checkedId) {
		case R.id.fenlei_fragment_choose_result_radiobtn_xiangguan:
			sor_jg_status=-1;
			cur_cate=xg;
			//params_cur=params_xg;
			imv_jg_sort.setVisibility(View.GONE);
			
			
			break;
			
		case R.id.fenlei_fragment_choose_result_radiobtn_xiaoliang:
			sor_jg_status=-1;
			cur_cate=xl;
			imv_jg_sort.setVisibility(View.GONE);
			
			
			break;
			
		case R.id.fenlei_fragment_choose_result_radiobtn_jiage:
			imv_jg_sort.setVisibility(View.VISIBLE);
			
			//getNetWorkData(params_jg_up);
			
			break;
		
		case R.id.fenlei_fragment_choose_result_radiobtn_xinpin:
			sor_jg_status=-1;
			imv_jg_sort.setVisibility(View.GONE);
			cur_cate=xp;
			
			
			break;
		}
		getNetWorkData(cur_cate);
		
		
		
	}

    
    
    
    
}
