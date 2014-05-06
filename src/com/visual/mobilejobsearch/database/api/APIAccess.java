package com.visual.mobilejobsearch.database.api;


import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.visual.mobilejobsearch.database.APIErrorListener;
import com.visual.mobilejobsearch.database.GsonDelete;
import com.visual.mobilejobsearch.database.GsonGet;
import com.visual.mobilejobsearch.database.GsonPost;
import com.visual.mobilejobsearch.database.calls.GetCompetence;
import com.visual.mobilejobsearch.database.calls.GetCompetenceType;
import com.visual.mobilejobsearch.database.calls.GetDegree;
import com.visual.mobilejobsearch.database.calls.GetInstitute;
import com.visual.mobilejobsearch.database.calls.GetLogin;
import com.visual.mobilejobsearch.database.calls.GetQualification;
import com.visual.mobilejobsearch.database.objects.Competence;
import com.visual.mobilejobsearch.database.objects.CompetenceType;
import com.visual.mobilejobsearch.database.objects.Degree;
import com.visual.mobilejobsearch.database.objects.Institute;
import com.visual.mobilejobsearch.database.objects.Qualification;
import com.visual.mobilejobsearch.database.objects.Register;

public class APIAccess {
	// BASIC URLs
	public static final String MAIN_URL = "http://preparo.unter-guten-freunden.de/";
	private static final String API_URL = MAIN_URL + "api/v1/";
	private static final String IMAGE_URL = MAIN_URL + "media/";
	private static final String SLASH = "/";
	
	// GET URLs
	private static final String GET_LOGIN_URL = API_URL + "token/auth/";
	private static final String GET_INSTITUE_URL = API_URL + "institute/";
	private static final String GET_DEGREE_URL = API_URL + "degree/";
	private static final String GET_COMPETENCETYPE_URL = API_URL + "competencetype/";
	private static final String GET_COMPETENCE_URL = API_URL + "competence/";
	private static final String GET_QUALIFICATION_URL = API_URL + "qualification/";
	
	// POST URLs
	private static final String POST_REGISTER_URL = API_URL + "register/";
	private static final String POST_INSTITUTE_URL = API_URL + "institute/";
	private static final String POST_DEGREE_URL = API_URL + "degree/";
	private static final String POST_COMPETENCETYPE_URL = API_URL + "competencetype/";
	private static final String POST_COMPETENCE_URL = API_URL + "competence/";
	private static final String POST_QUALIFICATION_URL = API_URL + "qualification/";
	
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
	
	public APIAccess(RequestQueue requestQueue, ImageLoader imageLoader) {
		mRequestQueue = requestQueue;
		mImageLoader = imageLoader;
	}
	
	
	//**********************//********************//*******************//*********************//******************//*****************//*********************//
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
	
	public GsonGet<GetInstitute> newGetInstitute(Listener<GetInstitute> listener, ErrorListener errorListener){
		return new GsonGet<GetInstitute>(
				GET_INSTITUE_URL,
				GetInstitute.class,
				listener,
				errorListener
				);
	}
	
	public GsonGet<GetDegree> newGetDegree(Listener<GetDegree> listener, ErrorListener errorListener){
		return new GsonGet<GetDegree>(
				GET_DEGREE_URL + "?limit=100",
				GetDegree.class,
				listener,
				errorListener
				);
	}
	
	public GsonGet <GetQualification> newGetQualification(Listener<GetQualification> listener, ErrorListener errorListener){
		return new GsonGet<GetQualification>(
				GET_QUALIFICATION_URL +"?limit=100",
				GetQualification.class,
				listener,
				errorListener
				);
	}
	
	public GsonGet<GetCompetenceType> newGetCompetenceType(Listener<GetCompetenceType> listener, ErrorListener errorListener){
		return new GsonGet<GetCompetenceType>(
				GET_COMPETENCETYPE_URL,
				GetCompetenceType.class,
				listener,
				errorListener);
	}
	
	public GsonGet<GetCompetence> newGetCompetence(Listener<GetCompetence> listener, ErrorListener errorListener){
		return new GsonGet<GetCompetence>(
				GET_COMPETENCE_URL,
				GetCompetence.class,
				listener,
				errorListener);
	}
	
	
	//**********************//********************//*******************//*********************//******************//*****************//*********************//
	
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
	
	
	public GsonPost<Qualification> newPostQualification (String idDegree, String idInstitute, String begin, String end,
			 Listener<Qualification> listener, APIErrorListener errorListener){
		Qualification qualification = new Qualification();
		qualification.degree = new Degree();
		qualification.institute = new Institute();
		qualification.degree.id = idDegree;
		qualification.institute.id = idInstitute;
		qualification.begin_of_education = begin;
		qualification.end_of_education = end;
		
		return new GsonPost<Qualification>(
				POST_QUALIFICATION_URL,
				qualification,
				Qualification.class,
				listener,
				errorListener);
			}
	
	public GsonPost<Degree> newPostDegree (String name, Listener<Degree> listener, APIErrorListener errorListener){
		
		Degree degree = new Degree();
		degree.name = name;
		return new GsonPost<Degree>(
				POST_DEGREE_URL, 
				degree,
				Degree.class,
				listener,
				errorListener);
	}
	
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
	
	public GsonPost<CompetenceType> newPostCompetenceType(String name,Listener<CompetenceType> listener, APIErrorListener errorListener){
		
		CompetenceType competenceType = new CompetenceType();
		competenceType.name= name;
		
		return new GsonPost<CompetenceType>(
				POST_COMPETENCETYPE_URL,
				competenceType,
				CompetenceType.class,
				listener,
				errorListener);
		
	}
	
	public GsonPost<Competence> newPostCompetence(String idCompetenceType, float rating, Listener<Competence> listener, APIErrorListener errorListener){
		
		Competence competence = new Competence();
		competence.type = new CompetenceType();
		competence.type.id = idCompetenceType;
		competence.rating = rating;
		Log.e("GsonPost", idCompetenceType);
		
		return new GsonPost<Competence>(
				POST_COMPETENCE_URL,
				competence,
				Competence.class,
				listener,
				errorListener);
	}
	

	//public GsonPost <PostQualification> newPostQualification ()
	
	//**********************//********************//*******************//*********************//******************//*****************//*********************//
	public GsonDelete<Qualification> newDeleteQualification(int id, Listener<Qualification> listener, ErrorListener errorListener){
		return new GsonDelete<Qualification>(
				 GET_QUALIFICATION_URL+ String.valueOf(id) + SLASH,
				 Qualification.class,
				 listener,
				 errorListener);
	}
	
	public GsonDelete<Competence> newDeleteCompetence(String id, Listener<Competence> listener, ErrorListener errorListener){
		return new GsonDelete<Competence>(
				GET_COMPETENCE_URL + id + SLASH, 
				Competence.class,
				listener,
				errorListener);
	}
	
	//**********************//********************//*******************//*********************//******************//*****************//*********************//
	
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
