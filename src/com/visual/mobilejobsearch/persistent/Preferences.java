package com.visual.mobilejobsearch.persistent;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
	private static final String PREF = "MOBILE_JOB_SEARCH";
	private static final String PREF_API_KEY = "PREF_API_KEY";
	private static final String PREF_USER = "PREF_USER";

	private SharedPreferences settings;

	public Preferences(Context context) {
		settings = context.getSharedPreferences(PREF, 0);
	}

	public String getApiKey() {
		return settings.getString(PREF_API_KEY, null);
	}

	public void putApiKey(String apiKey) {
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(PREF_API_KEY, apiKey);
		editor.commit();
	}
	
	public String getUser() {
		return settings.getString(PREF_USER, null);
	}
	
	public void putUser(String apiKey) {
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(PREF_USER, apiKey);
		editor.commit();
	}
}
