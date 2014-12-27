package com.gyh.digou.gouwuche;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyh.digou.Data;
import com.gyh.digou.MainActivity;
import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.bean.Cart;
import com.gyh.digou.bean.LoginInfo;
import com.gyh.digou.bean.ShopOwner;
import com.gyh.digou.gouwuche.ShakeListener.OnShakeListener;
import com.gyh.digou.mytoast.CustomToast;
import com.gyh.digou.progerss.CustomProgressDialog;
import com.gyh.digou.util.NetworkUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;

public class GouWuCheFragement extends Fragment{

	
	Context mContext;
	List<ShopOwner> listShopOwner;
	List<Cart> listCart;
	List<JSONObject> cartInfoList=new ArrayList<JSONObject>();
	TextView tv_all_price;
	TextView btn_jiesuan;
	Vibrator mVibrator;	
	SoundPool sndPool;
	MainActivity mainActivity;
	
	public static GouWuCheFragement gf;
	
	public GouWuCheFragement()
	{
		super();
	}
	public static GouWuCheFragement getInstance(Context mContext,String fname)
	{
		if(gf==null)
		{
			
			
			gf=new GouWuCheFragement();
		}
		return gf;
	}
	
	@SuppressLint("UseSparseArrays")
	private HashMap<Integer, Integer> soundPoolMap = new HashMap<Integer, Integer>();
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if(mShakeListener!=null)
			mShakeListener.stop();
		if(cartInfoAdapter!=null)
		{
			cartInfoAdapter.setFlag(false);
		}
	}
	@Override
	public void onPause() {
		super.onPause();
	}
	@Override
	public void onResume() {
		super.onResume();
		refreshCartInfo();
	}
	@Override
	public void onStart() {
		super.onStart();
	}
	@Override
	public void onStop() {
		super.onStop();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		mContext=mainActivity=(MainActivity) getActivity();
		
		mVibrator = (Vibrator)mainActivity.getApplication().getSystemService(Activity.VIBRATOR_SERVICE);
		
	}
	private void loadSound() {
		sndPool = new SoundPool(2, AudioManager.STREAM_SYSTEM, 5);
		sndPool.load(mContext, R.raw.dog,1);
	}
	
	FrameLayout frameLayout ;
    ImageView guideImage;
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	
	private final static int refreshData=0x1;
	private final static int refreshComplete=0x2;
	private final static int delitem=0x0010;
	private final static int updateitemnum=0x0011;
	 LoginInfo info = null;
	 Handler handler=new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			
			switch(msg.what)
			{
			case refreshData:
				refreshData();
				break;
			case refreshComplete:
				pullToRefreshExpandableListView.onRefreshComplete();
				break;
			case delitem:
				deleteItem((String)msg.obj,msg.arg1,msg.arg2);
				break;
			case updateitemnum:
				
				cartUpdateNum((String)msg.obj,msg.arg1+"");
				break;
			}
		}
	};
	HashMap<String,JSONObject> cartMapInfo=new HashMap<String,JSONObject>();
	PullToRefreshExpandableListView pullToRefreshExpandableListView;
	
	//PullToRefreshExpandableListView.InternalExpandableListView internalExpandableListView;
	ExpandableListView cartInfoListView;
	CartInfoAdapter cartInfoAdapter;
	String token;
	Resources res;
	ShakeListener mShakeListener;
	boolean is_refresh=false;
	View emptyView;
	public void expandAll()
	{
		for(int i=0;i<cartInfoAdapter.getGroupCount();i++)
		{
			
			cartInfoListView.expandGroup(i);
		}
	}
	public void closeAll()
	{
		for(int i=0;i<cartInfoAdapter.getGroupCount();i++)
		{
			
			cartInfoListView.collapseGroup(i);
		}
		
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.cart, container, false);
		
		
		pullToRefreshExpandableListView=(PullToRefreshExpandableListView)view.findViewById(R.id.cart_expandlistview);
		
		cartInfoListView=pullToRefreshExpandableListView.getRefreshableView();
		
		pullToRefreshExpandableListView.setOnRefreshListener(new OnRefreshListener<ExpandableListView>() {

			@Override
			public void onRefresh(
					PullToRefreshBase<ExpandableListView> refreshView) {
				is_refresh=true;
				refreshCartInfo();
			}
		});
		emptyView=inflater.inflate(R.layout.cart_empty_view,null,false);
		
		tv_all_price=(TextView) view.findViewById(R.id.cart_bottom_heji_content);
		btn_jiesuan=(TextView) view.findViewById(R.id.cart_bottom_btn_jiesuan);
		res=getResources();
		loadSound();
		
		mShakeListener = new ShakeListener(mContext);
        mShakeListener.setOnShakeListener(new OnShakeListener() {
			public void onShake() {
				mShakeListener.stop();
				
				
				frameLayout.removeView(guideImage);
				
				if(cartInfoAdapter.getGroupCount()>0)
				{
					sndPool.play(1, (float) 1, (float) 1, 0, 0,(float) 1.2);
					
					shakeRefreshData();
				}else
				{
					Toast.makeText(mContext, "购物车是空的,您暂时不能使用该功能",Toast.LENGTH_SHORT).show();	
				}
				
				
				
			}
        });
       final String tv_edit_string= res.getString(R.string.cart_edit_tv);
       final String tv_edit_fin_string= res.getString(R.string.cart_edit_tv_fin);
       final TextView tv_edit_text= (TextView) view.findViewById(R.id.cart_edit_text);
       
		view.findViewById(R.id.cart_layout_edit).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if(tv_edit_text.getText().toString().equals(tv_edit_string))
				{
					tv_edit_text.setText(tv_edit_fin_string);
					cartInfoAdapter.editCartInfo(true);
				}else
				{
					cartInfoAdapter.editCartInfo(false);
					tv_edit_text.setText(tv_edit_string);
				}
				
				
				
			}
		});
		
		LinearLayout btn_shake=(LinearLayout) view.findViewById(R.id.cart_btn_shake);
		btn_shake.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				
				
				View myView=mainActivity.getWindow().getDecorView().findViewById(R.id.cart_content);
				ViewParent viewParent=myView.getParent();
				if(viewParent instanceof FrameLayout)
				{
					 frameLayout = (FrameLayout)viewParent;
			         guideImage = new ImageView(mContext);
		             FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
		             guideImage.setLayoutParams(params);
		             guideImage.setScaleType(ScaleType.FIT_XY);
		             guideImage.setImageResource(R.drawable.shake);
		             guideImage.setOnClickListener(new View.OnClickListener() {
		                 @Override
		                 public void onClick(View v) {
		                	 frameLayout.removeView(guideImage);
		                     mShakeListener.stop();
		                 }
		             });
		             frameLayout.addView(guideImage);
				}
				mShakeListener.start();
			}
		});
		
		
		
		
		
		
		
		
		cart_bottom_tv_last_layout=(RelativeLayout) view.findViewById(R.id.cart_bottom_heji_layout_last);
		
		cart_bottom_tv_last_price=(TextView) view.findViewById(R.id.cart_bottom_heji_last_content);
		
		
		view.findViewById(R.id.cart_bottom_btn_jiesuan).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				//addCommer();
				
				
				if(cartInfoAdapter.getGroupCount()>0)
				{
					Intent intent=new Intent(mContext,MakeOrderActivity.class);
					
					//;
					ArrayList<String> cartInfo=new ArrayList<String>();
					for(JSONObject json:cartInfoAdapter.getData())
					{
						cartInfo.add(json.toString());
					}
					
					intent.putStringArrayListExtra("cartInfo", cartInfo);
					intent.putExtra("allPrice",tv_all_price.getText().toString());
					startActivity(intent);
				}else
				{
					Toast.makeText(mContext, "当前不能结算,因为购物车是空的",Toast.LENGTH_SHORT).show();
				
				
				}
				
				
			}
		});
		
		cartInfoAdapter=new CartInfoAdapter(mContext);
		cartInfoListView.setAdapter(cartInfoAdapter);	
		cartInfoAdapter.setHandler(handler);
		
		//cartInfoListView 
		
		return view;
	}
	//CustomToast toast;
	
	
	
	
	
	/*public void addCommer()
	{
		
		AjaxParams params=new AjaxParams();
		params.put("token",Data.getInfo().getData().getToken());
		params.put("barcode","3937162684955");
		params.put("brand", "\\U54c1\\U724c");
		params.put("cate_id","1729");
		params.put("cate_name", "\\U76ae\\U978b");
		params.put("description","\\U63cf\\U8ff0");
		params.put("goods_file_id[]", "4881");
		
		params.put("goods_name","\\U6807\\U9898");
		params.put("if_show","1");
		params.put("minimum_price", "100");
		params.put("minimum_price[]","100,200");
		params.put("mk_price", "150");
		params.put("mk_price[]","150,250");
		params.put("price", "188");
		
		params.put("price[]","188,288");
		params.put("recommended", "1");
		params.put("spec_1[]","\\U767d\\U8272,\\U767d\\U8272");
		params.put("spec_2[]", "\\U7ea2\\U8272,\\U7ea2\\U8272");
		params.put("spec_name_1","\\U89c4\\U683c1");
		params.put("spec_name_2", "\\U89c4\\U683c2");
		
		params.put("stock","100");
		params.put("stock[]", "100,200");
		
		FinalHttp fh=new FinalHttp();
		fh.post("http://www.cddego.com/api.php?app=my_goods&act=api_add",params,new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {
				
				Log.d("addCommer", "onSuccess---"+t);
				
				
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				Log.d("addCommer", "onFailure---"+strMsg);
				
			}
		
		
		
		});
		
	}*/
	
	TextView cart_bottom_tv_last_price;
	RelativeLayout cart_bottom_tv_last_layout;
	
	protected void refreshCartInfo() {
		cartInfoList.clear();
		initData();
	}
	public void shakeRefreshData()
	{
		
		
		AjaxParams params=new AjaxParams();
		params.put("token",Data.getInfo().getData().getToken());
		FinalHttp fh=new FinalHttp();
		fh.post(Data.getshakeUrl(), params,new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {
				
				try {
					JSONObject json_result=new JSONObject(t);
					JSONObject json_data=json_result.getJSONObject("data");
					int ErrNum=json_result.getInt("ErrNum");
					
					if(ErrNum==0)
					{
						Toast.makeText(mContext,"您获得的折扣为:"+json_data.getString("discount"),Toast.LENGTH_SHORT).show();
						handler.postDelayed(new Runnable() {
							
							@Override
							public void run() {
								
								refreshCartInfo();
							}
						}, 800);
						
					}else
					{
						Toast.makeText(mContext,"出错了!",Toast.LENGTH_SHORT).show();
					}
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				
				
				
				
			}
			
			
			
		});
		/*new Thread(new Runnable(){

			@Override
			public void run() {
				
				List<NameValuePair> params2 = new ArrayList<NameValuePair>();
				params2.add(new BasicNameValuePair("token",getToken()));
				String result=NetworkUtil.post(Data.getCartUrl(), params2);
				System.out.println(result);
					
					try{
						
						cartInfoList.clear();
						JSONObject cartInfo=new JSONObject(result);
						
						JSONObject data=cartInfo.getJSONObject("data");
					
						Iterator<String> itr=data.keys();
						
						while(itr.hasNext())
						{
							String key=itr.next();
							JSONObject jsonObject=data.getJSONObject(key);
							cartInfoList.add(jsonObject);
							
						}
					
						mainActivity.runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								cartInfoAdapter.setCartData(cartInfoList);
							}
						});
					}catch (Exception e) {

					}
			}
		}).start();*/
		
	}
	
	
	
	public void cartUpdateNum(String spec_id,String num)
	{
		
		
		mainActivity.showDialog();
		AjaxParams params=new AjaxParams();
		params.put("token",Data.getInfo().getData().getToken());
		params.put("spec_id",spec_id);
		params.put("quantity",num);
		FinalHttp fh=new FinalHttp();
		fh.post(Data.getUpdateCartNum(), params,new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {
				refreshCartInfo();
			}
		});
		
		
	}
	
	public void initData()
	{
		
		mainActivity.showDialog();
		AjaxParams params=new AjaxParams();
		params.put("token",Data.info.getData().getToken());
		FinalHttp fh=new FinalHttp();
		fh.post(Data.getCartUrl(), params,new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {
			Log.d("initData...","t===="+t); 
				try{
					
					JSONObject cartInfo=new JSONObject(t);
					
					JSONObject data=cartInfo.getJSONObject("data");
				
					Iterator<String> itr=data.keys();
					
					while(itr.hasNext())
					{
						String key=itr.next();
						JSONObject jsonObject=data.getJSONObject(key);
						cartInfoList.add(jsonObject);
						
					}
					
					
					if(is_refresh)
					{
						handler.sendEmptyMessage(refreshComplete);
						is_refresh=false;
					}
					
					cartInfoAdapter.setCartData(cartInfoList);
					mainActivity.hideDialog();
					closeAll();
					expandAll();
					/*for(int i=0;i<cartInfoList.size();i++)
					{
						cartInfoListView.expandGroup(i);
					}*/
					
					//更新显示购物车底部数据
					refreshData();
				}catch (Exception e) {
					mainActivity.hideDialog();
					pullToRefreshExpandableListView.setEmptyView(emptyView);
					cartInfoAdapter.clear();
					if(is_refresh)
					{
						handler.sendEmptyMessage(refreshComplete);
						is_refresh=false;
					}
					refreshData();
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				
			}
			
			
			
		});
		
	}
	public void deleteItem(final String rec_id,final int arg2,final int arg3)
	{
		
		
		
		AjaxParams params=new AjaxParams();
		params.put("token",Data.getInfo().getData().getToken());
		params.put("rec_id",rec_id);
		FinalHttp fh=new FinalHttp();
		fh.post(Data.getDropCartUrl(), params,new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {
				Log.d("deleteItem", t);
				
				refreshCartInfo();
				
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				
				
				
			}
			
			
		});
	
	}
	public void refreshData() {
		
		//List<JSONObject> benList=cartInfoAdapter.getData();
		
		int count=0;
		double prices=0.00;
		double original_prices=0.00;
		for(int i=1;i<=cartInfoList.size();i++)
		{
			try {
				
				
			JSONObject jsonObj_w=cartInfoList.get(i-1);
			JSONArray arry=jsonObj_w.getJSONArray("goods");
			if(jsonObj_w.getBoolean("check"))
			{
				for(int j=0;j<arry.length();j++)
				{
					
						JSONObject json=arry.getJSONObject(j);
						int num=json.getInt("quantity");
						double price=json.getDouble("price");
						double original_price=json.getDouble("original_price");
						count+=num;
						prices+=num*price;
						original_prices+=num*original_price;
				
				}
			}
			} catch (JSONException e) {
				
				e.printStackTrace();
			}
		}
		
		if(original_prices!=prices)
		{
			cart_bottom_tv_last_layout.setVisibility(View.VISIBLE);
			String orinPricesStr=original_prices+"000";
			String pricesSub=orinPricesStr.substring(0,orinPricesStr.indexOf(".")+3);
			cart_bottom_tv_last_price.setText(pricesSub);
		}else
		{
			
			cart_bottom_tv_last_layout.setVisibility(View.GONE);
			
		}
		
		
		String pricesStr=prices+"000";
		String pricesSub=pricesStr.substring(0,pricesStr.indexOf(".")+3);
		tv_all_price.setText(pricesSub);
		btn_jiesuan.setText("结算("+count+")");
		
	}

	public static final String removeBOM(String data) {
		if (TextUtils.isEmpty(data)) {
		return data;
		}

		if (data.startsWith("\ufeff")) {
			return data.substring(1);
		} 
		else
		{
			return data;
		}
	}
	
	
	
	
	
	
}
