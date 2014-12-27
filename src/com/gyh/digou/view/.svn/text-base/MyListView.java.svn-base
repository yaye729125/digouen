package com.gyh.digou.view;



import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.ListView;

public class MyListView extends ListView {
	private boolean canScroll;
	 
    private GestureDetector mGestureDetector;
    View.OnTouchListener mGestureListener;
	public MyListView(Context context) {
		super(context);
		init();
	}

	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		 if(ev.getAction() == MotionEvent.ACTION_UP)
	            canScroll = true;
	        return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
	}

	private void init()
	{
		mGestureDetector = new GestureDetector(new YScrollDetector());
        canScroll = true;
	}
	
	
	class YScrollDetector extends SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if(canScroll)
                if (Math.abs(distanceY) >= Math.abs(distanceX))
                    canScroll = true;
                else
                    canScroll = false;
            return canScroll;
        }
    }
}
