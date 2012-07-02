package com.st.nyam.models;

import java.io.Serializable;
import java.util.Date;

public class MainCategory implements Serializable {

	private int id;
	private String name;
	private String cached_slug;
	private int weight;
	private String photo_file_name;
	private String photo_content_type;
	private int photo_file_size;
	private Date photo_updated_at;
	private Date created_at;
	private Date updated_at;
	private int parent_id;
	private String description;

	public MainCategory(int id, String name, String cached_slug, int weight,
			String photo_file_name, String photo_content_type,
			int photo_file_size, Date photo_updated_at, Date created_at,
			Date updated_at, int parent_id, String description) {
		super();
		this.id = id;
		this.name = name;
		this.cached_slug = cached_slug;
		this.weight = weight;
		this.photo_file_name = photo_file_name;
		this.photo_content_type = photo_content_type;
		this.photo_file_size = photo_file_size;
		this.photo_updated_at = photo_updated_at;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.parent_id = parent_id;
		this.description = description;
	}
	
	public MainCategory() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCached_slug() {
		return cached_slug;
	}

	public void setCached_slug(String cached_slug) {
		this.cached_slug = cached_slug;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
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

	public int getParent_id() {
		return parent_id;
	}

	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}
