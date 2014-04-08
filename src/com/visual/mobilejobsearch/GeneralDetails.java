package com.visual.mobilejobsearch;




import android.app.Activity;
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
	
	EditText nameEditText, passEditText, emailEditText;
	Button signUpButton;
	String UserName, EmailAddress, Password;
	private static SubmitDataToTheServer doPost;
	private static Activity activity;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		final View view = inflater.inflate(
		R.layout.signuppage, container, false);
		activity= getActivity();
		
		doPost = new SubmitDataToTheServer(activity);
		nameEditText=(EditText)view.findViewById(R.id.signUpName);
		emailEditText=(EditText)view.findViewById(R.id.signUpEmail);
		passEditText=(EditText)view.findViewById(R.id.signUpPassword);
		
		signUpButton = (Button) view.findViewById(R.id.signUpButton);
		signUpButton.setOnClickListener(onClickSignUp(view));
		
		
		
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
				Log.e("name", UserName);
				doPost.setNewPost(UserName,EmailAddress, Password);
				Toast.makeText(getActivity(), "Registering the account",Toast.LENGTH_SHORT).show();
				
			}
		};
	

}
}