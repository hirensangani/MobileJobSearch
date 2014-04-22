package com.visual.mobilejobsearch.database.api.httpstack;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.HurlStack;

public class APIKeyStack extends HurlStack {
	private String mUser, mAPIKey;
	
	public APIKeyStack(String user, String apiKey) {
		mUser = user;
		mAPIKey = apiKey;
	}
	
	@Override
	public HttpResponse performRequest(Request<?> request, Map<String, String> additionalHeaders)
			throws IOException, AuthFailureError {
		additionalHeaders.put(
				"authorization", 
				"apikey " + mUser + ":" + mAPIKey);
		
		return super.performRequest(request, additionalHeaders);
	}
}
