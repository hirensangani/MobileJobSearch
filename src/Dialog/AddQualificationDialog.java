package Dialog;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.visual.mobilejobsearch.R;
import com.visual.mobilejobsearch.database.APIErrorListener;
import com.visual.mobilejobsearch.database.PostError;
import com.visual.mobilejobsearch.database.api.APIAccess;
import com.visual.mobilejobsearch.database.api.APIAccessFactory;
import com.visual.mobilejobsearch.database.objects.Degree;
import com.visual.mobilejobsearch.database.objects.Institute;
import com.visual.mobilejobsearch.database.objects.Qualification;
import com.visual.mobilejobsearch.persistent.Preferences;

public class AddQualificationDialog extends DialogFragment {




	EditText degreeName, von, bis, institutName, street, zipcode, city;
	Button save;
	APIAccess api;
	Preferences preferences;
	private OnReturnListener mOnReturnListener;

	//private String idDegree,idInstitute;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_add_qualification, container, false);

		Toast.makeText(getActivity(), "Add the new qualification", Toast.LENGTH_SHORT).show();

		getDialog().setTitle("Add new qualification");

		degreeName=(EditText)view.findViewById(R.id.QDegreeName);
		von=(EditText)view.findViewById(R.id.QVon);
		bis=(EditText)view.findViewById(R.id.QBis);
		institutName=(EditText)view.findViewById(R.id.EditTextNameOfInstitute);
		street=(EditText)view.findViewById(R.id.EditTextStreet);
		zipcode=(EditText)view.findViewById(R.id.EditText_Qua_ZipCode);
		city=(EditText)view.findViewById(R.id.EditText_Qua_City);

		preferences = new Preferences(getActivity());

		api=APIAccessFactory.apiKeyInstance(preferences.getUser(), preferences.getApiKey(), getActivity());

		save = (Button)view.findViewById(R.id.Button_Qua_Save);
		save.setOnClickListener(onClickSave(view));

		return view;
	}

	String idDegree,idInstitute ;
	private OnClickListener onClickSave(View view) {




		// TODO Auto-generated method stub
		return new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if(!degreeName.getText().toString().isEmpty() && !institutName.getText().toString().isEmpty()  && !von.getText().toString().isEmpty() ){
					
					api.getRequestQueue().add(api.newPostDegree(degreeName.getText().toString(),
							new Listener<Degree>() {

						@Override
						public void onResponse(Degree response) { 
							idDegree=response.id;
						}
					}, 
					new APIErrorListener() {

						@Override
						public void onErrorResponse(int statusCode, PostError error) {
							// TODO Auto-generated method stub

						}
					}));


					api.getRequestQueue().add(api.newPostInstitute(institutName.getText().toString(),
							street.getText().toString(),
							zipcode.getText().toString(),
							city.getText().toString(),
							new Listener<Institute>() {

						@Override
						public void onResponse(Institute response) {
							idInstitute=response.id;

						}

					},
					new APIErrorListener() {

						@Override
						public void onErrorResponse(int statusCode, PostError error) {
							Toast.makeText(getActivity(), "Something is wrong..please try again..", Toast.LENGTH_SHORT).show();

						}
					}));

					api.getRequestQueue().add(api.newPostQualification(
							idDegree,
							idInstitute,
							von.getText().toString(),
							bis.getText().toString(),
							new Listener<Qualification>() {

								@Override
								public void onResponse(Qualification response) {
									//updateListView();

								}
							}, new APIErrorListener() {

								@Override
								public void onErrorResponse(int statusCode, PostError error) {
									Log.e("QualificationError","Qualification Errror");

								}
							}));
					
					
				}else{

					Toast toast = Toast.makeText(getActivity(),R.string.toastQualification, Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();					

			}
		  }
		};
	}
	
	@Override
	public void onDismiss(DialogInterface dialog) {
		// TODO Auto-generated method stub
		super.onDismiss(dialog);
	}

	public void updateListView(){
		mOnReturnListener.onReturn(
				degreeName.getText().toString(),
				institutName.getText().toString(),
				von.getText().toString(),
				bis.getText().toString()
				);
		Log.e("UpdateListView", "working");
		dismiss();
	}

	public void setOnReturnListener(OnReturnListener onReturnListener) {
		mOnReturnListener = onReturnListener;
	}

	public interface OnReturnListener {
		public void onReturn(String degreeName, String instituteName, String timeVon, String timeBis);
	}
}
