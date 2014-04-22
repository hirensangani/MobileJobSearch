package com.visual.mobilejobsearch;


import java.util.ArrayList;

import serverStuff.Person;
import serverStuff.loginUser;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.visual.mobilejobsearch.database.api.APIAccess;
import com.visual.mobilejobsearch.database.api.APIAccessFactory;
import com.visual.mobilejobsearch.database.calls.GetLogin;

public class MainActivity extends FragmentActivity {
	
	Button registerTextView, loginButton;
	EditText userName, password;
	
	  APIAccess api;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginpage);
	     userName=(EditText)findViewById(R.id.loginUserName);
	     password=(EditText)findViewById(R.id.loginPassword);
	   
		
		registerTextView= (Button)findViewById(R.id.registerAccount);
        registerTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Please fill up the details",Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(MainActivity.this, GeneralDetails.class);
			 startActivity(intent);
			 
			}
		});
		
        loginButton=(Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				api=APIAccessFactory.basicAuthInstance(userName.getText().toString(), password.getText().toString(), getApplicationContext());
				api.getRequestQueue().add(api.newGetLoginRequest(new Listener<GetLogin>() {

					@Override
					public void onResponse(GetLogin response) {
                   
						Log.e("apiKey", response.key);
						
						Intent intent = new Intent(MainActivity.this, OpeningApplicationFragment.class);
						 startActivity(intent);
						
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("apiError", "error");
						
					}
					
				}));
				
				//new HttpAsyncTaskGet().execute("http://preparo.unter-guten-freunden.de/api/v1/token/auth/");
				
			}
		});
		
  
		
	}
	
	class HttpAsyncTaskGet extends AsyncTask<String, Void, String> {
		
		Person person;

		@Override
		protected String doInBackground(String... urls) {

		        person = new Person();
		        person.setLoginUserName(userName.getText().toString());
		        person.setLoginPassword(password.getText().toString());
			
			return loginUser.GET(urls[0],person);
		}

	}
	
	
	
	
//	@Override
//	public void onBackPressed() {
//		// TODO Auto-generated method stub
//		i++;
//		Toast.makeText(getApplicationContext(), "od",Toast.LENGTH_SHORT).show();
//		if(i==2){
//		finish();
//		}
//		super.onBackPressed();
//	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
