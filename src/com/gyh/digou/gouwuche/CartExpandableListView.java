package com.gyh.digou.gouwuche;



import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ExpandableListView;

public class CartExpandableListView extends ExpandableListView {

	public CartExpandableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

	private static final String TAG = "ListViewCompat";

    private SlideView mFocusedItemView;
	
    
    public class MessageItem
    {
    	SlideView slideView;
    }
    
	 public void shrinkListItem(int position) {
	        View item = getChildAt(position);

	        if (item != null) {
	            try {
	                ((SlideView) item).shrink();
	            } catch (ClassCastException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    @Override
	    public boolean onTouchEvent(MotionEvent event) {
	        switch (event.getAction()) {
	        case MotionEvent.ACTION_DOWN: {
	            int x = (int) event.getX();
	            int y = (int) event.getY();
	            int position = pointToPosition(x, y);
	            Log.e(TAG, "postion=" + position);
	            if (position != INVALID_POSITION) {
	                MessageItem data = (MessageItem) getItemAtPosition(position);
	                mFocusedItemView = data.slideView;
	                Log.e(TAG, "FocusedItemView=" + mFocusedItemView);
	            }
	        }
	        default:
	            break;
	        }

	        if (mFocusedItemView != null) {
	            mFocusedItemView.onRequireTouchEvent(event);
	        }

	        return super.onTouchEvent(event);
	    }
	
	
	
	
}
