package SlidingBar;

import qualification.ExpandableProfileListAdapter;
import qualification.ExpandableProfileListAdapter.HeaderItem;
import SlidingBar.mediaactivity.ProfileMedia;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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
	private static final int SELECT_PHOTO = 100;
	private static final int SELECT_VIDEO = 101;
	
	APIAccess api;
	private Bitmap bitmap;
	
	TextView profileName;
	ImageView profileImageView;
	VideoView profileVideoView;
	
	ExpandableProfileListAdapter listAdapter;
    ExpandableListView expListView;
    private static String dName, Iname, Von, Bis, Address;

    Preferences preferences;
    
    ProfileMedia profileMedia;
    
	protected static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	protected static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;

	// directory name to store captured images and videos
	protected static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
	
	protected Uri fileUri; // file url to store image/video
//    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.full_profile_details, container, false);
        //ListView
        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);
        
        listAdapter = new ExpandableProfileListAdapter(getActivity());
 
        profileName = (TextView) rootView.findViewById(R.id.profileName);
        
        profileMedia = new ProfileMedia();
        
        TextView loadImage = (TextView)rootView.findViewById(R.id.profileImageText);
        loadImage.setOnClickListener(onClickImageViewLoader(rootView));
        
        TextView loadVideo = (TextView) rootView.findViewById(R.id.profileVideoText);
        loadVideo.setOnClickListener(onClickProfileVideoLoader(rootView));
        
         profileImageView = (ImageView) rootView.findViewById(R.id.profileImage);
        profileImageView.setOnClickListener(onClickImageView(rootView));
        
         profileVideoView = (VideoView)rootView.findViewById(R.id.profile_video);
      
        
        
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
							.setCancelable(true)
							.setIcon(R.drawable.ic_delete)
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
									dialog.dismiss();
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
						.setCancelable(true)
						.setIcon(R.drawable.ic_delete)
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
	
	
	private OnClickListener onClickImageView(View rootView) {
		// TODO Auto-generated method stub
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				captureImage();
				
			}
		};
	}
	
	
	
	private OnClickListener onClickImageViewLoader(View rootView) {
	// TODO Auto-generated method stub
	return new OnClickListener() {
		
		@Override
		public void onClick(View v) {
					captureImage();
		}
	};
}


	
	
private OnClickListener onClickProfileVideoLoader(View rootView) {
	return new OnClickListener() {
		
		@Override
		public void onClick(View v) {
		
			recordVideo();
			
		}
	};
}

/*
 * Capturing Camera Image will lauch camera app requrest image capture
 */
private void captureImage() {
	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

	fileUri = profileMedia.getOutputMediaFileUri(profileMedia.MEDIA_TYPE_IMAGE);

	intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

	// start the image capture Intent
	startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
	
}


/*
 * Recording video
 */
private void recordVideo() {
	Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

	fileUri = profileMedia.getOutputMediaFileUri(profileMedia.MEDIA_TYPE_VIDEO);

	// set video quality
	intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

	intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
														// name

	// start the video capture Intent
	startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
}
//

@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
	// if the result is capturing Image
	
	Log.e("OnActivityResult", String.valueOf(getActivity().RESULT_OK));
	
	if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
		
		Log.e("ResultCode", String.valueOf(resultCode));
		if (resultCode == getActivity().RESULT_OK) {
			// successfully captured the image
			// display it in image view
			Log.e("ResultCode", String.valueOf(getActivity().RESULT_OK));
			previewCapturedImage();
		} else if (resultCode == getActivity().RESULT_CANCELED) {
			// user cancelled Image capture
			Toast.makeText(getActivity(),
					"User cancelled image capture", Toast.LENGTH_SHORT)
					.show();
		} else {
			// failed to capture image
			Toast.makeText(getActivity(),
					"Sorry! Failed to capture image", Toast.LENGTH_SHORT)
					.show();
		}
	} else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
		if (resultCode == getActivity().RESULT_OK) {
			// video successfully recorded
			// preview the recorded video
			previewVideo();
		} else if (resultCode ==getActivity().RESULT_CANCELED) {
			// user cancelled recording
			Toast.makeText(getActivity(),
					"User cancelled video recording", Toast.LENGTH_SHORT)
					.show();
		} else {
			// failed to record video
			Toast.makeText(getActivity(),
					"Sorry! Failed to record video", Toast.LENGTH_SHORT)
					.show();
		}
	}
}
public void previewCapturedImage() {
	try {
		// hide video preview

		profileImageView.setVisibility(View.VISIBLE);

		// bimatp factory
		BitmapFactory.Options options = new BitmapFactory.Options();

		// downsizing image as it throws OutOfMemory Exception for larger
		// images
		options.inSampleSize = 8;

		final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
				options);

		profileImageView.setImageBitmap(bitmap);
	} catch (NullPointerException e) {
		e.printStackTrace();
	}
}

/*
 * Previewing recorded video
 */
public void previewVideo() {
	try {
		// hide image preview

		profileVideoView.setVisibility(View.VISIBLE);
		profileVideoView.setVideoPath(fileUri.getPath());
		// start playing
		profileVideoView.start();
	} catch (Exception e) {
		e.printStackTrace();
	}
}
}
