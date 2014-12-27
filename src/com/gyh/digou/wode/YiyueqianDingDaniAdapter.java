package com.gyh.digou.wode;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gyh.digou.R;
import com.gyh.digou.fenlei.LeftViewAdapter.ViewHolder;

public class YiyueqianDingDaniAdapter extends BaseAdapter {

	
	private Context mContext;
	private List<JSONObject> list;
	LayoutInflater inflater;
	
	//FinalBitmap imageLoader;
	

	public YiyueqianDingDaniAdapter(Context mContext, List<JSONObject> list) {

		System.out.println("YiyueqianDingDaniAdapter cons......."+list.get(0).toString());
		this.mContext = mContext;
		this.list = list;
		inflater = LayoutInflater.from(mContext);
		Log.d("list.size==",this.list.size()+"");
		//imageLoader=FinalBitmap.create(mContext);
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

		arg1 = inflater.inflate(R.layout.maijiadingdan_styl, null);
		Log.d("YiyueqianDingDaniAdapter", "getView===="+list.get(arg0).toString());
		//Log.d("YiyueqianDingDaniAdapter", "getView===="+list.get(arg0).toString());
		
		/*try {
			
			ViewHolder viewHolder = null;

			if (arg1 == null) {
				viewHolder = new ViewHolder();

				arg1 = inflater.inflate(R.layout.maijiadingdan_styl, null);
				viewHolder.textView1 = (TextView) arg1
						.findViewById(R.id.dingdanhao_text);
				viewHolder.textView2 = (TextView) arg1
						.findViewById(R.id.dingdanjine_text);
				viewHolder.textView3 = (TextView) arg1
						.findViewById(R.id.xiadanshijian_text);
				viewHolder.textView4 = (TextView) arg1
						.findViewById(R.id.dingdanzhuangtai_text);
				viewHolder.textView5 = (TextView) arg1.findViewById(R.id.butt);
				
				viewHolder.gallery=(Gallery) arg1.findViewById(R.id.maijiadingan_styl_gallery);
				
				
				
				arg1.setTag(viewHolder);

			} else {
				viewHolder = (ViewHolder) arg1.getTag();
			}

			viewHolder.textView1.setText(list.get(arg0).getString("order_sn"));

			viewHolder.textView2.setText(list.get(arg0).getString(
					"order_amount"));

			viewHolder.textView3.setText(list.get(arg0).getString("add_time"));

			viewHolder.textView4.setText(list.get(arg0)
					.getString("status_name"));

		
			JSONObject json_item=list.get(arg0);
			
			
			System.out.println("getView..."+json_item.toString());
			String	sta = json_item.getString("status");
              //  Toast.makeText(mContext, sta, 0).show();
			if (sta.equals("11")) {
				
				viewHolder.textView5.setText("付款");

			}
			if (sta.equals("30")) {
				viewHolder.textView5.setText("确认收货");
			}
			if (sta.equals("0")) {
				viewHolder.textView5.setText("关闭");
			}
			if (sta.equals("40")) {
				viewHolder.textView5.setText("成功");
			}
			if (sta.equals("20")) {
				viewHolder.textView5.setText("等待卖家发货");

			}

			

			viewHolder.gallery.setAdapter(new GalleryAdapter(json_item.getJSONObject("order_goods"),mContext));

			

		} catch (Exception e) {

			e.printStackTrace();
		}*/

		return arg1;

	}

	public class ViewHolder {
		public TextView textView1;
		public TextView textView2;
		public TextView textView3;
		public TextView textView4;
		public TextView textView5;
		//public ImageView po;
		//public FinalBitmap fi;
		
		
		Gallery gallery;

	}
	
	
	
	
}
