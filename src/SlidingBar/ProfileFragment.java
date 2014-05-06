package SlidingBar;

import qualification.ExpandableProfileListAdapter;
import qualification.ExpandableProfileListAdapter.HeaderItem;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.visual.mobilejobsearch.R;
import com.visual.mobilejobsearch.database.api.APIAccess;
import com.visual.mobilejobsearch.database.api.APIAccessFactory;
import com.visual.mobilejobsearch.database.calls.GetCompetence;
import com.visual.mobilejobsearch.database.calls.GetCompetenceType;
import com.visual.mobilejobsearch.database.calls.GetDegree;
import com.visual.mobilejobsearch.database.calls.GetInstitute;
import com.visual.mobilejobsearch.database.calls.GetQualification;
import com.visual.mobilejobsearch.database.objects.APIObject;
import com.visual.mobilejobsearch.database.objects.Competence;
import com.visual.mobilejobsearch.database.objects.Qualification;
import com.visual.mobilejobsearch.persistent.Preferences;

public class ProfileFragment extends Fragment {
	
	public ProfileFragment(){}
	
	final static String QUALIFICATION = "Qualification";
	final static String EXPERIENCE = "Experience";
	final static String LANGUAGE = "Language";
	final static String SKILLS = "Skills & Expertise";
	final static String INTERESTS = "Interests";
	
	APIAccess api;
	
	ImageButton btnAddQualification;
	
	TextView profileName;
	
	ExpandableProfileListAdapter listAdapter;
    ExpandableListView expListView;
    private static String dName, Iname, Von, Bis, Address;

    Preferences preferences;
//    @Override
//    public void onPause() {
//    	HomeFragment fragment = new HomeFragment();
//    	FragmentManager fragmentManager = getFragmentManager();
//		fragmentManager.beginTransaction()
//				.replace(R.id.frame_container, fragment).addToBackStack(null).commit();
//		// Toast.makeText(getActivity(), "onPause", Toast.LENGTH_SHORT).show();
//    	super.onPause();
//    }
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.full_profile_details, container, false);
        //ListView
        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);
        
        listAdapter = new ExpandableProfileListAdapter(getActivity());
 
        profileName = (TextView) rootView.findViewById(R.id.profileName);
        
        
        preferences = new Preferences(getActivity());
		
		api=APIAccessFactory.apiKeyInstance(preferences.getUser(), preferences.getApiKey(), getActivity());
		
		//listAdapter.clear();
		api.getRequestQueue().add(api.newGetInstitute(new Listener<GetInstitute>() {

			@Override
			public void onResponse(GetInstitute response) {
//				for(Institute institute : response.objects){
//					//listAdapter.addChild(HeaderItem.DEGREE,institute);
//				}
				
			}
		}, 
		
			new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					// TODO Auto-generated method stub
					
				}
			}	));
		
		api.getRequestQueue().add(api.newGetDegree(new Listener<GetDegree>() {

			@Override
			public void onResponse(GetDegree response) {
				
//				for(Degree degree : response.objects){
//				//	listAdapter.addChild(HeaderItem.QUALIFICATION,degree);
//				}
				

			}
		}, 
		new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		}));
		
		api.getRequestQueue().add(api.newGetQualification(new Listener<GetQualification>() {

			@Override
			public void onResponse(GetQualification response) {
				
				for(Qualification qualification:response.objects){
					//Log.e("Error",String.valueOf(qualification.id));
					listAdapter.addChild(HeaderItem.QUALIFICATION, qualification);
				}
				
			}
			
		},new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		}));
		
		//get request for Competence
		
		api.getRequestQueue().add(api.newGetCompetenceType(new Listener<GetCompetenceType>() {

			@Override
			public void onResponse(GetCompetenceType response) {
				
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				
			}
		}));
		
		api.getRequestQueue().add(api.newGetCompetence(new Listener<GetCompetence>() {

			@Override
			public void onResponse(GetCompetence response) {
				
				for (Competence competence : response.objects){
					listAdapter.addChild(HeaderItem.COMPETENCE, competence);
				}
				
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		}));
		
		 expListView.setAdapter(listAdapter);
		 
		 expListView.setOnChildClickListener(new OnChildClickListener() {
			 
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					final int groupPosition,  final int childPosition, long id) {
				
				
				HeaderItem group = (HeaderItem) listAdapter.getGroup(groupPosition);
				switch(group){
				
				case QUALIFICATION:
					
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							getActivity());
			 
						// set title
						alertDialogBuilder.setTitle(R.string.titleQualification);
			 
						// set dialog message
						alertDialogBuilder
							.setMessage(R.string.sure)
							.setCancelable(false)
							.setPositiveButton(R.string.yes,
									new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									//Qualification qualification = new Qualification();
									
									Qualification qObject = (Qualification) listAdapter.getChild(groupPosition, childPosition);
									
									//delete request
									api.getRequestQueue().add(api.newDeleteQualification(
											qObject.id,
											new Listener<Qualification>() {

												@Override
												public void onResponse(Qualification response) {
													
												}
											}, new ErrorListener() {

												@Override
												public void onErrorResponse(VolleyError error) {
													Log.e("onErrorResponse", "Delete Qualification Error");													
												}
											}))	;								
									//
									
									APIObject object;
								    object=(APIObject)listAdapter.getChild(groupPosition, childPosition);
								    
									listAdapter.deleteChild(HeaderItem.QUALIFICATION,object);
									
								}
							  })
							.setNegativeButton(R.string.no,
									new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									// if this button is clicked, just close
									// the dialog box and do nothing
									dialog.cancel();
								}
							})
							.create().show();
					
					break;
					
				case COMPETENCE:
				
				AlertDialog.Builder alertDialogBuilderCompetence = new AlertDialog.Builder(
						getActivity());
		 
					// set title
					alertDialogBuilderCompetence.setTitle(R.string.titleCompetence);
		 
					// set dialog message
					alertDialogBuilderCompetence
						.setMessage(R.string.sure)
						.setCancelable(false)
						.setPositiveButton(R.string.yes,
								new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								
								Competence cObject = (Competence) listAdapter.getChild(groupPosition, childPosition);
								api.getRequestQueue().add(api.newDeleteCompetence(cObject.id,
										new Listener<Competence>() {

											@Override
											public void onResponse(Competence response) {
												// TODO Auto-generated method stub
												
											}
										}, new ErrorListener() {

											@Override
											public void onErrorResponse(VolleyError error) {
												Log.e("onErrorResponse", "Delete Competence Error");		
												
											}
										}));
								
								APIObject object;
							    object=(APIObject)listAdapter.getChild(groupPosition, childPosition);
								listAdapter.deleteChild(HeaderItem.COMPETENCE,object);
								
							}
						  })
						.setNegativeButton(R.string.no,
								new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, just close
								// the dialog box and do nothing
								dialog.cancel();
							}
						})
						.create().show();
					break;
					
				case EXPERIENCE:
					break;
				}		
		 

				return true;
			}
		});
		 
		         
        return rootView;
    }
    
}
