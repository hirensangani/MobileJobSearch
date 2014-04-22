package com.visual.mobilejobsearch.database;

import java.io.UnsupportedEncodingException;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class GsonGet<T> extends Request<T> {
	private Gson gson;
	private Class<T> clazz;
	private Listener<T> listener;

	public GsonGet(String url, Class<T> clazz, Listener<T> listener,
			ErrorListener errorListener) {
		super(Method.GET, url, errorListener);
		
		this.clazz = clazz;
		this.listener = listener;
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		gson = gsonBuilder.create();
		
		// TODO find optimum values
		setRetryPolicy(new DefaultRetryPolicy(
				15000, // DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
	}

	@Override
	protected void deliverResponse(T response) {
		listener.onResponse(response);
	}
	
	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(response.data, "UTF-8");
			return Response.success(gson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}
}