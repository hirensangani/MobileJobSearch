package com.visual.mobilejobsearch;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.visual.mobilejobsearch.database.api.APIAccess;
import com.visual.mobilejobsearch.database.api.APIAccessFactory;
import com.visual.mobilejobsearch.database.calls.GetLogin;
import com.visual.mobilejobsearch.persistent.Preferences;

public class LoginFragment extends Activity {
	
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
			Intent intent = new Intent(LoginFragment.this, GeneralDetails.class);
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
                   
						Preferences preference = new Preferences(getApplicationContext());
						preference.putApiKey(response.key);
						preference.putUser(response.username);
						
						Log.e("apiKey", response.key);
						
						finish();						
						Intent intent = new Intent(LoginFragment.this, DrawerActivity.class);
						 startActivity(intent);
						
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("apiError", "error");
						
					}
					
				}));
				
			}
		});
		
  
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		finish();
		super.onDestroy();
	}

}
