package sandip.upgrad_test_sandip.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;



@SuppressLint("ResourceAsColor")
public class MyUtils {

	public static boolean showlog = true;
	public static String MOVIE_DATA = "movie_data";

	public static int POST = 1;
	public static int GET = 0;

	
	public static boolean isOnline(Context context) {

		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		Class[] items = { String.class, Integer.class };

		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(context.CONNECTIVITY_SERVICE);

		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI")) {
				if (ni.isConnected()) {
					haveConnectedWifi = true;
					System.out.println("WIFI CONNECTION AVAILABLE");
				} else {
					System.out.println("WIFI CONNECTION NOT AVAILABLE");
				}
			}
			if (ni.getTypeName().equalsIgnoreCase("MOBILE")) {
				if (ni.isConnected()) {
					haveConnectedMobile = true;
					System.out.println("MOBILE INTERNET CONNECTION AVAILABLE");
				} else {
					System.out
							.println("MOBILE INTERNET CONNECTION NOT AVAILABLE");
				}
			}

		}
		return haveConnectedWifi || haveConnectedMobile;

	}

	public static void l(String name, String value) {
		if (showlog) {
			// Log.d(name,value);

			if (value.length() > 4000) {
				Log.e(name, value.substring(0, 4000));
				// log(name,value.substring(4000));
			} else
				Log.e(name, value);
		}
	}


	
	public static StringBuilder generate_req_string(LinkedHashMap<String, String> hm_request){
		
		StringBuilder str_request=new StringBuilder();
		
		Iterator it = hm_request.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	       // System.out.println(pair.getKey() + " = " + pair.getValue());
	        str_request.append(pair.getKey()+"="+pair.getValue()+"&");
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    return str_request;
	}
	

}
