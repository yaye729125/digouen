package com.gyh.digou.itemdetail;



import net.tsz.afinal.FinalBitmap;

import com.gyh.digou.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * ��ʾ��ͼ��ʵ�֣����ҿ��ԷŴ���С
 * @author http://yecaoly.taobao.com
 *
 */
@SuppressLint("ValidFragment")
public class PictrueFragment extends Fragment {

	private String url;
	public FinalBitmap imageLoader;
	
	public PictrueFragment(Context mContext){
		super();	
	}
	public PictrueFragment()
	{
		
	}
	
	public PictrueFragment(String url,Context mContext){
		
		this.url=url;
		imageLoader=FinalBitmap.create(mContext);
		imageLoader.configLoadingImage(R.drawable.ic_launcher);
	}
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view=LayoutInflater.from(getActivity()).inflate(R.layout.scale_pic_item, null);
		initView(view);
		return view;
	}
	
	private void initView(View view){
		ImageView imageView=(ImageView) view.findViewById(R.id.scale_pic_item);
		imageLoader.display(imageView, url);
		
	}

	
	
}
