package com.st.nyam.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

public class SynchronizeService extends IntentService {
	
	private static final String TAG = "SynchronizeService";
	private NyamApplication application;
	private DataBaseFactory db;
	private String login;
	private String password;
	private ResultReceiver receiver;

	public SynchronizeService() {
        super("SynchronizeService");
    }
	
	@Override
    protected void onHandleIntent(Intent intent) {
		try {
			Log.d(TAG, "onHandleIntent 1");
			receiver = (ResultReceiver) intent.getParcelableExtra("receiver");
	        Log.d(TAG, "onHandleIntent 2");
	        application = (NyamApplication)getApplication();
	        db = application.getDB();
	        login = NyamApplication.getLogin();
	        password = NyamApplication.getPassword();
	        ArrayList<RecipeGeneral> knownRecipes = db.getRecipes();
	       
	        Log.d(TAG, "SynchronizeService login: " + login);
			Log.d(TAG, "SynchronizeService password: " + password);
	        Log.d(TAG, "onHandleIntent 3");
	        if (HttpFactory.isNetworkAvailable(this)) {
		        ArrayList<RecipeGeneral> recipes = getSyncRecipes(knownRecipes);
		        Log.d(TAG, "onHandleIntent 4");
		        Bundle resultData = new Bundle();
		        resultData.putSerializable("recipes_from_server", recipes);
		        Log.d(TAG, "onHandleIntent 5");
		        receiver.send(Constants.AUTHORIZATION_PASSED, resultData);
		        Log.d(TAG, "onHandleIntent 6");    
	        }
		} catch (Exception e) {
			
		}
    }
	
	 private ArrayList<RecipeGeneral> getSyncRecipes(ArrayList<RecipeGeneral> knownRecipes) throws IllegalStateException, JSONException {
		 
		   	ArrayList<RecipeGeneral> resultList = null;
		   	String result = "";
		   	try {
	    	Log.d(TAG, "AsyncLogin 1");
	    	
	    		StringBuilder idsParams = new StringBuilder();
		    	for (int i = 0; i < knownRecipes.size(); i ++) {
		    		idsParams.append("&ids[]=" + knownRecipes.get(i).getId());
		    	}
		    	result = HttpFactory.getSyncJSONRecipes(idsParams.toString());
		    	Log.d(TAG, "list of recipes sync = " +  result);
		    	resultList = ModelUtil.getRecipesFromJSONString(result);
		    	
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
	    	    List<Integer> ids_to_delete = ModelUtil.getListToDelete(result);
	    	    if (ids_to_delete != null && !ids_to_delete.isEmpty()) {
	    	    	for(Integer i : ids_to_delete) {
	    	    		Log.d(TAG, "Deleted recipe id: " + i);
	    	    		Recipe recipe = db.getRecipeById(i);
	    	    		if (recipe != null) {
	    	    			Log.d(TAG, "Deleting recipe: ");
	    	    			db.deleteRecipeFromFavorites(recipe);
	    	    		}
	    	    	}
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
	    

}
