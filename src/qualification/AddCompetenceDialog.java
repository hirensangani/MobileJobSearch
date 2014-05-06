package qualification;

import com.android.volley.Response.Listener;
import com.visual.mobilejobsearch.R;
import com.visual.mobilejobsearch.database.APIErrorListener;
import com.visual.mobilejobsearch.database.PostError;
import com.visual.mobilejobsearch.database.api.APIAccess;
import com.visual.mobilejobsearch.database.api.APIAccessFactory;
import com.visual.mobilejobsearch.database.objects.Competence;
import com.visual.mobilejobsearch.database.objects.CompetenceType;
import com.visual.mobilejobsearch.persistent.Preferences;

import Dialog.AddQualificationDialog.OnReturnListener;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class AddCompetenceDialog extends DialogFragment {
	RatingBar ratingBar;
	APIAccess api;
	Preferences preferences;
	EditText nameEditText;
	
	Button okButton,cancelButton;
	
	private OnReturnListenerCompetence mOnReturnListenerCompetence;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.dialog_fragment_competence, container, false);
		
		getDialog().setTitle("Add competence");
		nameEditText = (EditText) view.findViewById(R.id.nameCompetence);
		
		 ratingBar = (RatingBar)view.findViewById(R.id.ratingBarCompetence);
		 
		 preferences = new Preferences(getActivity());
			
			api=APIAccessFactory.apiKeyInstance(preferences.getUser(), preferences.getApiKey(), getActivity());
			
		 okButton = (Button) view.findViewById(R.id.okButtonCompetence);
		okButton.setOnClickListener(onClickOk(view));
		
		 cancelButton = (Button) view.findViewById(R.id.cancelButtonCompetence);
		cancelButton.setOnClickListener(onClickCancel(view));
		
       
		return view;
	}
	

	private OnClickListener onClickOk(View view) {
		
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				api.getRequestQueue().add(api.newPostCompetenceType(nameEditText.getText().toString(),
						new Listener<CompetenceType>() {

							@Override
							public void onResponse(CompetenceType response) {
							postCompetence(response.id);
							updateListViewCompetence();
							}
						},
						new APIErrorListener() {
							
							@Override
							public void onErrorResponse(int statusCode, PostError error) {
								Log.e("CompetenceType", "Error");
								
							}
						}));
				
			}
		};
	}
	
	private void postCompetence(String competenceId){
		api.getRequestQueue().add(api.newPostCompetence(competenceId, ratingBar.getRating(),
				new Listener<Competence>() {

					@Override
					public void onResponse(Competence response) {
						
						//updateListViewCompetence();
						
					}
				},
				new APIErrorListener() {
					
					@Override
					public void onErrorResponse(int statusCode, PostError error) {
						Log.e("onError", "Error");
					}
				}));
	}
	
	
	@Override
	public void onDismiss(DialogInterface dialog) {
		// TODO Auto-generated method stub
		super.onDismiss(dialog);
	}
	
	private OnClickListener onClickCancel(View view) {
		// TODO Auto-generated method stub
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
			}
		};
	}
	
	
	public void updateListViewCompetence(){
		mOnReturnListenerCompetence.onReturn(
				                nameEditText.getText().toString(),
				                ratingBar.getRating()
				                   );
	}

	public void setOnReturnListener(OnReturnListenerCompetence onReturnListenerCompetence) {
		mOnReturnListenerCompetence = onReturnListenerCompetence;
	}

	public interface OnReturnListenerCompetence {
		public void onReturn(String competenceType, float ratingValue);
	}

}
