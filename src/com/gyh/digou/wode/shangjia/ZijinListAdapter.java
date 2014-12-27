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

public class ZijinListAdapter extends BaseAdapter {

	
	private Context mContext;
	private List<JSONObject> list;
	LayoutInflater inflater;
	
	

	public ZijinListAdapter(Context mContext, List<JSONObject> list) {

		//System.out.println(list.get(0).toString());
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
			
			

//			ViewHolder viewHolder = null;
//
//			if (arg1 == null) {
//				viewHolder = new ViewHolder();

				arg1 = inflater.inflate(R.layout.zijinlist_style, null);
			TextView	textView1 = (TextView) arg1
						.findViewById(R.id.jine_1);
			TextView	textView2 = (TextView) arg1
						.findViewById(R.id.time_1);
			TextView	textView3 = (TextView) arg1
					.findViewById(R.id.zhuantai_1);
			
			
//
//			} else {
//				viewHolder = (ViewHolder) arg1.getTag();
//			}

			textView1.setText("提现金额:"+list.get(arg0).getString("funds"));
			          System.out.println(list.get(arg0).getString("funds")+"-0-0-0-0-0----0");

		     textView2.setText(list.get(arg0).getString(
					"time"));
		     
		   String num=  list.get(arg0).getString("is_over");
		     System.out.println("0-00-0-0"+num);
		   if(num.endsWith("0")){
		   
		     textView3.setText("处理中");
		   }
		   if(num.endsWith("1")){
			   
			   textView3.setText("提现成功");
		   }
	
		
			

			

		} catch (JSONException e) {

			e.printStackTrace();
		}

		return arg1;

	}

	public class ViewHolder {
		public TextView textView1;
		public TextView textView2;
		
		

	}
}