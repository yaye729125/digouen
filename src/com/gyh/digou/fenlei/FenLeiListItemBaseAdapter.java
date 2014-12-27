package com.gyh.digou.fenlei;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gyh.digou.R;
import com.gyh.digou.bean.GoodsCategoryInfo;


public class FenLeiListItemBaseAdapter extends BaseAdapter{
	@SuppressWarnings("unused")
	private Context mContext;
	private LayoutInflater inflater = null;
	GoodsCategoryInfo info;
	int cur_position;
	public FenLeiListItemBaseAdapter() {}
	public FenLeiListItemBaseAdapter(Context mContext,GoodsCategoryInfo info){
		this.mContext = mContext;
		this.info=info;
		inflater = LayoutInflater.from(mContext);
		
		System.out.println("mContext=.....======="+mContext + inflater);
	}
	public void setPosition(int position)
	{
		this.cur_position=position;
		
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return info.getData().size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return info.getData().get(position).getChildren().get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	   	ViewHolder viewHolder = new ViewHolder();
		if (null == convertView) {
	   		convertView = inflater.inflate(R.layout.fenlei_nianlingjieduan_expanded_parentview, null);

			System.out.println("convertView============="+convertView);
	   		viewHolder.tv = (TextView) convertView.findViewById(R.id.fenlei_nianlingjieduan_checkedtv);
	   		convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		if(position==cur_position)
		{
			convertView.setBackgroundColor(0x89890000);
			//convertView.setBackgroundResource(R.drawable.back);
			viewHolder.tv.setTextColor(0x88292929);
		}else
		{
			convertView.setBackgroundColor(0x00ffffff);
			//convertView.setBackgroundResource(R.drawable.ic_launcher);
			viewHolder.tv.setTextColor(0x56292929);
		}
		
		viewHolder.tv.setText(info.getData().get(position).getCate_name());
		
		return convertView;
	}
	
	class ViewHolder{
		TextView tv;
	}
}
