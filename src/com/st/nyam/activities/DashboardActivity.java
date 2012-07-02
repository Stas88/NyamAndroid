package com.st.nyam.activities;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.st.nyam.NyamApplication;
import com.st.nyam.R;
import com.st.nyam.activities.DialogFragmentActivity.EditNameDialogListener;
import com.st.nyam.factories.DataBaseFactory;
import com.st.nyam.factories.HttpFactory;
import com.st.nyam.models.RecipeGeneral;
import com.st.nyam.util.Constants;

public class DashboardActivity extends SherlockFragmentActivity implements EditNameDialogListener  {

	private final static String TAG = "DashboardActivity";
	private NyamApplication application;
	private DataBaseFactory db;
	private DialogFragmentActivity editNameDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Display mDisplay = this.getWindowManager().getDefaultDisplay();
		int width = mDisplay.getWidth();
		int height = mDisplay.getHeight();

		LayoutInflater inflater = LayoutInflater.from(this);
		RelativeLayout v = (RelativeLayout) inflater.inflate(
				R.layout.dashboard_layout, null);
		Drawable drawable;
		if (width < height) {
			Resources res = getResources(); // resource handle
			drawable = res.getDrawable(R.drawable.background_main);
		} else {
			Resources res = getResources(); // resource handle
			drawable = res.getDrawable(R.drawable.background_main_land);
		}
		v.setBackgroundDrawable(drawable);
		super.onCreate(savedInstanceState);
		setContentView(v);
		getSupportActionBar().hide();
		application = (NyamApplication) getApplication();
		db = application.getDB();
	}

	public void onClickFeature(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.home_btn_feature2:
			if (HttpFactory.isNetworkAvailable(this)) {
				Log.d(TAG, "home_btn_feature2 pressed");
				try {
					Intent intentRecipes = new Intent(this,
							MainListActivity.class);
					intentRecipes.setAction(Constants.ACTION_NEW_RECIPES);
					intentRecipes.putExtra("URL", Constants.URL_RECIPES
							+ Constants.JSON);
					startActivity(intentRecipes);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				showDialog(Constants.DAILOG_INTERNET_UNAVAILABLE);
			}
			break;
		case R.id.home_btn_feature4:
			Log.d(TAG, "home_btn_feature4 pressed");
			if (HttpFactory.isNetworkAvailable(this)) {
				Log.d(TAG, "Add button pressed Network eavailable");
				if (NyamApplication.isPasswordExists()) {
					Log.d(TAG, "Something stored");
					Intent intentFavorites = new Intent(this,
							MainListActivity.class);
					ArrayList<RecipeGeneral> recipes = db.getRecipes();
					intentFavorites.putExtra("Recipes", recipes);
					intentFavorites.setAction(Constants.ACTION_FAVORITE_RECIPES);
					intentFavorites.putExtra("URL", Constants.URL_RECIPES
							+ Constants.JSON);
					startActivityForResult(intentFavorites, 1);
				}
				else {
					showDialog();
				}
			} else {
				showDialog(Constants.DAILOG_INTERNET_UNAVAILABLE);
			}
			break;
		case R.id.home_btn_feature1:
			if (HttpFactory.isNetworkAvailable(this)) {
				Log.d(TAG, "Add button pressed Network eavailable");
				if (NyamApplication.isPasswordExists()) {
					Log.d(TAG, "application.isPasswordExists(): " + application.isPasswordExists());
					Log.d(TAG, "Pressed save to favorites password exists");
					//Open ProfileActivity
				}
				else {
					showDialog();
				}
			} else {
				showDialog(Constants.DAILOG_INTERNET_UNAVAILABLE);
			}
			showDialog();
			break;
		case R.id.home_btn_feature3:
			/*
			 * if(HttpFactory.isNetworkAvailable(this)) { Log.d(TAG,
			 * "home_btn_feature3 pressed"); ArrayList<MainCategory>
			 * mainCategories = db.getMainCategories(0); Log.d(TAG,
			 * "mainCategories: " + mainCategories); Intent mainCategoryIntent =
			 * new Intent(this, MainCategoryActivity.class);
			 * mainCategoryIntent.putExtra("MainCategories", mainCategories);
			 * mainCategoryIntent.putExtra("FirstLayer", true);
			 * mainCategoryIntent.setAction(Constants.ACTION_CATALOG_RECIPES);
			 * startActivity(mainCategoryIntent); } else {
			 * showDialog(Constants.DAILOG_INTERNET_UNAVAILABLE); }
			 */
			Intent mainCategoryIntent = new Intent(this,ExpandableCategoriesActivity.class);
			startActivity(mainCategoryIntent);
			break;
		default:
			break;
		}
	}


	public void showDialog() {
		android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
		editNameDialog = new DialogFragmentActivity();
        editNameDialog.show(fm, "fragment_edit_name");
	}
	
	@Override
    public void onFinishEditDialog(String login, String password) {
        try {
        	String [] params = {login, password};
        	new LoginingCheckout().execute(params);
        } catch (Exception e) {}
    }
	
	public void clearFields() {
		if (editNameDialog != null) {
			editNameDialog.clearFialds();
		}
	}
	
	private class LoginingCheckout extends AsyncTask<String, Void, Boolean> {
		
		@Override
		protected Boolean doInBackground(String... params) {
			boolean log = false;
			try {
				log = HttpFactory.isLoginned(params[0], params[1]);
				if (log) {
					application.setLogin(params[0]);
			        application.setPassword(params[1]);
			        application.storeLoginPassword();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return log;
		}
		
		@Override 
		protected void onPostExecute(Boolean result) {
			if (result) {
				if (editNameDialog != null) {
					editNameDialog.dismiss();
				}
			} else {
				DashboardActivity.this.clearFields();
			}
		}
	}

}
