package com.st.nyam.factories;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class HttpFactory {

	private static final String TAG = "HttpFactory";

	final static public String get(String url) {
		// return object
		String result = null;
		// Create the httpclient
		HttpClient httpclient = new DefaultHttpClient();
		// Prepare a request object
		Log.d(TAG, "REQUEST: " + url);
		HttpGet httpGet = new HttpGet(url); 
		// Execute the request
		HttpResponse response;
		try {
			// Open the webpage.
			response = httpclient.execute(httpGet);
			if(response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
				// Connection was established. Get the content. 
				HttpEntity entity = response.getEntity();
				// If the response does not enclose an entity, there is no need
				// to worry about connection release
				if (entity != null) {
					// A Simple JSON Response Read
					InputStream instream = entity.getContent();

					// Load the requested page converted to a string into a JSONObject.
					result = HttpFactory.convertStreamToString(instream);
					Log.d(TAG, "RESPONSE: " + result);

					// Cose the stream.
					instream.close();
				}
			} else {
				// code here for a response othet than 200.  A response 200 means the webpage was ok
				// Other codes include 404 - not found, 301 - redirect etc...
				// Display the response line.
				Log.d(TAG, "Unable to load page - " + response.getStatusLine());
			}
		} catch (ClientProtocolException e) {
			Log.d(TAG, "Connection failed; " + e.getMessage());
		} catch (IOException e) {
			// thrown by line 80 - getContent();
			// Connection was not established
			Log.d(TAG, "Connection failed; " + e.getMessage());
		}
		return result;
	}

	final static public String post(String url, List<NameValuePair> nameValuePairs) {
		// return object
		String result = null;
		// Create the httpclient
		HttpClient httpclient = new DefaultHttpClient();
		// Prepare a request object
		Log.d(TAG, "REQUEST: " + url);
		Log.d(TAG, "PARAMS: " + nameValuePairs.toString());
		HttpPost httpPost = new HttpPost(url); 
		// Execute the request
		HttpResponse response;
		try {
			// Add your data
			UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
			httpPost.setEntity(postEntity);
			// Execute HTTP Post Request
			response = httpclient.execute(httpPost);
			if(response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
				// Connection was established. Get the content. 
				HttpEntity entity = response.getEntity();
				// If the response does not enclose an entity, there is no need
				// to worry about connection release
				if (entity != null) {
					// A Simple JSON Response Read
					InputStream instream = entity.getContent();

					// Load the requested page converted to a string into a JSONObject.
					result = HttpFactory.convertStreamToString(instream);
					Log.d(TAG, "RESPONSE: " + result);

					// Cose the stream.
					instream.close();
				}
			} else {
				// code here for a response othet than 200.  A response 200 means the webpage was ok
				// Other codes include 404 - not found, 301 - redirect etc...
				// Display the response line.
				Log.d(TAG, "Unable to load page - " + response.getStatusLine());
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		return result;
	}

	final static private String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the BufferedReader.readLine()
		 * method. We iterate until the BufferedReader return null which means
		 * there's no more data to read. Each line will appended to a StringBuilder
		 * and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			Log.d("HttpFactory", e.getMessage());
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				Log.d("HttpFactory",  e.getMessage());
			}
		}
		return sb.toString();
	}

}