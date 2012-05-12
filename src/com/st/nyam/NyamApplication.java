package com.st.nyam;


import java.util.List;

import android.app.Application;

import com.st.nyam.factories.ApiFactory;
import com.st.nyam.factories.DataBaseFactory;

public class NyamApplication extends Application {

	private DataBaseFactory db;

	public void onCreate() {
		super.onCreate();
		db = new DataBaseFactory(this);
	}

	public DataBaseFactory getDB() {
		return db;
	}

	/*
	public void synchrRecepyecepies() {
		List<Recepy> recepies = ApiFactory.getRecepies();
		if (recepies != null && recepies.size() != 0) {
			for (Recepy recepy : recepies) {
				db.putRecepy(recepy);
			}
		}
	}
	 */
}