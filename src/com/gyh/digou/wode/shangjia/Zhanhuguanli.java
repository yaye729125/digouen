package com.gyh.digou.wode.shangjia;

import java.util.ArrayList;
import java.util.HashMap;

import com.gyh.digou.R;
import com.gyh.digou.wode.maijia.DizhiguanliActivity;
import com.gyh.digou.wode.maijia.Mimagenggai;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

  
public class Zhanhuguanli extends Activity{
	ListView listView;
	
	String [] ss=new String[]{"地址管理","密码管理","资金详情","资金提现"};
	//int [] im=new int[]{R.drawable.ic_arrow,R.drawable.ic_arrow,R.drawable.ic_arrow,R.drawable.ic_arrow};
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhanghuguanli);
		listView=(ListView) findViewById(R.id.zhanhu_listView1);
		  ArrayList<HashMap<String, Object>> list=new ArrayList<HashMap<String,Object>>();
		  for(int x=0;x<ss.length;x++){
		  HashMap<String, Object> map=new HashMap<String, Object>();
		  map.put("text", ss[x]);
		  map.put("image", R.drawable.ic_arrow);
		  list.add(map);
		  
		  }
		SimpleAdapter adapter=new SimpleAdapter(Zhanhuguanli.this,list, R.layout.zhanghu_styl, new String[]{"text","image"}, new int[]{R.id.textView1,R.id.imageView1});
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				if(arg2==0){
					Intent intent=new Intent(Zhanhuguanli.this, DizhiguanliActivity.class);
					startActivity(intent);
				}
				if(arg2==2){
					Intent intent=new Intent(Zhanhuguanli.this, Zijinxiangqing.class);
					startActivity(intent);
				}
				if(arg2==1){
					Intent intent=new Intent(Zhanhuguanli.this, Mimagenggai.class);
					startActivity(intent);
				
				}
				if(arg2==3){
					Intent intent=new Intent(Zhanhuguanli.this, Zijintixian.class);
					startActivity(intent);
					
					
				}
				
				
				
				
				
			}
		});
		
	}

}
