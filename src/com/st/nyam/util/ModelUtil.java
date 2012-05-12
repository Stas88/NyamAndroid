package com.st.nyam.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.database.Cursor;

import com.st.nyam.models.Recipe;
import com.st.nyam.models.GeneralRecipe;
import com.st.nyam.models.Step;

public class ModelUtil {

	private final String TAG = "ModelUtil";

	public static Recipe getRecipeFromCursor(Cursor c) throws ParseException {

		Recipe recipe = new Recipe();
		recipe.setId(c.getInt(c.getColumnIndex("id")));
		recipe.setTitle(c.getString(c.getColumnIndex("title")));
		recipe.setDescription(c.getString(c.getColumnIndex("description")));
		recipe.setCook_time(c.getInt(c.getColumnIndex("cook_time")));
		recipe.setServ_num(c.getInt(c.getColumnIndex("serv_num")));
		recipe.setUser_id(c.getInt(c.getColumnIndex("user_id")));
		if (c.getString(c.getColumnIndex("created_at")) != null) {
			recipe.setCreated_at(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(c.getString(c.getColumnIndex("created_at"))));
		}
		if (c.getString(c.getColumnIndex("updated_at")) != null) {
			recipe.setUpdated_at(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(c.getString(c.getColumnIndex("updated_at"))));
		}
		recipe.setMain_photo_file_name(c.getString(c.getColumnIndex("main_photo_file_name")));
		recipe.setMain_photo_content_type(c.getString(c.getColumnIndex("main_photo_content_type")));
		recipe.setMain_photo_file_size(c.getInt(c.getColumnIndex("main_photo_file_size")));
		recipe.setViews(c.getInt(c.getColumnIndex("views")));
		recipe.setCached_slug(c.getString(c.getColumnIndex("cached_slug")));
		recipe.setDelta(c.getInt(c.getColumnIndex("delta")));
		recipe.setMain_category_id(c.getInt(c.getColumnIndex("main_category_id")));
		recipe.setStatus(c.getInt(c.getColumnIndex("status")));
		recipe.setMain_photo_processing(c.getInt(c.getColumnIndex("main_photo_processing")));
		recipe.setHide_watermark_text(c.getInt(c.getColumnIndex("hide_watermark_text")));
		recipe.setVk_comments_count(c.getInt(c.getColumnIndex("vk_comments_count")));
		recipe.setTitle2_genitive(c.getString(c.getColumnIndex("title2_genitive")));
		recipe.setPublication_on_main(c.getInt(c.getColumnIndex("publication_on_main")));
		if (c.getString(c.getColumnIndex("publication_on_main_date")) != null) {
			recipe.setPublication_on_main_date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(c.getString(c.getColumnIndex("publication_on_main_date"))));
		}
		recipe.setRecipe_variation_id(c.getInt(c.getColumnIndex("recipe_variation_id")));
		recipe.setParent_variation_id(c.getInt(c.getColumnIndex("parent_variation_id")));
		recipe.setVk_likes_count(c.getInt(c.getColumnIndex("vk_likes_count")));
		recipe.setGoogle_plus_count(c.getInt(c.getColumnIndex("google_plus_count")));
		recipe.setFacebook_likes_count(c.getInt(c.getColumnIndex("facebook_likes_count")));
		recipe.setTwitter_tweets_count(c.getInt(c.getColumnIndex("recipe_variation_id")));
		return recipe;
	}

	public static Step getStepFromCursor(Cursor c) throws ParseException {
		Step step = new Step();
		step.setId(c.getInt(c.getColumnIndex("id")));
		step.setRecipe_id(c.getInt(c.getColumnIndex("recipe_id")));
		step.setBody(c.getString(c.getColumnIndex("body")));
		step.setPhoto_file_name(c.getString(c.getColumnIndex("photo_file_name")));
		step.setPhoto_content_type(c.getString(c.getColumnIndex("photo_content_type")));
		step.setPhoto_file_size(c.getInt(c.getColumnIndex("photo_file_size")));
		step.setPhoto_updated_at(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(c.getString(c.getColumnIndex("photo_updated_at"))));
		step.setCreated_at(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(c.getString(c.getColumnIndex("created_at"))));
		step.setUpdated_at(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(c.getString(c.getColumnIndex("updated_at"))));
		step.setPhoto_processing(c.getInt(c.getColumnIndex("photo_processing")));
		return step;
	}

	public static GeneralRecipe getRecipeFromJSON(JSONObject object) throws ParseException, JSONException{
		GeneralRecipe recipe = new GeneralRecipe();
		recipe.setId(object.getInt("id"));
		recipe.setTitle(object.getString("title"));
		recipe.setPath(object.getString("path"));
		recipe.setAuthor(object.getString("author"));
		recipe.setImg_url(object.getString("img_url"));
		recipe.setRating(object.getInt("rating"));
		String created_at = object.getString("created_at").replace("T", "");
		if (created_at != null) {
			recipe.setCreated_at(new SimpleDateFormat("yyyy-MM-ddHH:mm:ss")
			.parse((created_at.substring(0, created_at.length()-6)))); 
		}
		return recipe;
	}

}
