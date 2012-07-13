package com.st.nyam.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.st.nyam.NyamApplication;
import com.st.nyam.R;
import com.st.nyam.factories.DataBaseFactory;
import com.st.nyam.models.RecipeGeneral;
import com.st.nyam.util.Constants;
import com.st.nyam.util.listUtil.ImageLoader;

public class LazyAdapter extends ArrayAdapter<RecipeGeneral> {
    
    private Context context; 
    private int layoutResourceId;    
    private String TAG = "LazyAdapter";
    private ArrayList<RecipeGeneral> recipes = null;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    private NyamApplication application;
	private DataBaseFactory db;
   
    private boolean isFavorites;
    
    public LazyAdapter(Context context, int layoutResourceId, ArrayList<RecipeGeneral> recipes, boolean isFavorites) {
		super(context, layoutResourceId, recipes);
		this.layoutResourceId = layoutResourceId;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(context.getApplicationContext());
		this.context = context;
		this.recipes = recipes;
		this.isFavorites = isFavorites;
		application  = (NyamApplication)context.getApplicationContext();
		db = application.getDB();
	}
    
 


    public int getCount() {
        return recipes.size();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row =convertView;
        if(convertView==null)
        	row = inflater.inflate(layoutResourceId, parent, false);
        
        TextView favoritesView = (TextView)row.findViewById(R.id.txtTitle3);
        TextView txtTitle = (TextView)row.findViewById(R.id.txtTitle);
        
        ImageView imgIcon = (ImageView)row.findViewById(R.id.icon);
        RecipeGeneral recipe = recipes.get(position);
        Log.d(TAG, "Recipe: "  + recipe.getTitle());
        if (favoritesView != null) {
        	favoritesView.setText(String.valueOf(recipe.getFavorites_by()));
        }
        if (txtTitle != null ) {
        	txtTitle.setText(recipe.getTitle());
        }
        
        TextView ratingView = (TextView)row.findViewById(R.id.txtTitle2);
        TextView cooked_dishesView = (TextView)row.findViewById(R.id.txtTitle1);
        
        ratingView.setText(String.valueOf(recipe.getRating()));
        cooked_dishesView.setText(String.valueOf(recipe.getCooked_dishes_count()));
        if (db.isRecipeExists(recipe.getId())) {
        	ImageView favorites = (ImageView)row.findViewById(R.id.icon_little3);
        	Drawable yellow_star = context.getResources().getDrawable(R.drawable.favorites_yellow);
        	favorites.setImageDrawable(yellow_star);
        } else {
        	ImageView favorites = (ImageView)row.findViewById(R.id.icon_little3);
        	Drawable dark_star = context.getResources().getDrawable(R.drawable.favorite_dark);
        	favorites.setImageDrawable(dark_star);
        }
        /*
        if (isFavorites == true ) {
        	Log.d(TAG, "isFavorites: "  + isFavorites);
        	Log.d(TAG, "Recipe.getImg_url()"  + recipe.getImg_url());
        	Log.d(TAG, "Path to image : "  + Environment.getExternalStorageDirectory().toString() + 
        			"/Nyam/NyamRecipesFavorites/" + recipe.getImg_url().replace('/', '&'));
        	Bitmap bitmap = BitmapFactory.decodeFile(
        			Environment.getExternalStorageDirectory().toString() + 
        			"/Nyam/NyamRecipesFavorites/" + recipe.getImg_url().replace('/', '&'));
        	Log.d(TAG, "Bitmap: "  + bitmap);
        	imgIcon.setImageBitmap(bitmap);ыеги
        } else {
        */
    	Log.d(TAG, "Not favorite");
    	imageLoader.displayImage(Constants.URL+recipe.getImg_url(), imgIcon, isFavorites, recipe);
    	Log.d(TAG, "URL+recipe.getImg_url()" + Constants.URL+recipe.getImg_url());
        //}
        //if (position == getCount() - 1) {
            //loadNextPage();
        return row;
    }
    
    
}