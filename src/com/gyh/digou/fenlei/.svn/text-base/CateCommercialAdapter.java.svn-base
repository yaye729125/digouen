package com.gyh.digou.fenlei;

import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;

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


public class CateCommercialAdapter extends BaseAdapter {
	//private Context mContext;
	private ArrayList<JSONObject> jsonList=new ArrayList<JSONObject>();
	private LayoutInflater inflater;
	FinalBitmap imageLoader;
	public CateCommercialAdapter(Context mContext)
	{
		inflater=LayoutInflater.from(mContext);
		//this.mContext=mContext;
		imageLoader=FinalBitmap.create(mContext);
		imageLoader.configLoadingImage(R.drawable.ic_launcher);
		imageLoader.configLoadfailImage(R.drawable.ic_launcher);
	}
	
	public void setData(ArrayList<JSONObject> jsonList)
	{
		if(jsonList!=null)
		{
			this.jsonList=(ArrayList<JSONObject>)jsonList.clone();
			notifyDataSetChanged();
		}
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return jsonList==null?0:jsonList.size();
	}

	@Override
	public Object getItem(int arg0) {
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
			holder=new ViewHolder();
			arg1=inflater.inflate(R.layout.list_item,null);
			holder.image=(ImageView) arg1.findViewById(R.id.pictureName);
			holder.tv_title=(TextView) arg1.findViewById(R.id.shangPinMingXi);
			arg1.setTag(holder);
		}else
		{
			holder=(ViewHolder) arg1.getTag();
		}
		
		
		String url=null,goods_name=null;
		JSONObject jsonObject=jsonList.get(arg0);
		try {
			
			url =jsonObject.getString("default_image");
			goods_name=jsonObject.getString("goods_name");
		} catch (JSONException e) {
		
			e.printStackTrace();
		}
		holder.tv_title.setText(goods_name);
		imageLoader.display(holder.image,url);
		
		return arg1;
	}

	static class ViewHolder{
	ImageView image;
	TextView tv_title;
		
		
	}
	
}
