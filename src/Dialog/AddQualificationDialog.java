package Dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.visual.mobilejobsearch.R;
import com.visual.mobilejobsearch.database.APIErrorListener;
import com.visual.mobilejobsearch.database.PostError;
import com.visual.mobilejobsearch.database.api.APIAccess;
import com.visual.mobilejobsearch.database.api.APIAccessFactory;
import com.visual.mobilejobsearch.database.objects.Degree;
import com.visual.mobilejobsearch.database.objects.Institute;
import com.visual.mobilejobsearch.persistent.Preferences;

public class AddQualificationDialog extends DialogFragment implements OnEditorActionListener{
	

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	
	EditText degreeName, von, bis, name, street, zipcode, city;
	Button save;
	APIAccess api;
	Preferences preferences;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_add_qualification, container, false);
		
        Toast.makeText(getActivity(), "Add the new qualification", Toast.LENGTH_SHORT).show();
        
        getDialog().setTitle("Add new qualification");
		
		degreeName=(EditText)view.findViewById(R.id.QDegreeName);
		von=(EditText)view.findViewById(R.id.QVon);
		bis=(EditText)view.findViewById(R.id.QBis);
		name=(EditText)view.findViewById(R.id.EditTextNameOfInstitute);
		street=(EditText)view.findViewById(R.id.EditTextStreet);
		zipcode=(EditText)view.findViewById(R.id.EditText_Qua_ZipCode);
		city=(EditText)view.findViewById(R.id.EditText_Qua_City);
		
		preferences = new Preferences(getActivity());
		
		api=APIAccessFactory.apiKeyInstance(preferences.getUser(), preferences.getApiKey(), getActivity());
		
		save = (Button)view.findViewById(R.id.Button_Qua_Save);
		save.setOnClickListener(onClickSave(view));
		
		return view;
	}
	
	

	private OnClickListener onClickSave(View view) {
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
                                
								//preferences.putInstitute(response.name, response.street, response.zipcode, response.city);
							//	Toast.makeText(getActivity(), response.name, Toast.LENGTH_SHORT).show();
								
							}

				},
				 new APIErrorListener() {
					
					@Override
					public void onErrorResponse(int statusCode, PostError error) {
						Toast.makeText(getActivity(), "||||||||||||", Toast.LENGTH_SHORT).show();
						
					}
				}));
				
				api.getRequestQueue().add(api.newPostDegree(degreeName.getText().toString(),
						new Listener<Degree>() {

							@Override
							public void onResponse(Degree response) { 
								
							}
						}, 
						new APIErrorListener() {
							
							@Override
							public void onErrorResponse(int statusCode, PostError error) {
								// TODO Auto-generated method stub
								
							}
						}));
						
				
			}
		};
	}

}
