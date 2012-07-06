package com.st.nyam.factories;

import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.st.nyam.models.Profile;
import com.st.nyam.models.Recipe;
import com.st.nyam.models.RecipeGeneral;
import com.st.nyam.models.Step;
import com.st.nyam.util.ModelUtil;





public class ApiFactory {

	private static final String TAG = "Apifactory";

	public static ArrayList<RecipeGeneral> getRecipes(Context context, String url) throws JSONException {
		Log.d(TAG, "In ApiFactory getRecipes() 1");
		ArrayList<RecipeGeneral> recepies = new ArrayList<RecipeGeneral>();
		Log.d(TAG, "In ApiFactory getRecipes() 2");
		JSONArray jsonElements = JsonFactory.getJsonArrayFromUrl(url, context);
		Log.d(TAG, "In ApiFactory getRecipes() 3:" + jsonElements);
		if (jsonElements != null) {
			for (int i = 0; i < jsonElements.length(); i++) {
				recepies.add(ModelUtil.getRecipeGeneralFromJSON(jsonElements.getJSONObject(i)));
				Log.d(TAG, "In ApiFactory getRecipes() Adding every recipe");
			}
			Log.d(TAG, "In ApiFactory getRecipes() 4");
		}
		return recepies;
	}
	
	public static ArrayList<Step> getSteps( Context context, String url) throws JSONException, ParseException {
		ArrayList<Step> recepies = new ArrayList<Step>();
		Log.d(TAG, "In ApiFactory getSteps() ");
		JSONArray jsonElements = JsonFactory.getJsonArrayFromUrlByName(url, "steps", context);
		Log.d(TAG, "array = " + jsonElements.toString());
		for (int i = 0; i < jsonElements.length(); i++) {
			recepies.add(ModelUtil.getStepFromJSON(jsonElements.getJSONObject(i)));
		}
		return recepies;
	}
	
	public static Recipe getRecipe(Context context, String URL) throws JSONException {
		JSONObject object = JsonFactory.getJsonObjectFromUrl(URL, context);
		Recipe recipe = null;
		if (object != null) {
		Log.d(TAG, "object = " + object.toString());
		recipe = ModelUtil.getRecipeFromJSON(object);
		Log.d(TAG, "recipe = " + recipe.toString());
		}
		return recipe;
	}
	
	public static Profile getProfile() throws JSONException {
		Profile profile = null;
		JSONObject object = JsonFactory.getProfileFromUrl();
		if (object != null) {
			Log.d(TAG, "object = " + object.toString());
			profile = ModelUtil.getProfileFromJSON(object);
			Log.d(TAG, "profile = " + profile.toString());
		}
		return profile;
	}
	

	
	


}
