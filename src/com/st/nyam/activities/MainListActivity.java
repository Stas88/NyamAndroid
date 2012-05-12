package com.st.nyam.activities;

import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONException;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.st.nyam.NyamApplication;
import com.st.nyam.R;
import com.st.nyam.adapters.MainListArrayAdapter;
import com.st.nyam.factories.ApiFactory;
import com.st.nyam.factories.DataBaseFactory;
import com.st.nyam.models.GeneralRecipe;

public class MainListActivity extends SherlockListActivity  {

	private NyamApplication application;
	private ArrayList<GeneralRecipe> recipes;
	private DataBaseFactory db;
	private static String TAG = "MainListActivity";

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		recipes = new ArrayList<GeneralRecipe>();
		try {	
			Log.i(TAG, "Getting Application");
			application = (NyamApplication)getApplication();
			Log.i(TAG, "Getting DB");
			//db = application.getDB();
			Log.i(TAG, "Getting Recipes");
			//recipes = db.getRecipes();
			recipes =  ApiFactory.getRecipes();
			Log.i(TAG, "Getting Adding Every Recipe");
			//View view = findViewById(R.layout.list_item_picture_text);
			//view.setBackgroundColor(R.drawable.list_item_selector);
			setListAdapter(new MainListArrayAdapter(this, R.layout.list_item_picture_text, recipes));
			getSupportActionBar().setDisplayShowTitleEnabled(false);
			//Получаем Intent
			Intent intent = getIntent();
			//Проверяем тип Intent
			if (Intent.ACTION_SEARCH.equals(intent.getAction())) { 
				//Берем строку запроса из экстры
				String query = intent.getStringExtra(SearchManager.QUERY);
				//Выполняем поиск
				//showResults(query);
			}
		} catch (ParseException e) {
			Toast.makeText(this, "Error occured" + e.getMessage(), 200);
		} catch (JSONException e) {
			Toast.makeText(this, "Error occured" + e.getMessage(), 200);
		}
	}


	/*
	private void showResults(String query) throws ParseException {
	    //Ищем совпадения
	    recipes = db.fetchRecipesByQuery(query);

	    //Обновляем адаптер
	    setListAdapter(new MyArrayAdapter(this, R.layout.list_item_picture_text, recipes));
	  }
	 */

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		GeneralRecipe recipe = (GeneralRecipe)l.getItemAtPosition(position);
		Intent myIntent = new Intent(v.getContext(), RecipeActivity.class);
		myIntent.putExtra("Recipe", recipe);
		myIntent.putExtra("Picture", "http://kuharka.com/uploads/posts/2011-12/1323081421_ovoshhnoj-salat-iz-krevetok-s-limonom.jpeg");
		startActivity(myIntent);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main_menu, (com.actionbarsherlock.view.Menu) menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())  {
		case R.id.menu_search: 
			onSearchRequested();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}
}
