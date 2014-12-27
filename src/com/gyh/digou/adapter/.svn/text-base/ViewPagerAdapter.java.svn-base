package com.gyh.digou.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gyh.digou.R;

import net.tsz.afinal.FinalBitmap;



import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class ViewPagerAdapter extends PagerAdapter{
	List<HashMap<String,String>> ads;
	public Context mContext;
	public FinalBitmap imageLoader; 
	
	
		    
	public ViewPagerAdapter( List<HashMap<String,String>> ads,Context mContext,FinalBitmap imageLoader){  
		//this.mContext=mContext;
		this.ads = ads;  
        this.mContext = mContext; 
        this.imageLoader=imageLoader;
       
       
       
	   // imageLoader.
	    
	    
    } 
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		return Integer.MAX_VALUE;
		
		
	}

	/** 
     * 判断两张图片是否一致  
     */
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {  
		System.out.println("isViewFromObject");
	    return arg0 ==(View)arg1;  
	}

	/** 
     * 向适配器返回要显示的图片 
     * */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		System.out.println("instantiateItem");
		ImageView imageView= new ImageView(mContext);
		
		((ViewPager)container).addView(imageView);
		
		HashMap<String,String> map = ads.get(position%ads.size());  
		  
        imageLoader.display(imageView, map.get("pic")); 
        //imageLoader.
        
        
        System.out.println("pic="+map.get("pic"));
        imageView.setOnClickListener(new OnClickListener(){  
            @Override  
            public void onClick(View arg0) {  
                ;  
            }  
        }); 
        return imageView;
	}
	
	/** 
     * 从ViewGroup中移除当前view 
     * */  
    @Override  
    public void destroyItem(ViewGroup container, int position, Object object) {  
        //container.removeView(container.getChildAt(position));  
    	((ViewPager)container).removeView((View) object);
    }
}
