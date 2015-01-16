package com.campusguide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class visitorView extends Activity
{
	ImageButton btnmap,btnaskdir;
	String bestProvider;
	private LocationManager lm;
    private LocationListener locationListener;
    double lat;
    double lng;
    double lati,longi;
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.visitorview);
        btnmap=(ImageButton)this.findViewById(R.id.map);
        btnaskdir=(ImageButton)this.findViewById(R.id.direction);
        
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();
        
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);  
        
        bestProvider = lm.getBestProvider(criteria, true);
        
        lm.requestLocationUpdates(bestProvider,0,0,locationListener);  
        
        btnmap.setOnClickListener(new OnClickListener() 
        {
            public void onClick(View view) 
            {
            	Intent intent =new Intent(visitorView.this, viewVisitorMap.class); 
            	Bundle bundle = new Bundle();
            	bundle.putString("latitude",lat+"");
            	bundle.putString("longitude",lng+"");
            	intent.putExtras(bundle);
               	startActivity(intent);  
            }
        }
    	);
        
        btnaskdir.setOnClickListener(new OnClickListener() 
        {
            public void onClick(View view) 
            {
            	Intent intent =new Intent(visitorView.this, askmevisitor.class); 
            	Bundle bundle = new Bundle();
            	bundle.putString("latitude",lat+"");
            	bundle.putString("longitude",lng+"");
            	intent.putExtras(bundle);
               	startActivity(intent);  
            }
        }
    	);
        
	}
	
	
	 private class MyLocationListener implements LocationListener 
	    {
	        @Override
	        public void onLocationChanged(Location loc) {
	            if (loc != null) {
	                Toast.makeText(getBaseContext(), 
	                    "Location changed : Lat: " + loc.getLatitude() + 
	                    " Lng: " + loc.getLongitude(), 
	                    Toast.LENGTH_SHORT).show();
	                lat=loc.getLatitude();
	                lng=loc.getLongitude();
	                
	            }
	        }
	        @Override
	        public void onProviderDisabled(String provider) {
	            // TODO Auto-generated method stub
	        }


	         @Override
	        public void onProviderEnabled(String provider) {
	            // TODO Auto-generated method stub
	        }

	        @Override
	        public void onStatusChanged(String provider, int status, 
	            Bundle extras) {
	            // TODO Auto-generated method stub
	        }

			
	    }
}
	
