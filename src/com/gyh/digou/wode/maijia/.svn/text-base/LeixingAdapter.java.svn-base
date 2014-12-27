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

public class LeixingAdapter extends BaseAdapter {

	
	
	
	private Context mContext;
	private List<JSONObject> list;
	LayoutInflater inflater;
	public LeixingAdapter(Context mContext,List<JSONObject> list)
	{
		
		//System.out.println(list.get(0).toString());
		this.mContext=mContext;
		this.list=list;
		inflater=LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
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
		arg1=inflater.inflate(R.layout.diqu_style, null);
		
		TextView t2=(TextView) arg1.findViewById(R.id.diqu_textView1);
		
		try {
			t2.setText(list.get(arg0).getString("grade_name"));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		return arg1;
	}

}
