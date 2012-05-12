package com.st.nyam.factories;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.st.nyam.models.Recipe;
import com.st.nyam.models.Step;
import com.st.nyam.util.ModelUtil;


public class DataBaseFactory {

	private SQLiteDatabase db;
	private final Context context;
	private static String DB_NAME = "nyam_db.db3";
	private static String DB_PATH = "/data/data/com.st.nyam/databases/";	
	private static String TAG = "DataBaseFactory";
	//private final String INSERT_RECEPY = "INSERT into RECEPIES ('id', 'recepy', 'author') VALUES (?, ?, ?)";

	private final String SELECT_RECIPES = "SELECT * FROM recipes ORDER BY 'title'";
	private final String SELECT_STEPS = "SELECT * FROM steps";
	private final String SELECT_TABLES = "SELECT name FROM sqlite_master WHERE type= 'table' ORDER BY name";
	private final String SELECT_STEPS_BY_ID = "SELECT * FROM steps where recipe_id = ?";


	public DataBaseFactory(Context ctx) {
		context = ctx;	
		SQLiteDatabase temp_db = ctx.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
		temp_db.close();
		try {
			Log.i(TAG, "Copy intenting");
			copyDataBase();
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
		Log.i(TAG, "Temp created");
		if (db == null) {
			db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
		}
		Log.i(TAG, "Temp opened");
	}


	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;
		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		} catch(SQLiteException e){
			//database does't exist yet.
			e.printStackTrace();
		}
		if(checkDB != null){
			checkDB.close();
		}
		return checkDB != null ? true : false;
	}

	public void openDataBase() throws SQLException {
		//Open the database
		String myPath = DB_PATH + DB_NAME;
		db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	}



	private void copyDataBase() throws IOException {
		//Open your local db as the input stream
		InputStream myInput = context.getAssets().open("db/" + DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		//Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		//transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer))>0){
			myOutput.write(buffer, 0, length);
		}
		Log.i(TAG, "Copy data");
		//Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}



	public ArrayList<Recipe> getRecipes() throws ParseException {
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		Cursor c = db.rawQuery(SELECT_RECIPES, null);
		Log.i(TAG, "Get Query");
		if (c != null && c.getCount() > 0) {
			c.moveToFirst();
			do {
				Log.i(TAG, "Getting recipe");
				Recipe recipe = ModelUtil.getRecipeFromCursor(c);
				recipes.add(recipe);
				Log.i(TAG, "Getting recipe added");
			} while (c.moveToNext());

		}
		c.close();
		return recipes;
	}

	public ArrayList<Step> getStepsByRecipe(int recipeId) throws ParseException {
		Log.d(TAG, "In getStepsByRecipe");
		ArrayList<Step> steps = new ArrayList<Step>();
		Cursor c = db.rawQuery(SELECT_STEPS_BY_ID, new String[]{ Integer.toString(recipeId) });
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

	public ArrayList<Step> getSteps() throws ParseException {
		ArrayList<Step> steps = new ArrayList<Step>();
		Cursor c = db.rawQuery(SELECT_STEPS, null);
		if (c != null && c.getCount() > 0) {
			c.moveToFirst();
			do {
				Step step = new Step();
				step.setId(c.getInt(c.getColumnIndex("id")));
				step.setRecipe_id(c.getInt(c.getColumnIndex("recipe_id")));
				step.setBody(c.getString(c.getColumnIndex("body")));
				step.setPhoto_file_name(c.getString(c.getColumnIndex("photo_file_name")));
				step.setPhoto_content_type(c.getString(c.getColumnIndex("photo_content_type")));
				step.setPhoto_file_size(c.getInt(c.getColumnIndex("photo_file_size")));
				step.setPhoto_updated_at(new SimpleDateFormat("yyyy.MM.dd G HH:mm:ss").parse(c.getString(c.getColumnIndex("photo_updated_at"))));
				step.setCreated_at(new SimpleDateFormat("yyyy.MM.dd G HH:mm:ss").parse(c.getString(c.getColumnIndex("created_at"))));
				step.setUpdated_at(new SimpleDateFormat("yyyy.MM.dd G HH:mm:ss").parse(c.getString(c.getColumnIndex("updated_at"))));
				step.setPhoto_processing(c.getInt(c.getColumnIndex("photo_processing")));
				steps.add(step);
			} while (c.moveToNext());
		}
		c.close();
		return steps;
	}

	public ArrayList<Recipe> fetchRecipesByQuery(String query) throws ParseException {
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		Cursor c =  db.query(true, "virt", null, "description " + " Match " + "'*" + query + "*'", null,
				null, null, null, null);
		try{ 
			Log.i(TAG, "Get Query");
			if (c != null && c.getCount() > 0) {
				c.moveToFirst();
				do {
					Log.i(TAG, "Getting recipe");
					Recipe recipe = ModelUtil.getRecipeFromCursor(c);
					recipes.add(recipe);
					Log.i(TAG, "Getting recipe added");
				} while (c.moveToNext());
			}
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return recipes;
	}

	/*
	public void putRecepy(Recepy recepy) {
		db.execSQL(INSERT_RECEPY, new String[] 
					{Integer.toString(recepy.getId()),
								recepy.getRecepy(), recepy.getAuthor()});
	}
	 */
}
