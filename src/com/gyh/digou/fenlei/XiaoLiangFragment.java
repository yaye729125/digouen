package com.gyh.digou.fenlei;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyh.digou.R;

public class XiaoLiangFragment extends Fragment{
	private LayoutInflater inflater;
	private Bundle bundle;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fenLeiXiaoLiangView = inflater.inflate(R.layout.fenlei_xiaoliang_fragment, null);
		
		bundle = this.getArguments();
		System.out.println("XiaoLiang  bundle=====+++++++++>>>>>>>>" + bundle);
		
		xiaoLiangParseJson();
		
		return fenLeiXiaoLiangView;
	}
	
	private void xiaoLiangParseJson(){
		AjaxParams params = new AjaxParams();//异步参数
		params.put("cate_id","bundle");
		
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
			}
		});
	}
	
}
