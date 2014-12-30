package com.gyh.digou.wode.shangjia.commermana;

import java.util.ArrayList;

import com.gyh.digou.R;
import com.gyh.digou.bean.IImages;
import com.gyh.digou.itemdetail.PictrueFragment;
import com.gyh.digou.itemdetail.ShowItemBigPictureActivity;

import com.gyh.digou.itemdetail.view.HackyViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class EditCommerPhotoActivity extends FragmentActivity {

	
	private HackyViewPager viewPager;
	//private int[] resId={R.drawable.one,R.drawable.two,R.drawable.three};
	ArrayList<IImages> list;
	private int position=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		
		Intent intent=getIntent();
		position=intent.getIntExtra("position", 0);
		list=intent.getParcelableArrayListExtra("imageList");
		
		initViewPager();
	}

	
	
	
	
	private void initViewPager(){
		
		viewPager = (HackyViewPager) findViewById(R.id.viewPager_show_bigPic);
		ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(position);
		
	}
	
	private class ViewPagerAdapter extends FragmentStatePagerAdapter{

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		
		
		@Override
		public Fragment getItem(int position) {
			
			return new PictrueFragment(list.get(position).getImage_url(),EditCommerPhotoActivity.this);
		}

		@Override
		public int getCount() {
			return list.size();
		}

		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
	
		super.onPause();
	}

	@Override
	protected void onRestart() {
		
		super.onRestart();
	}

	@Override
	protected void onResume() {
		
		super.onResume();
	}

	@Override
	protected void onStart() {
		
		super.onStart();
	}

	@Override
	protected void onStop() {
		
		super.onStop();
	}

	
	
	
	
	
}
