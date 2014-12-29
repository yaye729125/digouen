package com.gyh.digou.gouwuche;

import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyh.digou.Data;
import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.mytoast.CustomToast;
import com.gyh.digou.progerss.CustomProgressDialog;
import com.gyh.digou.util.Tools;

public class MakeOrderActivity extends Activity {

	
	ArrayList<JSONObject> data=new ArrayList<JSONObject>();
	LayoutInflater inflater;
	FinalBitmap imageLoader;
	TextView tv_address;
	
	
	boolean chooseAddressed=false;
	Handler mHander=new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		inflater=LayoutInflater.from(this);
		imageLoader=FinalBitmap.create(this);
		imageLoader.configLoadingImage(R.drawable.ic_launcher);
		//setContentView(R.layout.makeorder);
		View parent=inflater.inflate(R.layout.makeorder,null);
		Intent intent=getIntent();
		String allPrice=intent.getStringExtra("allPrice");
		final ArrayList<String> cartInfo=intent.getStringArrayListExtra("cartInfo");
		tv_address=(TextView) parent.findViewById(R.id.makeorder_tv_chooseaddress);
		parent.findViewById(R.id.makeorder_layout_chooseaddress).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent intent=new Intent(MakeOrderActivity.this,MakeOrderChooseAddressActivity.class);
				startActivityForResult(intent, 0x321);
				
				
				
				
			}
		});
		
		TextView tv=(TextView) parent.findViewById(R.id.makeorder_bottom_tv_content);
		tv.setText(allPrice);
		parent.findViewById(R.id.makeorder_bottom_btn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if(chooseAddressed)
				{
					makeorder();
				}else
				{
					Toast.makeText(MakeOrderActivity.this,"请选择收货地址",Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		layout_onlyone=(RelativeLayout) parent.findViewById(R.id.makeorder_layout_onlyoneitem);
		layout_hasmore=(RelativeLayout) parent.findViewById(R.id.makeorder_layout_hasmoreitem);
		layout_hasmore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				
				Intent intent=new Intent(MakeOrderActivity.this,MakeOrderSHowCartItemsActivity.class);
				
				intent.putStringArrayListExtra("cartInfo", cartInfo);
				startActivity(intent);
			
			}
		});
		if(cartInfo!=null)
		{
			for(int i=0;i<cartInfo.size();i++)
			{
				try {
					data.add(new JSONObject(cartInfo.get(i)));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		getRealData();
		if(realList.size()==1)
		{
			showOnlyOne();
		}else if(realList.size()<=3)
		{
			showHasMore(true);
		}else
		{
			showHasMore(false);
		}
		
		setContentView(parent);
	}
	
	
	 protected void makeorder() {
		  
		  showDialog();
		  final AjaxParams params=new AjaxParams();
		  params.put("token",Data.info.getData().getToken());
		  String store_id="";
		  try {
		  if(data.size()>1)
		  {
			  for(int j=0;j<data.size()-1;j++)
			  {
				 
				  
				  	Log.d("makeorder() ",data.get(j).toString() );
					store_id+=data.get(j).getJSONArray("goods").getJSONObject(0).getString("store_id")+",";
				
			  }
			  store_id+=data.get(data.size()-1).getJSONArray("goods").getJSONObject(0).getString("store_id");
		  }else
		  {
			 // Log.d("makeorder() else",realList.get(0).toString() );
				store_id+=realList.get(0).getString("store_id");
			
		  }
		  params.put("store_id",store_id);
		  params.put("postscript","圆通");
		  params.put("consignee",json_address.getString("consignee"));
		  params.put("region_id",1212+"");
		  params.put("address",json_address.getString("address"));
		  params.put("phone_mob",json_address.getString("phone_mob")+"");
		  params.put("phone_tel",json_address.getString("phone_tel")+"");
		  
		  
		  
		  FinalHttp fh=new FinalHttp();
		  fh.post(Data.getMakeOrderUrl(),params,new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {
				
				
				
				try {
					
					JSONObject result=new JSONObject(t);
					//boolean flag=false;
					if(result.getInt("ErrNum")==0)
					{
						//flag=true;
						dialog.setResultMessage(result.getString("ErrMsg"),R.id.mytoast_tv,true);
						
						
						mHander.postDelayed(new Runnable(){

							@Override
							public void run() {
								dialog.hideProgressDialog();
								
								
								Intent intent=new Intent(MakeOrderActivity.this,MakeOrderPayActivity.class);
								startActivity(intent);
							}
							
						}, 800);
						
						
					}else
					{
						
						dialog.setResultMessage(result.getString("ErrMsg"),R.id.mytoast_tv,false);
						
						dialog.hideProgressDialog(1000);
						
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				//hideDialog();
				Log.d("params",params.toString());
				Log.d("akeOrder", "t==="+t);
				
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
			
			}
			  
			  
			  
		});
		  
		  
		  } catch (JSONException e) {
				e.printStackTrace();
			}
		
	}
	
	CustomProgressDialog dialog;
	    
	 
	
	
	public void showDialog()
	{
		    	
		    	if(dialog==null)
		    	{
		    		dialog=CustomProgressDialog.createDialog(this, R.style.CustomProgressDialog,R.layout.mytoast);
		    		dialog.setMessage("正在提交订单",R.id.mytoast_tv);
		    		dialog.show();
		    	}else
		    	{
		    		if(!dialog.isShowing())
		    			dialog.show();
		    	}
		    	
	}
		    
		    
		    
		   
	public void showOnlyOne()
	{
		layout_onlyone.setVisibility(View.VISIBLE);
		layout_hasmore.setVisibility(View.GONE);
		ImageView imv_onlyone=(ImageView) layout_onlyone.findViewById(R.id.makeorder_onlyoneitem_imv);
		TextView tv_onlyone=(TextView) layout_onlyone.findViewById(R.id.makeorder_onlyoneitem_tv);
		try {
			imageLoader.display(imv_onlyone, realList.get(0).getString("goods_image"));
			tv_onlyone.setText(realList.get(0).getString("goods_name"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	public void showHasMore(boolean flag)
	{
		LinearLayout root=(LinearLayout) layout_hasmore.findViewById(R.id.makeorder_layout_item_root);
		if(flag)
		{
			
			for(int i=0;i<realList.size();i++)
			{
				ImageView imv=new ImageView(this);
				try {
					imageLoader.display(imv, realList.get(i).getString("goods_image"));
					LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(Tools.dip2px(MakeOrderActivity.this,50),Tools.dip2px(MakeOrderActivity.this,30));
					imv.setLayoutParams(params);
					imv.setScaleType(ScaleType.CENTER_CROP);
					root.addView(imv);
					//root.addView(LayoutParams.WRAP_CONTENT, 0, 1);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		}else
		{
			
			for(int i=0;i<3;i++)
			{
				ImageView imv=new ImageView(this);
				try {
					imageLoader.display(imv, realList.get(i).getString("goods_image"));
					
					//RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(this,);
					//imv.setLayoutParams()
					//root.addView(imv);
					//root.addv
					LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT,(float) 1.0);
					imv.setLayoutParams(params);
					root.addView(imv);
					//root.addView(LayoutParams.WRAP_CONTENT, 0, 1);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
	}
	
	RelativeLayout layout_onlyone;
	RelativeLayout layout_hasmore;
	ArrayList<JSONObject> realList=new ArrayList<JSONObject>();
	
	
	private void getRealData() {
		
		for(JSONObject json:data)
		{
			try {
				if(json.getBoolean("check"))
				{
					
					JSONArray arry=json.getJSONArray("goods");
					for(int j=0;j<arry.length();j++)
					{
						realList.add(arry.getJSONObject(j));
					}
					
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}

	
	JSONObject json_address;
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(resultCode!=RESULT_CANCELED)
		{
			switch(requestCode)
			{
			case 0x321:
				chooseAddressed=true;
				String address=data.getStringExtra("address");
				try {
					
					json_address=new JSONObject(address);
					StringBuilder sb =new StringBuilder();
					sb.append(json_address.getString("consignee"));
					sb.append("    ");
					sb.append(json_address.getString("phone_tel")+"\n");
					sb.append(json_address.getString("address"));
					
					tv_address.setText(sb.toString());
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
		
		
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
	protected void onRestart() {
		
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	
	
	
	
	
}
