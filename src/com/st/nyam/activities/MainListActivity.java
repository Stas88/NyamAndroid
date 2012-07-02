package com.st.nyam.activities;

import java.util.ArrayList;

import org.json.JSONException;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.st.nyam.NyamApplication;
import com.st.nyam.R;
import com.st.nyam.adapters.LazyAdapter;
import com.st.nyam.factories.ApiFactory;
import com.st.nyam.factories.DataBaseFactory;
import com.st.nyam.factories.HttpFactory;
import com.st.nyam.models.RecipeGeneral;
import com.st.nyam.services.SynchronizeService;
import com.st.nyam.util.Constants;

public class MainListActivity extends SherlockListActivity  implements OnScrollListener{

	private NyamApplication application;
	private DataBaseFactory db;
	//private ArrayList<RecipeGeneral> recipesGeneral;
	private static String TAG = "MainListActivity";
	private ArrayAdapter<RecipeGeneral> adapter;
	
	
	private int visibleThreshold = 5;
    private int currentPage = 0;
    
    private int previousTotal = 0;
    private boolean loading = true;
    private boolean isLastOne = true;
    int page = 1;
    private ProgressDialog dialog;
    private ArrayList<RecipeGeneral> tempList = new ArrayList<RecipeGeneral>();
    private String URL;
    private boolean isFavorites;
    private String action;
    private Bundle  appData;
    
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);  
		setSupportProgressBarIndeterminateVisibility(true);
		//getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		try{
			ProgressDialog.show(this.getApplicationContext(), "", 
	                "Loading. Please wait...", true);
		} catch(Exception e) {}
		application  = (NyamApplication)getApplication();
		db = application.getDB();
		Log.d(TAG, "Getting Application 123");
		action = getIntent().getAction();
		application = (NyamApplication)getApplication();
		Log.d(TAG, "Getting DB");
		Log.d(TAG, "Getting Recipes");
		Log.d(TAG, "Getting Application 123 1");
		URL = (String)getIntent().getSerializableExtra("URL");
		Log.d(TAG, "Getting Application 123 2");
		if (URL == null) {
			URL = Constants.URL_RECIPES + Constants.JSON;
		}
		Log.d(TAG, "URL: " + URL);
		Intent intent = getIntent();
		Log.d(TAG, "Getting Application 123 3");
		Log.d(TAG, "Intent: " + intent);
		Log.d(TAG, "IntentAction: " + intent.getAction());
		isFavorites = getIntent().getBooleanExtra("isFavorites", false);
		try {
			if (action.equals(Constants.ACTION_SEARCH)) {
				Log.d(TAG, "Constants.ACTION_SEARCH");
				appData = intent.getBundleExtra(SearchManager.APP_DATA);
				boolean isSearchFavorites = appData.getBoolean("isSearchFavorites");
				
				Log.d(TAG, "Is Search Favorites : " + isSearchFavorites);
				if (isSearchFavorites) {
					if (dialog != null && dialog.isShowing()) {
						try {
				   	        dialog.dismiss();
				   	        dialog = null;
				   	    } catch (Exception e) {}
					}
					Log.d(TAG, "Intent.ACTION_SEARCH");
					String query = intent.getStringExtra(SearchManager.QUERY);
					Log.d(TAG, "Search query: " + query);
					tempList = db.fetchRecipesByQuery(query);
					adapter = new LazyAdapter(this, R.layout.list_item_picture_text, tempList, true);
					Log.d(TAG, "Dialog Canceled");
					setListAdapter(adapter);
					
					Log.d(TAG, "Getting Application 123 4");
				} else {
					isFavorites = false;
					tempList.clear();
					Log.d(TAG, "Search not Favorites 1");
					String query = intent.getStringExtra(SearchManager.QUERY);
					Log.d(TAG, "Search not Favorites 2");
					adapter = new LazyAdapter(this, R.layout.list_item_picture_text, tempList, false);
					Log.d(TAG, "Search not Favorites 3");
					Object [] params = new Object[] {this, Constants.URL_SEARCH+Constants.JSON+"?search="+query};
					new AsyncSearchRequest().execute(params);
					Log.d(TAG, "Dialog Canceled");
					Log.d(TAG, "Search not Favorites 4");
					setListAdapter(adapter);
					Log.d(TAG, "Search not Favorites 5");
						
				}
			}
			
			else if (action.equals(Constants.ACTION_NEW_RECIPES)) {
				getSupportActionBar().setTitle("Новые");
				Log.d(TAG, "Getting Application 123 5");
				Log.d(TAG, "Next");
				Log.d(TAG, "Not Favorites");
				Log.d(TAG, "Getting Application 123 8");
				Log.d(TAG, "URL: " + URL);
				adapter = new LazyAdapter(this, R.layout.list_item_picture_text, tempList, false);
				setListAdapter(adapter);
				Object [] params = new Object[] {this, URL};
				Log.d(TAG, "Getting Application 123 9");
				new AsyncHttpGet().execute(params);
				Log.d(TAG, "Getting Application 123 10");
				
			} else if (action.equals(Constants.ACTION_FAVORITE_RECIPES)){
				getSupportActionBar().setTitle("Избранное");
				Log.d(TAG, "Constants.ACTION_FAVORITE_RECIPES");
				Log.d(TAG, "Favorites");
				Log.d(TAG, "Getting Application 123 6");
				Log.d(TAG, "Favorites 1");
				setSupportProgressBarIndeterminateVisibility(false);

				Log.d(TAG, "Favorites 5");
				if (dialog != null && dialog.isShowing()) {
					try {
			   	        dialog.dismiss();
			   	        dialog = null;
			   	    } catch (Exception e) {}
				}
				Log.d(TAG, "Favorites 6");
				Log.d(TAG, "Starting favorites");
				tempList = (ArrayList<RecipeGeneral>)getIntent().getSerializableExtra("Recipes");
				Log.d(TAG, "tempList = " + tempList);
				Log.d(TAG, "Favorites 7");
				adapter = new LazyAdapter(this, R.layout.list_item_picture_text, tempList, true);
				Log.d(TAG, "Favorites 8");
				setListAdapter(adapter);
				Log.d(TAG, "Favorites 9");
				
				if (HttpFactory.isNetworkAvailable(this)) { 
					Log.d(TAG, "Favorites 10");
					Log.d(TAG, "Starting Service");
					Intent serviceIntent = new Intent(this, SynchronizeService.class);
					serviceIntent.putExtra("receiver", new DownloadReceiver(new Handler()));
					String login = (String)getIntent().getStringExtra("login");
					String password = (String)getIntent().getStringExtra("password");
					Log.d(TAG, "login: " + login);
					Log.d(TAG, "password: " + password);
					serviceIntent.putExtra("login", login);
					serviceIntent.putExtra("password", password);
					startService(serviceIntent);
					Log.d(TAG, "Favorites 11");
				Log.d(TAG, "Service started");
				}
				
				
			} else if (action.equals(Constants.ACTION_CATALOG_RECIPES)) {
				Log.d(TAG, "Constants.ACTION_CATALOG_RECIPES");
				URL = (String)getIntent().getSerializableExtra("URL");
				adapter = new LazyAdapter(this, R.layout.list_item_picture_text, tempList, false);
				setListAdapter(adapter);
				Object [] params = new Object[] {this, URL};
				Log.d(TAG, "Getting Application 123 9");
				new AsyncHttpGet().execute(params);
			}
			ListView lv =  getListView();
			lv.setOnScrollListener(this);
			Log.d(TAG, "end Of create");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
    protected void onRestart() {
    	super.onRestart();
    	Log.d(TAG, "OnRestart invoked");
    	if (action.equals(Constants.ACTION_FAVORITE_RECIPES)) {
    		tempList =  db.getRecipes();
			adapter = new LazyAdapter(this, R.layout.list_item_picture_text, tempList, true);
			setListAdapter(adapter);
			if (dialog != null && dialog.isShowing()) {
				try {
		   	        dialog.dismiss();
		   	        dialog = null;
		   	    } catch (Exception e) {}
			}
    	}
    }

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Log.d(TAG, "OnListItemClick" + 1);
		super.onListItemClick(l, v, position, id);
		Log.d(TAG, "OnListItemClick" + 2);
		RecipeGeneral recipe = (RecipeGeneral)l.getItemAtPosition(position);
		Log.d(TAG, "recipe on click" + recipe);
		Intent intent = new Intent(v.getContext(), RecipeActivity.class);
		intent.putExtra("Recipe", recipe);
		Log.d(TAG, "OnListItemClick" + 3);
		if (action.equals(Constants.ACTION_FAVORITE_RECIPES)) {
			intent.setAction(Constants.ACTION_FAVORITE_RECIPES);
			startActivityForResult(intent, 1);
		} 
		if (action.equals(Constants.ACTION_SEARCH)) {
			boolean isSearchFavorites = appData.getBoolean("isSearchFavorites", false);
			Log.d(TAG, "isSearchFavorites: " + isSearchFavorites);
			if (isSearchFavorites) {
				intent.setAction(Constants.ACTION_FAVORITE_RECIPES);
				appData.putBoolean("isSearchFavorites", true);
				startActivityForResult(intent, 1);
			} else {
				Log.d(TAG, "OnListItemClick" + 6);
				intent.setAction(Constants.ACTION_NEW_RECIPES);
				startActivityForResult(intent, 1);
			}
		}  else if (action.equals(Constants.ACTION_NEW_RECIPES)) {
			Log.d(TAG, "OnListItemClick" + 6);
			intent.setAction(Constants.ACTION_NEW_RECIPES);
			startActivityForResult(intent, 1);
		} else if (action.equals(Constants.ACTION_CATALOG_RECIPES)) {
			intent.setAction(Constants.ACTION_NEW_RECIPES);
			startActivityForResult(intent, 1);
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK && action.equals(Constants.ACTION_FAVORITE_RECIPES)) {
	        setResult(RESULT_OK);
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     super.onActivityResult(requestCode, resultCode, data);
	     Log.d(TAG, "onActivityResult");
	     if(resultCode == RESULT_OK && requestCode == 1){
	    	 Log.d(TAG, "RESULT_OK && requestCode == 1");
	    	 RecipeGeneral deletedRecipe  = (RecipeGeneral)data.getSerializableExtra("deletedRecipe");
	    	 Log.d(TAG, "deletedRecipe.id = " + deletedRecipe.getId());
		     if (deletedRecipe != null) {
		    	 tempList.remove(findPositionById(deletedRecipe.getId(), tempList));
		    	 Log.d(TAG, "Deleted from list ");
		    	 adapter.notifyDataSetChanged();
		    	 Log.d(TAG, "Dataset Changed");
		     }
	     }
	     if(resultCode == Constants.RESULT_OK_REMOVE_FAV && requestCode == 1){
	    	 try {
		    	 Log.d(TAG, "Constants.RESULT_OK_REMOVE_FAV");
		    	 RecipeGeneral deletedRecipe  = (RecipeGeneral)data.getSerializableExtra("deletedRecipe");
		    	 Log.d(TAG, "deletedRecipe.id = " + deletedRecipe.getId());
			     if (deletedRecipe != null) {
			    	 int index = findPositionById(deletedRecipe.getId(), tempList);
			    	 Log.d(TAG, "index = " + index);
			    	 View child = getListView().getChildAt(index-1);
			    	 Log.d(TAG, "child = " + child);
			    	 ImageView star = (ImageView)child.findViewById(R.id.icon_little3);
			    	 Drawable d = this.getResources().getDrawable(R.drawable.favorite_dark);
			    	 star.setImageDrawable(d);
			    	 Log.d(TAG, "Star changed ");
			    	 adapter.notifyDataSetChanged();
			    	 Log.d(TAG, "Dataset Changed");
			     }
	    	 } catch (Exception e) {}
	     }
	     if(resultCode == Constants.RESULT_OK_ADD_FAV && requestCode == 1){
	    	 try {
		    	 Log.d(TAG, "Constants.RESULT_OK_A_FAV");
		    	 RecipeGeneral deletedRecipe  = (RecipeGeneral)data.getSerializableExtra("deletedRecipe");
		    	 Log.d(TAG, "deletedRecipe.id = " + deletedRecipe.getId());
			     if (deletedRecipe != null) {
			    	 int index = findPositionById(deletedRecipe.getId(), tempList);
			    	 Log.d(TAG, "index = " + index);
			    	 View child = getListView().getChildAt(index-1);
			    	 Log.d(TAG, "child = " + child);
			    	 ImageView star = (ImageView)child.findViewById(R.id.icon_little3);
			    	 Drawable d = this.getResources().getDrawable(R.drawable.favorites_yellow);
			    	 star.setImageDrawable(d);
			    	 Log.d(TAG, "Star changed ");
			    	 adapter.notifyDataSetChanged();
			    	 Log.d(TAG, "Dataset Changed");
			     }
	    	 } catch (Exception e) {}
	     }
	 }   

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())  {
		case R.id.menu_search: 
			onSearchRequested();
			return true;
		case R.id.catalog: 
			if(HttpFactory.isNetworkAvailable(this)) {
				Intent mainCategoryIntent = new Intent(this, ExpandableCategoriesActivity.class);
				startActivity(mainCategoryIntent);
			} else {
				showDialog(Constants.DAILOG_INTERNET_UNAVAILABLE);
			}
			return true;
		case 16908332: 
			Log.d(TAG, "logo");
			startActivity(new Intent(this, DashboardActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}		
	}
	
	@Override
	public boolean onSearchRequested() {
		Log.d(TAG, "Action :" + action);
	    Bundle appData = new Bundle();
	    if (action.equals(Constants.ACTION_FAVORITE_RECIPES)){
	    	appData.putBoolean("isSearchFavorites", true);
	    	Log.d(TAG, "In Search putted isSearchFavorites false");
	    } else {
	    	appData.putBoolean("isSearchFavorites", false);
	    	Log.d(TAG, "In Search putted isSearchFavorites true");
	    }
	    startSearch(null, false, appData, false);
	    return true;
	}

	
	private int findPositionById(int recipeId, ArrayList<RecipeGeneral> recipes) {
		Log.d(TAG, "findPositionById");
		int position = -1;
		for (RecipeGeneral recipe : recipes) {
			Log.d(TAG, "findPositionById 1");
			if (recipeId == recipe.getId()) {
				Log.d(TAG, "findPositionById 2");
				position = recipes.indexOf(recipe);
				Log.d(TAG, "findPositionById 3");
			}
		}
		Log.d(TAG, "Position = " + position);
		return position;
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
            int visibleItemCount, int totalItemCount) {
		Log.d(TAG, "onScroll isFavorites = " + isFavorites);
		Log.d(TAG, "onScroll Intent.ACTION_SEARCH = " + Intent.ACTION_SEARCH.equals(getIntent().getAction()));
		if (!action.equals(Constants.ACTION_FAVORITE_RECIPES) && !action.equals(Constants.ACTION_SEARCH)) {
			try {
				if (visibleItemCount > 0 && firstVisibleItem + visibleItemCount == totalItemCount && isLastOne) {
					isLastOne = false;
					Object [] params = new Object[] {this, URL +  "?page=" + String.valueOf(++page)};
					Log.d(TAG, "onScroll URL: " + params[1]);
					new AsyncHttpGet().execute(params);
					Log.d(TAG, "on Scroll Adding to list");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Log.d(TAG, "onScroll ActionSearch or Favorites  that is why not adding");
		}
	}
	
	@Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {}
    
	private class AsyncHttpGet extends AsyncTask<Object, String, ArrayList<RecipeGeneral>> {
	 
	    @Override
	    protected  ArrayList<RecipeGeneral> doInBackground(Object... params) {
	    	ArrayList<RecipeGeneral> listOfRecipes = null;
	    	Log.d(TAG, "AsyncHttpGet 1");
	    	try {
	    		listOfRecipes = ApiFactory.getRecipes((Context)params[0], (String)params[1]);
	    		Log.d(TAG, "AsyncHttpGet 2");
	    	} catch (JSONException e) {
	    		e.printStackTrace();
	    	}
	    	return listOfRecipes;
	    }
	    
	    @Override
	    protected void onPostExecute(ArrayList<RecipeGeneral> resultList) {
	    	Log.d(TAG, "AsyncHttpGet 3");
	        for (RecipeGeneral r : resultList) {
	        	adapter.add((RecipeGeneral)r);
	        	Log.d(TAG, "Recipe added to adapter");
	        }
	        isLastOne = true;
	        Log.d(TAG, "AsyncHttpGet 4");
	        setSupportProgressBarIndeterminateVisibility(false);
	        if (dialog != null && dialog.isShowing()) {
	        	try {
	       	        dialog.dismiss();
	       	        dialog = null;
	       	    } catch (Exception e) {}
			}
	    }
	}
	
	
	private class AsyncSearchRequest extends AsyncTask<Object, String, ArrayList<RecipeGeneral>> {
	    //private HashMap<String, String> mData = null;// post data
	    /**
	     * background
	     */
	    @Override
	    protected  ArrayList<RecipeGeneral> doInBackground(Object... params) {
	    	ArrayList<RecipeGeneral> resultList = null;
	    	try {
	    		
	    		Log.d(TAG, "AsyncSearchRequest doInBackground1");
	    		Log.d(TAG, "AsyncSearchRequest URL: " +(String)params[1]);
	    		resultList = ApiFactory.getRecipes((Context)params[0], (String)params[1]);
	    		Log.d(TAG, "AsyncSearchRequest URL: " +(String)params[1]);
	    		Log.d(TAG, "AsyncSearchRequest doInBackground2");
	    		
	    	} catch (JSONException e) {
	    		e.printStackTrace();
	    	}
	    	return resultList;
	    }
	    
	    @Override
	    protected void onPostExecute(ArrayList<RecipeGeneral> resultList) {
	        //recipesGeneral = resultList;
	    	Log.d(TAG, "AsyncSearchRequest doInBackground3");
	        for (RecipeGeneral r : resultList) {
	        	Log.d(TAG, "AsyncSearchRequest doInBackground4");
	        	adapter.add((RecipeGeneral)r);
	        }
	        setSupportProgressBarIndeterminateVisibility(false);
	        if (dialog != null && dialog.isShowing()) {
	        	try {
	       	        dialog.dismiss();
	       	        dialog = null;
	       	    } catch (Exception e) {}
			}
	    }
	}
	
	
	private class DownloadReceiver extends ResultReceiver {
		
	    public DownloadReceiver(Handler handler) {
	        super(handler);
	    }

	    @Override
	    protected void onReceiveResult(int resultCode, Bundle resultData) {
	        super.onReceiveResult(resultCode, resultData);
	        if (resultCode == Constants.AUTHORIZATION_PASSED) {
	        	Log.d(TAG, "onReceiveResult 1");
	        	tempList = (ArrayList<RecipeGeneral>)getIntent().getSerializableExtra("Recipes");
				adapter = new LazyAdapter(MainListActivity.this, R.layout.list_item_picture_text, tempList, true);
				setListAdapter(adapter);
				setSupportProgressBarIndeterminateVisibility(false);
				
	        	ArrayList<RecipeGeneral> recipes = (ArrayList<RecipeGeneral>) resultData.getSerializable("recipes_from_server");
	        	if (recipes != null) {
		        	for (RecipeGeneral r : recipes) {
		 	        	adapter.add((RecipeGeneral)r);
		 	        }
		        	if (dialog != null && dialog.isShowing()) {
		        		try {
		           	        dialog.dismiss();
		           	        dialog = null;
		           	    } catch (Exception e) {}
					}
	        	}
	        	setResult(RESULT_OK);
	        	Log.d(TAG, "onReceiveResult  RESULT_OK");
	        	Log.d(TAG, "onReceiveResult 2");
	        	return;
	        }
	        if (resultCode == Constants.AUTHORIZATION_NOT_PASSED) {
	        	setResult(RESULT_CANCELED);
	        	Log.d(TAG, "onReceiveResult RESULT_CANCELED");
	        	finish();
	        }
	    }
	}
	
}
