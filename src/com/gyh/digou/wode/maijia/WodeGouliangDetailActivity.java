package com.gyh.digou.wode.maijia;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gyh.digou.Data;
import com.gyh.digou.R;
import com.gyh.digou.fenlei.NestRadioGroup;
import com.gyh.digou.fenlei.NestRadioGroup.OnCheckedChangeListener;
import com.gyh.digou.util.Tools;

public class WodeGouliangDetailActivity extends Activity {
	ListView listView;
	LayoutInflater inflater;
	WodeGouliangDetailAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wodegouliang_detail);
		listView=(ListView) findViewById(R.id.wodegouliang_detail_list);
		
		inflater=getLayoutInflater();
		adapter=new WodeGouliangDetailAdapter();
		listView.setAdapter(adapter);
		
		
		
	
		
		NestRadioGroup group=(NestRadioGroup) findViewById(R.id.wodegouliang_detail_radiogroup);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(NestRadioGroup group, int checkedId) {
				switch(checkedId)
				{
				
				case R.id.wodegouliang_detail_radiobtn_cz:
					
					status=1;
					
					getData("deposit");
					break;
				case R.id.wodegouliang_detail_radiobtn_xf:
					
					status=2;
					getData("deposit");
					break;
				case R.id.wodegouliang_detail_radiobtn_sc:
					
					status=3;
					getData("give");
					break;
				case R.id.wodegouliang_detail_radiobtn_hd:
					
					status=4;
					getData("get");
					break;
				
				
				
				
				}
				//adapter.notifyDataSetChanged();
			}
		});
		getData("order");
	}

	int total=-1;
	/*List<JSONObject> adapterData=new ArrayList<JSONObject>();
	
	List<JSONObject> orderList=new ArrayList<JSONObject>();
	List<JSONObject> giveList=new ArrayList<JSONObject>();
	List<JSONObject> getList=new ArrayList<JSONObject>();*/
	List<JSONObject> depositList=new ArrayList<JSONObject>();
	public void getData(String type)
	{
		
		AjaxParams params=new AjaxParams();
		params.put("token",Data.info.getData().getToken());
		
		params.put("page","1");
		params.put("pageSize","20");
		params.put("type", type);
		
		FinalHttp fh=new FinalHttp();
		fh.post(Data.getGouliangDetailUrl(), params, new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {
				
				System.out.println("t===="+t);
				//Log.d("wodegoulaing_detial", t);
				try {
					JSONObject json_result=new JSONObject(t);
					JSONObject json_data=json_result.getJSONObject("data");
					total=json_data.getInt("total");
					
					JSONObject json_list=json_data.getJSONObject("list");
					
					Iterator itr=json_list.keys();
					data_list.clear();
					int i=0;
					while(itr.hasNext())
					{
						
						
						String key=(String) itr.next();
						
						JSONObject json_obj=json_list.getJSONObject(key);
						Log.d("wodegoulaing_detial", json_obj.getString("time"));
						//System.out.println
						data_list.add(i, json_obj);
						//data_list.add(json_obj);
						i++;
						
					}
					/*LinkedList<JSONObject> list=new LinkedList<JSONObject>(data_list);
					Arrays.so*/
					//data_list.s
					adapter.notifyDataSetChanged();
					
					
				} catch (JSONException e) {
					
					e.printStackTrace();
				}
				
				
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				
			}
		
		
		
		
		});
		
		
		
		
		
		
	}
	
	
	List<JSONObject> data_list=new ArrayList<JSONObject>();
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	
	
	
	int status=-1;
	class WodeGouliangDetailAdapter extends BaseAdapter
	{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data_list.size();
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
				arg1=inflater.inflate(R.layout.wodegouliang_detail_item, null);
				holder.target_id=(TextView) arg1.findViewById(R.id.wodegouliang_detail_item_tv_target_id);
				holder.point=(TextView) arg1.findViewById(R.id.wodegouliang_detail_item_tv_point);
				
				
				
				holder.time=(TextView) arg1.findViewById(R.id.wodegouliang_detail_item_tv_time);
				
				holder.mem0=(TextView) arg1.findViewById(R.id.wodegouliang_detail_item_tv_memo);
				arg1.setTag(holder);
			}else
			{
				holder=(ViewHolder) arg1.getTag();
			}
			
			
			JSONObject json=data_list.get(arg0);
			try {
				
				
				String target_id_s="";
				
				int target_id=-1;
				
				target_id=json.getInt("target_id");
				//int type=json.getInt("type");
				switch(status)
				{
				case 1:
					target_id_s=target_id+"";
					//target_id=json.getString("target_id");
					break;
				case 2:
					target_id_s=target_id+"";
					//target_id=json.getString("target_id");
					break;
				case 3:
					
					target_id_s=json.getJSONObject("user").getString("user_name");
					break;
				case 4:
					if(!(target_id==0))
					{
						target_id_s=json.getJSONObject("store").getString("store_name");
					}else
					{
						target_id_s="system give";
					}
					
					break;
					
				}
				
				
				holder.target_id.setText(target_id_s);
			
			
			
			
			
				holder.point.setText(json.getString("point"));
			
			
			
				holder.time.setText(json.getString("time"));
			
			
			
				holder.mem0.setText(json.getString("memo"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return arg1;
		}
		
		
		

		
	}
	static class ViewHolder
	{
		
		TextView target_id;
		TextView point;
		TextView time;
		TextView mem0;
		//TextView xingming;
		
	}
	
	
	
	
}
