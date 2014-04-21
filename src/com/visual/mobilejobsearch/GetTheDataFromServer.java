//package com.visual.mobilejobsearch;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.app.Activity;
//import android.content.Context;
//import android.net.ConnectivityManager;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.util.Log;
//import android.widget.Toast;
//
//public class GetTheDataFromServer  extends AsyncTask<Void, String, Void> {
//		private List<NameValuePair> nameValuePairs;
//		private HttpClient httpclient;
//		HttpResponse httpResponse= null;
//		HttpEntity httpEntity = null;
//		HttpGet httpGet=null;
//		public static String response = null;
//		
//		private static final String TAG_VORNAME = "first_name";
//		private static final String TAG_USER = "username";
//		private static final String TAG_PASSWORD = "password";
//		private static final String TAG_EMAIL = "email";
//		private static final String TAG_LASTNAME = "last_name";
//		
//		
//		
//		public GetTheDataFromServer(Activity activity) {
//			nameValuePairs = new ArrayList<NameValuePair>();
//			httpclient = new DefaultHttpClient();
//			httpGet = new HttpGet("http://preparo.unter-guten-freunden.de/api/v1/token/auth/");
////			if (isNetworkAvailable(activity)) {
////				Log.e("activity", "isNetworkAvailable");
//				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//					executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//				} else {
//					execute();
//				}
////			}else 
////				Log.e("Http", "Network is Not Avaiable");
//		}
//
//		public void setNewPost(String userName, String email, String password, String Name, String VorName ){//, String patientID,String setMessage, 
//			//	String serverMessage, String minYellow, String maxYellow) {
//	BasicNameValuePair bUser=new BasicNameValuePair("username", userName);
//	BasicNameValuePair bEmail=new BasicNameValuePair("password", password);
//	BasicNameValuePair bPass=new BasicNameValuePair("email", email);
//	BasicNameValuePair bName=new BasicNameValuePair("last_name", Name);
//	BasicNameValuePair bVor=new BasicNameValuePair("first_name", VorName);
//			if (nameValuePairs.size() == 0) {
//				nameValuePairs.add(bUser);
//				nameValuePairs.add(bEmail);
//				nameValuePairs.add(bPass);
//				nameValuePairs.add(bName);
//				nameValuePairs.add(bVor);
//			}
//			
//		}
//
//		public static boolean isNetworkAvailable(Context ctx) {
//			ConnectivityManager connMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
//			Log.e("isNetworkAvailable", "ConnectivityManager");
//			if (!connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected())
//			{//by doing this app won't crash if there is Wifi or Network not avaible..
//			Toast.makeText(ctx, "Network is not avaiable", Toast.LENGTH_LONG).show();
//			return false;
//			}else if (connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()
//					|| connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {
//				Log.e("isNetworkAvailable", "ConnectivityManager");
//				return true;
//			}else
//			return false;
//
//			//return false;
//		}
//
//
//		@Override
//		protected Void doInBackground(Void... arg0) {
//			Log.e("Fragment_Bluetooth_Main.SimulationTask", "doInBackground");
//			while (true) {
//
//				if (nameValuePairs.size() == 5) {
//
//					try {
//						// Add your data
//						httppost.setHeader("Content-Type","application/json");
//						httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//						// Execute HTTP Post Request
//					    httpResponse = httpclient.execute(httppost);
//						//nameValuePairs.removeAll(nameValuePairs);
//					    httpEntity=httpResponse.getEntity();
//					    response=EntityUtils.toString(httpEntity);
//					//    Log.e("response", response);
//					    
//					    if(response!=null){
//					    	JSONObject jsonObj= new JSONObject(response);
//					    	jsonObj.getString(TAG_EMAIL);
//					    	jsonObj.getString(TAG_VORNAME);
//					    	jsonObj.getString(TAG_LASTNAME);
//					    	jsonObj.getString(TAG_PASSWORD);
//					    	jsonObj.getString(TAG_USER);
//					    	
//					    	Log.e("Strings",jsonObj.getString(TAG_USER));
//					    }
//
//					} catch (Exception e) {
//						//Log.e("DoPost", "exeption!!!");
//					}
//				}
//			}
//		}
//
//	}
//
//
