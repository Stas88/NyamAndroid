package com.st.nyam.models;

import java.util.Date;

public class Ingredient {

	private int id;
	private String name;
	private String desc;
	private int parent;
	private Date created_at;
	private Date updated_at;
	private String ablative;
	private int moderated;

	public Ingredient(int id, String name, String desc, int parent,
			Date created_at, Date updated_at, String ablative, int moderated) {
		super();
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.parent = parent;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.ablative = ablative;
		this.moderated = moderated;
	}

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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
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

	public String getAblative() {
		return ablative;
	}

	public void setAblative(String ablative) {
		this.ablative = ablative;
	}

	public int getModerated() {
		return moderated;
	}

	public void setModerated(int moderated) {
		this.moderated = moderated;
	}

}
