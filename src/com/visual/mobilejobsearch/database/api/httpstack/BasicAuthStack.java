package com.visual.mobilejobsearch.database.api.httpstack;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;

import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.HurlStack;

public class BasicAuthStack extends HurlStack {
	private String mUser, mPassword;
	
	public BasicAuthStack(String user, String password) {
		mUser = user;
		mPassword = password;
	}
	
	@Override
	public HttpResponse performRequest(Request<?> request, Map<String, String> additionalHeaders)
			throws IOException, AuthFailureError {
		String credentials = mUser + ":" + mPassword;
		additionalHeaders.put(
				"authorization", 
				"basic " + Base64.encodeToString(credentials.getBytes("UTF-8"), Base64.DEFAULT));
		
		return super.performRequest(request, additionalHeaders);
	}
}
