package SlidingBar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.visual.mobilejobsearch.R;

public class ProfileTestFragment extends Fragment {
	private ImageView imageView;
	private Bitmap bitmap;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.full_profile_details, container, false);



		imageView = (ImageView) view.findViewById(R.id.profileImage);
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handleOnProfileImageClick();
			}
		});

		return view;
	}

	private void handleOnProfileImageClick() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		startActivityForResult(intent, 42);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("sg.start", "onActivityResult");

		Log.d("ProfileFragment#onActivityResult", requestCode + ":" + resultCode);
		Log.d("ProfileFragment#onActivityResult", "RESULT_OK=" + (resultCode == Activity.RESULT_OK));
		
		InputStream stream = null;
		if (requestCode == 42 && resultCode == Activity.RESULT_OK)
			try {
				// recyle unused bitmaps
				if (bitmap != null) {
					bitmap.recycle();
				}
				stream = getActivity().getContentResolver().openInputStream(
						data.getData());
				bitmap = BitmapFactory.decodeStream(stream);

				imageView.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				if (stream != null)
					try {
						stream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
	}
}
