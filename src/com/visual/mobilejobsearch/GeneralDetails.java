package com.visual.mobilejobsearch;




import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.visual.mobilejobsearch.database.APIErrorListener;
import com.visual.mobilejobsearch.database.PostError;
import com.visual.mobilejobsearch.database.api.APIAccess;
import com.visual.mobilejobsearch.database.api.APIAccessFactory;
import com.visual.mobilejobsearch.database.objects.Register;

public class GeneralDetails extends FragmentActivity{
	
	EditText nameEditText, passEditText, emailEditText, vorEditText,userEditText;
	Button signUpButton;
	String UserName, EmailAddress, Password, vorName, Name;
	APIAccess api;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signuppage);
		
		
		//doPost = new SubmitDataToTheServer(activity);
		nameEditText=(EditText)findViewById(R.id.signUpName);
		emailEditText=(EditText)findViewById(R.id.signUpEmail);
		passEditText=(EditText)findViewById(R.id.signUpPassword);
		vorEditText=(EditText)findViewById(R.id.signUpFirstName);
		userEditText=(EditText)findViewById(R.id.signUpUsername);
		
		signUpButton = (Button)findViewById(R.id.signUpButton);
		signUpButton.setOnClickListener(onClickSignUp());
		
		//get the response of JSON
		//api=APIAccessFactory.
		
		
	}
	
	


	private OnClickListener onClickSignUp() {
		// TODO Auto-generated method stub
		return new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				UserName=userEditText.getText().toString();
				EmailAddress=emailEditText.getText().toString();
				Password=passEditText.getText().toString();
				Name=nameEditText.getText().toString();
				vorName=vorEditText.getText().toString();
//				doPost.setNewPost(UserName,EmailAddress, Password, Name, vorName);
//				Toast.makeText(getActivity(), "Registering the account",Toast.LENGTH_SHORT).show();
				//new HttpAsyncTaskPost().execute("http://preparo.unter-guten-freunden.de/api/v1/register/");
				
				api=APIAccessFactory.basicAuthInstance(UserName, Password, getApplicationContext());
				api.getRequestQueue().add(api.newPostRegisterRequest(
						EmailAddress, 
						vorName, 
						Name, 
						Password,
						UserName,
						new Listener<Register>() {
							@Override
							public void onResponse(Register response) {
								// TODO Auto-generated method stub
								Toast.makeText(getApplicationContext(), "Data has been sent", Toast.LENGTH_SHORT).show();
							}
						}, 
						new APIErrorListener() {
							
							@Override
							public void onErrorResponse(int statusCode, PostError error) {
								Toast.makeText(getApplicationContext(), error.error.error, Toast.LENGTH_SHORT).show();
								
								
							}
						}));
			}
		};
   }
	

//	class HttpAsyncTaskPost extends AsyncTask<String, Void, String> {
//		
//		Person person;
//	    @Override
//	    protected String doInBackground(String... urls) {
//
//	        person = new Person();
//	        person.setEmail(emailEditText.getText().toString());
//	        person.setUsername(userEditText.getText().toString());
//	        person.setPassword(passEditText.getText().toString());
//	        person.setVorname(vorEditText.getText().toString());
//	        person.setName(nameEditText.getText().toString());
//	        
//	        return registerUser.POST(urls[0],person);
//	    }
//	    // onPostExecute displays the results of the AsyncTask.
//	    @Override
//	    protected void onPostExecute(String result) {
//	        Toast.makeText(getApplicationContext(), "Data Sent!", Toast.LENGTH_LONG).show();
//	   }
//	}
	
}