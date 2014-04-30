package com.visual.mobilejobsearch.database.methods;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Method {
	Context context;
	Activity activity;

	public void logVerbose(String tag, String value){
		Log.v(tag,value);
	}
	
	public void logError(String tag, String value){
		Log.e(tag,value);
	}
	
	public void logDebug(String tag, String value){
		Log.d(tag,value);
	}
	
	public void toastContext(String text){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
	
	public void toastActivity(String text){
		Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
	}
	
}
