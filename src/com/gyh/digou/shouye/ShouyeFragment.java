package com.gyh.digou.shouye;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gyh.digou.Data;
import com.gyh.digou.MainActivity;
import com.gyh.digou.R;
import com.gyh.digou.bean.IImages;
import com.gyh.digou.fenlei.FenLeiChooseResultActivity;
import com.gyh.digou.itemdetail.ItemDetailActivity;
import com.gyh.digou.itemdetail.shop.ShopActivity;
import com.gyh.digou.progerss.CustomProgressDialog;
import com.gyh.digou.shouye.ads.view.AbOnItemClickListener;
import com.gyh.digou.shouye.ads.view.AbSlidingPlayView;
import com.gyh.digou.shouye.search.ShouyeSearchActivity;
import com.gyh.digou.util.ACache;
import com.gyh.digou.util.NetworkUtil;
import com.gyh.digou.wode.ViewPagerAdapter;
import com.gyh.digou.wode.maijia.UserIn;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zxing.activity.CaptureActivity;



public class ShouyeFragment extends Fragment{
	
	//private View mParent; 
	//public ImageView IVdicator[]; 
	ListView listView;
	public final int PICTURE = 0x1;
	public final int initRecommandFin=0x2;
	public final int initAdsFin=0x3;
	public static int mTimerNum = 0; 
    public Timer mTimer; 
    public TimerTask mTimerTask;  
   // List<HashMap<String,String>> ads=new ArrayList<HashMap<String,String>>();
    
    JSONArray list=new JSONArray();
    //ArrayList<JSONObject> list = new ArrayList<JSONObject>();
	LayoutInflater myinflater;
	Context mContex;
	MainActivity mainActivity;
	FinalBitmap imageLoader;
	
	
	PullToRefreshListView pullToRefreshListView;
	
	//int currentPage=1;
	int pageSize=10;
	
   @SuppressLint("HandlerLeak")
   Handler mHandler = new Handler() {  
        public void handleMessage(Message msg) {  
            switch(msg.what){  
            case PICTURE:  
                   
                break; 
            case initRecommandFin:
            	if(is_refresh)
            		pullToRefreshListView.onRefreshComplete();
            	is_refresh=false;
            	break;
            	
            }  
            super.handleMessage(msg);  
        }  
    };
	  
    
    View headerView;
    LinearLayout noNetWorkLayout;
    AbSlidingPlayView viewPager ;
   
    
    public static ShouyeFragment sf;
    
    public static ShouyeFragment getInstance()
    {
    	if(sf==null)
    	{
    		sf=new ShouyeFragment();
    	}
    	return sf;
    }
    ACache  cache;
    int page_cur=1;
    int list_count;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		
	
		View fragmentView = inflater.inflate(R.layout.shouye, container, false);
		
		
		LinearLayout searchLayout=(LinearLayout) fragmentView.findViewById(R.id.shouye_search_layout);
		
		
		LinearLayout scanLayout=(LinearLayout) fragmentView.findViewById(R.id.scan_title_layout); 
		scanLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(mContex,CaptureActivity.class);
				mainActivity.startActivityForResult(intent,3);
				
			}
		});
		searchLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				
				Intent intent=new Intent(mContex,FenLeiChooseResultActivity.class);
				intent.putExtra("isshouye",true);
				startActivity(intent);
				
			}
		});
		pullToRefreshListView = (PullToRefreshListView) fragmentView.findViewById(R.id.listView1);
		
		//pullToRefreshListView.
		
		
		listView=pullToRefreshListView.getRefreshableView();
		noNetWorkLayout=(LinearLayout) fragmentView.findViewById(R.id.shouye_nonetwork);
		pullToRefreshListView.setMode(Mode.BOTH);
		headerView=getActivity().getLayoutInflater().inflate(R.layout.list_header, null);
		viewPager = (AbSlidingPlayView)headerView.findViewById(R.id.list_header_viewPager_ad);
		ImageView imv_txpf=(ImageView) headerView.findViewById(R.id.shouye_txpf);
		ImageView imv_fjsj=(ImageView) headerView.findViewById(R.id.shouye_fjsj);
		ImageView imv_lssc=(ImageView) headerView.findViewById(R.id.shouye_lssc);
		imv_txpf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(mContex,BaseRecommendActivity.class);
				intent.putExtra("id", "30");
				intent.putExtra("title","童鞋批发");
				startActivity(intent);
				
			}
		});
		imv_fjsj.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				mainActivity.group.check(R.id.third);
			}
		});
		imv_lssc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent intent=new Intent(mContex,BaseRecommendActivity.class);
				intent.putExtra("id", "31");
				intent.putExtra("title","零售商城");
				startActivity(intent);
				
			}
		});
		pullToRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				page_cur=1;
				is_refresh=true;
				initRecommand(true);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				is_refresh=true;
				if(list_count>listView.getCount())
				{
					page_cur++;
					
					initRecommand(false);
				}else
				{
					
					//
					Toast.makeText(mContex, "没有更多数据",Toast.LENGTH_SHORT).show();
					mHandler.sendEmptyMessage(initRecommandFin);
					//pullToRefreshListView.onRefreshComplete();
				}
				
			}

			
		});;
		viewPager.setOnItemClickListener(new AbOnItemClickListener() {
			@Override
			public void onClick(int position) {
				
				
				Log.d("viewPager.setOnItemClickListener","onClick");
				try {
					JSONObject json=ads.get(position);
					String type=json.getString("type");
					Intent intent=new Intent();
					if(type!=null&&type.equals("store"))
					{
						intent.setClass(mContex, ShopActivity.class);
						intent.putExtra("store_id",json.getString("target_id"));
					}else if(type!=null&&type.equals("goods"))
					{
						intent.setClass(mContex,ItemDetailActivity.class);
						intent.putExtra("store_id",json.getString("target_id"));
					}
					startActivity(intent);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent=new Intent(getActivity(),ItemDetailActivity.class);
				try {
					intent.putExtra("goods_id", list.getJSONObject(position-2).getString("goods_id"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				//System.out.println(list.get(position-1).get("goods_id"));
				startActivity(intent);
			}
			
		});
		
		cache=ACache.get(mContex);
		
		imageLoader=FinalBitmap.create(mContex);
		if(list.length()<1)
		{
			if(NetworkUtil.isNetworkAvailable(mContex))
			{
				mainActivity.showDialog();
				/*toast=new CustomToast(mContex);
				View view = LayoutInflater.from(mContex).inflate(R.layout.mytoast, null);
				toast.setGravity(Gravity.TOP,0,PixelFormat.formatDipToPx(mContex, 70));
				toast.setView(view);
				toast.show(-1);*/
				initAds();
				initRecommand(true);
			}else{
				
				if(cache.getAsJSONArray("shouye")!=null&&cache.getAsString("shouyead")!=null)
				{
					list=cache.getAsJSONArray("shouye");
					initAdPager(cache.getAsString("shouyead"));
				}
				//noNetWorkLayout.setVisibility(View.VISIBLE);
			}
		}else{
			
			viewPager.addViews(allListView);
			viewPager.startPlay();
		}
		
		
		listView.addHeaderView(headerView);
		shouyeListAdapter=new ShouyeListAdapter();
		listView.setAdapter(shouyeListAdapter);
		//listBaseAdapter=new MyListViewBaseAdapter(mContex);
		//listBaseAdapter.setData(list);
		//listView.setAdapter(listBaseAdapter);
		
		
		return fragmentView;
	} 
	
	//FinalBitmap imageLoader;
	
	public AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {

		 public void onScrollStateChanged(AbsListView view, int scrollState) {

		 switch (scrollState) {

		 case AbsListView.OnScrollListener.SCROLL_STATE_FLING:

			 
		 //imageLoader.
		// imageLoader.lock();//处于滚动状态锁定加载线程

		 break;

		 case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:

		 int start = listView.getFirstVisiblePosition();//得到listView当前屏的开始位置

		 int end = listView.getLastVisiblePosition(); //得到结束位置

		 if (end >= shouyeListAdapter.getCount()) {

			 end = shouyeListAdapter.getCount() - 1;

		 }

		 
		 //imageLoader.
		// imageLoader.setLoadLimit(start, end);//设置要加载图片的起始位置和结束位置

		// imageLoader.unlock();//解除锁定

		 break;

		 case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:

		// imageLoader.lock();

		 break;

		 default:

		 break;

		 }

		 }

		 public void onScroll(AbsListView view, int firstVisibleItem,

		 int visibleItemCount, int totalItemCount) {

		 }

		 };
	
	
	
	
	
	
	
	
	
	
	
	
	ShouyeListAdapter shouyeListAdapter;
	
	class ShouyeListAdapter extends BaseAdapter
	{
		LayoutInflater inflater;
		
		
		//FinalBitmap imageLoader;
		ShouyeListAdapter()
		{
			
			inflater=LayoutInflater.from(mContex);
			
			
			
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.length();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			
			ShouyeAdapterViewHolder viewHolder ;
			if(null==convertView){
				viewHolder = new ShouyeAdapterViewHolder();
			
				convertView = inflater.inflate(R.layout.list_item, null);
				
			
				viewHolder.pictureName = (ImageView)convertView.findViewById(R.id.pictureName);
				viewHolder.shangPinMingXi = (TextView)convertView.findViewById(R.id.shangPinMingXi);
				viewHolder.shangPinNewPrice = (TextView)convertView.findViewById(R.id.shangPinNewPrice);
				viewHolder.shangPinOldPrice = (TextView)convertView.findViewById(R.id.shangPinOldPrice);
				viewHolder.layout_oldprice=(RelativeLayout) convertView.findViewById(R.id.layout_oldprice);
				
				convertView.setTag(viewHolder);
			}else{
				
				viewHolder = (ShouyeAdapterViewHolder)convertView.getTag();
			}
			
			try {
				JSONObject json=list.getJSONObject(position);
			
			//Bitmap image;
			
				//viewHolder.pictureName.setImageBitmap(imageLoader.getBitmapFromMemoryCache(map.get("default_image")));
				
				if(Data.user.equals(Data.getUserType()))
				{
					
					viewHolder.layout_oldprice.setVisibility(View.GONE);
					viewHolder.shangPinNewPrice.setText(json.getString("price"));
				}else
				{
					viewHolder.layout_oldprice.setVisibility(View.VISIBLE);
					viewHolder.shangPinNewPrice.setText(json.getString("mk_price"));
					viewHolder.shangPinOldPrice.setText(json.getString("price"));
				}
				
				
				imageLoader.display(viewHolder.pictureName,json.getString("default_image"));
				
				viewHolder.shangPinMingXi.setText(json.getString("goods_name"));
				} catch (JSONException e) {
					
					e.printStackTrace();
				}
				return convertView;
			}
		}
		
		
		
		
		
		
	
	
	static class ShouyeAdapterViewHolder
	{
		ImageView pictureName;
		TextView shangPinMingXi;
		TextView shangPinNewPrice;
		TextView shangPinOldPrice;
		Button yaoYiYao;
		RelativeLayout layout_oldprice;
		
		
	}
	
	
	
	
	
	public void initRecommand(boolean cleaned)
	{
		if(list==null||cleaned)
			list=new JSONArray();
		AjaxParams params=new AjaxParams();
		params.put("recommend_id","20");
		params.put("page",page_cur+"");
		
		FinalHttp fh=new FinalHttp();
		fh.post("http://www.cddego.com/api.php?app=search&act=api_recommend", params,new AjaxCallBack<String>() {

			@Override
			public void onLoading(long count, long current) {
				super.onLoading(count, current);
			}

			@Override
			public void onSuccess(String t) {
				
				System.out.println(t);
				
				JSONObject jsonObject = null;
				
				try {
					
					jsonObject = new JSONObject(t);
					JSONObject dataObject = jsonObject.getJSONObject("data"); 
					
					String total = dataObject.getString("total"); 
					list_count=Integer.parseInt(total);
					
					JSONObject listObject= (JSONObject) dataObject.get("list"); 
					Iterator itr=listObject.keys();
					while(itr.hasNext())
					{
						String key=(String) itr.next();
						JSONObject numObject=(JSONObject) listObject.get(key);
						list.put(numObject);
		
					}
					shouyeListAdapter.notifyDataSetChanged();
					
					mHandler.sendEmptyMessage(initRecommandFin);
					cache.put("shouye",list);//加入缓存
				} catch (Exception e) {
					mHandler.sendEmptyMessage(initRecommandFin);
					e.printStackTrace();
				}
			}
		});
		
		
		
	}
	
	//MyListViewBaseAdapter listBaseAdapter;
	ArrayList<View>  allListView = new ArrayList<View>();
	
	boolean is_refresh=false;
	
	List<JSONObject> ads=new ArrayList<JSONObject>();
	public void initAdPager(String t)
	{
		
		System.out.println(t);
		try {
			
			JSONObject json=new JSONObject(t);
			JSONObject data=json.getJSONObject("data");
			
			Iterator<String> itr=data.keys();
			
			if (allListView != null) {
				allListView.clear();
				ads.clear();
				//allListView = null;
			}else
			{
				allListView = new ArrayList<View>();
			}
			
			while(itr.hasNext())
			{
				String key=itr.next();
				JSONObject json_ad=data.getJSONObject(key);
				ads.add(json_ad);
				/*HashMap<String,String> map=new HashMap<String,String>();
				map.put("pic",json_ad.getString("pic"));
				ads.add(map);*/
				ImageView imageView=new ImageView(mContex);
				//imageView.setImageResource(resId[i]);
				//Bitmap image;
				
					//imageView.setImageBitmap(imageLoader.getBitmapFromMemoryCache(json_ad.getString("pic")));
				
				System.out.println(json_ad.getString("pic"));
				
				imageView.setScaleType(ScaleType.CENTER_CROP);
				imageLoader.display(imageView,json_ad.getString("pic"));
				
				allListView.add(imageView);
				
			}
			
			viewPager.addViews(allListView);
			viewPager.startPlay();
			mainActivity.hideDialog();
			
		} catch (JSONException e) {
		
			e.printStackTrace();
		}
		
	}
	public void initAds()
	{
		
		
		AjaxParams params2=new AjaxParams();
		
		params2.put("page",page_cur+"");
		FinalHttp fh2=new FinalHttp();
		fh2.post("http://www.cddego.com/api.php?app=goods&act=api_ads", params2,new AjaxCallBack<String>() {

			@Override
			public void onLoading(long count, long current) {
				
			}

			@Override
			public void onSuccess(String t) {
				System.out.println(t);
				initAdPager(t);
				cache.remove("shouyead");
				cache.put("shouyead",t);
				
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				
			}
			
		});
		
	}
	
	
	ViewPagerAdapter adAdapter;
	
	/*int count;
	public void buildSmallCircle(View headerView)
	{
		
		
		count=ads.size();
		
		IVdicator = new ImageView[count]; 
        
		LayoutParams  mLayoutParams = new LinearLayout.LayoutParams(20, 20);  
          
      
        mLayoutParams.setMargins(7, 10, 7, 10);  
          
      
        LinearLayout Vdicator = (LinearLayout) headerView.findViewById(R.id.viewpager_indicator); 
        for(int i = 0; i <count; i++){  
             
        
            ImageView nowImageView = new ImageView(mContex);  
             
            nowImageView.setLayoutParams(mLayoutParams); 
            if(i==0)
            {
            	nowImageView.setBackgroundResource(R.drawable.indicator_focused);  
            }else {
            	nowImageView.setBackgroundResource(R.drawable.indicator);  
			}
            IVdicator[i] = nowImageView;  
              
           
            ((LinearLayout) Vdicator).addView(IVdicator[i]);  
        }
		
	}*/
	
	/*boolean timer_start=false;
	public void startTimer(){  
	    mTimer = new Timer();  
	    mTimerTask = new TimerTask(){  
	        @Override  
	        public void run() {  
	           // MyApp.ad_position++; 
	            mTimerNum++;
	          
	            mHandler.sendEmptyMessage(PICTURE);  
	        }  
	    };  
	      
	   
	    mTimer.schedule(mTimerTask, 3 * 1000, 3 * 1000); 
	    
	    timer_start=true;
	}*/  
	
	
	
	
	CustomProgressDialog progressDialog;
	
	@Override
	
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		
		mainActivity=(MainActivity)getActivity();
		
		mContex=mainActivity;
		myinflater=LayoutInflater.from(mContex);
	}
	
	@Override
	
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.d("myfragment","onactivitycreated");
	}	
	
	@Override

	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//mTimer.schedule(mTimerTask, 3 * 1000, 3 * 1000);
		Log.d("myfragment","onStart");
	}

	@Override
	
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		viewPager.startPlay();
		imageLoader=mainActivity.getImageLoader();
		//mTimer.
		//startTimer();
		// mTimer.schedule(mTimerTask, 3 * 1000, 3 * 1000);
		Log.d("myfragment","onResume");
	}
	
	@Override
	
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		viewPager.stopPlay();
		mainActivity.hideDialog();
		/*if(toast!=null)
		{
			if(toast.isShowing())
				toast.hide();
		}*/
		//mTimer.cancel();
		Log.d("myfragment","onPause");
	}

	@Override
	
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		//mTimer.cancel();
		Log.d("myfragment","onStop");
	}
	
	@Override
	
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		/*if(no_network_toast!=null)
		{
			if(no_network_toast.isShowing())
				no_network_toast.hide();
		}*/
		
		
		//mTimer.cancel();
		Log.d("myfragment","onDestroyView");
	}

	@Override
	
	public void onDestroy() {
	
		super.onDestroy();
		
	}
	
	@Override
	
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
	}

	@Override
	
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}  
	
}
