package com.st.nyam.factories;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.util.Log;

import com.st.nyam.NyamApplication;
import com.st.nyam.util.Constants;
import com.st.nyam.util.HttpDeleteWithBody;

public class HttpFactory  {

	private static final String TAG = "HttpFactory";
	private static String token_value;
	private static String token_name;
	private static DefaultHttpClient httpclient;

	static {
		HttpParams params = new BasicHttpParams();
	    SchemeRegistry registry = new SchemeRegistry();
	    registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	    ClientConnectionManager cm = new ThreadSafeClientConnManager(params, registry);
	    httpclient = new DefaultHttpClient(cm, params);
	}

	public final static String get(String url, Context context) {

		// return object
		String result = null;
		// Create the httpclient
		if (isNetworkAvailable(context)) {
		
			// Prepare a request object
			Log.d(TAG, "REQUEST: " + url);
			HttpGet httpGet = new HttpGet(url);
			// Execute the request
			HttpResponse response;
			try {
				// Open the webpage.
				response = httpclient.execute(httpGet);
				if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
					// Connection was established. Get the content.
					HttpEntity entity = response.getEntity();
					// If the response does not enclose an entity, there is no
					// need
					// to worry about connection release
					if (entity != null) {
						// A Simple JSON Response Read
						InputStream instream = entity.getContent();

						// Load the requested page converted to a string into a
						// JSONObject.
						result = HttpFactory.convertStreamToString(instream);
						Log.d(TAG, "RESPONSE: " + result);

						// Cose the stream.
						instream.close();
					}
				} else {
					Log.d(TAG,
							"Unable to load page - " + response.getStatusLine());
				}
			} catch (ClientProtocolException e) {
				Log.d(TAG, "Connection failed; " + e.getMessage());
			} catch (IOException e) {
				Log.d(TAG, "Connection failed; " + e.getMessage());
			}
		} else {
			Log.d(TAG, "Network unavalable ");
		}
		return result;
	}

	public final static String post(String url,
			List<NameValuePair> nameValuePairs) {
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
			UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(
					nameValuePairs, "UTF-8");
			httpPost.setEntity(postEntity);
			// Execute HTTP Post Request
			response = httpclient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
				// Connection was established. Get the content.
				HttpEntity entity = response.getEntity();
				// If the response does not enclose an entity, there is no need
				// to worry about connection release
				if (entity != null) {
					// A Simple JSON Response Read
					InputStream instream = entity.getContent();

					// Load the requested page converted to a string into a
					// JSONObject.
					result = HttpFactory.convertStreamToString(instream);
					Log.d(TAG, "RESPONSE: " + result);

					// Cose the stream.
					instream.close();
				}
			} else {
				// code here for a response othet than 200. A response 200 means
				// the webpage was ok
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

	public final static String convertStreamToString(InputStream is) {

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
				Log.d("HttpFactory", e.getMessage());
			}
		}
		return sb.toString();
	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager conMan = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		//mobile
		State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		//wifi
		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();

		// if no network is available networkInfo will be null
		// otherwise check if we are connected
		if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {
			Log.d(TAG, "isNetworkAvailable true");
			return true;
		}
		return false;
	}

	public static int sendAddToFavorites(Context context, int recipeId) throws JSONException {
		String result = null;
		int item_id = -1;
		try {
			//Tokens
			if (isLoginned()) {
				Log.d(TAG, "isLoginned()");
				// Adding to favorites
				HttpPost httget = new HttpPost(Constants.URL_ADD_TO_FAVORITES
						+ "?recipe[id]=" + recipeId);
				httget.setHeader("Accept", "application/json");
				
				List<NameValuePair> nvps1 = new ArrayList<NameValuePair>();
				nvps1.add(new BasicNameValuePair("recipe[id]", String
						.valueOf(recipeId)));
				nvps1.add(new BasicNameValuePair(HttpFactory.token_name, HttpFactory.token_value));
				httget.setEntity(new UrlEncodedFormEntity(nvps1, HTTP.UTF_8));
	
				HttpResponse response2 = httpclient.execute(httget);
				HttpEntity entity2 = response2.getEntity();
	
				Log.d(TAG, "Login form post: " + response2.getStatusLine());
				if (entity2 != null) {
					result = HttpFactory
							.convertStreamToString(entity2.getContent());
					Log.d(TAG, "Entity: " + result);
					JSONObject object = new JSONObject(result);
					item_id = object.getInt("item_id");
					Log.d(TAG, "item_id: " + item_id);
				}
			} else {
				Log.d(TAG, "Not isLoginned()");
			}
		} catch (ClientProtocolException e) {
			Log.d(TAG, "Connection failed; " + e.getMessage());
		} catch (IOException e) {
			Log.d(TAG, "Connection failed; " + e.getMessage());
		}
		return item_id;
	}
	
	
	
	public static void sendDeleteFromFavorites(Context context, int itemId) throws JSONException {
		String result = null;
		try {
			if (isLoginned()) {
				// Deleting from favorites
				HttpDeleteWithBody httdelete = new HttpDeleteWithBody(Constants.URL + "/items/" + itemId);
				Log.d(TAG, "Delete Statement: " + Constants.URL + "/items/" + itemId);

				List<NameValuePair> nvps1 = new ArrayList<NameValuePair>();
				nvps1.add(new BasicNameValuePair(HttpFactory.token_name, HttpFactory.token_value));
				
				httdelete.setEntity(new UrlEncodedFormEntity(nvps1, HTTP.UTF_8));
				httdelete.setHeader("Accept", "application/json");
				HttpResponse response2 = httpclient.execute(httdelete);
				HttpEntity entity2 = response2.getEntity();

				Log.d(TAG, "Login form post: " + response2.getStatusLine());
				if (entity2 != null) {
					result = HttpFactory
							.convertStreamToString(entity2.getContent());
					Log.d(TAG, "Entity2: " + result);
				}
			} else {
				Log.d(TAG, "Not isLoginned()");
			}
		} catch (ClientProtocolException e) {
			Log.d(TAG, "Connection failed; " + e.getMessage());
		} catch (IOException e) {
			Log.d(TAG, "Connection failed; " + e.getMessage());
		}
	}
	
	private static void setUpTokens() throws ClientProtocolException, IOException, JSONException {
		String result = "";
		// Requesting tokens
			HttpGet httpget = new HttpGet(Constants.URL_LOGIN);
			httpget.setHeader("Accept", "application/json");

			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			Log.d(TAG, "Login form get: " + response.getStatusLine());
			Log.d(TAG, "Entity: " + entity.toString());
			if (entity != null) {
				result = HttpFactory.convertStreamToString(entity.getContent());
				Log.d(TAG, "Result: " + result);
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
			HttpFactory.token_value = jsonResponse.getString("token_value");
			HttpFactory.token_name = jsonResponse.getString("token_name");
			Log.d(TAG, "setUpTokens token_value: " + HttpFactory.token_value);
			Log.d(TAG, "setUpTokens token_name: " + HttpFactory.token_name);
	}
	
	public static boolean isLoginned() throws IllegalStateException, IOException, JSONException {
		String result = "";
		// Requesting tokens
		setUpTokens();
		// Logining
		HttpPost httpost = new HttpPost(Constants.URL_LOGIN);
		httpost.setHeader("Accept", "application/json");

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	
		nvps.add(new BasicNameValuePair("user[email]", NyamApplication.getLogin()));
		nvps.add(new BasicNameValuePair("user[password]", NyamApplication.getPassword()));
		//nvps.add(new BasicNameValuePair("user[remember_me]", "1"));
		nvps.add(new BasicNameValuePair(HttpFactory.token_name, HttpFactory.token_value));
		nvps.add(new BasicNameValuePair(HttpFactory.token_name, HttpFactory.token_value));

		httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

		HttpResponse response1 = httpclient.execute(httpost);
		HttpEntity entity1 = response1.getEntity();

		Log.d(TAG, "Login form post: " + response1.getStatusLine());
		if (entity1 != null) {
			result = HttpFactory
					.convertStreamToString(entity1.getContent());
			Log.d(TAG, "Entity1: " + result);
		}
		boolean response;
		try {
			JSONObject jsonResponse = new JSONObject(result);
			Log.d(TAG, "jsonResponse: " + jsonResponse.getBoolean("status"));
			response = jsonResponse.getBoolean("status");
			Log.d(TAG, "response: " + response);
		} catch (Exception e) {
			return false;
		}
		if (response) {
			Log.d(TAG, "isLoginned(String login, String password) " + true);
			return true;
		} else {
			Log.d(TAG, "isLoginned(String login, String password): " + false);
			return false;
		}
   }
	
	public static boolean isLoginned(String login, String password) throws IllegalStateException, IOException, JSONException {
		String result = "";
		// Requesting tokens
		setUpTokens();
		// Logining
		HttpPost httpost = new HttpPost(Constants.URL_LOGIN);
		httpost.setHeader("Accept", "application/json");

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Log.d(TAG, "isLoginned(String login, String password) login : " + login);
		Log.d(TAG, "isLoginned(String login, String password) pasword : " + password);
		nvps.add(new BasicNameValuePair("user[email]", login));
		nvps.add(new BasicNameValuePair("user[password]", password));
		nvps.add(new BasicNameValuePair(HttpFactory.token_name, HttpFactory.token_value));

		httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

		HttpResponse response1 = httpclient.execute(httpost);
		HttpEntity entity1 = response1.getEntity();

		Log.d(TAG, "Login form post: " + response1.getStatusLine());
		if (entity1 != null) {
			result = HttpFactory
					.convertStreamToString(entity1.getContent());
			Log.d(TAG, "Entity1: " + result);
		}
		boolean response;
		try {
			JSONObject jsonResponse = new JSONObject(result);
			Log.d(TAG, "jsonResponse: " + jsonResponse.getBoolean("status"));
			response = jsonResponse.getBoolean("status");
			Log.d(TAG, "response: " + response);
		} catch (Exception e) {
			return false;
		}
		if (response) {
			Log.d(TAG, "isLoginned(String login, String password) " + true);
			return true;
		} else {
			Log.d(TAG, "isLoginned(String login, String password): " + false);
			return false;
		}
   }

	public static String getSyncJSONRecipes(String idsParams) throws ClientProtocolException, IOException {
		String result = "";
		HttpGet httpget2 = new HttpGet(Constants.URL_SYNC + "?ids[]=0" + idsParams);
        httpget2.setHeader("Accept", "application/json");
        Log.d(TAG, "Request line: " + httpget2.getRequestLine());
        Log.d(TAG, "Request params: " + httpget2.getParams());
        Log.d(TAG, "Request uri: " + httpget2.getURI());
        Log.d(TAG, "idsParams: " + idsParams);
        HttpResponse response2 = httpclient.execute(httpget2);
        HttpEntity entity2 = response2.getEntity();

        Log.d(TAG, "Login form get2: " + response2.getStatusLine());
        Log.d(TAG, "Recipes sync: " +  entity2.toString());
     
        if (entity2 != null) {
            result = HttpFactory.convertStreamToString(entity2.getContent());
            Log.d(TAG, "getSyncJSONRecipes Result2: " +  result);
        }
		return result;
	}
	
	 public static String getProfileString() throws IllegalStateException, JSONException {
		 String result = null;
				try {
					isLoginned();
					//setUpTokens();
				  Log.d(TAG, "getProfileString: " +  1);
				// Deleting from favorites
				// Prepare a request object
				HttpGet httpGet = new HttpGet(Constants.Profile_URL + Constants.JSON);
				// Execute the request
				HttpResponse response;
				Log.d(TAG, "getProfileString: " +  2);
				try {
					// Open the webpage.
					response = httpclient.execute(httpGet);
					if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
						Log.d(TAG, "getProfileString: " +  3);
						// Connection was established. Get the content.
						HttpEntity entity = response.getEntity();
						// If the response does not enclose an entity, there is no
						// need
						// to worry about connection release
						Log.d(TAG, "getProfileString: " +  4);
						if (entity != null) {
							// A Simple JSON Response Read
							InputStream instream = entity.getContent();
							// Load the requested page converted to a string into a
							// JSONObject.
							Log.d(TAG, "getProfileString: " +  5);
							result = HttpFactory.convertStreamToString(instream);
							Log.d(TAG, "RESPONSE: " + result);
							// Cose the stream.
							instream.close();
						}
						Log.d(TAG, "getProfileString: " +  6);
					} else {
						Log.d(TAG,
								"Unable to load page - " + response.getStatusLine());
					}
				} catch (ClientProtocolException e) {
					Log.d(TAG, "Connection failed; " + e.getMessage());
				} catch (IOException e) {
					Log.d(TAG, "Connection failed; " + e.getMessage());
				}	
			
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return result;
	 }
	 

	
}