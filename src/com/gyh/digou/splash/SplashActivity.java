package com.gyh.digou.splash;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.gyh.digou.R;

public class SplashActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.splash);
		resIds.add(R.drawable.splash_loc);
		resIds.add(R.drawable.splash_shake);
		resIds.add(R.drawable.splash_change);
		ViewPager pager=(ViewPager) findViewById(R.id.splash_pager);
		
		
		
		pager.setAdapter(new SplashViewPagerAdapter(getSupportFragmentManager()));
		
	}

	
	List<Integer> resIds=new ArrayList<Integer>();
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	}

	
	
	class SplashViewPagerAdapter extends FragmentStatePagerAdapter
	{

		public SplashViewPagerAdapter(FragmentManager fm) {
			super(fm);
			
		}

		@Override
		public Fragment getItem(int arg0) {
			
			Fragment fragment=null;
			if(!(arg0==resIds.size()-1))
			{
				fragment=new SplashImageFragment(resIds.get(arg0),SplashActivity.this);
			}else
			{
				fragment=new SplashLashFragment(SplashActivity.this);
			}
			return fragment;
		}

		@Override
		public int getCount() {
			
			return resIds.size();
		}
		
		
		
		
		
		
	}
	
	
	
}
