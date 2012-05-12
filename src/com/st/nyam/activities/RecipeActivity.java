package com.st.nyam.activities;


import java.io.InputStream;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.st.nyam.R;
import com.st.nyam.models.GeneralRecipe;
import com.st.nyam.util.SanInputStream;


public class RecipeActivity extends SherlockActivity {

	private String picture;
	private String TAG = "RecipeActivity";
	private ImageView viewPicture;
	private GeneralRecipe recipe = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_page);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		TextView viewDescription = (TextView)findViewById(R.id.recepy_description);
		recipe = (GeneralRecipe)getIntent().getSerializableExtra("Recipe");
		String desc = recipe.getTitle();
		picture = (String)getIntent().getSerializableExtra("Picture");
		TextView viewTitle = (TextView)findViewById(R.id.recepy_title);
		viewTitle.setText(recipe.getTitle());
		viewDescription.setText(desc);
		viewPicture = (ImageView)findViewById(R.id.picture);
		new DownloadImageTask().execute("");
	}


	private class DownloadImageTask extends AsyncTask<String,Void,Bitmap> {

		Bitmap  bitmap = null;

		@Override
		protected Bitmap doInBackground(String... str) {
			try{   
				InputStream in = new java.net.URL(picture).openStream();
				bitmap = BitmapFactory.decodeStream(new SanInputStream(in));
				//viewPicture.setImageBitmap(bitmap);
				viewPicture.setBackgroundDrawable(new BitmapDrawable(bitmap));
				viewPicture.refreshDrawableState();
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return bitmap;
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.item_page_menu, (com.actionbarsherlock.view.Menu) menu);
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())  {
		case R.id.menu_further: 
			Intent myIntent = new Intent(this, StepActivity.class);
			recipe = (GeneralRecipe)getIntent().getSerializableExtra("Recipe");
			Log.d(TAG,"id: " + recipe.getId() );
			Log.d(TAG,"desc: " + recipe.getTitle() );
			myIntent.putExtra("Recipe1", recipe);
			myIntent.putExtra("Picture", "http://kuharka.com/uploads/posts/2011-12/1323081421_ovoshhnoj-salat-iz-krevetok-s-limonom.jpeg");
			startActivity(myIntent);
			return true;
		case R.id.menu_cart: 
			Toast.makeText(this, "Cart", 10000).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
