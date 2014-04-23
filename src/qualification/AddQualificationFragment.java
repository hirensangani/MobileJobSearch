package qualification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.visual.mobilejobsearch.OpeningApplicationFragment;
import com.visual.mobilejobsearch.R;
import com.visual.mobilejobsearch.SplashScreen;
import com.visual.mobilejobsearch.database.APIErrorListener;
import com.visual.mobilejobsearch.database.PostError;
import com.visual.mobilejobsearch.database.api.APIAccess;
import com.visual.mobilejobsearch.database.api.APIAccessFactory;
import com.visual.mobilejobsearch.database.objects.Institute;
import com.visual.mobilejobsearch.persistent.Preferences;

public class AddQualificationFragment extends FragmentActivity{

	EditText degree, von, bis, name, street, zipcode, city;
	Button save;
	APIAccess api;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qualification);
		Toast.makeText(getApplicationContext(), "Add the new qualification", Toast.LENGTH_SHORT).show();
		
		degree=(EditText)findViewById(R.id.QDegreeName);
		von=(EditText)findViewById(R.id.QVon);
		bis=(EditText)findViewById(R.id.QBis);
		name=(EditText)findViewById(R.id.EditTextNameOfInstitute);
		street=(EditText)findViewById(R.id.EditTextStreet);
		zipcode=(EditText)findViewById(R.id.EditText_Qua_ZipCode);
		city=(EditText)findViewById(R.id.EditText_Qua_City);
		
		Preferences preferences = new Preferences(getApplicationContext());
		
		api=APIAccessFactory.apiKeyInstance(preferences.getUser(), preferences.getApiKey(), getApplicationContext());
		
		save = (Button)findViewById(R.id.Button_Qua_Save);
		save.setOnClickListener(onClickSave());
		
		
		
	}

	private OnClickListener onClickSave() {
		// TODO Auto-generated method stub
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				api.getRequestQueue().add(api.newPostInstitute(name.getText().toString(),
						street.getText().toString(),
						zipcode.getText().toString(),
						city.getText().toString(),
						new Listener<Institute>() {

							@Override
							public void onResponse(Institute response) {

								Toast.makeText(getBaseContext(), "Qualification has been updated", Toast.LENGTH_SHORT).show();
							}

				},
				 new APIErrorListener() {
					
					@Override
					public void onErrorResponse(int statusCode, PostError error) {
						Toast.makeText(getBaseContext(), "||||||||||||", Toast.LENGTH_SHORT).show();
						
					}
				}));
						
				
			}
		};
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(AddQualificationFragment.this, OpeningApplicationFragment.class);
		 startActivity(intent);
		super.onBackPressed();
	}
}
