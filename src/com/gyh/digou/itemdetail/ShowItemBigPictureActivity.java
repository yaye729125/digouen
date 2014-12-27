package com.gyh.digou.itemdetail;



import java.util.ArrayList;
import java.util.List;

import com.gyh.digou.R;
import com.gyh.digou.R.id;
import com.gyh.digou.R.layout;
import com.gyh.digou.bean.IImages;
import com.gyh.digou.itemdetail.view.HackyViewPager;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ShowItemBigPictureActivity extends FragmentActivity {

	private HackyViewPager viewPager;
	//private int[] resId={R.drawable.one,R.drawable.two,R.drawable.three};
	ArrayList<IImages> list;
	private int position=0;
	@Override
	protected void onCreate(Bundle arg0) {
		
		super.onCreate(arg0);
		setContentView(R.layout.show_item_big_picture);
		Intent intent=getIntent();
		position=intent.getIntExtra("position", 0);
		list=intent.getParcelableArrayListExtra("imageList");
		
		initViewPager();
		
		
		
	}



	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		
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
			
			return new PictrueFragment(list.get(position).getImage_url(),ShowItemBigPictureActivity.this);
		}

		@Override
		public int getCount() {
			return list.size();
		}

		
	}

}
