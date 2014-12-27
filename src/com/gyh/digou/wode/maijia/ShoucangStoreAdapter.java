package com.gyh.digou.wode.maijia;

import java.util.List;

import net.tsz.afinal.FinalBitmap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyh.digou.R;

public class ShoucangStoreAdapter extends BaseAdapter {

	
	
	
	private Context mContext;
	private List<JSONObject> list;
	LayoutInflater inflater;
	public ShoucangStoreAdapter(Context mContext)
	{
		
		//System.out.println(list.get(0).toString());
		this.mContext=mContext;
		
		inflater=LayoutInflater.from(mContext);
	}
	
	public List<JSONObject> getData()
	{
		
		return list;
	}
	public void setData(List<JSONObject> list)
	{
		if(null!=list)
		{
			this.list=list;
			notifyDataSetChanged();
		}
		
		
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list==null?0:list.size();
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
		
		//System.out.println(list.get(arg0).toString());
		
		
		ViewHolder holder=null;
		
		if(arg1==null)
		{
			arg1=inflater.inflate(R.layout.shoucang_store_item, null);
			holder=new ViewHolder();
			holder.tv_storename=(TextView) arg1.findViewById(R.id.shoucang_goods_item_tv_name);
			holder.imv=(ImageView) arg1.findViewById(R.id.shoucang_goods_item_imv);
			holder.tv_address=(TextView) arg1.findViewById(R.id.shoucang_goods_item_tv_price);
			 
			 arg1.setTag(holder);
		}else
		{
			holder=(ViewHolder) arg1.getTag();
		}
		
		
		try {
			
			Log.d("shoucang",
					list.get(arg0).toString());
			holder.tv_storename.setText(list.get(arg0).getString("store_name"));
		
		
			holder.tv_address.setText(list.get(arg0).getString("address"));
		
	    
			String im=	 list.get(arg0).getString("store_logo");
			
			
			FinalBitmap bitmap=FinalBitmap.create(mContext);
			
			bitmap.display(holder.imv, im); 
			
		
			
			
			
			
			
			
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		 
		 
		
		
		
		return arg1;
	}
	
	static class ViewHolder
	{
		TextView tv_storename;
		TextView tv_address;
		ImageView imv;
		
	}

}
