package com.st.nyam.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe extends RecipeGeneral implements Serializable {

	private int id;
	private String title;
	private String description;
	private String user;
	private String title2_genitive;
	private int views;
	private int total_rating;
	private String avatar;
	private String level_ico;
	private String img_url;
	private int favorites_by;
	private int cooked_dishes_count;
	private String category;
	private int cook_time;
	private int item_id;
	private ArrayList<Step> steps;
	
	
	
	public Recipe () {}
	
	public Recipe(int id, String title, String description, String user,
			String title2_genitive, int views, int total_rating, String avatar,
			String level_ico, String img_url, int favorites_by,
			int cooked_dishes_count, String category, int cook_time,
			int item_id, ArrayList<Step> steps) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.user = user;
		this.title2_genitive = title2_genitive;
		this.views = views;
		this.total_rating = total_rating;
		this.avatar = avatar;
		this.level_ico = level_ico;
		this.img_url = img_url;
		this.favorites_by = favorites_by;
		this.cooked_dishes_count = cooked_dishes_count;
		this.category = category;
		this.cook_time = cook_time;
		this.item_id = item_id;
		this.steps = steps;
	}

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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTitle2_genitive() {
		return title2_genitive;
	}

	public void setTitle2_genitive(String title2_genitive) {
		this.title2_genitive = title2_genitive;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getTotal_rating() {
		return total_rating;
	}

	public void setTotal_rating(int total_rating) {
		this.total_rating = total_rating;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getLevel_ico() {
		return level_ico;
	}

	public void setLevel_ico(String level_ico) {
		this.level_ico = level_ico;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public int getFavorites_by() {
		return favorites_by;
	}

	public void setFavorites_by(int favorites_by) {
		this.favorites_by = favorites_by;
	}

	public int getCooked_dishes_count() {
		return cooked_dishes_count;
	}

	public void setCooked_dishes_count(int cooked_dishes_count) {
		this.cooked_dishes_count = cooked_dishes_count;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getCook_time() {
		return cook_time;
	}

	public void setCook_time(int cook_time) {
		this.cook_time = cook_time;
	}

	public int getItem_id() {
		return item_id;
	}

	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}

	public ArrayList<Step> getSteps() {
		return steps;
	}

	public void setSteps(ArrayList<Step> steps) {
		this.steps = steps;
	}

	@Override
	public String toString() {
		return "Recipe [id=" + id + ", title=" + title + ", description="
				+ description + ", user=" + user + ", title2_genitive="
				+ title2_genitive + ", views=" + views + ", total_rating="
				+ total_rating + ", avatar=" + avatar + ", level_ico="
				+ level_ico + ", img_url=" + img_url + ", favorites_by="
				+ favorites_by + ", cooked_dishes_count=" + cooked_dishes_count
				+ ", category=" + category + ", cook_time=" + cook_time
				+ ", steps=" + steps + "]";
	}

}
