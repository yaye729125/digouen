package com.gyh.digou.wode;

import java.util.List;

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

public class DindanxiangxiAdapter extends BaseAdapter {

	
	
	
	private Context mContext;
	private List<JSONObject> list;
	LayoutInflater inflater;
	public DindanxiangxiAdapter(Context mContext)
	{
		
		//System.out.println(list.get(0).toString());
		this.mContext=mContext;
		
		inflater=LayoutInflater.from(mContext);
	}
	
	
	
	public void setData(List<JSONObject> list)
	{
		if(list!=null)
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
		arg1=inflater.inflate(R.layout.dingdanxiangxi_sytle, null);
		
		TextView t2=(TextView) arg1.findViewById(R.id.xianxi);
		
		try {
			t2.setText(list.get(arg0).getString("goods_name"));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ImageView imageView=(ImageView) arg1.findViewById(R.id.tupian);
		
		try {
		String img=	list.get(arg0).getString("goods_image");
		
        FinalBitmap bitmap=FinalBitmap.create(mContext);
		
		  bitmap.display(imageView, img); 
		
	
		
		
		
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return arg1;
	}

}
