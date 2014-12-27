package com.gyh.digou.wode.shangjia;


import java.util.ArrayList;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.util.Tools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class Xuanzefenlei2 extends Activity{
	ListView xuanzefenlei_listView;
	
	ArrayList<JSONObject> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fenlei);
		xuanzefenlei_listView=(ListView) findViewById(R.id.xuanzefenlei_listView);
		
		Intent intent=getIntent();
	           String child=	intent.getStringExtra("chile");
	         
	          try {
				
	        	  JSONArray arrays=new JSONArray(child);
					
					list=new ArrayList<JSONObject>();
			for(int y=0;y<arrays.length();y++){
				JSONObject jsonObject=arrays.getJSONObject(y);	
				list.add(jsonObject);
				ShangpinfeileiAdapter adapter=new ShangpinfeileiAdapter(Xuanzefenlei2.this, list);
				xuanzefenlei_listView.setAdapter(adapter);		
	        	  
			}
	        	  
	        	
	        	  
			} catch (Exception e) {
				// TODO: handle exception
			}
		xuanzefenlei_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			
			
				try {
				String	cate_name = list.get(arg2).getString("cate_name");
				String	cate_id = list.get(arg2).getString("cate_id");
					
					
					Intent intent = new Intent();
					intent.putExtra("cate_name", cate_name);
					intent.putExtra("cate_id", cate_id);
					setResult(4, intent);
				
					finish();//结束之后会将结果传回From
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		});
		 
		
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event)  
    {  
        if (keyCode == KeyEvent.KEYCODE_BACK )  
        {  
        	Toast.makeText(Xuanzefenlei2.this, "请选择分类", 0).show();
       
  
        }  
          
        return false;  


    }
}
