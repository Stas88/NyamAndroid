package com.st.nyam.models;

import java.io.Serializable;
import java.util.Date;

public class Recipe implements Serializable {

	private int id;
	private String title;
	private String description;
	private int cook_time;
	private int serv_num;
	private int user_id;
	private Date created_at;
	private Date updated_at;
	private String main_photo_file_name;
	private String main_photo_content_type;
	private int main_photo_file_size;
	private int views;
	private String cached_slug;
	private int delta;
	private int main_category_id;
	private int status;
	private int main_photo_processing;
	private int hide_watermark_text;
	private int vk_comments_count;
	private String title2_genitive;
	private int publication_on_main;
	private Date publication_on_main_date;
	private int recipe_variation_id;
	private int parent_variation_id;
	private int vk_likes_count;
	private int google_plus_count;
	private int facebook_likes_count;
	private int twitter_tweets_count;

	public Recipe(int id, String title, String description, int cook_time,
			int serv_num, int user_id, Date created_at, Date updated_at,
			String main_photo_file_name, String main_photo_content_type,
			int main_photo_file_size, int views, String cached_slug, int delta,
			int main_category_id, int status, int main_photo_processing,
			int hide_watermark_text, int vk_comments_count,
			String title2_genitive, int publication_on_main,
			Date publication_on_main_date, int recipe_variation_id,
			int parent_variation_id, int vk_likes_count, int google_plus_count,
			int facebook_likes_count, int twitter_tweets_count) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.cook_time = cook_time;
		this.serv_num = serv_num;
		this.user_id = user_id;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.main_photo_file_name = main_photo_file_name;
		this.main_photo_content_type = main_photo_content_type;
		this.main_photo_file_size = main_photo_file_size;
		this.views = views;
		this.cached_slug = cached_slug;
		this.delta = delta;
		this.main_category_id = main_category_id;
		this.status = status;
		this.main_photo_processing = main_photo_processing;
		this.hide_watermark_text = hide_watermark_text;
		this.vk_comments_count = vk_comments_count;
		this.title2_genitive = title2_genitive;
		this.publication_on_main = publication_on_main;
		this.publication_on_main_date = publication_on_main_date;
		this.recipe_variation_id = recipe_variation_id;
		this.parent_variation_id = parent_variation_id;
		this.vk_likes_count = vk_likes_count;
		this.google_plus_count = google_plus_count;
		this.facebook_likes_count = facebook_likes_count;
		this.twitter_tweets_count = twitter_tweets_count;
	}

	public Recipe() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCook_time() {
		return cook_time;
	}

	public void setCook_time(int cook_time) {
		this.cook_time = cook_time;
	}

	public int getServ_num() {
		return serv_num;
	}

	public void setServ_num(int serv_num) {
		this.serv_num = serv_num;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public String getMain_photo_file_name() {
		return main_photo_file_name;
	}

	public void setMain_photo_file_name(String main_photo_file_name) {
		this.main_photo_file_name = main_photo_file_name;
	}

	public String getMain_photo_content_type() {
		return main_photo_content_type;
	}

	public void setMain_photo_content_type(String main_photo_content_type) {
		this.main_photo_content_type = main_photo_content_type;
	}

	public int getMain_photo_file_size() {
		return main_photo_file_size;
	}

	public void setMain_photo_file_size(int main_photo_file_size) {
		this.main_photo_file_size = main_photo_file_size;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public String getCached_slug() {
		return cached_slug;
	}

	public void setCached_slug(String cached_slug) {
		this.cached_slug = cached_slug;
	}

	public int getDelta() {
		return delta;
	}

	public void setDelta(int delta) {
		this.delta = delta;
	}

	public int getMain_category_id() {
		return main_category_id;
	}

	public void setMain_category_id(int main_category_id) {
		this.main_category_id = main_category_id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getMain_photo_processing() {
		return main_photo_processing;
	}

	public void setMain_photo_processing(int main_photo_processing) {
		this.main_photo_processing = main_photo_processing;
	}

	public int getHide_watermark_text() {
		return hide_watermark_text;
	}

	public void setHide_watermark_text(int hide_watermark_text) {
		this.hide_watermark_text = hide_watermark_text;
	}

	public int getVk_comments_count() {
		return vk_comments_count;
	}

	public void setVk_comments_count(int vk_comments_count) {
		this.vk_comments_count = vk_comments_count;
	}

	public String getTitle2_genitive() {
		return title2_genitive;
	}

	public void setTitle2_genitive(String title2_genitive) {
		this.title2_genitive = title2_genitive;
	}

	public int getPublication_on_main() {
		return publication_on_main;
	}

	public void setPublication_on_main(int publication_on_main) {
		this.publication_on_main = publication_on_main;
	}

	public Date getPublication_on_main_date() {
		return publication_on_main_date;
	}

	public void setPublication_on_main_date(Date publication_on_main_date) {
		this.publication_on_main_date = publication_on_main_date;
	}

	public int getRecipe_variation_id() {
		return recipe_variation_id;
	}

	public void setRecipe_variation_id(int recipe_variation_id) {
		this.recipe_variation_id = recipe_variation_id;
	}

	public int getParent_variation_id() {
		return parent_variation_id;
	}

	public void setParent_variation_id(int parent_variation_id) {
		this.parent_variation_id = parent_variation_id;
	}

	public int getVk_likes_count() {
		return vk_likes_count;
	}

	public void setVk_likes_count(int vk_likes_count) {
		this.vk_likes_count = vk_likes_count;
	}

	public int getGoogle_plus_count() {
		return google_plus_count;
	}

	public void setGoogle_plus_count(int google_plus_count) {
		this.google_plus_count = google_plus_count;
	}

	public int getFacebook_likes_count() {
		return facebook_likes_count;
	}

	public void setFacebook_likes_count(int facebook_likes_count) {
		this.facebook_likes_count = facebook_likes_count;
	}

	public int getTwitter_tweets_count() {
		return twitter_tweets_count;
	}

	public void setTwitter_tweets_count(int twitter_tweets_count) {
		this.twitter_tweets_count = twitter_tweets_count;
	}



}
