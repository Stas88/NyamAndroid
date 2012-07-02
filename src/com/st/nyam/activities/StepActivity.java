package com.st.nyam.activities;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.st.nyam.R;
import com.st.nyam.adapters.StepPagerAdapter;
import com.st.nyam.models.Recipe;
import com.st.nyam.models.Step;
import com.st.nyam.util.Constants;
import com.st.nyam.util.SanInputStream;

public class StepActivity extends SherlockActivity  {

	private Recipe recipe = null;
	private String TAG = "StepActivity1";
	private ArrayList<Step> steps;
	private int currentStep = 0;
	private int stepQuantity;
	private TextView viewStepBody;
	private TextView stepNumber;
	private ImageView viewPicture;
	private TextView recipeTitle;
	private String bodyText;
	private Object []  pictures;
	private ViewPager pager;
	
	
	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pager = new ViewPager(this);
		Log.d(TAG, "Pager Initialized");
		LayoutInflater inflater = LayoutInflater.from(this);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		
		
		
	    List<View> pages = new ArrayList<View>();
	    StepPagerAdapter adapter = new StepPagerAdapter(pages);
		pager.setAdapter(adapter);
		pager.setCurrentItem(0);     
		setContentView(pager);
	    try {
			recipe = (Recipe)getIntent().getSerializableExtra("Recipe1");
			Log.d(TAG, "Recipe got");
			steps = recipe.getSteps();
			Log.d(TAG, "Steps got");
			Log.d(TAG, "Step size():  " + steps.size() );
			for (int i = 0; i < steps.size(); i ++ ) {
				View v = inflater.inflate(R.layout.step_page, null);
				viewStepBody = (TextView)v.findViewById(R.id.step_body);
				stepNumber = (TextView)v.findViewById(R.id.step_number);
				viewPicture = (ImageView)v.findViewById(R.id.picture);
				recipeTitle = (TextView)v.findViewById(R.id.recipe_title);
				currentStep++;
				stepQuantity = steps.size();
				Log.d(TAG,"steps: " + steps.size());
				Step step = steps.get(i);
				bodyText =  step.getInstruction();
				if (getIntent().getBooleanExtra("isFavorites", false) == true) { 
					Log.d(TAG, "isFavorites = " + getIntent().getBooleanExtra("isFavorites", false));
					Bitmap bitmapViewPicture = BitmapFactory.decodeFile(
		        			Environment.getExternalStorageDirectory().toString() + 
		        			"/Nyam/NyamRecipesFavorites/" + step.getImg_url().replace('/', '&'));
					Log.d(TAG, "Bitmap is favorite url = " + step.getImg_url());
					Log.d(TAG, "Bitmap is favorite = " + bitmapViewPicture);
					viewPicture.setImageBitmap(bitmapViewPicture);
					Log.d(TAG, "Bitmap is favorite1 = " + bitmapViewPicture);
				} else {
					pictures = new Object [] {(String)Constants.URL + step.getImg_url(), (View)viewPicture};
					new DownloadImageTask().execute(pictures);
				}
				recipeTitle.setText(recipe.getTitle());
				viewStepBody.setText(bodyText);
				stepNumber.setText(currentStep + "/" + stepQuantity);
				
				pages.add(v);
				Log.d(TAG, "Added view to pages " + i );
			}
			
		} catch (Exception e) {
			Toast.makeText(this, "Error occured" + e.getMessage(), 200);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.step_menu,  menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int current = pager.getCurrentItem();
		switch(item.getItemId())  {
		case R.id.next: 
			Log.d(TAG, "showNext()");
			pager.setCurrentItem(++current, true);
			return true;
		case R.id.previous: 
			Log.d(TAG, "showPrevious()");
			pager.setCurrentItem(--current, true);
			return true;
		case R.id.start_step: 
			Log.d(TAG, "start");
			pager.setCurrentItem(0, true);
			return true;
		case R.id.end_step: 
			Log.d(TAG, "end");
			pager.setCurrentItem(stepQuantity - 1, true);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private class DownloadImageTask extends AsyncTask<Object,Void,Object[]> {
		
		Object [] outObjects;
		
		@Override
		protected Object[] doInBackground(Object...  o) {
			Bitmap outBitmap = null;
			try{   
				InputStream in = new java.net.URL((String)o[0]).openStream();
				outBitmap = BitmapFactory.decodeStream(new SanInputStream(in));
				outObjects = new Object [] {outBitmap, o[1]};
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return outObjects;
		}
		
		@Override 
		protected void onPostExecute(Object[] result) {
			super.onPostExecute(result);
			((View)result[1]).setBackgroundDrawable(new BitmapDrawable((Bitmap)result[0]));
		}
	}
	
	
	
	
}
