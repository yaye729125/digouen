package com.gyh.digou.wode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.tsz.afinal.FinalBitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gyh.digou.R;
import com.gyh.digou.util.Tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class DingdanAdapter extends BaseAdapter{

	LayoutInflater inflater;
	JSONArray jsonArray;
	//List<JSONObject> json_list;
	
	Context mContext;
	FinalBitmap imageLoader;
	
	Resources res;
	
	@SuppressLint("UseSparseArrays")
	HashMap<Integer,List<JSONObject>> mapList=new HashMap<Integer, List<JSONObject>>();
	public DingdanAdapter(Context mContext)
	{
		inflater=LayoutInflater.from(mContext);
		this.mContext=mContext;
		imageLoader=FinalBitmap.create(mContext);
		res=mContext.getResources();
	}
	
	
	public void setData(JSONArray array)
	{
		if(array!=null)
		{
			
			this.jsonArray=array;
			notifyDataSetChanged();
		}
		
	}
	
	public JSONArray getData()
	{
		
		
		return jsonArray;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return jsonArray==null?0:jsonArray.length();
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
		JSONObject json_item=(JSONObject) jsonArray.get(arg0);
	
			
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
				viewHolder.imv_0=(ImageView) arg1.findViewById(R.id.maijiadingdan_imv_one);
				//viewHolder.imv_1=(ImageView) arg1.findViewById(R.id.maijiadingdan_imv_two);
				//viewHolder.imv_2=(ImageView) arg1.findViewById(R.id.maijiadingdan_imv_three);
				//viewHolder.gallery= (LinearLayout) arg1.findViewById(R.id.maijiadingdan_style_image_root);
				
				
				
				arg1.setTag(viewHolder);

			} else {
				viewHolder = (ViewHolder) arg1.getTag();
			}

			viewHolder.textView1.setText(json_item.getString("order_sn"));

			viewHolder.textView2.setText(json_item.getString(
					"order_amount"));

			viewHolder.textView3.setText(Tools.getAddTime(json_item.getString("add_time")));

			viewHolder.textView4.setText(json_item
					.getString("status_name"));

		
			
			
			
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
			
			//viewHolder.gallery.removeAllViews();

			
			if(null==viewHolder.imv_0.getTag())
			{
				showImage(viewHolder,json_item.getJSONObject("order_goods"));
			}else
			{
				imageLoader.display(viewHolder.imv_0,(String)viewHolder.imv_0.getTag());
			}
			//GalleryAdapter adapter=new GalleryAdapter(json_item.getJSONObject("order_goods"),mContext);
			//viewHolder.gallery.setAdapter(adapter);
			//viewHolder.gallery.setNumColumns(adapter.getCount());
			

		} catch (Exception e) {

			e.printStackTrace();
		}

		//arg1 = inflater.inflate(R.layout.maijiadingdan_styl, null);
		//Log.d("YiyueqianDingDaniAdapter", "getView===="+json_list.get(arg0).toString());
		return arg1;
	}
	
	List<JSONObject> warrlist = new ArrayList<JSONObject>();
	private void showImage(ViewHolder viewHolder,JSONObject json) {
		
		
		
		try {
			
			warrlist.clear();
			Iterator<String> it = json.keys();
			while (it.hasNext()) {
				String key = it.next();
				JSONObject object;
				
					object = json.getJSONObject(key);
				
				warrlist.add(object);

			}
		
			//viewHolder.imv_one
		
		int len=0;
		if(warrlist.size()>3)
		{
			len=3;
		}else
		{
			len=warrlist.size();
		}
		
		for(int i=0;i<len;i++)
		{
			
			
			
			
			if(i==0)
			{
				imageLoader.display(viewHolder.imv_0, warrlist.get(i).getString("goods_image"));
				viewHolder.imv_0.setTag( warrlist.get(i).getString("goods_image"));
			}/*else if(i==2)
			{
				imageLoader.display(viewHolder.imv_three, warrlist.get(i).getString("goods_image"));	
			}else if(i==1)
			{
				imageLoader.display(viewHolder.imv_two, warrlist.get(i).getString("goods_image"));	
			}*/
			/*ImageView imv=new ImageView(mContext);
			
				imageLoader.display(imv, warrlist.get(i).getString("goods_image"));
				LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(Tools.dip2px(mContext, 90),Tools.dip2px(mContext, 70));
				params.setMargins(5, 5, 5, 5);
				imv.setLayoutParams(params);
				
				imv.setScaleType(ScaleType.CENTER_CROP);
				gallery.addView(imv);*/
				//root.addView(LayoutParams.WRAP_CONTENT, 0, 1);
			
		}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public String formatDate(String milis)
	{
		
		
		///DateFormat.getda
		
		
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		
		return format.format(new Date(Long.parseLong(milis)));
		/*Date date=new Date(Long.parseLong(milis));
		
		return java.text.DateFormat.getInstance(java.text.DateFormat.SHORT).format(date);*/
	}
	private String getAddTime(String string) {
		
		
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		
		return format.format(new Date(Long.parseLong(string)));
		
		//return null;
	}
	public class ViewHolder {
		public TextView textView1;
		public TextView textView2;
		public TextView textView3;
		public TextView textView4;
		public TextView textView5;
		
		ImageView imv_0;
		
		/*ImageView imv_1;
		ImageView imv_2;*/
		
		//public ImageView po;
		//public FinalBitmap fi;
		
		
		LinearLayout gallery;

	}
}
