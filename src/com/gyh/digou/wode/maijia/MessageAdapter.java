package com.gyh.digou.wode.maijia;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class MessageAdapter extends BaseAdapter {

	
	
	
	private Context mContext;
	private List<JSONObject> list;
	LayoutInflater inflater;
	
	
	
	public void clear()
	{
		
		if(null!=list)
		{
			list.clear();
			notifyDataSetChanged();
		}
		
	}
	public void setData(List<JSONObject> list)
	{
		if(list!=null)
		{
			this.list=list;
			notifyDataSetChanged();
		}
	}
	public MessageAdapter(Context mContext)
	{
		
		//System.out.println(list.get(0).toString());
		this.mContext=mContext;
		inflater=LayoutInflater.from(mContext);
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
			
			arg1=inflater.inflate(R.layout.xiaoxi_style, null);
			holder=new ViewHolder();
			holder.tv_time=(TextView) arg1.findViewById(R.id.shijian_text);
			holder.tv_content=(TextView) arg1.findViewById(R.id.neirong_text);
			arg1.setTag(holder);
		}else
		{
			holder=(ViewHolder) arg1.getTag();
		}
		
		
		try {
			holder.tv_time.setText(formatDate(list.get(arg0).getString("add_time")));
		
		
		
		
		
	
			holder.tv_content.setText(list.get(arg0).getString("content"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return arg1;
	}
	
	
	static class ViewHolder
	{
		
		TextView tv_time;
		TextView tv_content;
		
		
	}
	
	public String formatDate(String milis)
	{
		
		
		///DateFormat.getda
		
		
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		
		return format.format(new Date(Long.parseLong(milis)));
		/*Date date=new Date(Long.parseLong(milis));
		
		return java.text.DateFormat.getInstance(java.text.DateFormat.SHORT).format(date);*/
	}

}
