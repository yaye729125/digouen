package com.gyh.digou.wode.shangjia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalBitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gyh.digou.R;
import com.gyh.digou.fenlei.LeftViewAdapter.ViewHolder;

public class ShangpinAdapter extends BaseAdapter {

	
	private Context mContext;
	private List<JSONObject> list;
	LayoutInflater inflater;
	
	

	public ShangpinAdapter(Context mContext, List<JSONObject> list) {

		System.out.println(list.get(0).toString());
		this.mContext = mContext;
		this.list = list;
		inflater = LayoutInflater.from(mContext);
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

		try {
			
			

			ViewHolder viewHolder = null;

			if (arg1 == null) {
				viewHolder = new ViewHolder();

				arg1 = inflater.inflate(R.layout.shangpin_style, null);
				viewHolder.textView1 = (TextView) arg1
						.findViewById(R.id.shangpin_miaoshu);
				viewHolder.textView2 = (TextView) arg1
						.findViewById(R.id.shangpin_jiage);
			
				viewHolder.po = (ImageView) arg1
						.findViewById(R.id.shangpin_image);
				viewHolder.fi=FinalBitmap.create(mContext);
				arg1.setTag(viewHolder);

			} else {
				viewHolder = (ViewHolder) arg1.getTag();
			}

			viewHolder.textView1.setText(list.get(arg0).getString("goods_name"));

			viewHolder.textView2.setText(list.get(arg0).getString(
					"price"));

		
		String uu=	list.get(arg0).getString("default_image");
		
				
				viewHolder.fi.display(viewHolder.po, uu);
		    
			

			

			

		} catch (JSONException e) {

			e.printStackTrace();
		}

		return arg1;

	}

	public class ViewHolder {
		public TextView textView1;
		public TextView textView2;
		
		public ImageView po;
		public FinalBitmap fi;

	}
}