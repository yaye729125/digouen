package com.gyh.digou.mytoast;


import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast { 
	public static final int LENGTH_MAX = -1; 
	private boolean mCanceled = true;
	private Handler mHandler; 
	private Context mContext; 
	private MyToast mToast; 
	public  static Toast toast;
	public CustomToast(Context context) { 
		this(context,new Handler()); 
	} 
	/*public static void makeText(Context mContext,String text,boolean isJson)
	{
		
	}*/
	public static void makeText(Context mContext,String text,boolean isJson)
	{
		
			try {
				
				Toast toast=new Toast(mContext);
				TextView tv=new TextView(mContext);
				
				toast.setView(tv);
				toast.setGravity(Gravity.CENTER,0,0);
				toast.setDuration(Toast.LENGTH_SHORT);
				if(isJson)
				{
					JSONObject json=new JSONObject(text);
					tv.setText(json.getString("ErrMsg"));
				}else
				{
				
					tv.setText(text);
					
				}
				toast.show();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}
	public CustomToast(Context context,Handler h) { 
		mContext = context; 
		mHandler = h; 
		/*mToast = Toast.makeText(mContext,"",Toast.LENGTH_SHORT); 
		mToast.setGravity(Gravity.BOTTOM, 0, 0); */
		
		mToast=new MyToast(mContext);
		/*View view = LayoutInflater.from(mContext).inflate(R.layout.mytoast, null);
		  //TextView chapterNameTV = (TextView) view.findViewById(R.id.chapterName);
		  //TextView percentageTV = (TextView) view.findViewById(R.id.percentage);
		  //chapterNameTV.setText(chapterName);
		 // percentageTV.setText(df.format(persent * 100) + "%");
		  
		  //Toast toast = new Toast(this);
		  mToast.setGravity(Gravity.TOP, 0,PixelFormat.formatDipToPx(mContext, 70));
		  //mToast.setDuration(Toast.LENGTH_LONG);
		  mToast.setView(view);*/
		
	} 
	public void setGravity(int gravity, int xOffset, int yOffset)
	{
		 mToast.setGravity(gravity,xOffset,yOffset);
	}
	public void setView(View view)
	{
		mToast.setView(view);
	}
	
	public void show(int duration)
	{
		if(duration != LENGTH_MAX) { 
			mToast.setDuration(duration); 
			mToast.show(); 
		 } else if(mCanceled) { 
			 mToast.setDuration(Toast.LENGTH_LONG);
			 mCanceled = false;
			 showUntilCancel(); 
		 } 
	}
	public void show(int resId,int duration) { 
		mToast.setText(resId); 
		if(duration != LENGTH_MAX) { 
			mToast.setDuration(duration); 
			mToast.show(); 
		 } else if(mCanceled) { 
			 mToast.setDuration(Toast.LENGTH_LONG);
			 mCanceled = false;
			 showUntilCancel(); 
		 } 
	}
	
	/**
	 * @param text 要显示的内容
	 * @param duration 显示的时间长
	 * 根据LENGTH_MAX进行判断
	 * 如果不匹配，进行系统显示
	 * 如果匹配，永久显示，直到调用hide()
	 */
	public void show(String text,int duration) { 
		mToast.setText(text); 
		if(duration != LENGTH_MAX) { 
			mToast.setDuration(duration); 
			mToast.show(); 
			} else { 
				if(mCanceled) { 
					mToast.setDuration(Toast.LENGTH_LONG); 
					mCanceled = false; 
					showUntilCancel();
				}
			} 
		} 

	/**
	 * 隐藏Toast
	 */
	public void hide(){
		
		
		mToast.cancel();
		mCanceled = true;
	}
	
	public void hide(long delaymilis)
	{
		mHandler.postDelayed(new Runnable() {
			public void run() { 
				mToast.cancel();
			}
		},delaymilis);
		mCanceled = true;
	}
	
	public boolean isShowing() {
		return !mCanceled;
	}
	
	private void showUntilCancel() { 
		if(mCanceled) 
			return; 
		mToast.show();
		mHandler.postDelayed(new Runnable() {
			public void run() { 
				showUntilCancel(); 
			}
		},3000); 
	} 
} 