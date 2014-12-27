package com.gyh.digou.splash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

@SuppressLint("ValidFragment")
public class SplashImageFragment extends Fragment {

	int imageId;
	Context mContext;
	public SplashImageFragment(int imageId,Context mContext)
	{
		super();
		this.imageId=imageId;
		this.mContext=mContext;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		//;
		ImageView view=new ImageView(mContext);
		view.setScaleType(ScaleType.CENTER_CROP);
		view.setImageResource(imageId);
		return view;
	}
	@Override
	public void onDestroy() {	
		super.onDestroy();
	}
	@Override
	public void onDestroyView() {
		
		super.onDestroyView();
	}
	
	
	
	
	
	
}
