package com.gyh.digou.fenlei;

import java.util.ArrayList;
import java.util.Iterator;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gyh.digou.R;


public class XiangGuanFragment extends Fragment{
	private Bundle bundle;
	ListView list_xiangguan;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View shangPinItemFragementView = inflater.inflate(R.layout.fenlei_xiangguan_fragment, container, false);
//		ListView listView_XiangGuanFragment = (ListView)shangPinItemFragementView.findViewById(R.id.fenlei_shangpinitem_fragment);
		list_xiangguan=(ListView) shangPinItemFragementView.findViewById(R.id.listView_xiangguan);
		bundle = this.getArguments();
		System.out.println("this==============" + this);
		System.out.println("XiangGuan  bundle=====+++++++++>>>>>>>>" + bundle);
		
		xiangGuanParseJson();

//		listView_XiangGuanFragment.setAdapter(new FenLei_ShangPinItem_BaseAdapter());

		return shangPinItemFragementView;
	}
	
	
	
	CateCommercialAdapter adapter;
	
	//====================================解析“相关”商品列表==============================================================
	private ArrayList<JSONObject> commerList=new ArrayList<JSONObject>();
	private void xiangGuanParseJson(){
		AjaxParams params = new AjaxParams();//异步参数
		params.put("cate_id",bundle.getString("cate_id"));
		
		FinalHttp fh3 = new FinalHttp();//http请求
		fh3.post("http://www.cddego.com/api.php?app=search&act=api_goods", params,new AjaxCallBack<String>() {
			@Override
			public void onLoading(long count, long current) {
				// TODO Auto-generated method stub
				super.onLoading(count, current);
			}

			@Override
			public void onSuccess(String t) {
				
				System.out.println("t=-----====-------====---->>>>"+t);
				
				try {
					JSONObject json_cate= new JSONObject(t);
					int total=json_cate.getInt("total");					
					JSONObject json_catelist=json_cate.getJSONObject("list");
					@SuppressWarnings("unchecked")
					Iterator<String> keys=json_catelist.keys();
					
					while(keys.hasNext())
					{
						String key=keys.next();
						JSONObject json_commer=json_catelist.getJSONObject(key);
						commerList.add(json_commer);
					}
					
					adapter=new CateCommercialAdapter(getActivity());
					adapter.setData(commerList);
					list_xiangguan.setAdapter(adapter);
					
					
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
