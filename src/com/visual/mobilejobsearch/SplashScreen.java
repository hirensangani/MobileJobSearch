package com.visual.mobilejobsearch;

import com.visual.mobilejobsearch.persistent.Preferences;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class SplashScreen extends Activity {
	 
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1500;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        final Preferences preferences = new Preferences(getApplicationContext());
	    
 
        new Handler().postDelayed(new Runnable() {
 
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
 
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
            	
            	if(preferences.getUser()=="" && preferences.getApiKey()==""){
                Intent i = new Intent(SplashScreen.this, LoginFragment.class);
                startActivity(i);
                
            	}else{
            		Intent intent = new Intent(SplashScreen.this,DrawerActivity.class);
					 startActivity(intent);
            	}
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
    
 
}