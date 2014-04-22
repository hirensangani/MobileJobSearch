package com.visual.mobilejobsearch.database.api;


import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.visual.mobilejobsearch.database.APIErrorListener;
import com.visual.mobilejobsearch.database.GsonGet;
import com.visual.mobilejobsearch.database.GsonPost;
import com.visual.mobilejobsearch.database.calls.GetLogin;
import com.visual.mobilejobsearch.database.objects.Institute;
import com.visual.mobilejobsearch.database.objects.Register;

public class APIAccess {
	// BASIC URLs
	public static final String MAIN_URL = "http://preparo.unter-guten-freunden.de/";
	private static final String API_URL = MAIN_URL + "api/v1/";
	private static final String IMAGE_URL = MAIN_URL + "media/";
	
	// GET URLs
	private static final String GET_LOGIN_URL = API_URL + "token/auth/";
	
	// POST URLs
	private static final String POST_REGISTER_URL = API_URL + "register/";
	private static final String POST_INSTITUTE_URL = API_URL + "institute/";
	
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
	
	public APIAccess(RequestQueue requestQueue, ImageLoader imageLoader) {
		mRequestQueue = requestQueue;
		mImageLoader = imageLoader;
	}
		
	/**
	 * 
	 * @param listener
	 * @param errorListener
	 * @return
	 */
	public GsonGet<GetLogin> newGetLoginRequest(Listener<GetLogin> listener, ErrorListener errorListener) {
		return new GsonGet<GetLogin>(
				GET_LOGIN_URL,
				GetLogin.class,
				listener,
				errorListener);
	}
	
	public GsonPost<Institute> newPostInstitute (String name, String street, String zipcode, String city,
			  Listener<Institute> listener, APIErrorListener errorListener){
		
		Institute institute = new Institute();
		institute.name= name;
		institute.street= street;
		institute.zipcode=zipcode;
		institute.city=city;
		return new GsonPost<Institute>(
				POST_INSTITUTE_URL,
				institute,
				Institute.class,
				listener,
				errorListener
				);
	}

	
	/**
	 * 
	 * @param email
	 * @param firstName
	 * @param lastName
	 * @param password
	 * @param listener
	 * @param errorListener
	 * @return
	 */
	public GsonPost<Register> newPostRegisterRequest(String email, String firstName, String lastName, String password, String username,
			Listener<Register> listener, APIErrorListener errorListener) {
		
		Register content = new Register();
		content.email = email;
		content.first_name = firstName;
		content.last_name = lastName;
		content.password = password;
		content.username = username;
		
		return new GsonPost<Register>(
				POST_REGISTER_URL,
				content,
				Register.class,
				listener,
				errorListener);
	}
	
	
	public String toImageURL(String url) {
		return IMAGE_URL + url;
	}
	
	public RequestQueue getRequestQueue() {
		return mRequestQueue;
	}
	
	public ImageLoader getImageLoader() {
		return mImageLoader;
	}
}
