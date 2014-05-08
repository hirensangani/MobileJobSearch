package SlidingBar;

import com.visual.mobilejobsearch.R;
import com.visual.mobilejobsearch.persistent.Preferences;

import android.os.Bundle;
import android.app.Fragment;import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class SettingsFragment extends Fragment {
	
	Button logOut;
	Preferences preferences;
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        
        preferences = new Preferences(getActivity());
        
        logOut = (Button) rootView.findViewById(R.id.btnLogOut);
        logOut.setOnClickListener(onClickLogOut(rootView));
         
        return rootView;
    }


	private OnClickListener onClickLogOut(View rootView) {
		// TODO Auto-generated method stub
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!(preferences.getUser()==null) || !(preferences.getApiKey()==null)){
					preferences.putUser("");	
					preferences.putApiKey("");
					getActivity().finish();
				}
			}
		};
	}
}
