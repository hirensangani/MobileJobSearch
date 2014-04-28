package SlidingBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import qualification.ExpandableProfileListAdapter;
import qualification.ExpandableProfileListAdapter.HeaderItem;
import Dialog.AddQualificationDialog;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.visual.mobilejobsearch.R;
import com.visual.mobilejobsearch.database.api.APIAccess;
import com.visual.mobilejobsearch.database.api.APIAccessFactory;
import com.visual.mobilejobsearch.database.calls.GetDegree;
import com.visual.mobilejobsearch.database.calls.GetInstitute;
import com.visual.mobilejobsearch.database.objects.Degree;
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
	
	ImageButton btnAddQualification;
	
	TextView profileName;
	
	ExpandableProfileListAdapter listAdapter;
    ExpandableListView expListView;
    private static String dName, Iname, Von, Bis, Address;

    Preferences preferences;    
    @Override
    public void onPause() {
    	HomeFragment fragment = new HomeFragment();
    	FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.frame_container, fragment).addToBackStack(null).commit();
    	super.onPause();
    }
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.full_profile_details, container, false);
        //ListView
        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);
        prepareListData();
        
        listAdapter = new ExpandableProfileListAdapter(getActivity());
        
        // add some sample data
        // TODO remove
    	Degree d1 = new Degree();
    	d1.name = "Degree 1";
    	listAdapter.addChild(HeaderItem.DEGREE, d1);
    	
    	Degree d2 = new Degree();
    	d2.name = "Degree 2";
    	listAdapter.addChild(HeaderItem.DEGREE, d2);
        
 
        // setting list adapter
        expListView.setAdapter(listAdapter);
        
        profileName = (TextView) rootView.findViewById(R.id.profileName);
        
        //profileName.setText(preferences.getFirstName() + " " + preferences.getLastName());
        
        //end ListView
        btnAddQualification = (ImageButton) rootView.findViewById(R.id.addQualification);
        btnAddQualification.setOnClickListener(onClickAddProfile(rootView));
        
        preferences = new Preferences(getActivity());
		
		api=APIAccessFactory.apiKeyInstance(preferences.getUser(), preferences.getApiKey(), getActivity());
		
		expListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				parent.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Toast.makeText(getActivity(), "hiiiiii",Toast.LENGTH_SHORT).show();
						
					}
				});
				
			}
		});
	//	api.getRequestQueue().add(api.new)
		
		api.getRequestQueue().add(api.newGetInstitute(new Listener<GetInstitute>() {

			@Override
			public void onResponse(GetInstitute response) {
				for(Institute institute : response.objects){
					Log.d("Institute", institute.name);
					Log.d("InstID", institute.id);
					preferences.putInstitute(institute.name, institute.street, institute.zipcode, institute.city);
					Log.e("preference", preferences.getInstitute());
				}
				
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
				
				for(Degree degree : response.objects){
					Log.d("DegreeId", degree.id);
					Log.d("name", degree.name);
					dName = degree.name;
				}
				
			}
		}, 
		new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		}));
         
        return rootView;
    }

	
	
	public OnClickListener onClickAddProfile(View rootView) {
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogFragment dialogFragment = new AddQualificationDialog();
				dialogFragment.show(getActivity().getFragmentManager(), "GetDialog");
				
			}
		};
	}
	
	/*
     * Preparing the list data
     */
    private void prepareListData() {
//        listDataHeader = new ArrayList<String>();
//        listDataChild = new HashMap<String, List<String>>();
// 
//        // Adding child data
//        listDataHeader.add(QUALIFICATION);
//        listDataHeader.add(EXPERIENCE);
//        listDataHeader.add(LANGUAGE);
//        listDataHeader.add(SKILLS);
//        listDataHeader.add(INTERESTS);
// 
//        // Adding child data
//        List<String> Qualification = new ArrayList<String>();
//        Qualification.add(dName + "\n"+ Iname);
//   
// 
//        List<String> Experience = new ArrayList<String>();
//        Experience.add("The Conjuring");
//        Experience.add("Despicable Me 2");
//        Experience.add("Turbo");
//        Experience.add("Grown Ups 2");
//        Experience.add("Red 2");
//        Experience.add("The Wolverine");
// 
//        List<String> Language = new ArrayList<String>();
//        Language.add("English, Deutsch");
//       
//        
//        List<String> Skill = new ArrayList<String>();
//        Skill.add(".....");
//       
//        
//        List<String> Inrerests = new ArrayList<String>();
//        Inrerests.add("......");
//   
//        listDataChild.put(listDataHeader.get(0), Qualification); // Header, Child data
//        listDataChild.put(listDataHeader.get(1), Experience);
//        listDataChild.put(listDataHeader.get(2), Language);
//        listDataChild.put(listDataHeader.get(3), Skill);
//        listDataChild.put(listDataHeader.get(4), Inrerests);
    	

    }
    
    
    @Override
    public void onAttach(Activity activity) {
    	Log.e("onAttach", "onAttach");
    	// TODO Auto-generated method stub
    	super.onAttach(activity);
    }
  
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	Log.e("onAttach", "onActivityCreated");
    	super.onActivityCreated(savedInstanceState);
    }
    
    @Override
    public void onResume() {
    	// TODO Auto-generated method stub
    	Log.e("onre", "resume");
    	super.onResume();
    }
}
