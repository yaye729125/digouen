package com.gyh.digou.itemdetail;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviPara;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.gyh.digou.Data;
import com.gyh.digou.LoginActivity;
import com.gyh.digou.MainActivity;
import com.gyh.digou.R;
import com.gyh.digou.R.drawable;
import com.gyh.digou.R.id;
import com.gyh.digou.R.layout;
import com.gyh.digou.R.style;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.bean.Cart;
import com.gyh.digou.bean.Goods;
import com.gyh.digou.bean.IImages;
import com.gyh.digou.bean.ItemDetailInfo;
import com.gyh.digou.bean.ShopOwner;
import com.gyh.digou.bean.Specs;
import com.gyh.digou.bean.Store_Data;
import com.gyh.digou.itemdetail.map.BaseMapDemo;
import com.gyh.digou.itemdetail.map.MapNaviActivity;
import com.gyh.digou.itemdetail.shop.ShopActivity;
import com.gyh.digou.mytoast.CustomToast;
import com.gyh.digou.progerss.CustomProgressDialog;
import com.gyh.digou.shouye.ads.view.AbOnItemClickListener;
import com.gyh.digou.shouye.ads.view.AbSlidingPlayView;
import com.gyh.digou.util.Tools;
import com.gyh.digou.view.MyScrollView;

public class ItemDetailActivity extends Activity implements OnClickListener,
	OnGetGeoCoderResultListener {
	
	AbSlidingPlayView viewPager;
	LayoutInflater inflater;
	
	private ArrayList<View> allListView;
	
	EditText buynum;
	TextView tv_format;
	TextView tv_address;
	//ShopOwner shopOwner;
	Cart cart;
	
	@Override
	protected void onNewIntent(Intent intent) {
		
		Log.d("onNewIntent", intent.toString());
		
		getData(intent.getStringExtra("goods_id"));
		myScrollView.scrollTo(0, 0);
		
	}

	public void getData(String goods_id)
	{
		showDialog();
		AjaxParams params3=new AjaxParams();
		params3.put("goods_id", goods_id);
		//params.p
		
		FinalHttp fh3=new FinalHttp();
		fh3.post(Data.getGoodsDetailUrl(),params3,new AjaxCallBack<String>()
				{
					@Override
					public void onLoading(long count, long current) {
						super.onLoading(count, current);
					}
					@Override
					public void onSuccess(String t) {
					
						System.out.println(t);
						Gson gson=new Gson();
						
						try
						{
							itemDetailInfo=gson.fromJson(t,ItemDetailInfo.class);
							goods=itemDetailInfo.getData().getGoods();
							//itemDetailInfo.getData().get
							//tv_format.setText();
							
							initViewPager(goods.get_images());
							
							hideDialog();
							
							setText();
						}catch (JsonSyntaxException e)
						{
							Toast.makeText(ItemDetailActivity.this,"服务器出错",Toast.LENGTH_SHORT).show();
						}
					
					}
					private void setText() {
						
						tv_price.setText("价格: ￥"+goods.getPrice());
						tv_name.setText(goods.getGoods_name());
						setText2TvFormat(goods);
						
						store_Data=itemDetailInfo.getData().getStore_data();
						tv_shop_address.setText(store_Data.getAddress());
						shopName=store_Data.getStore_name();
						String store_id=store_Data.getStore_id();
						cart.setShopOwnerId(store_id);
						tv_shopname.setText(shopName);
						tv_shopowner_phonenum.setText(store_Data.getTel());
						
					}
			
				}
					
		);
		
		
	}
	
	
	
	

	String goods_id;
	TextView tv_shop_address;
	TextView tv_shopowner_phonenum;
	TextView tv_name;
	TextView tv_price;
	TextView tv_shopname;
	LinearLayout layout_option;
	
	
	MyScrollView myScrollView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.item_detail);
		//get the info deliver by fragment
		Intent intent=getIntent();
		//String goods_id=intent.getStringExtra("goods_id");
		goods_id=intent.getStringExtra("goods_id");
		
		//get the Inflater
		inflater=getLayoutInflater();
		
		//init views
		viewPager = (AbSlidingPlayView)findViewById(R.id.viewPager_menu);
		myScrollView=(MyScrollView) findViewById(R.id.item_detail_scrollview);
		RelativeLayout layout_format=(RelativeLayout) findViewById(R.id.item_detail_layout_format);
		LinearLayout add_cart=(LinearLayout) findViewById(R.id.item_detail_layout_add_cart);
		tv_format=(TextView) findViewById(R.id.item_detail_tv_format);
		
		tv_shop_address=(TextView) findViewById(R.id.item_detail_shop_address);
		
		
		RelativeLayout layout_shopowner_phonenum=(RelativeLayout) findViewById(R.id.item_detail_layout_shopper_phonenum);
		RelativeLayout layout_shop=(RelativeLayout) findViewById(R.id.item_detail_layout_shop);
		
		
		RelativeLayout layout_description=(RelativeLayout) findViewById(R.id.item_detail_layout_description);
		RelativeLayout layout_shop_loc=(RelativeLayout) findViewById(R.id.item_detail_layout_loc);
		layout_shop_loc.setOnClickListener(this);
		tv_shopowner_phonenum=(TextView) findViewById(R.id.item_detail_shopper_phonenum_content);
		tv_name=(TextView) findViewById(R.id.item_detail_name);
		tv_price=(TextView) findViewById(R.id.item_detail_price);
		tv_shopname=(TextView) findViewById(R.id.item_detail_shopname);
		TextView tv_itemdetail_yao=(TextView) findViewById(R.id.item_detail_tv_yao);
		TextView tv_cartnum=(TextView) findViewById(R.id.item_detail_bottom_tv_cartnum);
		ImageView imv_gocart=(ImageView) findViewById(R.id.item_detail_bottom_imv_gocart);
		
		
		//like this  maybe has less temp var 
		layout_option=(LinearLayout) findViewById(R.id.itemdetail_layout_title_option);
		layout_option.setOnClickListener(this);
		LinearLayout layout_back=(LinearLayout) findViewById(R.id.itemdetail_layout_title_back);
		layout_back.setOnClickListener(this);
		
		
		//make yao textstyle
		SpannableStringBuilder style=new SpannableStringBuilder(tv_itemdetail_yao.getText().toString());   
	    style.setSpan(new ForegroundColorSpan(Color.RED),6,10,Spannable.SPAN_EXCLUSIVE_INCLUSIVE); 
	    // style.setSpan(new For , start, end, flags)
	    tv_itemdetail_yao.setText(style);
		
	    //
	    cart=new Cart();
	    
	    
	    //setonclicklistener
	    layout_description.setOnClickListener(this);
		add_cart.setOnClickListener(this);
	    layout_format.setOnClickListener(this);
	    layout_shopowner_phonenum.setOnClickListener(this);
	    layout_shop.setOnClickListener(this);
	    imv_gocart.setOnClickListener(this);
		
	    
	    //System.out.println(goods_id);
		
	    //get networkdata by goods_id
		getData(goods_id);
		
		
		
	}
	
	
	
	public void setText2TvFormat(Goods goods)
	{
		StringBuilder sb=new StringBuilder();
		Specs specs=goods.get_specs().get(0);
		cart.setSpec_id(specs.getSpec_id());
		String format_tt1 = "",format_tt2="",format_tt3="",format_tt4="",format_en1="",format_en2="",format_en3="",format_en4="";
		for(int i=1;i<=Integer.parseInt(goods.getSpec_qty());i++)
		{
			
			if(i==1)
			{
				
				format_tt1=goods.getSpec_name_1();
				format_en1=specs.getSpec_1();
				sb.append(format_tt1+": "+format_en1+" ");
				
			}else if(i==2)
			{
				format_tt2=goods.getSpec_name_2();
				format_en2=specs.getSpec_2();
				sb.append(format_tt2+": "+format_en2+" ");
				
			}else if(i==3)
			{
				format_tt3=goods.getSpec_name_3();
				format_en3=specs.getSpec_3();
				sb.append(format_tt3+": "+format_en3+" ");
			}else
			{
				format_tt4=goods.getSpec_name_4();
				format_en4=specs.getSpec_4();
				sb.append(format_tt4+": "+format_en4);
				
			}
		}
		cart.setSpec(sb.toString());
		tv_format.setText("规格:  "+sb.toString()+"  x1");
	}
	
	Store_Data store_Data;
	String shopName;
	//json object goods,ItemDetailInfo
	Goods goods;
	ItemDetailInfo itemDetailInfo;
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	
	
	
	private void initViewPager(final List<IImages> list) {

		if (allListView != null) {
			allListView.clear();
			allListView = null;
		}
		allListView = new ArrayList<View>();

		FinalBitmap imageLoader=FinalBitmap.create(this);
		imageLoader.configLoadingImage(R.drawable.ic_launcher);
		
		for (int i = 0; i < list.size(); i++) {
			
			ImageView imageView=new ImageView(this);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			//imageView.setImageResource(resId[i]);
			imageLoader.display(imageView, list.get(i).getImage_url());
			
			allListView.add(imageView);
		}
		viewPager.addViews(allListView);
		
		viewPager.setOnItemClickListener(new AbOnItemClickListener() {
			@Override
			public void onClick(int position) {
				
				Toast.makeText(ItemDetailActivity.this,"item click",Toast.LENGTH_SHORT).show();
				
				Intent intent=new Intent(ItemDetailActivity.this,ShowItemBigPictureActivity.class);
				intent.putExtra("position", position);
				//intent.putStringArrayListExtra("srcList",(ArrayList<String>) srcList);
				intent.putParcelableArrayListExtra("imageList",(ArrayList<? extends Parcelable>) list);
				startActivity(intent);
				
			}
		});
	}
	
	
	ArrayList<ArrayList<String>> erList=new ArrayList<ArrayList<String>>();
	TextView base_toast_tv;
	ProgressBar bar;
	
	
	
	
	
	CustomProgressDialog dialog;
    
    
	public void showDialog()
	    {
	    	
	    	if(dialog==null)
	    	{
	    		dialog=CustomProgressDialog.createDialog(this, R.style.CustomProgressDialog,R.layout.mytoast);
	    		dialog.setMessage("正在加载",R.id.mytoast_tv);
	    		dialog.show();
	    		/*dialog=new CustomProgressDialog(mContex,R.style.basedialog);
	    		dialog.*/
	    	}else
	    	{
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
	    public void hideDialog(long delayMillis)
	    {
	    	
	    	new Handler().postDelayed(new Runnable()
	    	{

				@Override
				public void run() {
					
					
					if(dialog!=null)
			    	{
			    		dialog.dismiss();
			    	}
					
				}
	    		
	    	}, delayMillis);
	    	
	    }
	
	
	
	public View getChooseFormatView()
	{
		
		
		View choose_format=inflater.inflate(R.layout.item_detail_choose_format, null);
		RadioGroup group_format=(RadioGroup) choose_format.findViewById(R.id.item_detail_choose_format_root);
		
		ImageView buynum_add,buynum_del,imv_bk;
		Button btn_ok;
		btn_ok=(Button) choose_format.findViewById(R.id.item_detail_choose_format_btn_ok);
		imv_bk=(ImageView) choose_format.findViewById(R.id.item_detail_choose_format_imv);
		imv_bk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				pop.dismiss();
				
			}
		});
		buynum_add=(ImageView) choose_format.findViewById(R.id.pop_add);
		buynum_del=(ImageView) choose_format.findViewById(R.id.pop_reduce);
		buynum=(EditText) choose_format.findViewById(R.id.pop_num);
		
		buynum.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
				
				
				if (arg1==EditorInfo.IME_ACTION_NONE ||(arg2!=null&&arg2.getKeyCode()== KeyEvent.KEYCODE_ENTER)) 
				{                
					       
					
					InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);   

					imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
					return true;             
				}               
				
				return false;
			}
		});
	
		buynum_add.setOnClickListener(this);
		btn_ok.setOnClickListener(this);
		buynum_del.setOnClickListener(this);
		
		Resources res=getResources();
		//boolean flag=false;
		for(final Specs specs:goods.get_specs())
		{
			
			RadioButton radioButton1=new RadioButton(ItemDetailActivity.this);
			
			RadioGroup.LayoutParams params=new RadioGroup.LayoutParams(Tools.dip2px(ItemDetailActivity.this,200), ViewGroup.LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 15, 0, 0);
			radioButton1.setLayoutParams(params);
			//等同radiobutton 设置android:button=null
			radioButton1.setButtonDrawable(res.getDrawable(android.R.color.transparent));
			
			
			String format_tt1 = "",format_tt2="",format_tt3="",format_tt4="",format_en1="",format_en2="",format_en3="",format_en4="";
			final StringBuilder sb=new StringBuilder();
			for(int i=1;i<=Integer.parseInt(goods.getSpec_qty());i++)
			{
				
				if(i==1)
				{
					format_tt1=goods.getSpec_name_1();
					format_en1=specs.getSpec_1();
					sb.append(format_tt1+": "+format_en1+" ");
					
				}else if(i==2)
				{
					format_tt2=goods.getSpec_name_2();
					format_en2=specs.getSpec_2();
					sb.append(format_tt2+": "+format_en2+" ");
					
				}else if(i==3)
				{
					format_tt3=goods.getSpec_name_3();
					format_en3=specs.getSpec_3();
					sb.append(format_tt3+": "+format_en3+" ");
				}else
				{
					format_tt4=goods.getSpec_name_4();
					format_en4=specs.getSpec_4();
					sb.append(format_tt4+": "+format_en4);
					
				}
			}
			
			radioButton1.setOnCheckedChangeListener(new OnCheckedChangeListener(){
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					
					if(isChecked)
					{
						cart.setSpec_id(specs.getSpec_id());
						cart.setSpec(sb.toString());
					}
					
					
				}
			});
			
			radioButton1.setText(sb.toString());

			radioButton1.setPadding(30, 15, 30, 15);
			radioButton1.setGravity(Gravity.CENTER);
		
			radioButton1.setBackgroundResource(R.drawable.item_detail_choose_format_radiobtn_selector);
			//RadioButton radioButton2=new RadioButton(ItemDetailActivity.this);
			group_format.addView(radioButton1);
			
		}
		
		RadioButton radiobtn=(RadioButton) group_format.getChildAt(0);
		radiobtn.setChecked(true);
		
		return choose_format;
	}
	
	
	public void addToCart()
	{
		
		
		
		showDialog();
		
		//String token=((MyApp)getApplication()).getInfo().getData().getToken();
		
		
		AjaxParams params=new AjaxParams();
		params.put("token",Data.getInfo().getData().getToken());
		params.put("spec_id",cart.getSpec_id());
		params.put("quantity",cart.getNum());
		
		
		
		FinalHttp fh=new FinalHttp();
		fh.post(Data.getAddCartUrl(),params,new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {
				JSONObject json;
				try {
					json = new JSONObject(t);
				
				
					hideDialog();
					CustomToast.makeText(ItemDetailActivity.this, t,true);
					CustomToast.makeText(ItemDetailActivity.this,cart.toString(),false);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				
					hideDialog();
			}
			
			
			
		});
		
		
	}
	
	
	
	/*public boolean isUserLogin()
	{
		
		MyApp app=(MyApp)getApplication();
		return app.isLogin();
		
	}*/
	
	//ChooseFormatDialog selectDialog;
	PopupWindow pop;
	PopupWindow option_pop;
	@Override
	public void onClick(View arg0) {
	
		switch(arg0.getId())
		{
		
		case R.id.item_detail_bottom_imv_gocart:
			//ItemDetailActivity.this.finish();
			
			Intent intent;
			if(Data.isLogin())
			{
				intent=new Intent(ItemDetailActivity.this,ItemDetailCartActivity.class);
				
			}else
			{
				intent=new Intent(ItemDetailActivity.this,LoginActivity.class);
			}
				//intent.putExtra("action","gocart");
			startActivity(intent);
			
			
			break;
		case R.id.item_detail_layout_add_cart:
		case R.id.item_detail_choose_format_btn_ok:
			if(buynum!=null)
			{
				cart.setNum(buynum.getText().toString());
			}else
			{
				cart.setNum("1");
			}
			if(Data.isLogin())
			{
				addToCart();
			}else
			{
				
				Toast.makeText(this,"请先登录",Toast.LENGTH_SHORT).show();
				intent=new Intent(ItemDetailActivity.this,LoginActivity.class);
			
				//intent.putExtra("action","gocart");
				startActivity(intent);
			}
			break;
		case R.id.item_detail_layout_description:
			
			Intent intent_des=new Intent(ItemDetailActivity.this,ItemDetailDescriptionActivity.class);
			intent_des.putExtra("description",goods.getDescription());
			startActivity(intent_des);
			break;
			
		case R.id.itemdetail_layout_title_option:
			if(null==option_pop)
			{
				View option_pop_contentView=inflater.inflate(R.layout.itemdetail_option_pop, null);
				option_pop=new PopupWindow(option_pop_contentView,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,true);
				option_pop.setBackgroundDrawable(getResources().getDrawable(R.drawable.jshop_menuleft_bk));
				
				option_pop.setFocusable(true);
				option_pop.setOutsideTouchable(true);
				
				
				option_pop.showAsDropDown(layout_option,-20, 0);
				//option_pop.showAsDropDown(layout_option);
			}else
			{
				if(option_pop.isShowing())
				{
					option_pop.dismiss();
				}else
				{
					option_pop.showAsDropDown(layout_option,-20, 0);
					//pop.showAtLocation(tv_format,Gravity.CENTER,0, 0);
					option_pop.update();
				}
				
			}
			
			break;
		case R.id.item_detail_layout_format:
			if(pop==null)
			{
				View contentView =getChooseFormatView();
				pop=new PopupWindow(contentView,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,true);
				pop.setBackgroundDrawable(getResources().getDrawable(R.drawable.jshop_menuleft_bk));
				pop.setFocusable(true);
				pop.setOutsideTouchable(true);
				pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
					
					@Override
					public void onDismiss() {
						tv_format.setText("规格:  "+cart.getSpec()+" x"+buynum.getText().toString());
					}
				});				
				pop.showAtLocation(tv_format,Gravity.CENTER,0, 0);
				pop.update();
			}else
			{
				if(pop.isShowing())
				{
					pop.dismiss();
				}else
				{
					pop.showAtLocation(tv_format,Gravity.CENTER,0, 0);
					pop.update();
				}
			}
			
			break;
		case R.id.item_detail_layout_shopper_phonenum:
			
			View view=inflater.inflate(R.layout.item_detail_call_shopowner_dialog,null);
			
			
			
			final AlertDialog dialog = new AlertDialog.Builder(ItemDetailActivity.this).create();
            dialog.show();
			Button btn_cancel=(Button)view.findViewById(R.id.item_detail_call_shopowner_btn_cancel);
			Button btn_ok=(Button)view.findViewById(R.id.item_detail_call_shopowner_btn_ok);
			TextView tv_title=(TextView)view.findViewById(R.id.item_detail_call_shopowner_dialog_tv_title);
			
			
			//tv_title.setText("sdfasdf");
			btn_cancel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					dialog.cancel();
					
				}
			});
			btn_ok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					//
					dialog.cancel();
				}
			});
			dialog.setContentView(view);
			//dialog.show();
			
		
			break;
		case R.id.item_detail_layout_loc:
			Intent intent_loc=new Intent(ItemDetailActivity.this,MapNaviActivity.class);
			intent_loc.putExtra("address",store_Data.getAddress());
			startActivity(intent_loc);
			break;
			
		case R.id.item_detail_layout_shop:
			//GoToStore(store_Data.getStore_id());
			Intent intent_shop=new Intent(ItemDetailActivity.this,ShopActivity.class);
			intent_shop.putExtra("store_id",store_Data.getStore_id());
			startActivity(intent_shop);
			
			break;
			
			
		case R.id.pop_add:
			
			int num;
			try{
				num=Integer.parseInt(buynum.getText().toString());
			}catch (NumberFormatException e) {
				e.printStackTrace();
				num=0;
			}
			
				buynum.setText((num+1)+"");
			
			break;
		case R.id.pop_reduce:
			
			int nume;
			try{
				nume=Integer.parseInt(buynum.getText().toString());
			}catch (NumberFormatException e) {
				e.printStackTrace();
				nume=0;
			}
			
			
			if(nume<=1)
			{
				Toast.makeText(this,"数量不能低于一件",Toast.LENGTH_SHORT).show();
			}else
			{
				buynum.setText((nume-1)+"");
			}
			
			break;
			
			
		}
		
		
		
		
		
	}
	
	
	
	
	
	
	
	public void GoToStore(String address)
	{
		
		
		((MyApp)getApplication()).setHandler(handler);
		
		// 初始化搜索模块，注册事件监听
				mSearch = GeoCoder.newInstance();
				mSearch.setOnGetGeoCodeResultListener(this);
				mSearch.geocode(new GeoCodeOption().city(
						"成都").address(
						address));
		
		
	}
	
	
	GeoCoder mSearch = null;
	
	
	LatLng  latlng_des;
	BDLocation loc_src;
	boolean flag=false;
	Handler handler=new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			
			if(latlng_des!=null&&loc_src!=null)
			{
				startNavi();
				
			}else
			{
				switch(msg.what)
				{
				case 0x12:
					
					latlng_des=(LatLng) msg.obj;
					break;
				default:
					
					loc_src=(BDLocation) msg.obj;
					
					break;	
				}
			}
		}
		
	};
	
	
	/**
	 * 开始导航
	 * 
	 * @param view
	 */
	public void startNavi() {
		
		if(!flag){
			Log.d("startNavi", "flag=true");
			flag=true;
				LatLng pt1 = new LatLng(loc_src.getLatitude(), loc_src.getLongitude());
				//LatLng pt2 = new LatLng(mLat2, mLon2);
				// 构建 导航参数
				NaviPara para = new NaviPara();
				para.startPoint = pt1;
				para.startName = "从这里开始";
				para.endPoint = latlng_des;
				para.endName = "到这里结束";
		
				try {
		
					BaiduMapNavigation.openBaiduMapNavi(para, this);
		
				} catch (BaiduMapAppNotSupportNaviException e) {
					e.printStackTrace();
					
					startWebNavi();
					Log.d("startWebNavi", "flag=true");
				}
		}
	}

	public void startWebNavi() {
		LatLng pt1 = new LatLng(loc_src.getLatitude(), loc_src.getLongitude());
		//LatLng pt2 = new LatLng(mLat2, mLon2);
		// 构建 导航参数
		//NaviPara para = new NaviPara();
		// 构建 导航参数
		NaviPara para = new NaviPara();
		para.startPoint = pt1;
		para.endPoint = latlng_des;
		BaiduMapNavigation.openWebBaiduMapNavi(para, this);
	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
		
			return;
		}
		Message msg=new Message();
		msg.obj=result.getLocation();
		msg.what=0x12;
		
		handler.handleMessage(msg);
		//result.getLocation()
		String strInfo = String.format("纬度：%f 经度：%f",
				result.getLocation().latitude, result.getLocation().longitude);
		
		
		
		
		
	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
		
	}
	
	
	
	
}
