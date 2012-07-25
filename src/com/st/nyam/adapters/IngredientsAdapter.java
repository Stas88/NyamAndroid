package com.st.nyam.adapters;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.widget.SimpleExpandableListAdapter;

import com.st.nyam.activities.ExpandableCategoriesActivity;
import com.st.nyam.activities.RecipeActivity;

public class IngredientsAdapter extends SimpleExpandableListAdapter {
	
	private Context context;
	private ArrayList<Map<String, String>> groupData;
	
	public IngredientsAdapter(
			RecipeActivity recipeActivity,
			ArrayList<Map<String, String>> groupData, int expandableFirstLayer,
			String[] groupFrom, int[] groupTo,
			ArrayList<ArrayList<Map<String, String>>> childData,
			int expandableSecondLayer, String[] childFrom, int[] childTo) {
			super(recipeActivity,groupData, expandableFirstLayer, groupFrom, groupTo, childData, expandableSecondLayer,childFrom, childTo);
			context = recipeActivity.getBaseContext();
			this.groupData = groupData; 
			
	}

}
