package com.visual.mobilejobsearch;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class SubmitDataToTheServer  extends AsyncTask<Void, String, Void> {
		private List<NameValuePair> nameValuePairs;
		private HttpClient httpclient;
		private HttpPost httppost;
		public SubmitDataToTheServer(Activity activity) {
			nameValuePairs = new ArrayList<NameValuePair>();
			httpclient = new DefaultHttpClient();
			httppost = new HttpPost("http://139.30.218.184:8080/PreparoServer/SignUp");
//			if (isNetworkAvailable(activity)) {
//				Log.e("activity", "isNetworkAvailable");
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				} else {
					execute();
				}
//			}else 
//				Log.e("Http", "Network is Not Avaiable");
		}

		public void setNewPost(String name, String email, String password){//, String patientID,String setMessage, 
			//	String serverMessage, String minYellow, String maxYellow) {
	BasicNameValuePair bMin=new BasicNameValuePair("username", name);
	BasicNameValuePair bMax=new BasicNameValuePair("password", password);
	BasicNameValuePair bHeart=new BasicNameValuePair("email", email);
			if (nameValuePairs.size() == 0) {
				nameValuePairs.add(bMin);
				nameValuePairs.add(bMax);
				nameValuePairs.add(bHeart);
			}
			
			Log.e("Data", name);
		}

		public static boolean isNetworkAvailable(Context ctx) {
			ConnectivityManager connMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
			Log.e("isNetworkAvailable", "ConnectivityManager");
			if (!connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected())
			{//by doing this app won't crash if there is Wifi or Network not avaible..
			Toast.makeText(ctx, "Network is not avaiable", Toast.LENGTH_LONG).show();
			return false;
			}else if (connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()
					|| connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {
				Log.e("isNetworkAvailable", "ConnectivityManager");
				return true;
			}else
			return false;

			//return false;
		}


		@Override
		protected Void doInBackground(Void... arg0) {
			Log.e("Fragment_Bluetooth_Main.SimulationTask", "doInBackground");
			while (true) {

				if (nameValuePairs.size() == 3) {

					try {
						// Add your data
						httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
						// Execute HTTP Post Request
						httpclient.execute(httppost);
						nameValuePairs.removeAll(nameValuePairs);

					} catch (Exception e) {
						//Log.e("DoPost", "exeption!!!");
					}
				}
			}
		}

	}


