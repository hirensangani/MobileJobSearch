package com.visual.mobilejobsearch.database;

import java.io.UnsupportedEncodingException;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class GsonPost<T> extends Request<T> {
	private final String LOG = this.getClass().getName();
	
	private Gson gson;
	private T content;
	private Class<T> responseClazz;
	private Listener<T> listener;
	private APIErrorListener errorListener;
	
	public GsonPost(String url, T content, Class<T> responseClazz, Listener<T> listener, APIErrorListener errorListener) {
		super(Method.POST, url, null);
		
		this.content = content;
		this.responseClazz = responseClazz;
		this.listener = listener;
		this.errorListener = errorListener;
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		gson = gsonBuilder.create();
	}
	
	@Override
	public String getBodyContentType() {
		return "application/json; charset=" + getParamsEncoding();
	}
	
	@Override
	public byte[] getBody() throws AuthFailureError {
		try {
			String json = gson.toJson(content);
			return json.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			Log.e(LOG, "encoding json object failed", e);
			return null;
		}
	}

	@Override
	protected void deliverResponse(T response) {
		listener.onResponse(response);
	}
	
	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(response.data, "UTF-8");
			return Response.success(gson.fromJson(json, responseClazz), HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}
	
	@Override
	public void deliverError(VolleyError volleyError) {
		// extract json error description from error response
		PostError postError = null;
		int statusCode = -1;
		try {
			// get error description
			if (volleyError.networkResponse != null) {
				statusCode = volleyError.networkResponse.statusCode;
				
				byte[] data = volleyError.networkResponse.data;
				postError = gson.fromJson(new String(data, "UTF-8"), PostError.class);
			}
		} catch (UnsupportedEncodingException e) {
			Log.e(LOG, "decoding json object failed", e);
		} catch (JsonSyntaxException e) {
			Log.e(LOG, "decoding json object failed", e);		
		}
		
		// call error listener
		errorListener.onErrorResponse(statusCode, postError);
	}
}
