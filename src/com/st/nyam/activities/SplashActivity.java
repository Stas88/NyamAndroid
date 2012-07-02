package com.st.nyam.activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

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
		Display mDisplay= this.getWindowManager().getDefaultDisplay();
		int width= mDisplay.getWidth();
		int height= mDisplay.getHeight();
		
		LayoutInflater inflater = LayoutInflater.from(this);
		RelativeLayout v = (RelativeLayout) inflater.inflate(R.layout.splash, null);
		Drawable drawable;
		if(width<height){
			Resources res = getResources(); //resource handle
			drawable = res.getDrawable(R.drawable.splash_bg); 
		} else {
			Resources res = getResources(); //resource handle
			drawable = res.getDrawable(R.drawable.background_main_land); 
		}
		v.setBackgroundDrawable(drawable);
		setContentView(v);
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
