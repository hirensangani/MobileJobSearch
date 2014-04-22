package serverStuff;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

public class loginUser {

	public static String GET(String url, Person person) {
		
		   InputStream inputStream = null;
	        String result = "";
	      
	        try {
	        		 
	                // 1. create HttpClient
	                HttpClient httpclient = new DefaultHttpClient();
	     
	                // 2. make POST request to the given URL
	              //  HttpPost httpPost=new HttpPost(url);
	                byte[] data=(person.getLoginUserName()+":"+person.getPassword()).getBytes();
	                String base64EncodedCredentials =Base64.encodeToString(data, Base64.NO_WRAP);
	                String httpheader="Basic "+base64EncodedCredentials;
//	                System.out.println("httpheader "+httpheader);
	                HttpGet httpGet = new HttpGet(url);
//	                
//	                httpGet.addHeader("username", person.getLoginUserName());
//	                httpGet.addHeader("password", person.getLoginPassword());
//	     
	                httpGet.setHeader("Authorization", httpheader);
	                
	                HttpResponse httpResponse = httpclient.execute(httpGet); 
	                String requestCode = String.valueOf(httpResponse.getStatusLine().getStatusCode());
	               // Log.e("getResponse",httpResponse.getEntity().toString());
	                
	                Log.e("Strings", person.getLoginUserName() +" || "
	                		+ person.getLoginPassword());
	                Log.e("httpresponse", requestCode);
	                

	        }catch (Exception e) {
	            Log.d("InputStream", e.getLocalizedMessage());
	        }
		return null;
	}

}
