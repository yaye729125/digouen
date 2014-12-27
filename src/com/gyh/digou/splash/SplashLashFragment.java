package com.gyh.digou.splash;

import com.gyh.digou.MainActivity;
import com.gyh.digou.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

@SuppressLint("ValidFragment")
public class SplashLashFragment extends Fragment {

	int imageId;
	Context mContext;
	
	public SplashLashFragment(Context mContext)
	{
		super();
		this.mContext=mContext;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view=inflater.inflate(R.layout.splash_last,null,false);
		view.findViewById(R.id.splash_last_btn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(mContext,MainActivity.class));
				
				getActivity().finish();
			}
		});
		//;
		
		return view;
	}
	
	
	
}
