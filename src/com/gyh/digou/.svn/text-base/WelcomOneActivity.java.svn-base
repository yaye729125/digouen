package com.gyh.digou;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class WelcomOneActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcomone);

		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				Intent intent = new Intent(WelcomOneActivity.this,MainActivity.class);
//			    intent.putExtra(name, value);
				startActivity(intent);
			    WelcomOneActivity.this.finish();
			}
		};
		timer.schedule(task, 1000 * 1);
	}
}
