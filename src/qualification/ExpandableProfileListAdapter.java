package qualification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Dialog.AddQualificationDialog;
import Dialog.AddQualificationDialog.OnReturnListener;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
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
		QUALIFICATION,
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
			convertView = infalInflater.inflate(R.layout.child_item,
					null);

		}
		
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
		}
		
		TextView txtListChild1 = (TextView) convertView
				.findViewById(R.id.lblListItem1);
		txtListChild1.setText(headerString1);
		
		TextView txtListChild2 = (TextView) convertView
				.findViewById(R.id.lblListItem2);
		txtListChild2.setText(headerString2);
		
		TextView txtListChild3 = (TextView) convertView
				.findViewById(R.id.lblListItem3);
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
		
		// get caption
		String groupString = _activity.getResources().getStringArray(R.array.profile_header_items)[groupPosition];
		
		// set caption
		TextView lblListHeader = (TextView) convertView
				.findViewById(R.id.lblListHeader);
		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(groupString);
	
		// add listener to add button
		Button addButton = (Button) convertView.findViewById(R.id.addBtnQualification);
		addButton.setOnClickListener(new ButtonListener((HeaderItem)getGroup(groupPosition)));
		
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
	
	
	private class ButtonListener implements OnClickListener {
		private HeaderItem header;
		
		public ButtonListener(HeaderItem header) {
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
				public void onReturn(String degreeName, String instituteName, String time) {
					Qualification qualification= new Qualification();
					qualification.degree = new Degree();
					qualification.institute = new Institute();
					qualification.degree.name=degreeName;
					qualification.institute.name=instituteName;
					qualification.begin_of_education=time;
				    addChild(HeaderItem.QUALIFICATION,qualification);
					Toast.makeText(_activity, "hiii", Toast.LENGTH_SHORT).show();
					
				}
			});
			dialogFragment.show(_activity.getFragmentManager(), "GetDialog");
			
			//Log.e("Adapter", "header=" + header);
			
			
		}
	}
}