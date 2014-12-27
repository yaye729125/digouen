package com.gyh.digou.wode;

import java.util.ArrayList;
import java.util.Iterator;

import net.tsz.afinal.FinalBitmap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;


class GalleryAdapter extends BaseAdapter
{
	Context mContext;
	FinalBitmap imageLoader;
	ArrayList<JSONObject> warrlist = new ArrayList<JSONObject>();
	public GalleryAdapter(JSONObject jsonObject,Context mContext)
	{
		
		
		this.mContext=mContext;
		imageLoader=FinalBitmap.create(mContext);
		/*String name = list.get(arg0).getString("order_goods");

		JSONObject jsonObject = new JSONObject(name);*/
		try {
		
		warrlist.clear();
		Iterator<String> it = jsonObject.keys();
		while (it.hasNext()) {
			String key = it.next();
			JSONObject object;
			
				object = jsonObject.getJSONObject(key);
			
			warrlist.add(object);

		}

		// String hh= warrlist.get(arg0).getString("goods_name");
		/*JSONArray array = new JSONArray(warrlist);
		ArrayList<JSONObject> ll = new ArrayList<JSONObject>();
		for (int x = 0; x < array.length(); x++) {
			JSONObject jsonObject2 = array.getJSONObject(x);
			
			
		String	nnn = jsonObject2.getString("goods_name");
		String	uu = jsonObject2.getString("goods_image");
			ll.add(jsonObject2);
	
			
			
			
	    
		}*/
		
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return warrlist.size()>3?3:warrlist.size();
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
		
		GalleryViewHolder galleryholder=null;
		
		if(arg1==null)
		{
			galleryholder=new GalleryViewHolder();
			ImageView imv=new ImageView(mContext);
			imv.setLayoutParams(new Gallery.LayoutParams(150, 100));
			imv.setPadding(10, 10, 10, 10);
			imv.setScaleType(ScaleType.CENTER_CROP);
			arg1=galleryholder.imv=imv;
			arg1.setTag(galleryholder);
			
		}else
		{
			galleryholder=(GalleryViewHolder) arg1.getTag();
		}
		
		try {
			imageLoader.display(galleryholder.imv,warrlist.get(arg0).getString("goods_image"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arg1;
	}
	
	static class GalleryViewHolder
	{
		ImageView imv;
		
		
	}
	
	
}

