package com.st.nyam.factories;

import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.st.nyam.models.GeneralRecipe;
import com.st.nyam.util.ModelUtil;





public class ApiFactory {

	private static final String TAG = ApiFactory.class.getName();
	private static String URL = "http://nyam.ru/recipes.json";

	public static ArrayList<GeneralRecipe> getRecipes() throws JSONException, ParseException {
		ArrayList<GeneralRecipe> recepies = new ArrayList<GeneralRecipe>();
		JSONArray jsonElements = JsonFactory.getJsonArrayFromUrl(URL);
		for (int i = 0; i < jsonElements.length(); i++) {
			recepies.add(ModelUtil.getRecipeFromJSON(jsonElements.getJSONObject(i)));
		}
		return recepies;
	}

}
