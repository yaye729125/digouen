package com.gyh.digou.itemdetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gyh.digou.Data;
import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.bean.Cart;
import com.gyh.digou.bean.LoginInfo;
import com.gyh.digou.bean.ShopOwner;
import com.gyh.digou.gouwuche.CartInfoAdapter;
import com.gyh.digou.gouwuche.MakeOrderActivity;
import com.gyh.digou.gouwuche.ShakeListener;
import com.gyh.digou.gouwuche.ShakeListener.OnShakeListener;
import com.gyh.digou.progerss.CustomProgressDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

public class ItemDetailCartActivity extends Activity {

	
	//Context ItemDetailCartActivity.this;
	List<ShopOwner> listShopOwner;
	List<Cart> listCart;
	List<JSONObject> cartInfoList=new ArrayList<JSONObject>();
	TextView tv_all_price;
	TextView btn_jiesuan;
	Vibrator mVibrator;	
	SoundPool sndPool;
	
	FrameLayout frameLayout ;
    ImageView guideImage;
    
    
    
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
	//String token;
	Resources res;
	ShakeListener mShakeListener;
	boolean is_refresh=false;
	
	
	
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
	
	/*public String getToken()
	{
	
		return ((MyApp)getApplication()).getInfo().getData().getToken();
	}*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.cart);
		
		
		
		
		pullToRefreshExpandableListView=(PullToRefreshExpandableListView)findViewById(R.id.cart_expandlistview);
		
		cartInfoListView=pullToRefreshExpandableListView.getRefreshableView();
		pullToRefreshExpandableListView.setEmptyView(LayoutInflater.from(this).inflate(R.layout.cart_empty_view,null,false));
		pullToRefreshExpandableListView.setOnRefreshListener(new OnRefreshListener<ExpandableListView>() {

			@Override
			public void onRefresh(
					PullToRefreshBase<ExpandableListView> refreshView) {
				is_refresh=true;
				refreshCartInfo();
			}
		});
		tv_all_price=(TextView)findViewById(R.id.cart_bottom_heji_content);
		btn_jiesuan=(TextView) findViewById(R.id.cart_bottom_btn_jiesuan);
		res=getResources();
		loadSound();
		
		mShakeListener = new ShakeListener(ItemDetailCartActivity.this);
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
					Toast.makeText(ItemDetailCartActivity.this, "购物车是空的,您暂时不能使用该功能",Toast.LENGTH_SHORT).show();	
				}
				
				
				
			}
        });
       final String tv_edit_string= res.getString(R.string.cart_edit_tv);
       final String tv_edit_fin_string= res.getString(R.string.cart_edit_tv_fin);
       final TextView tv_edit_text= (TextView)findViewById(R.id.cart_edit_text);
       
		findViewById(R.id.cart_layout_edit).setOnClickListener(new OnClickListener() {
			
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
		
		LinearLayout btn_shake=(LinearLayout)findViewById(R.id.cart_btn_shake);
		btn_shake.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				
				
				View myView=getWindow().getDecorView().findViewById(R.id.cart_content);
				ViewParent viewParent=myView.getParent();
				if(viewParent instanceof FrameLayout)
				{
					 frameLayout = (FrameLayout)viewParent;
			         guideImage = new ImageView(ItemDetailCartActivity.this);
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
		
		
		
		findViewById(R.id.cart_bottom_btn_jiesuan).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(cartInfoAdapter.getGroupCount()>0)
				{
					Intent intent=new Intent(ItemDetailCartActivity.this,MakeOrderActivity.class);
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
					Toast.makeText(ItemDetailCartActivity.this, "当前不能结算,因为购物车是空的",Toast.LENGTH_SHORT).show();
				
				}
				
				
			}
		});
		
		cartInfoAdapter=new CartInfoAdapter(ItemDetailCartActivity.this);
		cartInfoListView.setAdapter(cartInfoAdapter);	
		cartInfoAdapter.setHandler(handler);
		
		
	}

	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	}
	@Override
	protected void onPause() {
	
		super.onPause();
	}
	@Override
	protected void onResume() {
		
		super.onResume();
		refreshCartInfo();
	}
	@Override
	protected void onStart() {
	
		super.onStart();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	private void loadSound() {

		sndPool = new SoundPool(2, AudioManager.STREAM_SYSTEM, 5);
		sndPool.load(ItemDetailCartActivity.this, R.raw.dog,1);
	}
	
	
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
						Toast.makeText(ItemDetailCartActivity.this,"您获得的折扣为:"+json_data.getString("discount"),Toast.LENGTH_SHORT).show();
						handler.postDelayed(new Runnable() {
							
							@Override
							public void run() {
								
								refreshCartInfo();
							}
						}, 800);
						
					}else
					{
						Toast.makeText(ItemDetailCartActivity.this,"出错了!",Toast.LENGTH_SHORT).show();
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
	}
	
	
	
	public void cartUpdateNum(String spec_id,String num)
	{
		
		
		showDialog();
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
		
		showDialog();
		AjaxParams params=new AjaxParams();
		params.put("token",Data.getInfo().getData().getToken());
		FinalHttp fh=new FinalHttp();
		fh.post(Data.getCartUrl(), params,new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {
			
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
					hideDialog();
					
					closeAll();
					expandAll();
					refreshData();
				}catch (Exception e) {
					hideDialog();
					cartInfoAdapter.setCartData(cartInfoList);
					if(is_refresh)
					{
						handler.sendEmptyMessage(refreshComplete);
						is_refresh=false;
					}
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				
			}
			
			
			
		});
		
	}
	
	
	CustomProgressDialog dialog;
    
    
	public void showDialog()
	    {
	    	
	    	if(dialog==null)
	    	{
	    		dialog=CustomProgressDialog.createDialog(this, R.style.CustomProgressDialog,R.layout.mytoast);
	    		dialog.setMessage("正在加载",R.id.mytoast_tv);
	    		dialog.show();
	    	}else
	    	{
	    		if(!dialog.isShowing())
	    			dialog.show();
	    	}
	    	
	    }
	    public void hideDialog()
	    {
	    	if(dialog!=null)
	    	{
	    		dialog.dismiss();
	    	}
	    	
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
				
				refreshCartInfo();
				
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				
				
				
			}
			
			
		});
	}
	
	
	public void refreshData() {
		
		List<JSONObject> benList=cartInfoAdapter.getData();
		
		int count=0;
		double prices=0.00;
		for(int i=1;i<=benList.size();i++)
		{
			try {
				
				
			JSONObject jsonObj_w=benList.get(i-1);
			JSONArray arry=jsonObj_w.getJSONArray("goods");
			if(jsonObj_w.getBoolean("check"))
			{
				for(int j=0;j<arry.length();j++)
				{
					
						JSONObject json=arry.getJSONObject(j);
						int num=json.getInt("quantity");
						double price=json.getDouble("price");
						count+=num;
						prices+=num*price;
				
				}
			}
			} catch (JSONException e) {
				
				e.printStackTrace();
			}
		}
		String pricesStr=prices+"000";
		String pricesSub=pricesStr.substring(0,pricesStr.indexOf(".")+3);
		tv_all_price.setText("￥"+pricesSub);
		btn_jiesuan.setText("结算("+count+")");
		
	}
	
	
}
