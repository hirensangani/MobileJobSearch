package qualification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Dialog.AddQualificationDialog;
import Dialog.AddQualificationDialog.OnReturnListener;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.visual.mobilejobsearch.R;
import com.visual.mobilejobsearch.database.objects.APIObject;
import com.visual.mobilejobsearch.database.objects.Degree;
import com.visual.mobilejobsearch.database.objects.Institute;
import com.visual.mobilejobsearch.database.objects.Qualification;

public class ExpandableProfileListAdapter extends BaseExpandableListAdapter {

	private Activity _activity;
	// child data in format of header title, child title
	private HashMap<HeaderItem, List<APIObject>> _data;

	public static enum HeaderItem {
		QUALIFICATION,COMPETENCE,EXPERIENCE,
	};

	public ExpandableProfileListAdapter(Activity activity) {
		_activity = activity;
		_data = new HashMap<HeaderItem, List<APIObject>>();
		for (HeaderItem header : HeaderItem.values()) {
			_data.put(header, new ArrayList<APIObject>());
		}
	}
	
	

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		HeaderItem key = HeaderItem.values()[groupPosition];
		APIObject childDegree = _data.get(key).get(childPosititon);
	//	Log.e("Child",String.valueOf(childPosititon));
		
		return childDegree;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.child_item,parent,false);

		}
		
		TextView txtListChild1 = (TextView) convertView.findViewById(R.id.lblListItem1);
		TextView txtListChild2 = (TextView) convertView.findViewById(R.id.lblListItem2);
		TextView txtListChild3 = (TextView) convertView.findViewById(R.id.lblListItem3);
		
//		if(convertView!=null){
//			txtListChild1.setText("Hiiiiiiiiiiiii");
//		}
		
		
		APIObject child = (APIObject) getChild(groupPosition,
				childPosition);
		
		HeaderItem group = (HeaderItem) getGroup(groupPosition);
		
		String headerString1 = "";
		String headerString2 = "";
		String headerString3 = "";
		switch (group) {
		case QUALIFICATION:
		headerString1 = ((Qualification)child).degree.name;
			headerString2=((Qualification)child).institute.name;
			headerString3=((Qualification)child).begin_of_education;
			//Log.e("String1", headerString1);
			break;
		case COMPETENCE:
			break;
		case EXPERIENCE:
			break;
		}
		
	
		txtListChild1.setText(headerString1);
		
		
		txtListChild2.setText(headerString2);
		
		
		txtListChild3.setText(headerString3);

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		HeaderItem key = HeaderItem.values()[groupPosition];
		List<APIObject> children = _data.get(key);
		
		return children.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		HeaderItem key = HeaderItem.values()[groupPosition];
	//	Log.e("Group", String.valueOf(groupPosition));
		
		return key;
	}

	@Override
	public int getGroupCount() {
		int count = HeaderItem.values().length;
		
		return count;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// inflate view if necessary
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) _activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(
					R.layout.group_items, null);
		}
		Button addButtonQualification = (Button) convertView.findViewById(R.id.addBtnQualification);
		// get caption
		String groupString = _activity.getResources().getStringArray(R.array.profile_header_items)[groupPosition];
		
		
		
		// set caption
		TextView lblListHeader = (TextView) convertView
				.findViewById(R.id.lblListHeader);
		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(groupString);
	
		// add listener to add button
		
		
		if(groupPosition==0){
			
			addButtonQualification.setOnClickListener(new ButtonListenerQualification((HeaderItem)getGroup(groupPosition)));
		}
		if(groupPosition==1){
		//	Button addButtonCompetence = (Button) convertView.findViewById(R.id.addBtnQualification);
			addButtonQualification.setOnClickListener(new ButtonListenerCompetence((HeaderItem)getGroup(groupPosition)));
		}
		
		
		return convertView;
	}



	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public void addChild(HeaderItem header, APIObject child) {
		_data.get(header).add(child);
	//	Log.e("Values",String.valueOf(_data.values().size()));
		
		notifyDataSetChanged();
	}
	
	
	private class ButtonListenerQualification implements OnClickListener {
		private HeaderItem header;
		
		public ButtonListenerQualification(HeaderItem header) {
			this.header = header;
		}
		
		@Override
		public void onClick(View v) {
			// launch dialog and pass header
			
//			Degree degree = null; // return value of dialog
//			addChild(header, degree);
			AddQualificationDialog dialogFragment = new AddQualificationDialog();
			dialogFragment.setOnReturnListener(new OnReturnListener() {
				
				@Override
				public void onReturn(String degreeName, String instituteName, String timeVon, String timeBis) {
					Qualification qualification= new Qualification();
					qualification.degree = new Degree();
					qualification.institute = new Institute();
					qualification.degree.name=degreeName;
					qualification.institute.name=instituteName;
					qualification.begin_of_education=timeVon;
					qualification.end_of_education=timeBis;
				    addChild(HeaderItem.QUALIFICATION,qualification);
					Toast.makeText(_activity, "Qualification has been added", Toast.LENGTH_SHORT).show();
					
				}
			});
			dialogFragment.show(_activity.getFragmentManager(), "GetDialog_Qualification");
			
			//Log.e("Adapter", "header=" + header);
			
			
		}
	}
	
	
	private class ButtonListenerCompetence implements OnClickListener {
		private HeaderItem header;
		private Dialog ratingDialog;
		
		public ButtonListenerCompetence(HeaderItem header) {
			this.header = header;
		}
		
		@Override
		public void onClick(View v) {
			// launch dialog and pass header
			AddCompetenceDialog dialogFragment = new AddCompetenceDialog();
			dialogFragment.show(_activity.getFragmentManager(), "GetDialog_Competence");
			
           Toast.makeText(_activity, "I did it", Toast.LENGTH_SHORT).show();
			
			
		}
	}
}