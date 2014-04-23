package SlidingBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import qualification.AddQualificationFragment;
import qualification.ExpandableProfileListView;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.visual.mobilejobsearch.R;
import com.visual.mobilejobsearch.database.api.APIAccess;
import com.visual.mobilejobsearch.database.api.APIAccessFactory;
import com.visual.mobilejobsearch.database.calls.GetInstitute;
import com.visual.mobilejobsearch.database.objects.Institute;
import com.visual.mobilejobsearch.persistent.Preferences;

public class ProfileFragment extends Fragment {
	
	public ProfileFragment(){}
	
	final static String QUALIFICATION = "Qualification";
	final static String EXPERIENCE = "Experience";
	final static String LANGUAGE = "Language";
	final static String SKILLS = "Skills & Expertise";
	final static String INTERESTS = "Interests";
	
	APIAccess api;
	
	Button btnAddQualification;
	
	ExpandableProfileListView listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.full_profile_details, container, false);
        //ListView
        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);
        prepareListData();
        
        listAdapter = new ExpandableProfileListView(getActivity(), listDataHeader, listDataChild);
 
        // setting list adapter
        expListView.setAdapter(listAdapter);
        
        //end ListView
        btnAddQualification = (Button) rootView.findViewById(R.id.addQualification);
        btnAddQualification.setOnClickListener(onClickAdd(rootView));
        
       Preferences preferences = new Preferences(getActivity());
		
		api=APIAccessFactory.apiKeyInstance(preferences.getUser(), preferences.getApiKey(), getActivity());
		
		api.getRequestQueue().add(api.newGetInstitute(new Listener<GetInstitute>() {

			@Override
			public void onResponse(GetInstitute response) {
				for(Institute institute : response.objects){
					Log.e("Institute", institute.name);
				}
				
			}
		}, 
		
			new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					// TODO Auto-generated method stub
					
				}
			}	));
         
        return rootView;
    }

	
	private OnClickListener onClickAdd(View rootView) {
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(getActivity(), AddQualificationFragment.class);
				 startActivity(intent);
				
			}
		};
	}
	
	/*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
 
        // Adding child data
        listDataHeader.add(QUALIFICATION);
        listDataHeader.add(EXPERIENCE);
        listDataHeader.add(LANGUAGE);
        listDataHeader.add(SKILLS);
        listDataHeader.add(INTERESTS);
 
        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");
 
        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");
 
        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");
 
        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }
}
