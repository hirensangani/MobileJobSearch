package com.visual.mobilejobsearch;




import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
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

public class GeneralDetails extends Activity{
	
	EditText nameEditText, passEditText, emailEditText, vorEditText,userEditText, passAgainEditText;
	Button signUpButton;
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
		passAgainEditText=(EditText) findViewById(R.id.signUpPasswordAgain);
		
		signUpButton = (Button)findViewById(R.id.signUpButton);
		signUpButton.setOnClickListener(onClickSignUp());
		
	}
	
	


	private OnClickListener onClickSignUp() {
		// TODO Auto-generated method stub
		return new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
//				
//				Preferences preferences = new Preferences(getApplicationContext());
//				preferences.putFirstName(vorEditText.getText().toString());
//				preferences.putLastName(nameEditText.getText().toString());
				
				if(passEditText.getText().toString().matches(passAgainEditText.getText().toString())){
				
				api=APIAccessFactory.basicAuthInstance(userEditText.getText().toString(), passEditText.getText().toString(), getApplicationContext());
				api.getRequestQueue().add(api.newPostRegisterRequest(
						emailEditText.getText().toString(), 
						vorEditText.getText().toString(), 
						nameEditText.getText().toString(), 
						passEditText.getText().toString(),
						userEditText.getText().toString(),
						new Listener<Register>() {
							@Override
							public void onResponse(Register response) {
								// TODO Auto-generated method stub
								Toast.makeText(getApplicationContext(), R.string.doneSignUp, Toast.LENGTH_SHORT).show();
								finish();
							}
						}, 
						new APIErrorListener() {
							
							@Override
							public void onErrorResponse(int statusCode, PostError error) {
								Toast toast = Toast.makeText(getApplicationContext(),R.string.errorSignUp, Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
							}
						}));
				}
				
				else{
					Toast.makeText(getApplicationContext(), R.string.errorSignUpPassword, Toast.LENGTH_SHORT).show();
				}
			}
		};
   }
	
}