package com.st.nyam.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.ResultReceiver;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.Log;

import com.st.nyam.NyamApplication;
import com.st.nyam.factories.DataBaseFactory;
import com.st.nyam.factories.HttpFactory;
import com.st.nyam.models.Recipe;
import com.st.nyam.models.RecipeGeneral;
import com.st.nyam.models.Step;
import com.st.nyam.util.Constants;
import com.st.nyam.util.ModelUtil;
import com.st.nyam.util.SanInputStream;

public class ConstantSynchronizationService extends IntentService {
	
	private static final String TAG = "ConstantSynchronizationService";
	private NyamApplication application;
	private DataBaseFactory db;
	private String login;
	private String password;
	private ResultReceiver receiver;

	public ConstantSynchronizationService() {
        super("ConstantSynchronizationService");
    }
	
	@Override
    protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "ConstantSynchronizationService started");
		receiver = (ResultReceiver) intent.getParcelableExtra("receiver");
        Log.d(TAG, "onHandleIntent 2");
        application = (NyamApplication)getApplication();
        db = application.getDB();
        SharedPreferences login_app_preferences =  this.getSharedPreferences("login_details", MODE_PRIVATE);
		login = login_app_preferences.getString("login", "");
		password = login_app_preferences.getString("password", "");
		if (login.equals("") || password.equals("")) {
			Log.d(TAG, "Service no password"); 									
		} else {
			Log.d(TAG, "Something stored"); 
			Log.d(TAG, "SynchronizeService login: " + login);
			Log.d(TAG, "SynchronizeService password: " + password);
	        Log.d(TAG, "onHandleIntent 3");
	        ArrayList<RecipeGeneral> recipes = getSyncRecipes();
	        Log.d(TAG, "onHandleIntent 4");  
		}
		scheduleNextUpdate();
    }
	
	 private ArrayList<RecipeGeneral> getSyncRecipes() {
    	ArrayList<RecipeGeneral> resultList = null;
    	try {
	    	Log.d(TAG, "AsyncLogin 1");
	    	ArrayList<RecipeGeneral> knownRecipes = db.getRecipes();
	    	int [] ids = new int [knownRecipes.size()];
	    	for (int i = 0; i < knownRecipes.size(); i ++) {
	    		ids[i] = knownRecipes.get(i).getId();
	    	}
	    	String holefavorites = login(ids);
	    	resultList = getRecipesFromJSONString(holefavorites);
	    	Log.d(TAG, "AsyncLogin 2");
	    	if (resultList != null && !resultList.isEmpty()) {
	    		Log.d(TAG, "AsyncLogin 3");
	    		for (RecipeGeneral recipe : resultList) {
	    			Log.d(TAG, "AsyncLogin 4");
	    			InputStream in = new java.net.URL(Constants.URL + recipe.getImg_url()).openStream();
	    			Log.d(TAG, Constants.URL + recipe.getImg_url());
	    			Bitmap bitmap = BitmapFactory.decodeStream(new SanInputStream(in));
	    			Log.d(TAG, "Bitmap: " + bitmap);
	    			db.addRecipeToFavorites((Recipe)recipe, bitmap);
	    		}
	    	} else {
	    		Log.d(TAG, "ResultList is empty");
	    	}
    	} catch (MalformedURLException e) {
    		e.printStackTrace();
    		receiver.send(Constants.AUTHORIZATION_NOT_PASSED, null);
    	} catch (IOException e) {
    		receiver.send(Constants.AUTHORIZATION_NOT_PASSED, null);
    		e.printStackTrace();
    	}
    	return resultList;
    }
	    
	    
	private String login(int... ids) {
		Log.d(TAG, "login(ids... method start");
		String result = "";
		String result2 = "";
		ArrayList<RecipeGeneral> recipes = null;
		try {
			DefaultHttpClient httpclient = new DefaultHttpClient();
	        HttpGet httpget = new HttpGet(Constants.URL_LOGIN);
	        httpget.setHeader("Accept", "application/json");
	
	        HttpResponse response = httpclient.execute(httpget);
	        HttpEntity entity = response.getEntity();
	        Log.d(TAG, "Login form get: " + response.getStatusLine());
	        Log.d(TAG, "Entity: " +  entity.toString());
	        if (entity != null) {
	            result = HttpFactory.convertStreamToString(entity.getContent());
	            Log.d(TAG, "Result: " +  result);
	        }
	        
	        List<Cookie> cookies = httpclient.getCookieStore().getCookies();
	        if (cookies.isEmpty()) {
	        	Log.d(TAG, "No cookies");
	        } else {
	            for (int i = 0; i < cookies.size(); i++) {
	            	Log.d(TAG, "Cookies: " + cookies.get(i).toString());
	            }
	        }
	        
	        JSONObject jsonResponse = new JSONObject(result.trim());
	        Log.d(TAG, "jsonResponce: " + jsonResponse.toString());
	        String token_value = jsonResponse.getString("token_value");
	        String token_name = jsonResponse.getString("token_name");
	        Log.d(TAG, "token_value: " + token_value);
	        Log.d(TAG, "token_name: " + token_name);
			
	        HttpPost httpost = new HttpPost(Constants.URL_LOGIN);
	        httpost.setHeader("Accept", "application/json");
	
	        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	        nvps.add(new BasicNameValuePair("user[email]", login));
	        nvps.add(new BasicNameValuePair("user[password]", password));
	        nvps.add(new BasicNameValuePair(token_name, token_value));

	        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
	
	        HttpResponse response1 = httpclient.execute(httpost);
	        HttpEntity entity1 = response1.getEntity();
	
	        Log.d(TAG,"Login form post: " + response1.getStatusLine());
	        if (entity1 != null) {
	        	result = HttpFactory.convertStreamToString(entity1.getContent());
	        	Log.d(TAG, "Entity: " +  result);
	        }
	        
	        StringBuilder idsParams = new StringBuilder();
	        for (int id : ids) {
	        	idsParams.append("&ids[]="+id);
	        }
	        HttpGet httpget2 = new HttpGet(Constants.URL_SYNC+"?ids[]=0"+idsParams);
	        httpget2.setHeader("Accept", "application/json");
	        Log.d(TAG, "Request line: " + httpget2.getRequestLine());
	        Log.d(TAG, "Request params: " + httpget2.getParams());
	        Log.d(TAG, "Request uri: " + httpget2.getURI());
	        
	        HttpResponse response2 = httpclient.execute(httpget2);
	        HttpEntity entity2 = response2.getEntity();
	
	        Log.d(TAG, "Login form get2: " + response2.getStatusLine());
	        Log.d(TAG, "Entity2: " +  entity2.toString());
	     
	        if (entity2 != null) {
	            result2 = HttpFactory.convertStreamToString(entity2.getContent());
	            Log.d(TAG, "Result2: " +  result2);
	        }
	        
	        List<Cookie> cookies2= httpclient.getCookieStore().getCookies();
	        if (cookies2.isEmpty()) {
	        	Log.d(TAG, "No cookies2 ");
	        } else {
	            for (int i = 0; i < cookies2.size(); i++) {
	            	Log.d(TAG, "Cookies2 : " + cookies2.get(i).toString());
	            }
	        }
		} catch (Exception e) {
			e.printStackTrace();
			receiver.send(Constants.AUTHORIZATION_NOT_PASSED, null);
		}
		return result2;
	}
	
	public ArrayList<RecipeGeneral> getRecipesFromJSONString(String result2) {
		ArrayList<RecipeGeneral> recipes = new ArrayList<RecipeGeneral>();
		try {
			JSONArray jsonElements = new JSONArray(result2);
			Log.d(TAG, "In MainListActivity getRecipes(): " + jsonElements);
			Log.d(TAG, "jsonElements.length(): " + jsonElements.length());
			for (int i = 0; i < jsonElements.length(); i++) {
				Recipe favRecipe = (ModelUtil.getRecipeFromJSON(jsonElements.getJSONObject(i)));
				Log.d(TAG, "Recipe: " + jsonElements.getJSONObject(i));
				JSONArray stepsArray = jsonElements.getJSONObject(i).getJSONArray("steps");
				Log.d(TAG, "Steps: " + jsonElements.getJSONObject(i).getJSONArray("steps"));
				ArrayList<Step> favSteps = new ArrayList<Step>();
				
				for (int j = 0; j < stepsArray.length(); j++) {
					Step step = ModelUtil.getStepFromJSON(stepsArray.getJSONObject(j));
					Log.d(TAG, "Step: " + step);
					favSteps.add(step);
				}
				Log.d(TAG, "Steps ArrayList: " + favSteps);
				favRecipe.setSteps(favSteps);
				recipes.add((RecipeGeneral)favRecipe);
				Log.d(TAG, "In MainListActivity getRecipes() Adding every recipe");
			}
		} catch (Exception e) {
			e.printStackTrace();
			receiver.send(Constants.AUTHORIZATION_NOT_PASSED, null);
		}
		return recipes;
	}
	
	
	private void scheduleNextUpdate() {
		    Intent intent = new Intent(this, this.getClass());
		    PendingIntent pendingIntent =
		        PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	
		    long currentTimeMillis = System.currentTimeMillis();
		    long nextUpdateTimeMillis = currentTimeMillis + 3 * DateUtils.MINUTE_IN_MILLIS;
		    Time nextUpdateTime = new Time();
		    nextUpdateTime.set(nextUpdateTimeMillis);
	
		    if (nextUpdateTime.hour < 8 || nextUpdateTime.hour >= 18)
		    {
		      nextUpdateTime.hour = 8;
		      nextUpdateTime.minute = 0;
		      nextUpdateTime.second = 0;
		      nextUpdateTimeMillis = nextUpdateTime.toMillis(false) + DateUtils.DAY_IN_MILLIS;
		    }
		    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		    alarmManager.set(AlarmManager.RTC, nextUpdateTimeMillis, pendingIntent);
	}
}

