package SlidingBar;

import com.visual.mobilejobsearch.R;

import android.os.Bundle;
import android.app.Fragment;import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BewerbungenFragment extends Fragment {
	
	public BewerbungenFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_pages, container, false);
         
        return rootView;
    }
}
