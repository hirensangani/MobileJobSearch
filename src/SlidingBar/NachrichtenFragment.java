package SlidingBar;

import com.visual.mobilejobsearch.R;


import android.os.Bundle;
import android.app.Fragment;import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NachrichtenFragment extends Fragment{
	
	public NachrichtenFragment(){}
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_whats_hot, container, false);
         
        return rootView;
    }
}
