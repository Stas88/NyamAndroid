package com.st.nyam.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NyamOpenHelper extends SQLiteOpenHelper {

	//public static final String TABLE_RECIPES = "recipes";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_COMMENT = "comment";

	private static String DB_NAME = "nyam_db.db3";
	private static final int DATABASE_VERSION = 1;
	

	public NyamOpenHelper(Context context) {
		super(context, DB_NAME, null, DATABASE_VERSION);
	}

	// Database creation sql statement
	private static final String RECIPES_TABLE_CREATE = "CREATE TABLE recipes (" +
	  "id integer NOT NULL PRIMARY KEY,"+
	  "title varchar(255)  DEFAULT NULL,"+
	  "description text ,"+
	  "cook_time integer DEFAULT NULL,"+
	  "serv_num integer DEFAULT NULL,"+
	  "user_id integer DEFAULT NULL,"+
	  "created_at text DEFAULT NULL,"+
	  "updated_at text DEFAULT NULL,"+
	  "main_photo_file_name varchar(255)  DEFAULT NULL,"+
	  "main_photo_content_type varchar(255)  DEFAULT NULL,"+
	  "main_photo_file_size integer DEFAULT NULL,"+
	  "views integer DEFAULT '0',"+
	  "cached_slug varchar(255)  DEFAULT NULL,"+
	  "delta integer NOT NULL DEFAULT '1',"+
	  "main_category_id integer DEFAULT NULL,"+
	  "status integer NOT NULL DEFAULT '1',"+
	  "main_photo_processing integer DEFAULT NULL,"+
	  "hide_watermark_text integer DEFAULT '0',"+
	  "vk_comments_count integer DEFAULT '0',"+
	  "title2_genitive varchar(255)  DEFAULT NULL,"+
	  "publication_on_main integer NOT NULL DEFAULT '0',"+
	  "publication_on_main_date text DEFAULT NULL,"+
	  "recipe_variation_id integer DEFAULT NULL,"+
	  "parent_variation_id integer DEFAULT NULL,"+
	  "vk_likes_count integer DEFAULT '0',"+
	  "google_plus_count integer DEFAULT '0',"+
	  "facebook_likes_count integer DEFAULT '0',"+
	  "twitter_tweets_count integer DEFAULT '0',"+
	  "favorites_by integer DEFAULT NULL);";
	
	private static final String STEPS_TABLE_CREATE = "CREATE TABLE steps ("+
			  "id integer NOT NULL,"+
			  "recipe_id integer DEFAULT NULL,"+
			  "body text,"+
			  "photo_file_name varchar(255)  DEFAULT NULL,"+
			  "photo_content_type varchar(255)  DEFAULT NULL,"+
			  "photo_file_size integer DEFAULT NULL,"+
			  "photo_updated_at text DEFAULT NULL,"+
			  "created_at text DEFAULT NULL,"+
			  "updated_at text DEFAULT NULL,"+
			  "photo_processing integer DEFAULT NULL);";


	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(RECIPES_TABLE_CREATE);
		database.execSQL(STEPS_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(NyamOpenHelper.class.getName(),
				"Upgrading database from version " + 0 + " to "
						+ 1 + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS recipes");
		db.execSQL("DROP TABLE IF EXISTS steps");
		onCreate(db);
	}

}
