package com.st.nyam.util;

import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.database.Cursor;
import android.util.Log;

import com.st.nyam.models.MainCategory;
import com.st.nyam.models.Recipe;
import com.st.nyam.models.RecipeGeneral;
import com.st.nyam.models.Step;

public class ModelUtil {

	private final static String TAG = "ModelUtil";

	/*
	 * public static Recipe getRecipeFromCursor(Cursor c) throws ParseException
	 * { Recipe recipe = new Recipe();
	 * recipe.setId(c.getInt(c.getColumnIndex("id")));
	 * recipe.setTitle(c.getString(c.getColumnIndex("title")));
	 * recipe.setDescription(c.getString(c.getColumnIndex("description")));
	 * recipe.setCook_time(c.getInt(c.getColumnIndex("cook_time")));
	 * recipe.setServ_num(c.getInt(c.getColumnIndex("serv_num")));
	 * recipe.setUser_id(c.getInt(c.getColumnIndex("user_id"))); if
	 * (c.getString(c.getColumnIndex("created_at")) != null) {
	 * recipe.setCreated_at(new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(c.getString
	 * (c.getColumnIndex("created_at")))); } if
	 * (c.getString(c.getColumnIndex("updated_at")) != null) {
	 * recipe.setUpdated_at(new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(c.getString
	 * (c.getColumnIndex("updated_at")))); }
	 * recipe.setMain_photo_file_name(c.getString
	 * (c.getColumnIndex("main_photo_file_name")));
	 * recipe.setMain_photo_content_type
	 * (c.getString(c.getColumnIndex("main_photo_content_type")));
	 * recipe.setMain_photo_file_size
	 * (c.getInt(c.getColumnIndex("main_photo_file_size")));
	 * recipe.setViews(c.getInt(c.getColumnIndex("views")));
	 * recipe.setCached_slug(c.getString(c.getColumnIndex("cached_slug")));
	 * recipe.setDelta(c.getInt(c.getColumnIndex("delta")));
	 * recipe.setMain_category_id
	 * (c.getInt(c.getColumnIndex("main_category_id")));
	 * recipe.setStatus(c.getInt(c.getColumnIndex("status")));
	 * recipe.setMain_photo_processing
	 * (c.getInt(c.getColumnIndex("main_photo_processing")));
	 * recipe.setHide_watermark_text
	 * (c.getInt(c.getColumnIndex("hide_watermark_text")));
	 * recipe.setVk_comments_count
	 * (c.getInt(c.getColumnIndex("vk_comments_count")));
	 * recipe.setTitle2_genitive
	 * (c.getString(c.getColumnIndex("title2_genitive")));
	 * recipe.setPublication_on_main
	 * (c.getInt(c.getColumnIndex("publication_on_main"))); if
	 * (c.getString(c.getColumnIndex("publication_on_main_date")) != null) {
	 * recipe.setPublication_on_main_date(new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"
	 * ).parse(c.getString(c.getColumnIndex("publication_on_main_date")))); }
	 * recipe
	 * .setRecipe_variation_id(c.getInt(c.getColumnIndex("recipe_variation_id"
	 * )));
	 * recipe.setParent_variation_id(c.getInt(c.getColumnIndex("parent_variation_id"
	 * )));
	 * recipe.setVk_likes_count(c.getInt(c.getColumnIndex("vk_likes_count")));
	 * recipe
	 * .setGoogle_plus_count(c.getInt(c.getColumnIndex("google_plus_count")));
	 * recipe
	 * .setFacebook_likes_count(c.getInt(c.getColumnIndex("facebook_likes_count"
	 * ))); recipe.setTwitter_tweets_count(c.getInt(c.getColumnIndex(
	 * "recipe_variation_id"))); return recipe; }
	 */

	public static Step getStepFromCursor(Cursor c) throws ParseException {
		Step step = new Step();
		step.setNumber(c.getInt(c.getColumnIndex("id")));
		step.setInstruction(c.getString(c.getColumnIndex("body")));
		step.setImg_url(c.getString(c.getColumnIndex("photo_file_name")));
		/*
		 * step.setPhoto_content_type(c.getString(c.getColumnIndex(
		 * "photo_content_type")));
		 * step.setPhoto_file_size(c.getInt(c.getColumnIndex
		 * ("photo_file_size"))); step.setPhoto_updated_at(new
		 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"
		 * ).parse(c.getString(c.getColumnIndex("photo_updated_at"))));
		 * step.setCreated_at(new
		 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(c
		 * .getString(c.getColumnIndex("created_at")))); step.setUpdated_at(new
		 * SimpleDateFormat
		 * ("yyyy-MM-dd HH:mm:ss").parse(c.getString(c.getColumnIndex
		 * ("updated_at"))));
		 * step.setPhoto_processing(c.getInt(c.getColumnIndex(
		 * "photo_processing")));
		 */
		return step;
	}

	public static Recipe getRecipeFromCursor(Cursor c) {
		Recipe recipe = new Recipe();
		Log.d(TAG, "id = " + c.getInt(c.getColumnIndex("id")));
		recipe.setId(c.getInt(c.getColumnIndex("id")));
		Log.d(TAG, "path = " + c.getString(c.getColumnIndex("path")));
		recipe.setPath(c.getString(c.getColumnIndex("path")));
		Log.d(TAG, "item_id = " + c.getInt(c.getColumnIndex("item_id")));
		recipe.setItem_id(c.getInt(c.getColumnIndex("item_id")));
		Log.d(TAG, "title = " + c.getString(c.getColumnIndex("title")));
		recipe.setTitle(c.getString(c.getColumnIndex("title")));
		Log.d(TAG,
				"description = " + c.getString(c.getColumnIndex("description")));
		recipe.setDescription(c.getString(c.getColumnIndex("description")));
		Log.d(TAG, "user = " + c.getString(c.getColumnIndex("user_id")));
		recipe.setUser(c.getString(c.getColumnIndex("user_id")));
		Log.d(TAG,
				"favorites_by = " + c.getInt(c.getColumnIndex("favorites_by")));
		recipe.setFavorites_by(c.getInt(c.getColumnIndex("favorites_by")));
		Log.d(TAG,
				"rating = " + c.getInt(c.getColumnIndex("rating")));
		recipe.setRating(c.getInt(c.getColumnIndex("rating")));
		Log.d(TAG,
				"cooked_dishes_count = " + c.getInt(c.getColumnIndex("cooked_dishes_count")));
		recipe.setCooked_dishes_count(c.getInt(c.getColumnIndex("cooked_dishes_count")));
		Log.d(TAG,
				"img_url = "
						+ c.getString(c.getColumnIndex("main_photo_file_name")));
		recipe.setImg_url(c.getString(c.getColumnIndex("main_photo_file_name")));
		return recipe;
	}
	
	public static MainCategory getMainCategoryFromCursor(Cursor c) {
		MainCategory mainCategory = new MainCategory();
		Log.d(TAG, "id = " + c.getInt(c.getColumnIndex("id")));
		mainCategory.setId(c.getInt(c.getColumnIndex("id")));
		Log.d(TAG, "name = " + c.getString(c.getColumnIndex("name")));
		mainCategory.setName(c.getString(c.getColumnIndex("name")));
		Log.d(TAG, "cached_slug = " + c.getString(c.getColumnIndex("cached_slug")));
		mainCategory.setCached_slug(c.getString(c.getColumnIndex("cached_slug")));
		Log.d(TAG, "parent_id = " + c.getInt(c.getColumnIndex("parent_id")));
		mainCategory.setParent_id(c.getInt(c.getColumnIndex("parent_id")));
		return mainCategory;
	}
	

	public static RecipeGeneral getRecipeGeneralFromJSON(JSONObject object)
			throws JSONException {
		RecipeGeneral recipe = new RecipeGeneral();
		Log.d(TAG, "object = " + object.toString());
		Log.d(TAG, "id = " + object.getInt("id"));
		recipe.setId(object.getInt("id"));
		Log.d(TAG, "title = " + object.getString("title"));
		recipe.setTitle(object.getString("title"));
		Log.d(TAG, "path = " + object.getString("path"));
		recipe.setPath(object.getString("path"));
		Log.d(TAG, "img_url = " + object.getString("img_url"));
		recipe.setImg_url(object.getString("img_url"));
		Log.d(TAG, "rating = " + object.getString("rating"));
		recipe.setRating(object.getInt("rating"));
		Log.d(TAG, "favorites_by = " + object.getString("favorites_by"));
		recipe.setFavorites_by(object.getInt("favorites_by"));
		Log.d(TAG, "cooked_dishes_count = " + object.getString("cooked_dishes_count"));
		recipe.setCooked_dishes_count(object.getInt("cooked_dishes_count"));
		return recipe;
	}

	public static Recipe getRecipeFromJSON(JSONObject object)
			throws JSONException {
		Recipe recipe = new Recipe();
		Log.d(TAG, "getRecipeFromJSON() object = " + object.toString());
		Log.d(TAG, "id = " + object.getInt("id"));
		recipe.setId(object.getInt("id"));
		Log.d(TAG, "title = " + object.getString("title"));
		recipe.setTitle(object.getString("title"));
		Log.d(TAG, "description = " + object.getString("description"));
		recipe.setDescription(object.getString("description"));
		JSONObject jsonObject = object.getJSONObject("user");
		String user = jsonObject.getString("nick");
		Log.d(TAG, "user = " + user);
		recipe.setUser(user);
		Log.d(TAG, "favorites_by = " + object.getInt("favorites_by"));
		recipe.setFavorites_by(object.getInt("favorites_by"));
		Log.d(TAG, "rating = " + object.getInt("rating"));
		recipe.setRating(object.getInt("rating"));
		Log.d(TAG, "cooked_dishes_count = " + object.getInt("cooked_dishes_count"));
		recipe.setCooked_dishes_count(object.getInt("cooked_dishes_count"));
		Log.d(TAG, "img_url = " + object.getString("img_url"));
		recipe.setImg_url(object.getString("img_url"));
		Log.d(TAG, "img_url = " + object.getString("img_url"));
		recipe.setImg_url(object.getString("img_url"));
		Log.d(TAG, "path = " + object.getString("path"));
		recipe.setPath(object.getString("path"));
		return recipe;
	}

	public static Step getStepFromJSON(JSONObject object) throws JSONException {
		Step step = new Step();
		Log.d(TAG, "StepsObject = " + object.toString());
		Log.d(TAG, "Number = " + object.getInt("number"));
		step.setNumber(object.getInt("number"));
		Log.d(TAG, "Instruction = " + object.getString("instruction"));
		step.setInstruction(object.getString("instruction"));
		String img = object.getString("img_url");
		Log.d(TAG, "img = " + img);
		step.setImg_url(img.replaceAll("[?].*", ""));
		Log.d(TAG, "After replacing ?++");
		return step;
	}
	
	public static ArrayList<RecipeGeneral> getRecipesFromJSONString(String result2) {
		ArrayList<RecipeGeneral> recipes = new ArrayList<RecipeGeneral>();
		try {
			JSONArray jsonElements = new JSONArray(result2);
			Log.d(TAG, "In MainListActivity getRecipes(): " + jsonElements);
			Log.d(TAG, "jsonElements.length(): " + jsonElements.length());
			for (int i = 0; i < jsonElements.length(); i++) {
				Recipe favRecipe = (ModelUtil.getRecipeFromJSON(jsonElements.getJSONObject(i)));
				
				Log.d(TAG, "Recipe: " + jsonElements.getJSONObject(i));
				
				JSONArray stepsArray = jsonElements.getJSONObject(i).getJSONArray("steps");
				Log.d(TAG, "Steps: " + jsonElements.getJSONObject(i).getJSONArray("steps"));
				ArrayList<Step> favSteps = new ArrayList<Step>();
				
				for (int j = 0; j < stepsArray.length(); j++) {
					Step step = ModelUtil.getStepFromJSON(stepsArray.getJSONObject(j));
					Log.d(TAG, "Step: " + step);
					favSteps.add(step);
				}
				Log.d(TAG, "Steps ArrayList: " + favSteps);
				favRecipe.setSteps(favSteps);
				recipes.add((RecipeGeneral)favRecipe);
				Log.d(TAG, "In MainListActivity getRecipes() Adding every recipe");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recipes;
	}

}
