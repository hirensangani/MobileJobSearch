package com.visual.mobilejobsearch.database.api;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.ImageLoader;
import com.visual.mobilejobsearch.database.api.httpstack.APIKeyStack;
import com.visual.mobilejobsearch.database.api.httpstack.BasicAuthStack;


public class APIAccessFactory {
	// TODO check cache dir location
	private static final int CACHE_SIZE = 500;
	private static final String DEFAULT_CACHE_DIR = "volley";
	
	// Singleton pattern
	private static Map<String, APIAccess> mApiKeyInstances;
	private static Map<String, APIAccess> mBasicAuthInstances;
	
	private APIAccessFactory() {
		// hidden constructor
	}
	
	
	public synchronized static APIAccess apiKeyInstance(String user, String apiKey, Context context) {
		if (mApiKeyInstances == null) {
			mApiKeyInstances = new HashMap<String, APIAccess>();
		}
		APIAccess access = mApiKeyInstances.get(user);
		if (access != null) {
			return access;
		}
		
		HttpStack stack = new APIKeyStack(user, apiKey);
		RequestQueue queue = createRequestQueue(stack, context);
		ImageLoader imageLoader = createImageLoader(queue);
		
		return new APIAccess(queue, imageLoader);
	}
	
	public synchronized static APIAccess basicAuthInstance(String user, String password, Context context) {
		if (mBasicAuthInstances == null) {
			mBasicAuthInstances = new HashMap<String, APIAccess>();
		}
		
		APIAccess access = mBasicAuthInstances.get(user);
		if (access != null) {
			return access;
		}
		
		HttpStack stack = new BasicAuthStack(user, password);
		RequestQueue queue = createRequestQueue(stack, context);
		ImageLoader imageLoader = createImageLoader(queue);
		
		return new APIAccess(queue, imageLoader);
	}
	
	private static RequestQueue createRequestQueue(HttpStack stack, Context context) {
		File cacheDir = new File(context.getCacheDir(), DEFAULT_CACHE_DIR);
		Network network = new BasicNetwork(stack);

		RequestQueue queue = new RequestQueue(new DiskBasedCache(cacheDir), network) {
			@SuppressWarnings("rawtypes")
			@Override
			public Request add(Request request) {
				int method = request.getMethod();
				String methodString;
				switch (method) {
				case Method.DEPRECATED_GET_OR_POST:
					methodString = "DEPRECATED_GET_OR_POST";
					break;
				case Method.GET:
					methodString = "GET";
					break;
				case Method.POST:
					methodString = "POST";
					break;
				case Method.PUT:
					methodString = "PUT";
					break;
				case Method.DELETE:
					methodString = "DELETE";
					break;
				case Method.HEAD:
					methodString = "HEAD";
					break;
				case Method.OPTIONS:
					methodString = "OPTIONS";
					break;
				case Method.TRACE:
					methodString = "TRACE";
					break;
				case Method.PATCH:
					methodString = "PATCH";
					break;
				default:
					methodString = "UNKNOWN";
					break;
				}
				
				String bodyString = null;
				try {
					byte[] body = request.getBody();
					if (body != null) {
						bodyString = new String(request.getBody());
					}
				} catch (AuthFailureError e1) {
				}
				
				Log.d("sg.network", "[" + methodString + "] " + request.getUrl() + " - " + bodyString);
				
				
				return super.add(request);
			}
		};
		queue.start();

		return queue;
	}
	
	private static ImageLoader createImageLoader(RequestQueue queue) {
		return new ImageLoader(queue, new ImageLoader.ImageCache() {
			private LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(CACHE_SIZE);

			@Override
			public void putBitmap(String url, Bitmap bitmap) {
				cache.put(url, bitmap);
			}

			@Override
			public Bitmap getBitmap(String url) {
				return cache.get(url);
			}
		});
	}
}
