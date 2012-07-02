package com.st.nyam.util;

import android.content.Intent;

public class Constants {
	
	public final static String URL = "http://192.168.11.150:3001";
	public final static String URL_MY = "http://192.168.11.150:3001";
	public final static String URL_RECIPES = "http://192.168.11.150:3001/recipes";
	public final static String JSON = ".json";
	
	public final static String URL_ADD_TO_FAVORITES = "http://192.168.11.150:3001/items";
	public final static String DELETE_FROM_FAVORITES = "delete_from_favorites";
	
	//public final static String URL_LOGIN = "http://77.120.196.135/users/sign_in";
	//public final static String URL_LOGIN = "http://192.168.11.121:3000/users/sign_in";
	
	public final static String URL_LOGIN = "http://192.168.11.150:3001/users/sign_in";
	public final static String URL_SYNC = "http://192.168.11.150:3001/recipes/sync";
	public final static String URL_SEARCH = "http://192.168.11.150:3001/search/simple";
	
	public final static String LOGIN = "s777345@gmail.com";
	public final static String PASSWORD = "123321";
	public final static String LOGIN_TEST = "user@nyam.re";
	public final static String PASSWORD_TEST = "1111111";
	
	
	//Actions
	public final static String ACTION_NEW_RECIPES = "NEW_RECIPES";
	public final static String ACTION_CATALOG_RECIPES = "CATALOG_RECIPES";
	public final static String ACTION_FAVORITE_RECIPES = "FAVORITE_RECIPES";
	public final static String ACTION_SEARCH = Intent.ACTION_SEARCH;
	
	//Dialogs
	public static final int DAILOG_INTERNET_UNAVAILABLE = 1;
	public static final int DAILOG_LOGINING = 2;
	public static final int FAVORITES_PRESSED = 3;
	
	//ResultReceiver ResultCode
	public static final int AUTHORIZATION_PASSED = 1;
	public static final int AUTHORIZATION_NOT_PASSED = 2;
	
	//Default Photo
	public static final String defaultPhoto  = "/uploads/recipes/default_processing_step/small.png";
	public static final String defaultPhoto1  = "/uploads/recipes/default_step/small.png";
	
	//RESULT CODES
	public static final int RESULT_OK_REMOVE_FAV = 5;
	public static final int RESULT_OK_ADD_FAV = 6;
	
	public static final String SERVER_LOGIN_STATUS_RESPOND_OK = "{\"status\":\"ok\"}";
	public static final String SERVER_LOGIN_STATUS_RESPOND_TRUE = "{\"status\":true}";
    
}
