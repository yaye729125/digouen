package com.gyh.digou.wode.shangjia.commermana.adapter;

import java.util.LinkedList;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.gyh.digou.R;

public class ImageAdapter extends BaseAdapter
{

	LayoutInflater inflater;
	LinkedList<String> goods_file_src;
	FinalBitmap imageLoader;
	public ImageAdapter(Context mContext)
	{
		inflater=LayoutInflater.from(mContext);
		imageLoader=FinalBitmap.create(mContext);
		
	}
	
	public void setData(LinkedList<String> goods_file_src)
	{
		if(goods_file_src!=null)
		{
			this.goods_file_src=goods_file_src;
			notifyDataSetChanged();
		}
	}
	
	public LinkedList<String> getData()
	{
		return goods_file_src;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return null==goods_file_src?0:goods_file_src.size();
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
		ImageViewHolder holder=null;
		if(arg1==null)	{
			
			
			holder=new ImageViewHolder();
			arg1=inflater.inflate(R.layout.image_grid_item,null);
			holder.imv=(ImageView) arg1.findViewById(R.id.image_grid_item_imv);
			
			arg1.setTag(holder);
				
				
				
			}else
			{
				holder=(ImageViewHolder) arg1.getTag();
				
			}
		
		
			if(arg0<goods_file_src.size()-1)
			{
				imageLoader.display(holder.imv,goods_file_src.get(arg0));
			}else
			{
				holder.imv.setImageResource(R.drawable.addgoods);
			}
			
		
		return arg1;
	}
	
	static class ImageViewHolder 
	{
		ImageView imv;
	}
	
	
}

