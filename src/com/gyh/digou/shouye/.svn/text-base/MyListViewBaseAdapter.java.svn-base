package com.gyh.digou.shouye;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalBitmap;

import android.view.LayoutInflater;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.gyh.digou.R;
import com.gyh.digou.imageswitcher.shangpinxinxi.ShangPinInfosItem;

public class MyListViewBaseAdapter extends BaseAdapter{
	private Context myContext;
	
	JSONArray list;
	private LayoutInflater inflater;
	FinalBitmap imageLoader;
	
	
	public void setData(JSONArray list)
	{
		if(list!=null)
		{
			this.list=list;
			notifyDataSetChanged();
		}
	}
	public MyListViewBaseAdapter(Context mContext)
	{
		this.list=list;
		this.myContext=mContext;
		inflater=LayoutInflater.from(myContext);
		
		
		imageLoader=FinalBitmap.create(mContext);  
	    imageLoader.configLoadingImage(R.drawable.ic_launcher);
	    imageLoader.configMemoryCachePercent(30);
	}
	
	@Override
	public int getCount() {
		return list.length();
	}

	

	
	@Override
	public long getItemId(int position) {
		return position;
	}
	

	
	
	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
	

		ViewHolder viewHolder ;
		if(null==convertView){
			viewHolder = new ViewHolder();
		
			convertView = inflater.inflate(R.layout.list_item, null);
			
		
			viewHolder.pictureName = (ImageView)convertView.findViewById(R.id.pictureName);
			viewHolder.shangPinMingXi = (TextView)convertView.findViewById(R.id.shangPinMingXi);
			viewHolder.shangPinNewPrice = (TextView)convertView.findViewById(R.id.shangPinNewPrice);
			viewHolder.shangPinOldPrice = (TextView)convertView.findViewById(R.id.shangPinOldPrice);
			
			
			convertView.setTag(viewHolder);
		}else{
			
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		try {
			JSONObject json=list.getJSONObject(position);
		
		//Bitmap image;
		
			//viewHolder.pictureName.setImageBitmap(imageLoader.getBitmapFromMemoryCache(map.get("default_image")));
			
				imageLoader.display(viewHolder.pictureName,json.getString("default_image"));
			
				viewHolder.shangPinMingXi.setText(json.getString("goods_name"));
			} catch (JSONException e) {
				
				e.printStackTrace();
			}
			return convertView;
		}
     
	
	
	private void initDate(){
		for (int i = 0; i < 10; i++) {
			ShangPinInfosItem shangPinInfosItem = new ShangPinInfosItem();
			
		}
	}
	
	
	static class ViewHolder{
		ImageView pictureName;
		TextView shangPinMingXi;
		TextView shangPinNewPrice;
		TextView shangPinOldPrice;
		Button yaoYiYao;
	}


	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}
}
