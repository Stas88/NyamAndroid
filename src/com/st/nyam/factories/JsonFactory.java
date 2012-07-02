package com.st.nyam.factories;

import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class JsonFactory {

	private static final String TAG = "JsonFactory";

	final public static JSONObject getJsonObjectFromUrl (String url, Context context) {
		String jsonString = HttpFactory.get(url, context);
		JSONObject jsonResponse = null;
		try {
			jsonResponse = new JSONObject(jsonString.trim());
		} catch (JSONException ex){
			Log.e(TAG, "JSON failed; " + ex.getMessage());
		}
		return jsonResponse;
	}
	final public static JSONObject putJsonObjectToUrl (String url, List<NameValuePair> nameValuePairs) {
		String jsonString = HttpFactory.post(url, nameValuePairs);
		JSONObject jsonResponse = null;
		try {
			jsonResponse = new JSONObject(jsonString);
		} catch (JSONException ex) {
			Log.e(TAG, "JSON failed; " + ex.getMessage());
		}
		return jsonResponse;
	}

	final public static JSONArray getJsonArrayFromUrl(String url, Context context) {
		String jsonString = HttpFactory.get(url, context);
		Log.d(TAG, "jsonString: " + jsonString);

		JSONArray jsonResponse = null;
		try {
			String newString = jsonString.trim();
			jsonResponse = new JSONArray(newString);
		} catch (JSONException ex) {
			Log.e(TAG, "JSON failed; " + ex.getMessage());
		}  catch (NullPointerException ex) {
			Log.e(TAG, "JSON failed; " + ex.getMessage());
		}
		return jsonResponse;
	}
	
	final public static JSONArray getJsonArrayFromUrlByName(String url, String name, Context context) {
		String jsonString = HttpFactory.get(url, context);
		Log.d(TAG, "jsonString By Name: " + jsonString);

		JSONArray jsonResponse = null;
		JSONObject object = null;
		try {
			object = new JSONObject(jsonString.trim());
			jsonResponse = object.getJSONArray(name);
			Log.d(TAG, "jsonString By Name StepsArray: " + jsonResponse.toString());
		} catch (JSONException ex) {
			Log.e(TAG, "JSON failed; " + ex.getMessage());
		}
		return jsonResponse;
	}
}