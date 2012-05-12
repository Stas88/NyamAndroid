package com.st.nyam.models;

public class CategoryRecipe {

	private int category_id;
	private int recipe_id;

	public CategoryRecipe(int category_id, int recipe_id) {
		super();
		this.category_id = category_id;
		this.recipe_id = recipe_id;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public int getRecipe_id() {
		return recipe_id;
	}

	public void setRecipe_id(int recipe_id) {
		this.recipe_id = recipe_id;
	}





}
