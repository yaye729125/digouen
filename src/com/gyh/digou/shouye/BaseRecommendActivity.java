package com.gyh.digou.shouye;

import java.util.Iterator;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.gyh.digou.R;

public class BaseRecommendActivity extends Activity {
	MyListViewBaseAdapter listBaseAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.child_shoe_wholesale);
		ListView listView=(ListView) findViewById(R.id.child_shoe_wholesale_list);
		listBaseAdapter=new MyListViewBaseAdapter(this);
		listBaseAdapter.setData(list);
		listView.setAdapter(listBaseAdapter);
		Intent intent=getIntent();
		String title=intent.getStringExtra("title");
		String id=intent.getStringExtra("id");
		
		if(title!=null&&id!=null)
		{
			initRecommand(id);
		}
		
		
		
						
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	JSONArray list=new JSONArray();
	public void initRecommand(String id)
	{

		list=new JSONArray();
		AjaxParams params=new AjaxParams();
		params.put("recommend_id",id);
		FinalHttp fh=new FinalHttp();
		fh.post("http://www.cddego.com/api.php?app=search&act=api_recommend", params,new AjaxCallBack<String>() {

			@Override
			public void onLoading(long count, long current) {
				super.onLoading(count, current);
			}

			@Override
			public void onSuccess(String t) {
				
				System.out.println(t);
				
				JSONObject jsonObject = null;
				
				try {
					//list.
					jsonObject = new JSONObject(t);
					JSONObject dataObject = jsonObject.getJSONObject("data"); 
					
					String total = dataObject.getString("total"); 
					int count=Integer.parseInt(total);
					
					JSONObject listObject= (JSONObject) dataObject.get("list"); 
					Iterator itr=listObject.keys();
					while(itr.hasNext())
					{
						String key=(String) itr.next();
						JSONObject numObject=(JSONObject) listObject.get(key);
						list.put(numObject);
		
					}
					
					listBaseAdapter.setData(list);
				}catch(Exception e)
				{
					return ;
				}
			}
		});
	}
}
