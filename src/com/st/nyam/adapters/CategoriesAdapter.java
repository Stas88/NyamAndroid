package com.st.nyam.adapters;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.SimpleExpandableListAdapter;
import com.st.nyam.activities.ExpandableCategoriesActivity;


public class CategoriesAdapter extends SimpleExpandableListAdapter {
	
	private Context context;
	private ArrayList<Map<String, String>> groupData;
	
	public CategoriesAdapter(
			ExpandableCategoriesActivity expandableCategoriesActivity,
			ArrayList<Map<String, String>> groupData, int expandableFirstLayer,
			String[] groupFrom, int[] groupTo,
			ArrayList<ArrayList<Map<String, String>>> childData,
			int expandableSecondLayer, String[] childFrom, int[] childTo) {
			super(expandableCategoriesActivity,groupData, expandableFirstLayer, groupFrom, groupTo, childData, expandableSecondLayer,childFrom, childTo);
			context = expandableCategoriesActivity.getBaseContext();
			this.groupData = groupData; 
			
	}
	
	

	

	
	

}
