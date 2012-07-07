package com.st.nyam.activities;

import java.util.ArrayList;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.SimpleExpandableListAdapter;

import com.actionbarsherlock.app.SherlockExpandableListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.st.nyam.NyamApplication;
import com.st.nyam.R;
import com.st.nyam.adapters.CategoriesAdapter;
import com.st.nyam.factories.DataBaseFactory;
import com.st.nyam.models.MainCategory;
import com.st.nyam.util.Constants;

public class ExpandableCategoriesActivity extends SherlockExpandableListActivity implements OnChildClickListener {
	
		  private NyamApplication application;
		  private static final String TAG = "ExpandableListView";
		  private SimpleExpandableListAdapter adapter;
		 
		  ArrayList<ArrayList<Map<String, String>>> childData;
		  Map<String, String> m;
		  private ArrayList<Map<String, String>> groupData;
		  private DataBaseFactory db;


	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        LayoutInflater inflater = LayoutInflater.from(this);
	        ExpandableListView list = (ExpandableListView)inflater.inflate(R.layout.main, null);
	        list.setOnGroupExpandListener(new OnGroupExpandListener() { 
	        	
	            @Override 
	            public void onGroupExpand(int groupPosition) { 
	            	View v = adapter.getGroupView(groupPosition, true, null, null);
	                v.setBackgroundColor(R.color.orange_expanded_upper);
	            } 
	        }); 

	        list.setOnGroupCollapseListener(new OnGroupCollapseListener() { 
	            @Override 
	            public void onGroupCollapse(int groupPosition) { 
	            	View v = adapter.getGroupView(groupPosition, true, null, null);
	                v.setBackgroundColor(R.color.white);
	            } 
	        }); 
	        setContentView(list);
	        
	        getSupportActionBar().setTitle("Категории");
	        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	        
	        
	        application  = (NyamApplication)getApplication();
			db = application.getDB();
			groupData = application.getFirstLayer();
	        String groupFrom[] = new String[] {"groupName"};
	        int groupTo[] = new int[] {R.id.expandable_first_layer};
	        childData = application.getSecondLayer();
	        String childFrom[] = new String[] {"levelTwoCat"};
	        int childTo[] = new int[] {R.id.expandable_second_layer_text};
	        
	        adapter = new CategoriesAdapter (
	            this,
	            groupData,
	            R.layout.expandable_first_layer,
	            groupFrom,
	            groupTo,
	            childData,
	            R.layout.expandable_second_layer,
	            childFrom,
	            childTo);
	        setListAdapter(adapter);
	        
	        list.setOnGroupExpandListener(new OnGroupExpandListener() { 
	            @Override 
	            public void onGroupExpand(int groupPosition) { 
	            	adapter.getGroup(groupPosition);
	            } 
	        }); 

	        list.setOnGroupCollapseListener(new OnGroupCollapseListener() { 
	            @Override 
	            public void onGroupCollapse(int groupPosition) { 
	                // code to set your background back to your collapsed color
	            } 
	        }); 
	    }
	    

		@Override
		public boolean onChildClick(android.widget.ExpandableListView parent,
				View v, int groupPosition, int childPosition, long id) {
			String name = (String)((Map<String, String>)adapter.getChild(groupPosition, childPosition)).get("levelTwoCat");
			MainCategory category = db.getMainCategoriesbyName(name);
			Intent intent = new Intent(v.getContext(), MainListActivity.class);
			intent.putExtra("URL", Constants.URL_RECIPES+"/"+category.getCached_slug()+Constants.JSON);
			intent.setAction(Constants.ACTION_CATALOG_RECIPES);
			startActivity(intent);
			return super.onChildClick(parent, v, groupPosition, childPosition, id);
		}
		
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
			inflater.inflate(R.menu.main_menu,
					(com.actionbarsherlock.view.Menu) menu);
			MenuItem catalogItem =  menu.getItem(1);
			catalogItem.setVisible(false);
			return super.onCreateOptionsMenu(menu);
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			Log.d(TAG, "In onOptionsItemSelected");
			Log.d(TAG, "In item.getItemId() = " + item.getItemId());
			switch(item.getItemId())  {
				case R.id.menu_search: 
					Log.d(TAG, "menu_search");
					onSearchRequested();
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
		    Bundle appData = new Bundle();
		    startSearch(null, false, appData, false);
		    return true;
		}


	}