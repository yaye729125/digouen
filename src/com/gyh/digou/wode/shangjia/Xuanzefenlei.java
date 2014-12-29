package com.gyh.digou.wode.shangjia;


import java.util.ArrayList;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gyh.digou.Data;
import com.gyh.digou.R;
import com.gyh.digou.app.MyApp;
import com.gyh.digou.util.Tools;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class Xuanzefenlei extends Activity{
	ListView xuanzefenlei_listView;
	ArrayList<JSONObject> arrayList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fenlei);
		xuanzefenlei_listView=(ListView) findViewById(R.id.xuanzefenlei_listView);
		
		AjaxParams params = new AjaxParams();
		
		params.put("token",Data.info.getData().getToken());
		

		FinalHttp fh = new FinalHttp();
		fh.post(Tools.getBaseUrl() + "?app=category&act=api_goods",
				params, new AjaxCallBack<String>() {

					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						Toast.makeText(Xuanzefenlei.this, strMsg,
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(String t) {
						 System.out.println("---we-------"+t+"----280-----80------------");
						 
						 
						try {
							JSONObject jsonObject=new JSONObject(t);
							String array=jsonObject.getString("data");
							 arrayList=new ArrayList<JSONObject>();
						JSONArray array2=new JSONArray(array);	
							for(int x=0;x<array2.length();x++){
								JSONObject jsonObject2=array2.getJSONObject(x);							
								arrayList.add(jsonObject2);
							ShangpinfeileiAdapter adapter=new ShangpinfeileiAdapter(Xuanzefenlei.this, arrayList);	
								xuanzefenlei_listView.setAdapter(adapter);			
							}
							
						} catch (JSONException e) {
							
							e.printStackTrace();
						} 
						 
						
					}

				});
		xuanzefenlei_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				Intent intent=new Intent(Xuanzefenlei.this, Xuanzefenlei2.class);
			
			

					String child;
					try {
						child = arrayList.get(arg2).getString("children");
						intent.putExtra("chile", child);
						startActivityForResult(intent, 4);
						
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					

             
				}
			
					
			
		});
		
	}
	
	
	public  void onActivityResult(int requestCode, int resultCode, Intent data) {
		 
         if(requestCode==4){
        String	cate_name1= data.getStringExtra("cate_name");
        String	cate_id= data.getStringExtra("cate_id");
        	
        	//Toast.makeText(Xuanzefenlei.this, cate_name1, 0).show();
        
        Intent intent1 = new Intent();
     
        intent1.putExtra("cate_id", cate_id);
		intent1.putExtra("cate_name", cate_name1);
		setResult(3, intent1);
		finish();
	
         }     
	}
	
	
	
	 public boolean onKeyDown(int keyCode, KeyEvent event)  
	    {  
	        if (keyCode == KeyEvent.KEYCODE_BACK )  
	        {  
	        	Toast.makeText(Xuanzefenlei.this, "请选择分类", 0).show();
	       
	  
	        }  
	          
	        return false;  
	
	
	    }
	
	
	
	
	
	
}
