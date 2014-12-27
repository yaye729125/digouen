package com.gyh.digou.wode.maijia;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gyh.digou.R;

public class DizhiAdapter extends BaseAdapter {

	
	
	
	private Context mContext;
	private List<JSONObject> list;
	LayoutInflater inflater;
	public DizhiAdapter(Context mContext)
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
		
		ViewHolder holder=null;
		if(arg1==null)
		{
			holder=new ViewHolder();
			arg1=inflater.inflate(R.layout.dizhi_styl, null);
			holder.dianhua=(TextView) arg1.findViewById(R.id.dianhua_textView2);
			holder.xingming=(TextView) arg1.findViewById(R.id.xingming_textView1);
			
			
			
			holder.youbian=(TextView) arg1.findViewById(R.id.youbian_textView3);
			
			holder.dizhi=(TextView) arg1.findViewById(R.id.dizhi_textView4);
			arg1.setTag(holder);
		}else
		{
			holder=(ViewHolder) arg1.getTag();
		}
		
		if(arg0<list.size())
		{
		JSONObject json=list.get(arg0);
		try {
			holder.xingming.setText(json.getString("consignee"));
		
		
		
		
		
			holder.dianhua.setText(json.getString("phone_mob"));
		
		
		
			holder.youbian.setText(json.getString("zipcode"));
		
		
		
			holder.dizhi.setText(json.getString("address"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		return arg1;
	}
	
	
	static class ViewHolder
	{
		
		TextView xingming;
		TextView dianhua;
		TextView youbian;
		TextView dizhi;
		//TextView xingming;
		
	}

}
