package com.st.nyam.models;

import java.util.Date;

public class IngredientSize {

	private int id;
	private String title;
	private Date created_at;
	private Date updated_at;
	private int is_quantity;

	public IngredientSize(int id, String title, Date created_at,
			Date updated_at, int is_quantity) {
		super();
		this.id = id;
		this.title = title;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.is_quantity = is_quantity;
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

	public int getIs_quantity() {
		return is_quantity;
	}

	public void setIs_quantity(int is_quantity) {
		this.is_quantity = is_quantity;
	}



}
