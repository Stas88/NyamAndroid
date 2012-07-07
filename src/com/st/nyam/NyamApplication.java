package com.st.nyam;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.st.nyam.factories.DataBaseFactory;
import com.st.nyam.factories.HttpFactory;
import com.st.nyam.models.MainCategory;

public class NyamApplication extends Application {

	private DataBaseFactory db;
	private static final String TAG = "NyamApplication";
	private ArrayList<Map<String, String>> childDataItem;
    private ArrayList<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
    private ArrayList<ArrayList<Map<String, String>>> childData = new ArrayList<ArrayList<Map<String, String>>>(); 
    private Object  []  mainCategories; 
    private Map<String, String> m;
    private static String login = "";
    private static String password = "";
    private static String token_name = "";
    private static String token_value = "";
    private boolean isLoginned;
    
	public void onCreate() {
		try {
			Class.forName("android.os.AsyncTask");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Object [] params = new Object [] {};
		new SetUpCookies().execute(params);
		db = new DataBaseFactory(this);
		mainCategories =  db.getMainCategories(0).toArray();
		isLoginned = false;
		fillFirstLayer();
		fillChildData();
		fillLoginPassword();
		super.onCreate();
	}
	
	public static void setUpCookies(Context context) {
		if (HttpFactory.isNetworkAvailable(context)) {
			try {
				HttpFactory.setUpCookies();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	
	public void onDestroy() {
		storeLoginPassword();
	}

	public DataBaseFactory getDB() {
		return db;
	}
	
	public ArrayList<Map<String, String>> getFirstLayer() {
		return groupData;
	}
	
	public ArrayList<ArrayList<Map<String, String>>> getSecondLayer() {
		return childData;
	}
	
	private void fillChildData() {
		for( Object o : mainCategories) {
        	childDataItem = new ArrayList<Map<String, String>>();
        	int parent_id = ((MainCategory)o).getId();
        	Object []  levelTwoCats = db.getMainCategories(parent_id).toArray();
        	for (Object ob  : levelTwoCats) {
  	          m = new HashMap<String, String>();
  	            m.put("levelTwoCat", ((MainCategory)ob).getName()); // название телефона
  	            childDataItem.add(m);  
  	        }
        	childData.add(childDataItem);
        }
	}
	
	private void fillFirstLayer() {
		for (Object category  : mainCategories) {
	          m = new HashMap<String, String>();
	            m.put("groupName", ((MainCategory)category).getName()); // имя компании
	            groupData.add(m);  
	        }
	        
	}

	public static String getLogin() {
		return NyamApplication.login;
	}

	public void setLogin(String login) {
		NyamApplication.login = login;
	}

	public static String getPassword() {
		return NyamApplication.password;
	}

	
	public void setPassword(String password) {
		NyamApplication.password = password;
	}
	
	private void fillLoginPassword() {
		SharedPreferences login_app_preferences = this.getSharedPreferences("login_details2", MODE_PRIVATE);
		NyamApplication.login = login_app_preferences.getString("login",
				"");
		NyamApplication.password = login_app_preferences.getString(
				"password", "");
	}
	
	public void storeLoginPassword() {
		Log.d(TAG, "Started store login and password");
		SharedPreferences login_app_preferences = this.getSharedPreferences(
				"login_details2", MODE_PRIVATE);
		SharedPreferences.Editor editor = login_app_preferences.edit();
		editor.putString("login", NyamApplication.login);
		editor.putString("password", NyamApplication.password);
		editor.commit();
		
		Log.d(TAG, "storeLoginPassword login: " + NyamApplication.login);
		Log.d(TAG, "storeLoginPassword password: " + NyamApplication.password);
		Log.d(TAG, "Ended store login and password");
	}
	
	public static boolean isPasswordExists() {
		if (!NyamApplication.login.equals("") && !NyamApplication.password.equals("") && NyamApplication.login != null && NyamApplication.password != null) {
			Log.d(TAG, "storeLoginPassword login: " + NyamApplication.login);
			Log.d(TAG, "storeLoginPassword password: " + NyamApplication.password);
			Log.d(TAG, "!NyamApplication.login.equals(\"\") " + !NyamApplication.login.equals(""));
			Log.d(TAG, " !NyamApplication.password.equals(\"\") " + !NyamApplication.password.equals(""));
			Log.d(TAG, "NyamApplication.login != null " + (NyamApplication.login != null));
			Log.d(TAG, "NyamApplication.password != null " + (NyamApplication.password != null));
			Log.d(TAG, "isPasswordExists() true");
			return true;
		} else {
			Log.d(TAG, "isPasswordExists() false");
			return false;
		}
	}

	public boolean isLoginned() {
		return isLoginned;
	}

	public void setLoginned(boolean isLoginned) {
		this.isLoginned = isLoginned;
	}

	public static String getToken_name() {
		return token_name;
	}

	public static void setToken_name(String token_name) {
		NyamApplication.token_name = token_name;
	}

	public static String getToken_value() {
		return token_value;
	}

	public static void setToken_value(String token_value) {
		NyamApplication.token_value = token_value;
	}
	
	private class SetUpCookies extends AsyncTask<Object, String, Boolean> {
		
		@Override
		protected Boolean doInBackground(Object... params) {
			Boolean isAdded = null;
			try {
				HttpFactory.setUpCookies();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return isAdded;
		}
	}
}