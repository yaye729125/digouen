package com.gyh.digou.shouye.search;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.gyh.digou.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchAdapter extends BaseAdapter {

	
	LayoutInflater inflater;
	public SearchAdapter(Context mContext)
	{
		inflater=LayoutInflater.from(mContext);
	
	}
	List<JSONObject> data;
	public void setData(List<JSONObject> data)
	{
		if(data!=null)
		{
			this.data=data;
			notifyDataSetChanged();
		}
	}
	@Override
	public int getCount() {
		
		return data==null?0:data.size();
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
		if(arg1==null){
			arg1=inflater.inflate(R.layout.shouye_search_list_item, null);
			holder=new ViewHolder();
			holder.tv_result=(TextView) arg1.findViewById(R.id.shouye_search_list_item_tv);
			arg1.setTag(holder);
		}else
		{
			holder=(ViewHolder) arg1.getTag();
		}
		
		try {
			holder.tv_result.setText(data.get(arg0).getString("goods_name"));
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		
		return arg1;
	}

	
	static class ViewHolder{
		TextView tv_result;
	}
}
