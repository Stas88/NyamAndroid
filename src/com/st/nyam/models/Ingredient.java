package com.st.nyam.models;

import java.io.Serializable;


public class Ingredient implements Serializable {

	private int id;
	private String name;
	private String value;
	private String type;
	private int recipe_id;

	public Ingredient() {}

	public Ingredient(int id, String name, String value, String type,
			int recipe_id) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
		this.type = type;
		this.recipe_id = recipe_id;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getRecipe_id() {
		return recipe_id;
	}

	public void setRecipe_id(int recipe_id) {
		this.recipe_id = recipe_id;
	}

	@Override
	public String toString() {
		return "Ingredient [id=" + id + ", name=" + name + ", value=" + value
				+ ", type=" + type + ", recipe_id=" + recipe_id + "]";
	}

	
	

}
