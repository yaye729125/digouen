package com.gyh.digou.faxian;

import net.tsz.afinal.FinalBitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyh.digou.R;

public class FaxianListBaseAdapter extends BaseAdapter {

	LayoutInflater inflater;
	FinalBitmap imageLoader;
	JSONArray list;
	public FaxianListBaseAdapter(Context mContext)
	{
		
		
		inflater=LayoutInflater.from(mContext);
		
		
		imageLoader=FinalBitmap.create(mContext);  
	    imageLoader.configLoadingImage(R.drawable.item_loading);
	    imageLoader.configMemoryCachePercent(30);
	}
	
	public void setData(JSONArray list)
	{
		if(list!=null)
		{
			this.list=list;
			notifyDataSetChanged();
		}
	}
	public  JSONArray getData()
	{
		
		return list;
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list==null?0:list.length();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		ViewHolder holder=null;
		if(arg1==null)
		{
		
			arg1=inflater.inflate(R.layout.faxian_list_item,null);
			holder=new ViewHolder();
			holder.tv_distance=(TextView) arg1.findViewById(R.id.faxian_list_item_distance);
			holder.tv_storename=(TextView) arg1.findViewById(R.id.faxian_storename);
			holder.tv_loc=(TextView) arg1.findViewById(R.id.faxian_list_item_loc);
			holder.imv_storelogo=(ImageView) arg1.findViewById(R.id.faxian_list_item_storelogo);
		}
		
		try {
			JSONObject json=list.getJSONObject(arg0);
			imageLoader.display(holder.imv_storelogo,json.getString("store_logo"));
			holder.tv_storename.setText(json.getString("store_name"));
			holder.tv_loc.setText(json.getString("address"));
			holder.tv_distance.setText(getDistance(json.getString("distance")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return arg1;
	}

	public String getDistance(String distance)
	{
		String result="";
		try{
			double dis=Double.parseDouble(distance);
			if(dis<1000)
			{
				result+=dis+"m";
			}else
			{
				result+=dis/1000+"km";
			}
			
			
		}catch(Exception e)
		{
			
		}
		return result;
	}
	static class ViewHolder
	{
		TextView tv_storename;
		TextView tv_distance;
		TextView tv_loc;
		ImageView imv_storelogo;
		
	}
	
}
