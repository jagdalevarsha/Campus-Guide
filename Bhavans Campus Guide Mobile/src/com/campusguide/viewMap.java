package com.campusguide;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.campusguide.ShowFriends.MyPositionOverlay;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;
import com.google.android.maps.MapView.LayoutParams;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;


public class viewMap extends MapActivity {
	

	
    
    private MapView mapView;
    private MapController mc;

    double lat;
    double lng;
    String uname;
    GeoPoint p;
    Canvas ca;
    boolean shadow;
    private final int mRadius = 5;
    MyPositionOverlay positionOverlay;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usermap);
         
        mapView = (MapView) findViewById(R.id.mapView1);
        mapView.setSatellite(true);
        mapView.setClickable(true);
        Bundle bundle = getIntent().getExtras();
		uname = bundle.getString("username");
		 Log.i("uname",uname);
		getLatitudeLongitude();
        
        LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.zoom);  
        @SuppressWarnings("deprecation")
		View zoomView = mapView.getZoomControls(); 
 
        zoomLayout.addView(zoomView, 
            new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, 
                LayoutParams.WRAP_CONTENT)); 
        mapView.displayZoomControls(true);
        mc = mapView.getController();
       p = new GeoPoint((int)(lat * 1E6),(int)(lng* 1E6));
       mc.animateTo(p);
        mc.setZoom(16);                
        mapView.invalidate();
        Drawable marker=getResources().getDrawable(R.drawable.mapmarker);
        marker.setBounds(0, 0, marker.getIntrinsicWidth(),
         marker.getIntrinsicHeight());
        List<Overlay> overlays = mapView.getOverlays();
        InterestingLocations markers = new InterestingLocations(marker);
        positionOverlay = new MyPositionOverlay();
        
        overlays.add(positionOverlay);
        overlays.add(markers);
        markers.addNewItem(p, "you r here", "SPCE");
       
        
        
}
    @Override
    protected boolean isLocationDisplayed() {
    return false;
    }
    protected boolean isRouteDisplayed() {
        // TODO Auto-generated method stub
        return false;
    }    
    
   /* private class MyLocationListener implements LocationListener 
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
                GeoPoint p = new GeoPoint((int)(lat * 1E6),(int)(lng * 1E6));
                mc.animateTo(p);
                mc.setZoom(16);                
                mapView.invalidate();
                Drawable marker=getResources().getDrawable(R.drawable.mapmarker);
                marker.setBounds(0, 0, marker.getIntrinsicWidth(),
                 marker.getIntrinsicHeight());
                List<Overlay> overlays = mapView.getOverlays();
                InterestingLocations markers = new InterestingLocations(marker);
                overlays.add(markers);
                markers.addNewItem(p, "SPCE", "SPCE");
                //GeoPoint p = new GeoPoint(
                  //      (int) (19.123515* 1E6), 
                    //    (int) (72.835915* 1E6));
                //mc.animateTo(p);
                //mc.setZoom(16);                
                //mapView.invalidate();

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

		
    }*/
    
    class InterestingLocations extends ItemizedOverlay<OverlayItem> {
    	//Location loc;
    	//private List<OverlayItem> locations = new ArrayList<OverlayItem>();
    	private ArrayList<OverlayItem> items;
    	//private Drawable marker;
		public InterestingLocations(Drawable marker) {
			//super(marker);
			//this.marker=marker;
			super(boundCenterBottom(marker));
			items = new ArrayList<OverlayItem>();
		
			populate();
			
			//double lat=loc.getLongitude();
			//double lng=loc.getLongitude();
			//GeoPoint p1= mapView.getMapCenter();
			//GeoPoint npark = new GeoPoint((int)(19.123515*1E6),(int)(72.835915*1E6));
			//locations.add(new OverlayItem(npark , "SPCE", "SPCE"));
			//populate();
			// TODO Auto-generated constructor stub
		}
		
		/*@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		  super.draw(canvas, mapView, shadow);
		  boundCenterBottom(marker);
		}*/
		
		public void addNewItem(GeoPoint location, String markerText,
				String snippet) {
				items.add(new OverlayItem(location, markerText, snippet));
				
				populate();
				}
		@Override
		protected OverlayItem createItem(int i) {
			// TODO Auto-generated method stub
			return items.get(i);
			
		}
		public void removeItem(int index) {
			items.remove(index);
			populate();
			}
		@Override
		public int size() {
			// TODO Auto-generated method stub
			 return items.size();
		}
    }
    
    public void getLatitudeLongitude()
    {
    	InputStream is = null;
		
	   String res = "";
	    
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("uname",uname));
        Log.i("uname",uname);
	    //http post
	    try{
	            HttpClient httpclient = new DefaultHttpClient();
	            HttpPost httppost = new HttpPost(login.IPADD+"viewMap.php");
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	            HttpResponse response = login.httpclient.execute(httppost);
	            HttpEntity entity = response.getEntity();
	            is = entity.getContent();
	            

	    }catch(Exception e){
	            Log.e("log_tag", "Error in http connection "+e.toString());
	    }

	    //convert response to string
	    try{
	            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
	            StringBuilder sb = new StringBuilder();
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                    sb.append(line + "\n");
	            }
	            is.close();
	            res=sb.toString();
	            Log.i("uname",res);
	    }catch(Exception e){
	            Log.e("log_tag", "Error converting result "+e.toString());
	    }
	    //parse json data
	    try{
	            
	    	    if(res.equalsIgnoreCase("null\n"))
	    	    {
	    	    	res="";
	    	    	
	    	    }
	    	    else
	    	    {
	    	    JSONArray jArray = new JSONArray(res);
	            for(int i=0;i<jArray.length();i++)
	            {
	                    JSONObject json_data = jArray.getJSONObject(i);
	                    //retuname = json_data.getString("uname");
	                    
	                    lat = json_data.getDouble("latitude");
	                    lng = json_data.getDouble("longitude");
                        Log.i(""+lat,""+lng); 
	                    
	            }
	    }}catch(JSONException e){
	            Log.e("log_tag", "Error parsing data "+e.toString());
	    }
	    
    }
    
    public class MyPositionOverlay extends Overlay
	{
		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		  // boundCenterBottom(marker);
		//super.draw(canvas, mapView, shadow);
		Projection projection = mapView.getProjection();
		Log.i("hii10","hello10");
        
		if(shadow==false){
		  Point point = new Point();
		  projection.toPixels(p, point);
		  Log.i("hii11","hello11");
	      RectF oval = new RectF(point.x - mRadius, point.y - mRadius,
	    		  point.x + mRadius, point.y + mRadius);
	      Paint paint = new Paint();
	      paint.setARGB(250, 0, 0, 0);
	      paint.setAntiAlias(true);
	      paint.setFakeBoldText(true);
	      //Paint backPaint = new Paint();
	     // backPaint.setARGB(175, 50, 50, 50);
	      //backPaint.setAntiAlias(true);
	      RectF backRect = new RectF(point.x + 2 + mRadius,
	    		  point.y - 3*mRadius,
	    		  point.x + 65, point.y + mRadius);
	      canvas.drawOval(oval, paint);
	      canvas.drawText("Your are here", point.x+mRadius, point.y, paint);
		}
	      
	      /* Toast.makeText(getBaseContext(), 
          "hello"+ "," + 
          "hiii" , Toast.LENGTH_SHORT).show(); */
	    //  canvas.drawOval(oval, paint);
	    //  canvas.drawRoundRect(backRect, 5, 5, backPaint);
	     //canvas.drawText("Here I Am",point.x + 2*mRadius, point.y,paint);
			
	      
		}
	}

}