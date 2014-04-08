package com.visual.mobilejobsearch;


import java.lang.reflect.Field;
import java.lang.reflect.Method;


import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class SignUpFragment extends FragmentActivity{
	
	
	
	@Override
	protected void onPause() {
		super.onPause();
	}
    Activity act;
	ViewPager mViewPager;
	TabsAdapter mTabsAdapter;
	TextView tabCenter;
	TextView tabText;
	private static ActionBar bar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signuppage);
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.pager);

		setContentView(mViewPager);
		bar = getActionBar();
		bar.setTitle("Vital Data");
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		bar.setDisplayShowTitleEnabled(true);
		bar.setDisplayShowHomeEnabled(true);

//		try {
//			Field actionBarField = bar.getClass().getDeclaredField("mActionBar");
//			actionBarField.setAccessible(true);
//			enableEmbeddedTabs(actionBarField.get(bar));
//		} catch (Exception e) {
//			Log.e("tag", "Error enabling embedded tabs", e);
//		}

		mTabsAdapter = new TabsAdapter(this, mViewPager);

		mTabsAdapter.addTab(bar.newTab().setText("General"), GeneralDetails.class, null);

	 mTabsAdapter.addTab(bar.newTab().setText("Full-profile"), FullDetails.class, null);
		
		//mTabsAdapter.addTab(bar.newTab().setText("SET-VALUES"), SetValue.class, null);
		
	}

	private void enableEmbeddedTabs(Object actionBar) {
		try {
			Method setHasEmbeddedTabsMethod = actionBar.getClass().getDeclaredMethod("setHasEmbeddedTabs", boolean.class);
			setHasEmbeddedTabsMethod.setAccessible(true);
			setHasEmbeddedTabsMethod.invoke(actionBar, true);
		} catch (Exception e) {
			Log.e("Tag", "Error marking actionbar embedded", e);
		}
	}

//		final View view= inflater.inflate(R.layout.signuppage, container, false);
//		submit= new SubmitDataToServer();
//		nameEditText=(EditText)view.findViewById(R.id.signUpName);
//		emailEditText=(EditText)view.findViewById(R.id.signUpEmail);
//		passEditText=(EditText)view.findViewById(R.id.signUpPassword);
//		
//		signUpButton = (Button) view.findViewById(R.id.signUpButton);
//		signUpButton.setOnClickListener(onClickSignUp(view));
//		
		
		
//		return view;
//		
//	}
//	
//
//
//	private OnClickListener onClickSignUp(View view) {
//		// TODO Auto-generated method stub
//		return new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				
//				UserName=nameEditText.getText().toString();
//				EmailAddress=emailEditText.getText().toString();
//				Password=passEditText.getText().toString();
//				
//				submit.setNewPost(UserName,EmailAddress, Password);
//				Toast.makeText(getActivity(), "Registering the account",Toast.LENGTH_SHORT).show();
//				
//			}
//		};
//	}
	

}
