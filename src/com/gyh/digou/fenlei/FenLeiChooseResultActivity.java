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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.gyh.digou.R;
import com.gyh.digou.fenlei.NestRadioGroup.OnCheckedChangeListener;
import com.gyh.digou.itemdetail.ItemDetailActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class FenLeiChooseResultActivity extends FragmentActivity implements OnCheckedChangeListener{
	//存在的问题。。
	//
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
	
	//判断跳转过来的activity如果是category isformfenlei为false 否则为true
	public boolean isformfenlei=false;
	
	
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
	
	
	private ArrayList<JSONObject> commerList=new ArrayList<JSONObject>();
	ImageView imv_jg_sort;
	View search_empty_view;
	boolean is_refresh=false;
	public final int xg=0x11;
	public final int xl=0x12;
	public final int jg_up=0x13;
	public final int jg_down=0x14;
	public final int xp=0x15;
	public int cur_cate=-1;
	
	AjaxParams cur_params;
	String keyword;
	//ViewPager pager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fenlei_choose_result);
		
		
		Intent intent = getIntent();
		Bundle bundle=intent.getExtras();
		isformfenlei=bundle.getBoolean("issearch");
		
			pullToRefreshListView=(PullToRefreshListView) findViewById(R.id.fenlei_fragment_choose_result_list);
			list=pullToRefreshListView.getRefreshableView();
			pullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
	
				@Override
				public void onRefresh(PullToRefreshBase<ListView> refreshView) {
					
					
					is_refresh=true;
					getNetWorkData(cur_params);
					
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
					e.printStackTrace();
				}
				startActivity(intent);
				
			}
		});
		fenLeiFragment_RadioGroup = (NestRadioGroup) findViewById(R.id.fenlei_fragment_choose_result_radiogroup);
		fenLeiFragment_RadioGroup.setOnCheckedChangeListener(this);
		
		imv_jg_sort=(ImageView) findViewById(R.id.fenlei_fragment_choose_result_imv_jg_sort);
		
		
		search_empty_view=getLayoutInflater().inflate(R.layout.search_empty_view,null);
		//imv_jg_sort.setImageResource(R.drawable.sort_button_price_up);
		
		
		final EditText search=(EditText) findViewById(R.id.shouye_edit_search);
		
		
		//search.setont
		
		
		search.setOnEditorActionListener(new OnEditorActionListener() {
			
			
			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
				
				if(arg1==EditorInfo.IME_ACTION_SEARCH)
				{
					keyword=search.getText().toString();
					
					Log.d("fenleichoose","afterTextChanged");
					if(keyword!=null&&!keyword.equals(""))
					{
						
						AjaxParams params=new AjaxParams("keyword",keyword);
						cur_params=params;
						isformfenlei=true;
						getNetWorkData(cur_params);
					}
					
					
					// oppo phone not run well
					
					
					/*InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);   

					imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);*/
					list.setSelection(0);
					return true;
				}
				
				return false;
			}
		});
		
		
		
		
		RadioButton radiobtn_jg=(RadioButton) findViewById(R.id.fenlei_fragment_choose_result_radiobtn_jiage);
		radiobtn_jg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if((sor_jg_status++)%2==0)
				{
					
					if(isformfenlei)
					{
						params_jg_up=new AjaxParams("keyword",keyword,"order","price_asc");
					}else
					{
						params_jg_up=new AjaxParams("cate_id",cate_id,"order","price_asc");
					}
					cur_params=params_jg_up;
					getNetWorkData(cur_params);
					imv_jg_sort.setImageResource(R.drawable.sort_button_price_up);
					Toast.makeText(FenLeiChooseResultActivity.this,"up",Toast.LENGTH_SHORT).show();
				}else
				{
					if(isformfenlei)
					{
						params_jg_down=new AjaxParams("keyword",keyword,"order","price_desc");
					}else
					{
						params_jg_down=new AjaxParams("cate_id",cate_id,"order","price_desc");
					}
					cur_params=params_jg_down;
					Toast.makeText(FenLeiChooseResultActivity.this,"down",Toast.LENGTH_SHORT).show();
					imv_jg_sort.setImageResource(R.drawable.sort_button_price_down);
					getNetWorkData(cur_params);
				}
				
				
			}
		});
		
		//params_xg=new AjaxParams("")
		//initParams();
		
		if(!isformfenlei)
		{
			pd=new ProgressDialog(this,R.style.basedialog);
			pd.setCancelable(false);
			pd.show();
			cate_id=bundle.getString("fenlei_search_cate_id");
			params_xg=new AjaxParams("cate_id",cate_id);
			cur_params=params_xg;
			getNetWorkData(cur_params);
		}/*else
		{
			params_xg=new AjaxParams("keyword","");
			getNetWorkData(params_xg);
		}*/
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
		
		
		/*params_xg=new AjaxParams();
		
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
		params_xp.put("order", "add_time_desc");*/
		
	}
	public void getNetWorkData(AjaxParams params)
	{
		
		if(pd==null)
		{
			pd=new ProgressDialog(FenLeiChooseResultActivity.this);
			if(!pd.isShowing())
				pd.show();
		}else
		{
			if(!pd.isShowing())
				pd.show();
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
					
					
					if(pd!=null)
					{
						if(pd.isShowing())
							pd.cancel();
					}
					
					if(!(commerList.size()==0))
					{
						adapter.setData(commerList);
						
						handler.sendEmptyMessage(0x1);
					}else
					{
						list.setEmptyView(search_empty_view);
					}
					
				}catch (Exception e) {
					pd.cancel();
					list.setEmptyView(search_empty_view);
					adapter.clear();
					//list.removeAllViewsInLayout();
					//list.setEmptyView(search_empty_view);
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
		
		switch (checkedId) {
		case R.id.fenlei_fragment_choose_result_radiobtn_xiangguan:
			sor_jg_status=-1;
			//cur_cate=xg;
			
			if(isformfenlei)
			{
				params_xg=new AjaxParams("keyword",keyword);
			}else
			{
				params_xg=new AjaxParams("cate_id",cate_id);
			}
			cur_params=params_xg;
			//params_cur=params_xg;
			imv_jg_sort.setVisibility(View.GONE);
			
			
			break;
			
		case R.id.fenlei_fragment_choose_result_radiobtn_xiaoliang:
			sor_jg_status=-1;
			if(isformfenlei)
			{
				params_xl=new AjaxParams("keyword",keyword,"order","sales_desc");
			}else
			{
				params_xl=new AjaxParams("cate_id",cate_id,"order","sales_desc");
			}
			cur_params=params_xl;
			imv_jg_sort.setVisibility(View.GONE);
			
			
			break;
			
		case R.id.fenlei_fragment_choose_result_radiobtn_jiage:
			imv_jg_sort.setVisibility(View.VISIBLE);
			
			//getNetWorkData(params_jg_up);
			break;
		
		case R.id.fenlei_fragment_choose_result_radiobtn_xinpin:
			sor_jg_status=-1;
			imv_jg_sort.setVisibility(View.GONE);
			if(isformfenlei)
			{
				params_xp=new AjaxParams("keyword",keyword,"order","add_time_desc");
			}else
			{
				params_xp=new AjaxParams("cate_id",cate_id,"order","add_time_desc");
			}
			cur_params=params_xp;
			break;
		}
		getNetWorkData(cur_params);
		
		
		
	}

    
    
    
    
}
