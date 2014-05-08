package com.visual.mobilejobsearch.persistent;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
	private static final String PREF = "MOBILE_JOB_SEARCH";
	private static final String PREF_API_KEY = "PREF_API_KEY";
	private static final String PREF_USER = "PREF_USER";
	private static final String PREF_USER_DEGREE = "PREF_USER_DEGREE";
	private static final String PREF_USER_DEGREE_INSTITUTE = "PREF_USER_DEGREE_INSTITUTE";
	private static final String PREF_USER_FIRSTNAME = "PREF_USER_FIRSTNAME";
	private static final String PREF_USER_LASTNAME = "PREF_USER_LASTNAME";
	
	private SharedPreferences settings;

	public Preferences(Context context) {
		settings = context.getSharedPreferences(PREF, 0);
	}

	//------------------------***************----------------****************----------------********************-------------**********-------------//
	
	public String getApiKey() {
		return settings.getString(PREF_API_KEY, null);
	}
	
	public String getUser() {
		return settings.getString(PREF_USER, null);
	}
	
	public String getDegree() {
		return settings.getString(PREF_USER_DEGREE, null);
	}
	
	public String getInstitute() {
		return settings.getString(PREF_USER_DEGREE_INSTITUTE, null);
	}
	
//	public String getFirstName() {
//		return settings.getString(PREF_USER_FIRSTNAME, null);
//	}
//	
//	public String getLastName() {
//		return settings.getString(PREF_USER_LASTNAME, null);
//	}
	
	//------------------------***************----------------****************----------------********************-------------**********-------------//
	public void putApiKey(String apiKey) {
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(PREF_API_KEY, apiKey);
		editor.commit();
	}
	
	public void putUser(String apiKey) {
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(PREF_USER, apiKey);
		editor.commit();
	}
	
	public void putDegree(String nameDegree, String von, String bis){
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(PREF_USER_DEGREE, nameDegree);
		editor.putString(PREF_USER_DEGREE, von);
		editor.putString(PREF_USER_DEGREE, bis);
		editor.commit();
	}
	
	public void putInstitute(String nameInstitute, String address, String zipcode, String city){
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(PREF_USER_DEGREE_INSTITUTE, nameInstitute);
		editor.putString(PREF_USER_DEGREE_INSTITUTE, address);
		editor.putString(PREF_USER_DEGREE_INSTITUTE, zipcode);
		editor.putString(PREF_USER_DEGREE_INSTITUTE, city);
		editor.commit();
	}
	
//	public void putFirstName(String firstname){
//		SharedPreferences.Editor editor = settings.edit();
//		editor.putString(PREF_USER_FIRSTNAME, firstname);
//		editor.commit();
//	}
//	
//	public void putLastName(String lastname){
//		SharedPreferences.Editor editor = settings.edit();
//		editor.putString(PREF_USER_LASTNAME, lastname);
//		editor.commit();
//	}
	
	//------------------------***************----------------****************----------------********************-------------**********-------------//
}
