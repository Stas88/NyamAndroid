package com.st.nyam.activities;

import java.io.InputStream;

import org.json.JSONException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.st.nyam.R;
import com.st.nyam.factories.ApiFactory;
import com.st.nyam.models.Profile;
import com.st.nyam.util.Constants;
import com.st.nyam.util.SanInputStream;

public class ProfileActivity extends SherlockActivity {
	
	private final static String TAG = "ProfileActivity"; 
	private ImageView img;
	
	private TextView name;
	private TextView level;
	private TextView favorite_dishes;
	private TextView hobbies;
	private TextView interests;
	private TextView experience;
	private TextView about;
	
	private TextView published_recepies;
	private TextView added_to_favorites;
	private TextView comments_left;
	private TextView voices_left;
	private TextView friends;
	private ViewGroup profile_layout;
	
	public void onCreate (Bundle bundle) {
		super.onCreate(bundle);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Профиль");
		setContentView(R.layout.progress_bar_layout);
		String [] params = {""};
		LayoutInflater inflater = LayoutInflater.from(this);
		profile_layout = (ViewGroup)inflater.inflate(R.layout.profile_layout, null);
		initViews(profile_layout);
		new LoadProfileData().execute(params);
	}
	
	private void initViews(ViewGroup profile_layout) {
		if (profile_layout != null) {
			Log.d(TAG, "initViews");
			img = (ImageView)profile_layout.findViewById(R.id.profile_image);
			name = (TextView)profile_layout.findViewById(R.id.name);
			level = (TextView)profile_layout.findViewById(R.id.level);
			favorite_dishes =  (TextView)profile_layout.findViewById(R.id.favorite_dishes_data);
			hobbies =  (TextView)profile_layout.findViewById(R.id.hobbies_data);
			interests =  (TextView)profile_layout.findViewById(R.id.interests_data);
			experience =  (TextView)profile_layout.findViewById(R.id.experience_data);
			about =  (TextView)profile_layout.findViewById(R.id.about_data);
			
			Log.d(TAG, "initViews 1");
			published_recepies = (TextView)profile_layout.findViewById(R.id.published_recepies_data);
			added_to_favorites = (TextView)profile_layout.findViewById(R.id.added_to_favorites_data);
			comments_left = (TextView)profile_layout.findViewById(R.id.comments_left_data);
			voices_left = (TextView)profile_layout.findViewById(R.id.voices_left_data);
			friends = (TextView)profile_layout.findViewById(R.id.friends_data);
		}
	}
	
	private void updateViews(Profile profile) {
		Log.d(TAG, "updateViews");
		String imgUrl = Constants.URL + profile.getImg_path();
		String [] params = new String[] {imgUrl};
		new DownloadImageTask().execute(params);
		name.setText(profile.getName());
		level.setText(profile.getLevel());
		favorite_dishes.setText(profile.getFavorite_dishes());
		hobbies.setText(profile.getHobbies());
		interests.setText(profile.getInterests());
		experience.setText(profile.getExperience());
		about.setText(profile.getAbout());
		
		published_recepies.setText(String.valueOf(profile.getPublished_recepies()));
		added_to_favorites.setText(String.valueOf(profile.getAdded_to_favorites()));
		comments_left.setText(String.valueOf(profile.getComments_left()));
		voices_left.setText(String.valueOf(profile.getVoices_left()));
		friends.setText(String.valueOf(profile.getFriends()));
		setContentView(profile_layout);
	}
	
	private class LoadProfileData extends AsyncTask<String, Void, Profile> {
		
		@Override
		protected Profile doInBackground(String... params) {
			Profile profile = null;
			try {
				profile =  ApiFactory.getProfile();
				Log.d(TAG, "profile " + profile);
				
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return profile;
		}
		
		@Override 
		protected void onPostExecute(Profile profile) {
			if (profile != null) {
				updateViews(profile);
			}
		}
	}
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... str) {
			Bitmap bitmap = null;
			try {
				InputStream in = new java.net.URL(str[0]).openStream();
				bitmap = BitmapFactory.decodeStream(new SanInputStream(in));
				// viewPicture.setImageBitmap(bitmap);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			img.setBackgroundDrawable(new BitmapDrawable(result));
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())  {
		case 16908332: 
			Log.d(TAG, "logo");
			startActivity(new Intent(this, DashboardActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
