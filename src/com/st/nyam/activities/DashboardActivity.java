package com.st.nyam.activities;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.st.nyam.R;

public class DashboardActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard_layout);
	}


	public void onClickFeature (View v)
	{
		int id = v.getId ();
		switch (id) {
		case R.id.home_btn_feature2 :
			startActivity (new Intent(this, MainListActivity.class));
			break;
		default: 
			break;
		}
	}


}
