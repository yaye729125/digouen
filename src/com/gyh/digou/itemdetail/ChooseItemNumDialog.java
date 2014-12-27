package com.gyh.digou.itemdetail;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.gyh.digou.R;

class ChooseItemNumDialog extends AlertDialog
{

	protected ChooseItemNumDialog(Context context) {
		super(context);
	
	}

	public ChooseItemNumDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		
	}

	public ChooseItemNumDialog(Context context, int theme) {
		super(context, theme);
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.cart_item);
		
		
		
		
	}
	
	
}
