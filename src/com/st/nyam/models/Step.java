package com.st.nyam.models;

import java.util.Date;


public class Step {

	private int id;
	private int recipe_id;
	private String body;
	private String photo_file_name;
	private String photo_content_type;
	private int photo_file_size;
	private Date photo_updated_at;
	private Date created_at;
	private Date updated_at;
	private int photo_processing;

	public Step(int id, int recipe_id, String body, String photo_file_name,
			String photo_content_type, int photo_file_size,
			Date photo_updated_at, Date created_at, Date updated_at,
			int photo_processing) {
		super();
		this.id = id;
		this.recipe_id = recipe_id;
		this.body = body;
		this.photo_file_name = photo_file_name;
		this.photo_content_type = photo_content_type;
		this.photo_file_size = photo_file_size;
		this.photo_updated_at = photo_updated_at;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.photo_processing = photo_processing;
	}

	public Step() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRecipe_id() {
		return recipe_id;
	}

	public void setRecipe_id(int recipe_id) {
		this.recipe_id = recipe_id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getPhoto_file_name() {
		return photo_file_name;
	}

	public void setPhoto_file_name(String photo_file_name) {
		this.photo_file_name = photo_file_name;
	}

	public String getPhoto_content_type() {
		return photo_content_type;
	}

	public void setPhoto_content_type(String photo_content_type) {
		this.photo_content_type = photo_content_type;
	}

	public int getPhoto_file_size() {
		return photo_file_size;
	}

	public void setPhoto_file_size(int photo_file_size) {
		this.photo_file_size = photo_file_size;
	}

	public Date getPhoto_updated_at() {
		return photo_updated_at;
	}

	public void setPhoto_updated_at(Date photo_updated_at) {
		this.photo_updated_at = photo_updated_at;
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

	public int getPhoto_processing() {
		return photo_processing;
	}

	public void setPhoto_processing(int photo_processing) {
		this.photo_processing = photo_processing;
	}

}
