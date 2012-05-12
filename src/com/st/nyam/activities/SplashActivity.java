package com.st.nyam.activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.st.nyam.R;

public class SplashActivity extends Activity {

	Thread splashTread;
	private int splashTime = 1000;
	private boolean active = true;
	private boolean stop = true;
	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		context = this.getApplication();
		splashTread = new Thread() {

			@Override
			public void run() {
				try {
					int waited = 0;
					while(active && (waited < splashTime)) {
						sleep(100);
						if(active) {
							waited += 100;
						}
						if (waited >= splashTime) {
							active = false;
						}
					}
				} catch(InterruptedException e) {
					e.printStackTrace();
				} finally {
					if(!active){
						startActivity(new Intent(context, DashboardActivity.class));
						finish();
					}
					else
						finish();
				}
			}

		};
		splashTread.start();

	}


}
