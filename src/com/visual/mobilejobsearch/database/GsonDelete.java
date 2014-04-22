package com.visual.mobilejobsearch.database;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

public class GsonDelete<T> extends Request<T> {
	private Listener<T> listener;
	
	public GsonDelete(String url, Class<T> clazz, Listener<T> listener, ErrorListener errorListener) {
		super(Method.DELETE, url, errorListener);
	
		this.listener = listener;
	}
	
	@Override
	protected void deliverResponse(T response) {
		listener.onResponse(response);
	}
	
	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		return Response.success(null, HttpHeaderParser.parseCacheHeaders(response));
	}
}


