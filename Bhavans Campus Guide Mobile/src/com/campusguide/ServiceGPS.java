package com.campusguide;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;




public class ServiceGPS extends Service{

	
	
	private LocationManager locationManager;
	
	String bestProvider;
	
	
	
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() 
	{
		
	}

	@Override
	public void onDestroy() {
		
	}
	
	@Override
	public void onStart(Intent intent, int startid) {
		
		updateGPSLoc();
		
	}
	public void updateGPSLoc()
    {
    	
    	locationManager =(LocationManager) getSystemService(Context.LOCATION_SERVICE);
    	
        
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);  
        
        bestProvider = locationManager.getBestProvider(criteria, true);
        
        locationManager.requestLocationUpdates(bestProvider,0,0,new LocationUpdate());
   
    	
    }




public class LocationUpdate implements LocationListener {

	@Override
	public void onLocationChanged(Location loc)
	{
    	
		
		   
    	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    	nameValuePairs.add(new BasicNameValuePair("latitude",""+loc.getLatitude()));
    	nameValuePairs.add(new BasicNameValuePair("longitude",""+loc.getLongitude()));
    	nameValuePairs.add(new BasicNameValuePair("uname",login.UNAME));
    	
    	
 	     try
 	   	 {
 	          // HttpClient httpclient = new DefaultHttpClient();
 	           HttpPost httppost = new HttpPost(login.IPADD+"uploadGPSLoc.php");
 	           httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
 	           HttpResponse response = login.httpclient.execute(httppost);
 	          
 	       }
 	   	
 	   	catch(Exception e)
 	   	{
 	           Log.e("log_tag", "Error in http connection "+e.toString());
 	       }
        
        
	}

	@Override
	public void onProviderDisabled(String provider) {}

	@Override
	public void onProviderEnabled(String provider) {}

	@Override
	public void onStatusChanged(String provider, int status, 
			Bundle extras) {}


}

}

