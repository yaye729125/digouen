package com.gyh.digou;
import java.util.Timer;
import java.util.TimerTask;

import com.gyh.digou.app.MyApp;
import com.gyh.digou.splash.SplashActivity;

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
				
				
				MyApp app=(MyApp)getApplication();
				if(!app.isFirstUse())
				{
					Intent intent = new Intent(WelcomOneActivity.this,MainActivity.class);
	//			    intent.putExtra(name, value);
					startActivity(intent);
				}else
				{
					Intent intent = new Intent(WelcomOneActivity.this,SplashActivity.class);
					//			    intent.putExtra(name, value);
									startActivity(intent);
					app.saveFirst("first");
				}
			    WelcomOneActivity.this.finish();
			}
		};
		timer.schedule(task, 1000 * 1);
	}
}
