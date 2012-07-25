package com.st.nyam.activities;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
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
		getSupportActionBar().hide();
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
			break;
		case R.id.home_btn_feature1:
			if (HttpFactory.isNetworkAvailable(this)) {
				Log.d(TAG, "Add button pressed Network eavailable");
				if (NyamApplication.isPasswordExists()) {
					Log.d(TAG, "application.isPasswordExists(): " + application.isPasswordExists());
					Log.d(TAG, "Pressed go to Profile");
					Intent profileIntent = new Intent(this,ProfileActivity.class);
					startActivity(profileIntent);
				}
				else {
					showDialog();
				}
			} else {
				showDialog(Constants.DAILOG_INTERNET_UNAVAILABLE);
			}
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
			if (HttpFactory.isNetworkAvailable(this)) {
				Intent mainCategoryIntent = new Intent(this,ExpandableCategoriesActivity.class);
				startActivity(mainCategoryIntent);
			} else {
				showDialog(Constants.DAILOG_INTERNET_UNAVAILABLE);
			}
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
	
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialogCurrent = null;
		AlertDialog.Builder builder;
		switch(id){
			case Constants.DAILOG_INTERNET_UNAVAILABLE:
				builder = new AlertDialog.Builder(this);
				builder.setMessage("Интернет недоступен")
			       .setCancelable(false)
			       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	try {
			       	        dialog.dismiss();
			       	        dialog = null;
			       	    } catch (Exception e) {
			       	        Log.d(TAG, e.getMessage());
			       	    }
			           }
			       });
				dialogCurrent = builder.create();;
				break;
		}
		return dialogCurrent;
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.dashboard_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	

   
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())  {
			case R.id.exit: 
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}		
	}
	
}
