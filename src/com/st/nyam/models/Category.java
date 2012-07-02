package com.st.nyam.models;

import java.util.Date;

public class Category {

	private int id;
	private String name;
	private int catalog_id;
	private Date created_at;
	private Date updated_at;
	private String cached_slug;
	private int weight; 
	private String description;

	public Category(int id, String name, int catalog_id, Date created_at,
			Date updated_at, String cached_slug, int weight, String description) {
		super();
		this.id = id;
		this.name = name;
		this.catalog_id = catalog_id;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.cached_slug = cached_slug;
		this.weight = weight;
		this.description = description;
	}

	public Category() {}

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

	public int getCatalog_id() {
		return catalog_id;
	}

	public void setCatalog_id(int catalog_id) {
		this.catalog_id = catalog_id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}





}
