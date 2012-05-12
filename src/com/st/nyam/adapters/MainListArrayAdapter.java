package com.st.nyam.adapters;

import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.st.nyam.R;
import com.st.nyam.models.GeneralRecipe;
import com.st.nyam.util.SanInputStream;

public class MainListArrayAdapter extends ArrayAdapter<GeneralRecipe> {

	Context context; 
	int layoutResourceId;    
	ArrayList<GeneralRecipe> recipes = null;
	private String TAG = "MainListArrayAdapter";
	private RecipeHolder holder = null;
	private String URL = "http://nyam.ru";

	public MainListArrayAdapter(Context context, int layoutResourceId, ArrayList<GeneralRecipe> recipes) {
		super(context, layoutResourceId, recipes);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.recipes = recipes;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if(row == null) {
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new RecipeHolder();
			holder.imgIcon = (ImageView)row.findViewById(R.id.icon);
			holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
			holder.ratingView = (TextView)row.findViewById(R.id.txtTitle3);
			row.setTag(holder);
		}
		else {
			holder = (RecipeHolder)row.getTag();
		}
		GeneralRecipe recipe = recipes.get(position);
		Log.d(TAG, "holder:" + holder);
		Log.d(TAG, "txtTitle:" + holder.txtTitle);
		Log.d(TAG, "recipe:" + recipe);
		holder.txtTitle.setText(recipe.getTitle());
		/*
        if (recipe.getRating() != 0)  {
        	holder.ratingView.setText(recipe.getRating());
        }
        else holder.ratingView.setText(0);
		 */
		new DownloadImageTask().execute(new String(URL+recipe.getImg_url()));
		return row;
	}


	private class DownloadImageTask extends AsyncTask<String,Void,Bitmap> {

		Bitmap  bitmap = null;

		@Override
		protected Bitmap doInBackground(String... str) {
			for (int i = 0; i< str.length; i ++) {
				try{   
					InputStream in = new java.net.URL(str[i]).openStream();
					bitmap = BitmapFactory.decodeStream(new SanInputStream(in));
					//viewPicture.setImageBitmap(mIcon11);
					//Drawable drawable =  new BitmapDrawable(bitmap);
					//holder.imgIcon.setBackgroundDrawable(drawable);
					holder.imgIcon.setImageBitmap(bitmap);
					holder.imgIcon.refreshDrawableState();
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			return bitmap;
		}
	}

	static class RecipeHolder
	{
		ImageView imgIcon;
		TextView txtTitle;
		TextView ratingView;
	}

}
