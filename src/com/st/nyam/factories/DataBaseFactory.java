package com.st.nyam.factories;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.st.nyam.models.Ingredient;
import com.st.nyam.models.MainCategory;
import com.st.nyam.models.Profile;
import com.st.nyam.models.Recipe;
import com.st.nyam.models.RecipeGeneral;
import com.st.nyam.models.Step;
import com.st.nyam.util.ModelUtil;
import com.st.nyam.util.SD_util;

public class DataBaseFactory   {

	private SQLiteDatabase db;
	private final Context context;
	private SD_util sdUtil;
	private static String DB_NAME = "nyam_db.db3";
	private static String DB_PATH = "/data/data/com.st.nyam/databases/";
	private static String TAG = "DataBaseFactory";
	private static int DATABASE_VERSION = 1;
	// private final String INSERT_RECEPY =
	// "INSERT into RECEPIES ('id', 'recepy', 'author') VALUES (?, ?, ?)";

	private final String SELECT_RECIPES = "SELECT * FROM recipes ORDER By time_add desc";
	private final String SELECT_RECIPE_BY_ID = "SELECT * FROM recipes WHERE ID = ?";
	private final String SELECT_COUNT_RECIPE_BY_ID = "SELECT count(*) FROM recipes WHERE ID = ?";
	private final String SELECT_STEPS = "SELECT * FROM steps";
	private final String SELECT_TABLES = "SELECT name FROM sqlite_master WHERE type= 'table' ORDER BY name";
	private final String SELECT_ITEM_ID_BY_ID = "SELECT item_id FROM recipes WHERE id = ?";
	private final String SELECT_STEPS_BY_ID = "SELECT * FROM steps where recipe_id = ?";
	private final String INSERT_STEP = "INSERT INTO steps ('id', 'recipe_id', 'body', 'photo_file_name') VALUES (?,?,?,?) ";
	private final String INSERT_INGREDIENT = "INSERT INTO ingredients_recipes ('name', 'value', 'type', 'recipe_id') VALUES (?,?,?,?) ";
	private final String INSERT_RECIPE = "INSERT INTO recipes ('id', 'title', 'description', 'user_id', 'favorites_by', 'main_photo_file_name', 'rating', 'cooked_dishes_count', 'path', 'item_id') VALUES (?,?,?,?,?,?,?,?,?,?) ";
	private final String INSERT_PROFILE = "INSERT INTO profile ('img_path', 'name', 'level', 'favorite_dishes', 'hobbies','interests', 'experience', 'about', 'published_recipes', 'added_to_favorites', 'cmments_left', 'voices_left', 'friends' ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?) ";
	private final String DELETE_RECIPE = "DELETE FROM recipes WHERE id = ?";
	private final String DELETE_RECIPES = "DELETE FROM recipes";
	private final String DELETE_STEPS = "DELETE FROM rsteps";
	private final String DELETE_STEPS_BY_RECIPEID = "DELETE FROM steps WHERE recipe_id = ?";
	private final String UPDATE_RECIPE_ITEM_ID = "UPDATE recipes SET item_id = ?  WHERE id = ?";
	
	private final String SELECT_PROFILE = "SELECT * FROM profile";
	private final String SELECT_MAINCATEGORIES = "SELECT * FROM main_categories WHERE parent_id = ?";
	private final String SELECT_MAINCATEGORIES_BY_NAME = "SELECT * FROM main_categories WHERE name = ?";
	
	private final String SELECT_INGREDIENT_BY_RECIPE = "SELECT * FROM ingredients_recipes WHERE recipe_id = ?";
	
	public DataBaseFactory(Context ctx) {
		context = ctx;
		sdUtil = new SD_util();
		File dbFile = new File(DB_PATH + DB_NAME);
		if (!dbFile.exists()) {
			SQLiteDatabase temp_db = ctx.openOrCreateDatabase(DB_PATH + DB_NAME,
					Context.MODE_PRIVATE, null);
			temp_db.close();
			try {
				Log.i(TAG, "Copy intenting");
				copyDataBase();
			} catch (IOException e) {
				Log.e(TAG, e.getMessage());
			}
		}
		Log.i(TAG, "Temp created");
		if (db == null) {
			db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null,
					SQLiteDatabase.OPEN_READWRITE);
		}
		Log.i(TAG, "Temp opened");
	}

	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;
		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READWRITE);
		} catch (SQLiteException e) {
			// database does't exist yet.
			e.printStackTrace();
		}
		if (checkDB != null) {
			checkDB.close();
		}
		return checkDB != null ? true : false;
	}

	public void openDataBase() throws SQLException {
		// Open the database
		String myPath = DB_PATH + DB_NAME;
		db = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);
	}

	private void copyDataBase() throws IOException {
		// Open your local db as the input stream
		InputStream myInput = context.getAssets().open("db/" + DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}
		Log.i(TAG, "Copy data");
		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}

	public ArrayList<RecipeGeneral> getRecipes() {
		ArrayList<RecipeGeneral> recipes = new ArrayList<RecipeGeneral>();
		Cursor c = db.rawQuery(SELECT_RECIPES, null);
		Log.d(TAG, "getRecipes()");
		if (c != null && c.getCount() > 0) {
			c.moveToFirst();
			do {
				Log.d(TAG, "Getting recipe");
				RecipeGeneral recipe = ModelUtil.getRecipeFromCursor(c);
				recipes.add(recipe);
				Log.d(TAG, "Getting recipe added");
			} while (c.moveToNext());

		}
		c.close();
		return recipes;
	}

	public ArrayList<Step> getStepsByRecipeId(int recipeId)
			throws ParseException {
		Log.d(TAG, "In getStepsByRecipe");
		ArrayList<Step> steps = new ArrayList<Step>();
		Cursor c = db.rawQuery(SELECT_STEPS_BY_ID,
				new String[] { Integer.toString(recipeId) });
		Log.d(TAG, "Get Query getStepsByRecipe");
		try {
			if (c != null && c.getCount() > 0) {
				c.moveToFirst();
				do {
					Log.d(TAG, "Getting step getStepsByRecipe");
					Step step = ModelUtil.getStepFromCursor(c);
					steps.add(step);
					Log.d(TAG, "Getting step added getStepsByRecipe");
				} while (c.moveToNext());
			}
		} finally {
			if (c != null) {
				c.close();
			}
		}
		c.close();
		return steps;

	}

	

	public ArrayList<RecipeGeneral> fetchRecipesByQuery(String query)
			throws ParseException {

		Log.d(TAG, "fetchRecipesByQuery");
		ArrayList<RecipeGeneral> recipes = new ArrayList<RecipeGeneral>();
		Cursor c = db.query(true, "recipes", null, "description " + " like "
				+ "'%" + query + "%'", null, null, null, null, null);
		try {
			Log.d(TAG, "Get Query");
			if (c != null && c.getCount() > 0) {
				c.moveToFirst();
				do {
					Log.d(TAG, "Getting recipe");
					Recipe recipe = ModelUtil.getRecipeFromCursor(c);
					recipes.add(recipe);
					Log.d(TAG, "Getting recipe added");
				} while (c.moveToNext());
			}
			Log.d(TAG, "Nothing returned");
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return recipes;
	}

	public void addRecipeToFavorites(Recipe recipe, Bitmap bitmap) {
		Log.d(TAG, "addRecipeToFavorites begin");
		Log.d(TAG, "recipe addRecipeToFavorites:" + recipe);
		Log.d(TAG, "recipe item_id:" + recipe.getItem_id());
		if (!isRecipeExists(recipe.getId())) {
			ArrayList<Step> steps = recipe.getSteps();
			ArrayList<Ingredient> ingredients = recipe.getIngredients();
			Log.d(TAG, "Adding  recipe to favorites addRecipeToFavorites()");
			sdUtil.saveRecipeImage(bitmap, recipe.getImg_url());
			db.execSQL(
					INSERT_RECIPE,
					new String[] { Integer.toString(recipe.getId()),
							recipe.getTitle(), recipe.getDescription(),
							recipe.getUser(),
							Integer.toString(recipe.getFavorites_by()),
							recipe.getImg_url(),
							Integer.toString(recipe.getRating()), 
							Integer.toString(recipe.getCooked_dishes_count()), 
							recipe.getPath(), Integer.toString(recipe.getItem_id())});
			if (recipe.getSteps() != null) {
				for (Step step : steps) {
					Object[] params = new Object[] { step.getImg_url() };
					new DownloadImageStep().execute(params);
					Log.d(TAG, "Adding step to favorites addRecipeToFavorites()");
					addStepToFavorites(step, recipe.getId());
				}
			} else {
				Log.d(TAG, "No steps in this recipe");
			}
			if (recipe.getIngredients() != null) {
				for (Ingredient in : ingredients) {
					addIngredientToFavorites(in, recipe.getId());
				}
			} else {
				Log.d(TAG, "No ingredients in this recipe");
			}
		} else {
			Log.d(TAG, "Рецепт уже добавлен");
			Toast.makeText(context, "Рецепт уже добавлен", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void addProfile(Profile profile) {
		Log.d(TAG, "addProfile:" + profile);
			db.execSQL(
					INSERT_PROFILE,
					new String[] {
							profile.getImg_path(),
							profile.getName(),
							profile.getLevel(),
							profile.getFavorite_dishes(),
							profile.getHobbies(),
							profile.getInterests(),
							profile.getExperience(),
							profile.getAbout(),
							Integer.toString(profile.getPublished_recepies()), 
							Integer.toString(profile.getAdded_to_favorites()), 
							Integer.toString(profile.getComments_left()), 
							Integer.toString(profile.getVoices_left()), 
							Integer.toString(profile.getFriends())});
	}
	
	public Profile getProfile() {
		Profile profile = null;
		Log.d(TAG, "getProfile:" + profile);
		Cursor c = db.rawQuery(SELECT_PROFILE, null);
		Log.d(TAG, "Done Query getProfile");
		try {
			if (c != null && c.getCount() > 0) {
				c.moveToFirst();
				do {
					profile = ModelUtil.getProfileFromCursor(c);
				} while (c.moveToNext());
			}
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return profile;
	}

	private void addStepToFavorites(Step step, int recipe_id) {
		db.execSQL(INSERT_STEP,new String[] { Integer.toString(step.getNumber()),
						Integer.toString(recipe_id), step.getInstruction(),
						step.getImg_url(), });
	}
	
	private void addIngredientToFavorites(Ingredient ingredient, int recipe_id) {
		db.execSQL(INSERT_INGREDIENT, new String[] {
				ingredient.getName(), ingredient.getValue(),ingredient.getType(),
						String.valueOf(recipe_id)});
	}
	
	public ArrayList<MainCategory> getMainCategories(int parentId) {
		ArrayList<MainCategory> categories = new ArrayList<MainCategory>();
		Log.d(TAG, "Start Query SELECT_MAINCATEGORIES_FIRST_LAYER");
		Cursor c = db.rawQuery(SELECT_MAINCATEGORIES, new String[] { Integer.toString(parentId)});
		Log.d(TAG, "Done Query SELECT_MAINCATEGORIES_FIRST_LAYER");
		try {
			if (c != null && c.getCount() > 0) {
				c.moveToFirst();
				do {
					Log.d(TAG, "Getting main_category getMainGategoriesFirstLayer");
					MainCategory mainCategory = ModelUtil.getMainCategoryFromCursor(c);
					categories.add(mainCategory);
					Log.d(TAG, "Getting main_category added getMainGategoriesFirstLayer");
				} while (c.moveToNext());
			}
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return categories;
	}
	
	public void clearRecipes() {
		db.execSQL(DELETE_STEPS);
		db.execSQL(DELETE_RECIPES);
	}
	
	
	public MainCategory getMainCategoriesbyName(String name) {
		MainCategory mainCategory = null;
		Log.d(TAG, "Start Query SELECT_MAINCATEGORIES_FIRST_LAYER");
		Cursor c = db.rawQuery(SELECT_MAINCATEGORIES_BY_NAME, new String[] {name});
		Log.d(TAG, "name" + name);
		try {
			if (c != null && c.getCount() > 0) {
				c.moveToFirst();
				do {
					Log.d(TAG, "Getting main_category getMainGategoriesFirstLayer");
					mainCategory = ModelUtil.getMainCategoryFromCursor(c);
					
					Log.d(TAG, "Getting main_category added getMainGategoriesFirstLayer");
				} while (c.moveToNext());
			}
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return mainCategory;
	}
	
	public void deleteRecipeFromFavorites(Recipe recipe) {
		Log.d(TAG, "deleteRecipeFromFavorites begin");
		if (isRecipeExists(recipe.getId())) {
			if (recipe.getSteps() != null) {
				for (Step step : recipe.getSteps()) {
					Log.d(TAG, "Boolean stepimage deleted = " + sdUtil.deleteImageFromSD(step.getImg_url().replace('/', '&')));
				}
				deleteStepsFromFavoritesByRecipeId(recipe.getId());
			} else {
				Log.d(TAG, "No steps in this recipe");
			}
			Log.d(TAG, "Image name in database = " + recipe.getImg_url().replace('/', '&'));
			Log.d(TAG, "Boolean recipeimage deleted = " + sdUtil.deleteImageFromSD(recipe.getImg_url().replace('/', '&')));
			Log.d(TAG, "Deleted rows: " + db.delete("recipes", "id=?", new String[] {Integer.toString(recipe.getId())}));
		} else {
			Log.d(TAG, "Recipe doesn`t exist");
		}
	}
	
	private void deleteStepsFromFavoritesByRecipeId(int recipeId) {
		Log.d(TAG, "deleteStepsFromFavoritesByRecipeId begin");
		db.delete("steps", "recipe_id=?", new String[] { Integer.toString(recipeId)});
			
	}
	
	public  void beginTransaction() {
		db.beginTransaction();
	}
	
	public  void setTransactionSuccessful() {
		db.setTransactionSuccessful();
	}
	
	public  void endTransaction() {
		db.endTransaction();
	}
	
	public void insertItemId(int recipeId, int itemId) {
		if (isRecipeExists(recipeId)) { 
			Log.d(TAG, "insertItemId = " + itemId);
			db.execSQL(UPDATE_RECIPE_ITEM_ID ,new String[] { Integer.toString(itemId),
					Integer.toString(recipeId)});
		}
	}
	
	public int getItemIdById(int recipeId) {
		int item_id = -1;
		Cursor c = db.rawQuery(SELECT_ITEM_ID_BY_ID,
				new String[] { Integer.toString(recipeId)});
		try {
			Log.d(TAG, "getItemIdById before c.movetoFirst()");
			if (c.moveToFirst()) {
				if (c != null && c.getCount() > 0) {
					Log.d(TAG, "Checking passed");
					item_id = c.getInt(c.getColumnIndex("item_id"));
				}
			}
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return item_id;
	}
	/*
	 * public void putRecepy(Recepy recepy) { db.execSQL(INSERT_RECEPY, new
	 * String[] {Integer.toString(recepy.getId()), recepy.getRecepy(),
	 * recepy.getAuthor()}); }
	 */
	public boolean isRecipeExists(int id) {
		Cursor c = db.rawQuery(SELECT_RECIPE_BY_ID,
				new String[] { Integer.toString(id) });
		try {
			Log.d(TAG, "isRecipeExists before c.movetoFirst()");
			if (c.moveToFirst()) {
				if (c != null && c.getCount() > 0) {
					Log.d(TAG, "Checking passed");
					//Recipe recipe = ModelUtil.getRecipeFromCursor(c);
					//Log.d(TAG, "RECIPEExists: " + recipe.toString());
					return true;
				}
			}
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return false;
	}
	
	
	public Recipe getRecipeById(int id) {
		Recipe recipe = null;
		Cursor c = db.rawQuery(SELECT_RECIPE_BY_ID,
				new String[] { Integer.toString(id) });
		try {
			Log.d(TAG, "isRecipeExists before c.movetoFirst()");
			if (c.moveToFirst()) {
				if (c != null && c.getCount() > 0) {
					Log.d(TAG, "Checking passed");
					recipe = ModelUtil.getRecipeFromCursor(c);
					Log.d(TAG, "RECIPEExists: " + recipe.toString());
				}
			}
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return recipe;
	}
	
	public List<Ingredient> getIngredientsByRecipeId(int id) throws ParseException {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		Cursor c = db.rawQuery(SELECT_INGREDIENT_BY_RECIPE,
				new String[] { Integer.toString(id) });
		try {
			Log.d(TAG, "getIngredientsByRecipeId before c.movetoFirst()");
			if (c.moveToFirst()) {
				do {
					Log.d(TAG, "Checking passed");
					Ingredient ingredient = ModelUtil.getIngredientFromCursor(c);
					Log.d(TAG, "Ingredient: " + ingredient.toString());
					ingredients.add(ingredient);
				} while (c.moveToNext());
			}
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return ingredients;
	}

	private class DownloadImageStep extends AsyncTask<Object, Void, Object> {
			
		@Override
		protected Object doInBackground(Object... o) {
			Bitmap outBitmap = null;
			try {
				sdUtil.saveStepImage((String) o[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return outBitmap;
		}
	}
	
	

}
