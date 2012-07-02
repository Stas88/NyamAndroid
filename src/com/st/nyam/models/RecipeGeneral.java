package com.st.nyam.models;

import java.io.Serializable;
import java.util.Date;

public class RecipeGeneral implements Serializable {

	private int id;
	private String title;
	private String path;
	private String author;
	private String img_url;
	private int rating;
	private Date created_at;
	private int favorites_by;
	private int cooked_dishes_count;
	private int item_id;
	
	
	public RecipeGeneral() {}


	public RecipeGeneral(int id, String title, String path, String author,
			String img_url, int rating, Date created_at, int favorites_by,
			int cooked_dishes_count, int item_id) {
		super();
		this.id = id;
		this.title = title;
		this.path = path;
		this.author = author;
		this.img_url = img_url;
		this.rating = rating;
		this.created_at = created_at;
		this.favorites_by = favorites_by;
		this.cooked_dishes_count = cooked_dishes_count;
		this.item_id = item_id;
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


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public String getImg_url() {
		return img_url;
	}


	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}


	public int getRating() {
		return rating;
	}


	public void setRating(int rating) {
		this.rating = rating;
	}


	public Date getCreated_at() {
		return created_at;
	}


	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
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


	public int getItem_id() {
		return item_id;
	}


	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}
	
	
	
	


}
