package com.gyh.digou.wode.shangjia;


import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gyh.digou.Data;
import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.gouwuche.MyCircleImageView;
import com.gyh.digou.itemdetail.shop.ShopActivity;
import com.gyh.digou.util.Tools;
import com.gyh.digou.wode.maijia.WodeGouLiangActivity;

public class ShangjiaShouyeActivity extends Activity{
	
	
	TextView textView1;
	TextView textView2;
	TextView textView3;
	MyCircleImageView view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		
	setContentView(R.layout.shangjiamoshi);
	
	
	textView1=(TextView) findViewById(R.id.shangjia_name);
	textView2=(TextView) findViewById(R.id.shangjia_huiyuan);
	textView3=(TextView) findViewById(R.id.shangjia_shuangdian);
	TextView textView4=(TextView) findViewById(R.id.shangjia_zijin);
	view=(MyCircleImageView) findViewById(R.id.shangjia_touxiang);
	//shangjizijinguanli=(Button) findViewById(R.id.shangjizijinguanli);
	
	
	initUserInfo();
	
	
	addData();
					
					
					
					
					
	GridView gridView=(GridView) findViewById(R.id.shangjiamoshi_gridview);
					
	gridView.setAdapter(new GridViewAdapter(this));			
					
					
	gridView.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			
			 Intent intent=new Intent();
			   
			 intent.setClass(ShangjiaShouyeActivity.this,list.get(arg2).IntentClass);
			 
			
			intent.putExtra("store_id",store_id);
			startActivity(intent);
			
		}
	});				

	ImageView caigou_button=(ImageView) findViewById(R.id.shangjia_caigou);
	caigou_button.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			finish();
			
		}
	});
	
	
	
	}
	
	
	
	
	
	private void addData() {
		GridItemObject obj=new GridItemObject("订单管理",R.drawable.dingdanguanli_image,ShangjiaDingdangunaliActivity.class);
		list.add(obj);
		obj=new  GridItemObject("店铺首页",R.drawable.dianpushouye_image,ShopActivity.class);
		list.add(obj);
		obj=new  GridItemObject("商品管理",R.drawable.shangpinguanli_image,Shangpinguanli.class);
		list.add(obj);
		obj=new  GridItemObject("地狗爽点",R.drawable.shuangdian_image,WodeGouLiangEnActivity.class);
		list.add(obj);
		obj=new  GridItemObject("资金管理",R.drawable.zijinguanli_image,Zijinxiangqing.class);
		list.add(obj);
		obj=new  GridItemObject("账户管理",R.drawable.zhanghuguanli_image,Zhanhuguanli.class);
		list.add(obj);
		
	}




	String store_id="";
	public void initUserInfo()
	{
		
		AjaxParams params = new AjaxParams();
		/*MyApp myApp = (MyApp) this.getApplication();
		String token = myApp.getInfo().getData().getToken();*/
		//MyApp app = (MyApp) ShangjiaShouyeActivity.this.getApplication();
		params.put("token", Data.info.getData().getToken());
		//params.put("pattern", "newpm");

		FinalHttp fh = new FinalHttp();
		fh.post(Tools.getBaseUrl() + "?app=member&act=api_user_info",
				params, new AjaxCallBack<String>() {

					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						Toast.makeText(ShangjiaShouyeActivity.this, strMsg,
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(String t) {
						 System.out.println("---80-------"+t+"-----80-----80------------");
						 
						try {
							
							
						JSONObject jsonObject=new JSONObject(t);
							 
						JSONObject jsonObject2= jsonObject.getJSONObject("data");
						String name=jsonObject2.getString("user_name");
						String huiyuan=	jsonObject2.getString("ugrade");
						String shuangdian=jsonObject2.getString("point");
						String image=	jsonObject2.getString("portrait");
						store_id=jsonObject2.getString("store_id");
						
						
						
						textView1.setText(name);
						textView2.setText("会员等级:"+huiyuan);
						textView3.setText("爽点:"+shuangdian);
						
						  FinalBitmap bitmap=FinalBitmap.create(ShangjiaShouyeActivity.this);
							
						  bitmap.display(view, image); 		
						
						
						
						
							 
						} catch (Exception e) {
							// TODO: handle exception
						}
		
						
						System.out.println("-=-========"+t);
						
						

						}

					

				});
	}
	
	class GridItemObject{
		
		int resId;
		String text;
		Class IntentClass;
		
		public GridItemObject(String text,int resId,Class IntentClass)
		{
			this.resId=resId;
			this.text=text;
			this.IntentClass=IntentClass;
		}
		
	}
	List<GridItemObject> list=new ArrayList<GridItemObject>();
	
	class GridViewAdapter extends BaseAdapter
	{

		
		LayoutInflater inflater;
		
		GridViewAdapter(Context mContext)
		{
			inflater=LayoutInflater.from(mContext);
		}
		
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			
			ViewHolder holder=null;
			if(arg1==null)
			{
				arg1=inflater.inflate(R.layout.shangjiamoshi_grid_item,null);
				holder=new ViewHolder();
				holder.imv=(ImageView) arg1.findViewById(R.id.shangjiamoshi_gridview_item_imv);
				
				holder.tv=(TextView) arg1.findViewById(R.id.shangjiamoshi_gridview_item_tv);
				arg1.setTag(holder);
			}else
			{
				holder=(ViewHolder) arg1.getTag();
			}
			
			holder.imv.setImageResource(list.get(arg0).resId);
			holder.tv.setText(list.get(arg0).text);
			return arg1;
		}
		
		class ViewHolder 
		{
			ImageView imv;
			TextView tv;
			
			
		}
		
	}
	
}
