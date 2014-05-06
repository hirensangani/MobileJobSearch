package SlidingBar;

import qualification.ExpandableProfileListAdapter;
import qualification.ExpandableProfileListAdapter.HeaderItem;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
				// TODO Auto-generated method stub
				
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
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
					int groupPosition, int childPosition, long id) {
				
//				final String selected = (String) listAdapter.getChild(
//						groupPosition, childPosition);
//				Toast.makeText(getActivity(), selected, Toast.LENGTH_SHORT).show();
				return true;
			}
		});
		 
		 
         
        return rootView;
    }

	
    
}
