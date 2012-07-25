package com.st.nyam.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.st.nyam.R;
import com.st.nyam.models.Ingredient;


public class IngredientsActivity extends Activity {
	
	
	private ArrayList<Ingredient> ingredients;
	private Bitmap bitmap;
	private final String TAG = "IngredientsActivity";
	private boolean isExpanded = false;
	private TextView ingredientsV;
	public void onCreate (Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.ingredients_layout);
		ingredients = (ArrayList)getIntent().getSerializableExtra("ingredients");
		bitmap = (Bitmap)getIntent().getParcelableExtra("bitmap");
		
		TextView user = (TextView)findViewById(R.id.user);
		TextView about = (TextView)findViewById(R.id.about);
		ingredientsV = (TextView)findViewById(R.id.ingredients);
		StringBuilder ingredientsString = new StringBuilder();
		for (Ingredient i : ingredients) {
			 
			    String v = i.getValue();
				if (!v.equals("null")) {
					ingredientsString.append("- " + i.getName() + " " + v + i.getType() + "\n");
				} else {
					ingredientsString.append("- " + i.getName() + " " + i.getType() + "\n");
				}
		 }
		ingredientsV.setText(ingredientsString);
		user.setText("Albert Einstein");
		about.setText("Albert Einstein (play /ˈælbərt ˈaɪnstaɪn/; German: [ˈalbɐt ˈaɪnʃtaɪn] ( listen); 14 March 1879 – 18 April 1955) was a German theoretical physicist who developed the theory of general relativity, effecting a revolution in physics. For thisfor his services to theoretical physics, and especially for his discovery of the law of the photoelectric effect.[5] The latter was pivotal in establishing quantum theory within physics.");
		RelativeLayout relativeclic1 = (RelativeLayout)findViewById(R.id.layoutClick);
        relativeclic1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(isExpanded) {
                	ingredientsV.setVisibility(View.GONE);
                	isExpanded = false;
                }
                else {
                	ingredientsV.setVisibility(View.VISIBLE);
                	isExpanded = true;
                }
            }
        });

	}
	
	

	/*
	private void prepareIngredients() {
		  SimpleExpandableListAdapter adapter;
		  final ExpandableListView list = (ExpandableListView)findViewById(R.id.elvMain);
		  
		  ArrayList<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
		  Map m  = new HashMap<String, String>();
		  m.put("groupName", "Datas");
		  groupData.add(m);
		  
		  
	      String groupFrom[] = new String[] {"groupName"};
	      
	      int groupTo[] = new int[] {R.id.groupname};
	     
	      
		 ArrayList<ArrayList<Map<String, String>>> childData = new ArrayList<ArrayList<Map<String, String>>>();
		 ArrayList<Map<String, String>> childDataItem = new ArrayList<Map<String, String>>();
	     for (Ingredient i : ingredients) {
	    	    Log.d(TAG, "Ingridient: " + i);
			    Map<String, String> m1 = new HashMap<String, String>();
			    StringBuilder ingredientsString = new StringBuilder();
			    String v = i.getValue();
				if (!v.equals("null")) {
					ingredientsString.append("- " + i.getName() + " " + v + i.getType());
				} else {
					ingredientsString.append("- " + i.getName() + " " + i.getType());
				}
			    m1.put("levelTwoCat", ingredientsString.toString());
			    childDataItem.add(m1);
	     }
	     childData.add(childDataItem);
	     
	     String childFrom[] = new String[] {"levelTwoCat"};
	     int childTo[] = new int[] {R.id.ingredients_second_layer_text};
	     
	     adapter = new SimpleExpandableListAdapter (
		            this,
		            groupData,
		            R.layout.ingredients_first_layer,
		            groupFrom,
		            groupTo,
		            childData,
		            R.layout.ingredients_second_layer,
		            childFrom,
		            childTo);
		
		 list.setAdapter(adapter);       
	}
	
	*/
}
