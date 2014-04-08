package com.visual.mobilejobsearch;


import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	
	Button registerTextView;
	static int i=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginpage);
		
		registerTextView= (Button)findViewById(R.id.registerAccount);
        registerTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Please fill up the details",Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(MainActivity.this, SignUpFragment.class);
			 startActivity(intent);
			 
			}
		});
		
		
		
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
