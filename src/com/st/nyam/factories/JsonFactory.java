package com.st.nyam.factories;

import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JsonFactory {

	private static final String TAG = "JsonFactory";

	final public static JSONObject getJsonObjectFromUrl (String url) {
		String jsonString = HttpFactory.get(url);
		JSONObject jsonResponse = null;
		try {
			jsonResponse = new JSONObject(jsonString);
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

	final public static JSONArray getJsonArrayFromUrl(String url) {
		String jsonString = HttpFactory.get(url);
		Log.d(TAG, "jsonString: " + jsonString);

		JSONArray jsonResponse = null;
		try {
			jsonResponse = new JSONArray(jsonString.trim());
		} catch (JSONException ex) {
			Log.e(TAG, "JSON failed; " + ex.getMessage());
		}
		return jsonResponse;
	}
}