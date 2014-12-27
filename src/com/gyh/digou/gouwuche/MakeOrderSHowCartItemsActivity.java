package com.gyh.digou.gouwuche;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalBitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gyh.digou.R;

public class MakeOrderSHowCartItemsActivity extends Activity{

	
	List<JSONObject> data=new ArrayList<JSONObject>();
	List<JSONObject> realList=new ArrayList<JSONObject>();
	LayoutInflater inflater;
	FinalBitmap imageLoader;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.makeorder_showitems);
		inflater=getLayoutInflater();
		imageLoader=FinalBitmap.create(this);
		ListView listView=(ListView) findViewById(R.id.makeorder_cartitems_list);
		
		final ArrayList<String> cartInfo=getIntent().getStringArrayListExtra("cartInfo");
		
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
		listView.setAdapter(new MakeOrderListAdapter());
		
	}

	
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
		//return realList;
		
	}
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	}

	
	
	class  MakeOrderListAdapter extends BaseAdapter
	{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return realList==null?0:realList.size();
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
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			
			ViewHolder holder=null;
			if(arg1==null)
			{
				arg1=inflater.inflate(R.layout.makeorder_list_item,null);
				holder=new ViewHolder();
				holder.imv=(ImageView) arg1.findViewById(R.id.makeorder_list_item_imv);
				holder.tv_format=(TextView) arg1.findViewById(R.id.makeorder_list_item_tv_format);
				holder.tv_name=(TextView) arg1.findViewById(R.id.makeorder_list_item_tv_name);
				holder.tv_num=(TextView) arg1.findViewById(R.id.makeorder_list_item_tv_num);
				holder.tv_price=(TextView) arg1.findViewById(R.id.makeorder_list_item_tv_price);
				arg1.setTag(holder);
				
			}else
			{
				holder=(ViewHolder) arg1.getTag();
			}
			
			JSONObject json_w=realList.get(arg0);
			
			try {
			
				imageLoader.display(holder.imv,json_w.getString("goods_image"));
			
			
			//final String goods_id=json_w.getString("goods_id");
			final String goods_num=json_w.getString("quantity");
			holder.tv_num.setText(goods_num);	
			holder.tv_name.setText(json_w.getString("goods_name"));
			holder.tv_price.setText(json_w.getString("price"));
			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return arg1;
		}
	

		
	}
	static class ViewHolder
	{
		
		TextView tv_name;
		TextView tv_price;
		TextView tv_num;
		TextView tv_format;
		ImageView imv;
		
		
		
	}
}
