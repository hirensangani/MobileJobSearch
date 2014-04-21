package com.visual.mobilejobsearch;




import serverStuff.Person;
import serverStuff.registerUser;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GeneralDetails extends Fragment{
	
	EditText nameEditText, passEditText, emailEditText, vorEditText,userEditText;
	Button signUpButton;
	String UserName, EmailAddress, Password, vorName, Name;
//	private static SubmitDataToTheServer doPost;
//	private static Activity activity;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		final View view = inflater.inflate(
		R.layout.signuppage, container, false);
		//activity= getActivity();
		
		//doPost = new SubmitDataToTheServer(activity);
		nameEditText=(EditText)view.findViewById(R.id.signUpName);
		emailEditText=(EditText)view.findViewById(R.id.signUpEmail);
		passEditText=(EditText)view.findViewById(R.id.signUpPassword);
		vorEditText=(EditText)view.findViewById(R.id.signUpFirstName);
		userEditText=(EditText)view.findViewById(R.id.signUpUsername);
		
		signUpButton = (Button) view.findViewById(R.id.signUpButton);
		signUpButton.setOnClickListener(onClickSignUp(view));
		
		//get the response of JSON
		
		
		return view;
		
	}
	


	private OnClickListener onClickSignUp(View view) {
		// TODO Auto-generated method stub
		return new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				UserName=nameEditText.getText().toString();
				EmailAddress=emailEditText.getText().toString();
				Password=passEditText.getText().toString();
				Name=nameEditText.getText().toString();
				vorName=vorEditText.getText().toString();
//				doPost.setNewPost(UserName,EmailAddress, Password, Name, vorName);
//				Toast.makeText(getActivity(), "Registering the account",Toast.LENGTH_SHORT).show();
				new HttpAsyncTask().execute("http://preparo.unter-guten-freunden.de/api/v1/register/");
			}
		};
   }
	

	class HttpAsyncTask extends AsyncTask<String, Void, String> {
		
		Person person;
	    @Override
	    protected String doInBackground(String... urls) {

	        person = new Person();
	        person.setName(nameEditText.getText().toString());
	        person.setVorname(vorEditText.getText().toString());
	        person.setUsername(userEditText.getText().toString());
	        person.setEmail(nameEditText.getText().toString());
	        person.setPassword(passEditText.getText().toString());

	        return registerUser.POST(urls[0],person);
	    }
	    // onPostExecute displays the results of the AsyncTask.
	    @Override
	    protected void onPostExecute(String result) {
	        Toast.makeText(getActivity(), "Data Sent!", Toast.LENGTH_LONG).show();
	   }
	}
	
}