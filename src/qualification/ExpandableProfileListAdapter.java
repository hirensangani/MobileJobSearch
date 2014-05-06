package qualification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import qualification.AddCompetenceDialog.OnReturnListenerCompetence;

import Dialog.AddQualificationDialog;
import Dialog.AddQualificationDialog.OnReturnListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.text.GetChars;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.visual.mobilejobsearch.R;
import com.visual.mobilejobsearch.database.objects.APIObject;
import com.visual.mobilejobsearch.database.objects.Competence;
import com.visual.mobilejobsearch.database.objects.CompetenceType;
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
	public Object getChild(int groupPosition,final int childPosititon) {
		HeaderItem key = HeaderItem.values()[groupPosition];
		APIObject childDegree = _data.get(key).get(childPosititon);
		
		return childDegree;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition,  final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		
			LayoutInflater infalInflater = (LayoutInflater) this._activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
		
		APIObject child = (APIObject) getChild(groupPosition,
				childPosition);
		
		HeaderItem group = (HeaderItem) getGroup(groupPosition);
		
		String headerStringDegreeName = "";
		String headerStringInstituteName = "";
		String headerStringTime = "";
		String headerStringCompetenceName = "";
		float ratingCompetence = 0;
		switch (group) {
		case QUALIFICATION:
			convertView = infalInflater.inflate(R.layout.child_item_qualification,parent,false);
			TextView txtListChild1 = (TextView) convertView.findViewById(R.id.lblListItem1);
			TextView txtListChild2 = (TextView) convertView.findViewById(R.id.lblListItem2);
			TextView txtListChild3 = (TextView) convertView.findViewById(R.id.lblListItem3);
		    headerStringDegreeName = ((Qualification)child).degree.name;
			headerStringInstituteName=((Qualification)child).institute.name;
			headerStringTime=((Qualification)child).begin_of_education +" to "+ ((Qualification)child).end_of_education;
			
			txtListChild1.setText(headerStringDegreeName);
			
			
			txtListChild2.setText(headerStringInstituteName);
			
			
			txtListChild3.setText(headerStringTime);
			
			break;
		case COMPETENCE:
			convertView = infalInflater.inflate(R.layout.child_item_competence,parent,false);
			
			TextView textView = (TextView)convertView.findViewById(R.id.textViewCompetence);
			RatingBar bar = (RatingBar)convertView.findViewById(R.id.ratingBarCompetence);
			
			headerStringCompetenceName = ((Competence)child).type.name;
			ratingCompetence = ((Competence)child).rating;
			textView.setText(headerStringCompetenceName);
			bar.setRating(ratingCompetence);
			break;
		case EXPERIENCE:
			break;
		}
		
	
		

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
	
	public void deleteChild(final HeaderItem header, APIObject child){
		
		_data.get(header).remove(child);
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
			dialogFragment.setOnReturnListener(new OnReturnListenerCompetence(){

				@Override
				public void onReturn(String competenceType, float ratingValue) {
					Competence competence = new Competence();
					competence.type = new CompetenceType();
					competence.type.name = competenceType;
					competence.rating = ratingValue;
					 addChild(HeaderItem.COMPETENCE,competence);
					  Toast.makeText(_activity, "Competence has been updated", Toast.LENGTH_SHORT).show();
				}
				
			});
			
			dialogFragment.show(_activity.getFragmentManager(), "GetDialog_Competence");
		}
	}
}