package com.st.nyam.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.database.Cursor;
import android.util.Log;

import com.st.nyam.models.MainCategory;
import com.st.nyam.models.Profile;
import com.st.nyam.models.Recipe;
import com.st.nyam.models.RecipeGeneral;
import com.st.nyam.models.Step;

public class ModelUtil {

	private final static String TAG = "ModelUtil";
	
	public static Profile getProfileFromJSON(JSONObject object) throws JSONException {
		Profile profile = new Profile();
		Log.d(TAG, "object = " + object.toString());
		
		Log.d(TAG, "img_path = " + object.getString("avatar"));
		profile.setImg_path(object.getString("avatar"));
		Log.d(TAG, "name = " + object.getString("nick"));
		profile.setName(object.getString("nick"));
		Log.d(TAG, "level = " + object.getString("level"));
		profile.setLevel(object.getString("level"));
		Log.d(TAG, "favorite_dishes = " + object.getString("favorite_kitchen_list"));
		profile.setFavorite_dishes(object.getString("favorite_kitchen_list"));
		Log.d(TAG, "hobbies = " + object.getString("hobby_list"));
		profile.setHobbies(object.getString("hobby_list"));
		Log.d(TAG, "interests = " + object.getString("cook_interest_list"));
		profile.setInterests(object.getString("cook_interest_list"));
		Log.d(TAG, "experience = " + object.getString("experience"));
		profile.setExperience(object.getString("experience"));
		Log.d(TAG, "about = " + object.getString("about"));
		profile.setAbout(object.getString("about"));
		
		Log.d(TAG, "published_recepies = " + object.getInt("recipes_count"));
		profile.setPublished_recepies(object.getInt("recipes_count"));
		Log.d(TAG, "added_to_favorites = " + object.getInt("favorites_count"));
		profile.setAdded_to_favorites(object.getInt("favorites_count"));
		Log.d(TAG, "added_to_favorites = " + object.getInt("favorites_count"));
		profile.setAdded_to_favorites(object.getInt("favorites_count"));
		Log.d(TAG, "comments_left = " + object.getInt("comment_count"));
		profile.setComments_left(object.getInt("comment_count"));
		Log.d(TAG, "voices_left = " + object.getInt("ratings_count"));
		profile.setVoices_left(object.getInt("ratings_count"));
		Log.d(TAG, "friends = " + object.getInt("friends_count"));
		profile.setFriends(object.getInt("friends_count"));
		return profile;
	}

	public static Profile getProfileFromCursor(Cursor c) {
		Profile profile = new Profile();
		
		Log.d(TAG, "img_path = " + c.getString(c.getColumnIndex("img_path")));
		profile.setImg_path(c.getString(c.getColumnIndex("img_path")));
		Log.d(TAG, "name = " + c.getString(c.getColumnIndex("name")));
		profile.setName(c.getString(c.getColumnIndex("name")));
		Log.d(TAG, "level = " + c.getString(c.getColumnIndex("level")));
		profile.setLevel(c.getString(c.getColumnIndex("level")));
		Log.d(TAG, "favorite_dishes = " + c.getString(c.getColumnIndex("favorite_dishes")));
		profile.setFavorite_dishes(c.getString(c.getColumnIndex("favorite_dishes")));
		Log.d(TAG, "hobbies = " + c.getString(c.getColumnIndex("hobbies")));
		profile.setHobbies(c.getString(c.getColumnIndex("hobbies")));
		Log.d(TAG, "interests = " + c.getString(c.getColumnIndex("interests")));
		profile.setInterests(c.getString(c.getColumnIndex("interests")));
		Log.d(TAG, "experience = " + c.getString(c.getColumnIndex("experience")));
		profile.setExperience(c.getString(c.getColumnIndex("experience")));
		Log.d(TAG, "about = " + c.getString(c.getColumnIndex("about")));
		profile.setAbout(c.getString(c.getColumnIndex("about")));
		
		Log.d(TAG, "published_recepies = " + c.getInt(c.getColumnIndex("published_recepies")));
		profile.setPublished_recepies(c.getInt(c.getColumnIndex("published_recepies")));
		Log.d(TAG, "added_to_favorites = " + c.getInt(c.getColumnIndex("added_to_favorites")));
		profile.setAdded_to_favorites(c.getInt(c.getColumnIndex("added_to_favorites")));
		Log.d(TAG, "comments_left = " + c.getInt(c.getColumnIndex("comments_left")));
		profile.setComments_left(c.getInt(c.getColumnIndex("comments_left")));
		Log.d(TAG, "voices_left = " + c.getInt(c.getColumnIndex("voices_left")));
		profile.setVoices_left(c.getInt(c.getColumnIndex("voices_left")));
		Log.d(TAG, "friends = " + c.getInt(c.getColumnIndex("friends")));
		profile.setFriends(c.getInt(c.getColumnIndex("friends")));
		return profile;
	}
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
		Log.d(TAG, "path = " + object.getString("path"));
		recipe.setPath(object.getString("path"));
		return recipe;
	}
	
	
	public static Recipe getRecipeFromJSONItem_id(JSONObject object)
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
		Log.d(TAG, "path = " + object.getString("path"));
		recipe.setPath(object.getString("path"));
		Log.d(TAG, "item_id = " + object.getInt("item_id"));
		recipe.setItem_id(object.getInt("item_id"));
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
	
	public static List<Integer> getListToDelete(String result2) throws JSONException {
		List<Integer> list = new ArrayList<Integer>();
		JSONArray jsonElements = new JSONArray(result2);
		JSONObject object = jsonElements.getJSONObject(jsonElements.length()-1);
		JSONArray ar = object.getJSONArray("deleted");
		for (int i=0; i<ar.length(); i++) {
		    list.add(ar.getInt(i));
		}
		return list;
	}
	
	public static ArrayList<RecipeGeneral> getRecipesFromJSONString(String result2) {
		ArrayList<RecipeGeneral> recipes = new ArrayList<RecipeGeneral>();
		try {
			JSONArray jsonElements = new JSONArray(result2);
			Log.d(TAG, "In MainListActivity getRecipes(): " + jsonElements);
			Log.d(TAG, "jsonElements.length(): " + jsonElements.length());
			for (int i = 0; i < jsonElements.length()-1; i++) {
				Log.d(TAG, "jsonElements.every: " + jsonElements.getJSONObject(i));
				Recipe favRecipe = (ModelUtil.getRecipeFromJSONItem_id(jsonElements.getJSONObject(i)));
				
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
