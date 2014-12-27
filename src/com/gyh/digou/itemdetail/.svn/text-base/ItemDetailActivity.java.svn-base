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

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gyh.digou.R;
import com.gyh.digou.R.drawable;
import com.gyh.digou.R.id;
import com.gyh.digou.R.layout;
import com.gyh.digou.R.style;
import com.gyh.digou.bean.Cart;
import com.gyh.digou.bean.Goods;
import com.gyh.digou.bean.IImages;
import com.gyh.digou.bean.ItemDetailInfo;
import com.gyh.digou.bean.ShopOwner;
import com.gyh.digou.bean.Specs;
import com.gyh.digou.bean.Store_Data;
import com.gyh.digou.util.Tools;
import com.gyh.digou.view.AbOnItemClickListener;
import com.gyh.digou.view.AbSlidingPlayView;

public class ItemDetailActivity extends Activity implements OnClickListener{
	
	AbSlidingPlayView viewPager;
	LayoutInflater inflater;
	
	private ArrayList<View> allListView;
	
	TextView buynum;
	TextView tv_format;
	TextView tv_address;
	//ShopOwner shopOwner;
	Cart cart;
	//private int[] resId={R.drawable.one,R.drawable.two,R.drawable.three};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.item_detail);
		//get the info deliver by fragment
		Intent intent=getIntent();
		//String goods_id=intent.getStringExtra("goods_id");
		String goods_id=intent.getStringExtra("goods_id");
		
		//get the Inflater
		inflater=getLayoutInflater();
		
		
		//init views
		viewPager = (AbSlidingPlayView)findViewById(R.id.viewPager_menu);
		RelativeLayout layout_format=(RelativeLayout) findViewById(R.id.item_detail_layout_format);
		LinearLayout add_cart=(LinearLayout) findViewById(R.id.item_detail_layout_add_cart);
		
		
		RelativeLayout layout_description=(RelativeLayout) findViewById(R.id.item_detail_layout_description);
		
		final TextView tv_stock=(TextView) findViewById(R.id.item_detail_stock);
		final TextView tv_name=(TextView) findViewById(R.id.item_detail_name);
		final TextView tv_price=(TextView) findViewById(R.id.item_detail_price);
		final TextView tv_title=(TextView) findViewById(R.id.item_detail_description);
		final TextView tv_shopname=(TextView) findViewById(R.id.item_detail_shopname);
		//make the textview'text 7-10 red color; 
		TextView tv_itemdetail_yao=(TextView) findViewById(R.id.item_detail_tv_yao);
		
		SpannableStringBuilder style=new SpannableStringBuilder(tv_itemdetail_yao.getText().toString());   
	     //style.setSpan(new BackgroundColorSpan(Color.RED),start,end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);   
	    style.setSpan(new ForegroundColorSpan(Color.RED),6,10,Spannable.SPAN_EXCLUSIVE_INCLUSIVE); 
	    // style.setSpan(new For , start, end, flags)
	    tv_itemdetail_yao.setText(style);
		
	     cart=new Cart();
	    
	    layout_description.setOnClickListener(this);
		add_cart.setOnClickListener(this);
	    layout_format.setOnClickListener(this);
		
		System.out.println(goods_id);
		//ajax request  get detail info by using goods_id
		AjaxParams params3=new AjaxParams();
		params3.put("goods_id", goods_id);
		//params.p
		
		FinalHttp fh3=new FinalHttp();
		fh3.post(Tools.getBaseUrl()+"?app=goods&act=api_goods_detail",params3,new AjaxCallBack<String>()
				{

					@Override
					public void onLoading(long count, long current) {
						
						super.onLoading(count, current);
					}

					@Override
					public void onSuccess(String t) {
					
						System.out.println(t);
						Gson gson=new Gson();
						itemDetailInfo=gson.fromJson(t,ItemDetailInfo.class);
						goods=itemDetailInfo.getData().getGoods();
						initViewPager(goods.get_images());
						setText();
					
					}
					private void setText() {
						tv_price.setText("价格: ￥"+goods.getPrice());
						tv_name.setText(goods.getGoods_name());
						//tv_title.setText(goods.getDescription());
						store_Data=itemDetailInfo.getData().getStore_data();
						//itemDetailInfo.getData().getGoods().get_
						shopName=store_Data.getStore_name();
						String store_id=store_Data.getStore_id();
						//shopOwner.setShopOwner(shopName);
						//shopOwner.setShopOwnerId(store_id);
						cart.setShopOwnerId(store_id);
						tv_shopname.setText(shopName);
					}
			
				}
					
		);
		
		
		//shopOwner=new ShopOwner();
		
		
		
		
		
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
	
	//锟斤拷始锟斤拷锟斤拷品展示图片
	private void initViewPager(final List<IImages> list) {

		if (allListView != null) {
			allListView.clear();
			allListView = null;
		}
		allListView = new ArrayList<View>();

		FinalBitmap imageLoader=FinalBitmap.create(this);
		imageLoader.configLoadingImage(R.drawable.ic_launcher);
		
		for (int i = 0; i < list.size(); i++) {
			//锟斤拷锟斤拷ViewPager锟侥诧拷锟斤拷
			/*View view = inflater.inflate(R.layout.pic_item, null);
			ImageView imageView = (ImageView) view.findViewById(R.id.pic_item);*/
			ImageView imageView=new ImageView(this);
			//imageView.setImageResource(resId[i]);
			imageLoader.display(imageView, list.get(i).getImage_url());
			
			allListView.add(imageView);
		}
		
		
		viewPager.addViews(allListView);
		//viewPager.startPlay();
		//锟斤拷始锟街诧拷
		//viewPager.startPlay();
		viewPager.setOnItemClickListener(new AbOnItemClickListener() {
			@Override
			public void onClick(int position) {
				
				Toast.makeText(ItemDetailActivity.this,"item click",Toast.LENGTH_SHORT).show();
				
				Intent intent=new Intent(ItemDetailActivity.this,ShowItemBigPictureActivity.class);
				intent.putExtra("position", position);
				//intent.putStringArrayListExtra("srcList",(ArrayList<String>) srcList);
				intent.putParcelableArrayListExtra("imageList",(ArrayList<? extends Parcelable>) list);
				//intent.putParcelableArrayListExtra(name, value)
				//intent.putStringArrayListExtra(name, value)
				//intent.put
				startActivity(intent);
				//锟斤拷转锟斤拷锟斤拷锟斤拷锟斤拷锟�
				/*Intent intent = new Intent(getActivity(), BabyActivity.class);
				startActivity(intent);*/
			}
		});
	}
	
	
	ArrayList<ArrayList<String>> erList=new ArrayList<ArrayList<String>>();
	
	
	class ChooseFormatDialog extends AlertDialog implements View.OnClickListener
	{

		Context mContext;
		FinalDb fb;
		LayoutInflater inflater;
		protected ChooseFormatDialog(Context context) {
			super(context);
			this.mContext=context;
			inflater=LayoutInflater.from(context);
		}

		public ChooseFormatDialog(Context context, int theme) {
			super(context, theme);
			this.mContext=context;
			inflater=LayoutInflater.from(context);
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			//fb=FinalDb.create(ItemDetailActivity.this,"Digou");
			View choose_format=inflater.inflate(R.layout.item_detail_choose_format, null);
			RadioGroup group_format=(RadioGroup) choose_format.findViewById(R.id.item_detail_choose_format_root);
			
			TextView buynum_add,buynum_del;
			
			Button btn_ok;
			btn_ok=(Button) choose_format.findViewById(R.id.item_detail_choose_format_btn_ok);
			buynum_add=(TextView) choose_format.findViewById(R.id.pop_add);
			buynum_del=(TextView) choose_format.findViewById(R.id.pop_reduce);
			buynum=(TextView) choose_format.findViewById(R.id.pop_num);
			buynum.setOnClickListener(this);
			buynum_add.setOnClickListener(this);
			btn_ok.setOnClickListener(this);
			buynum_del.setOnClickListener(this);
			
			Resources res=getResources();
			for(final Specs specs:goods.get_specs())
			{
				
				RadioButton radioButton1=new RadioButton(ItemDetailActivity.this);
				
				RadioGroup.LayoutParams params=new RadioGroup.LayoutParams(300, ViewGroup.LayoutParams.WRAP_CONTENT);
				params.setMargins(0, 15, 0, 0);
				radioButton1.setLayoutParams(params);
				
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
							//cart.setSpec(sb.toString());
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
			
			setContentView(choose_format);
			
			
		}

		@Override
		public void onClick(View arg0) {
			
			switch(arg0.getId())
			{
			case R.id.item_detail_choose_format_btn_ok:
				cart.setNum(buynum.getText().toString());
				this.dismiss();
				break;
			case R.id.pop_num:
				
				/*AlertDialog dialog=	new AlertDialog.Builder(mContext,R.style.dialog).create();
				dialog.setView(inflater.inflate(R.layout.cart_item,null));
				dialog.setCanceledOnTouchOutside(true);
				dialog.show();*/
				ChooseItemNumDialog dialog=new ChooseItemNumDialog(mContext,R.style.basedialog);
				dialog.show();
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
					Toast.makeText(mContext,"数量不能低于一件",Toast.LENGTH_SHORT).show();
				}else
				{
					buynum.setText((nume-1)+"");
				}
				
				break;
			}
			
			
			
		}
		
		
		
		
	}
	
	
	class ChooseItemNumDialog extends AlertDialog
	{

		protected ChooseItemNumDialog(Context context) {
			super(context);
		
		}

		public ChooseItemNumDialog(Context context, boolean cancelable,
				OnCancelListener cancelListener) {
			super(context, cancelable, cancelListener);
			
		}

		public ChooseItemNumDialog(Context context, int theme) {
			super(context, theme);
			
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			
			setContentView(R.layout.cart_item);
			
			
			
			
		}
		
		
		
		
		
		
	}


	public void addToCart()
	{
		
		
		new Thread(new Runnable()
		{

			@Override
			public void run() {
				HttpPost httpRequest = new HttpPost(Tools.getBaseUrl()+"?app=cart&act=api_add");
				
				
				List<NameValuePair> params2 = new ArrayList<NameValuePair>();
				params2.add(new BasicNameValuePair("token",Tools.getToken()));
				params2.add(new BasicNameValuePair("spec_id",cart.getSpec_id()));
				params2.add(new BasicNameValuePair("quantity",cart.getNum()));
				//BasicNameValuePair params2=new BasicNameValuePair();
				try {
					httpRequest.setEntity(new UrlEncodedFormEntity(params2,HTTP.UTF_8));
					
					HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
					
					//httpResponse.
					HttpEntity entity=httpResponse.getEntity();
					final String result = EntityUtils.toString(entity,"utf-8");
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							
							
							Toast.makeText(ItemDetailActivity.this,result,Toast.LENGTH_SHORT).show();
							
						}
					});
				}catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		}).start();
		
		
		
		
	}
	
	
	ChooseFormatDialog selectDialog;
	@Override
	public void onClick(View arg0) {
	
		switch(arg0.getId())
		{
		
		
		case R.id.item_detail_layout_add_cart:
			
			addToCart();
			break;
		case R.id.item_detail_layout_description:
			
			Intent intent=new Intent(ItemDetailActivity.this,ItemDetailDescriptionActivity.class);
			intent.putExtra("description",goods.getDescription());
			startActivity(intent);
			break;
			
			
		case R.id.item_detail_layout_format:
			if(selectDialog==null)
			{
				selectDialog = new ChooseFormatDialog(ItemDetailActivity.this,R.style.dialog);//锟斤拷锟斤拷Dialog锟斤拷锟斤拷锟斤拷锟斤拷式锟斤拷锟斤拷
				
				
				Window win = selectDialog.getWindow();
				win.setWindowAnimations(R.style.dialoganim);
			}
			selectDialog.show();
			
			break;
			
			
		}
		
		
		
		
	}
	
	
	
	
	
	
	
}
