package com.st.nyam.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.st.nyam.R;
import com.st.nyam.models.RecipeGeneral;

public class SD_util {
	
	private final static String TAG = "SD_util";
	private  Bitmap stepBitmap = null;

	
	public   void saveRecipeImage(Bitmap bitmap, String img_name) {
		FileOutputStream out = null;
		try {
			String pathExternal = Environment.getExternalStorageDirectory().toString();
			String pathRecipesFavorites = pathExternal + "/Nyam/NyamRecipesFavorites";
			Log.d(TAG, "path of storage = " + Environment.getExternalStorageDirectory().toString());
			new File(pathRecipesFavorites).mkdirs();
			Log.d(TAG, "file path = " + pathRecipesFavorites);
			File file = new File(pathExternal, "/Nyam/NyamRecipesFavorites/" + img_name.replace('/', '&'));
			out = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
		} catch (IOException e)  {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
			        out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public  void saveStepImage(String img_name) {
		FileOutputStream out = null;
		try {
			String pathExternal = Environment.getExternalStorageDirectory().toString();
			String pathRecipesFavorites = pathExternal + "/Nyam/NyamRecipesFavorites";
			Log.d(TAG, "path of storage = " + Environment.getExternalStorageDirectory().toString());
			new File(pathRecipesFavorites).mkdirs();
			Log.d(TAG, "file path = " + pathRecipesFavorites);
			String tempImg = img_name.replace('/', '&');
			Log.d(TAG, "tempImg = " + tempImg);
			File file = new File(pathExternal, "/Nyam/NyamRecipesFavorites/" + tempImg);
			out = new FileOutputStream(file);
			InputStream in = new java.net.URL(Constants.URL + img_name).openStream();
			Bitmap bitmap = BitmapFactory.decodeStream(new SanInputStream(in));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
		} catch (IOException e)  {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
			        out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean deleteImageFromSD(String imageName) {
		Log.d(TAG, "Deleting image: " + imageName);
		String pathExternal = Environment.getExternalStorageDirectory().toString();
		File file = new File(pathExternal, "/Nyam/NyamRecipesFavorites/" + imageName);
		boolean deleted = file.delete();
		return deleted;
	}
	
	
	
}
