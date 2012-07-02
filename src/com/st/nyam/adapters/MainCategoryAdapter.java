package com.st.nyam.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.st.nyam.R;
import com.st.nyam.models.MainCategory;

	
public class MainCategoryAdapter extends ArrayAdapter<MainCategory>{
	
	private String TAG = "MainCategoryAdapter";
	private Context context; 
    private int layoutResourceId;   
    private ArrayList<MainCategory> categories = null;
    private static LayoutInflater inflater=null;
    
	public MainCategoryAdapter(Context context, int layoutResourceId, ArrayList<MainCategory> categories) {
		super(context, layoutResourceId, categories);
		this.layoutResourceId = layoutResourceId;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		this.categories = categories;
	}
	
	public int getCount() {
        return categories.size();
    }
	
	public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if(convertView==null)
        	row = inflater.inflate(layoutResourceId, parent, false);
        MainCategory category = categories.get(position);
        Log.d(TAG, "Cetegory: "  + category.getName());
        TextView nameView = (TextView)row.findViewById(android.R.id.text1);
        nameView.setText(category.getName());
        return row;
    }
}
