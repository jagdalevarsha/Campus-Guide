package com.campusguide;

import java.util.ArrayList;
import java.util.List;


import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;
import com.google.android.maps.MapView.LayoutParams;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShowFriends extends MapActivity{
	double a,b;
	private MapView mapView;
    private MapController mc;
    GeoPoint p;
    Canvas ca;
    boolean shadow;
    private final int mRadius = 5;
    MyPositionOverlay positionOverlay;
    String stuff;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showfriend);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.setSatellite(true);
        mapView.setClickable(true);
        Bundle bundle = getIntent().getExtras();
        stuff = bundle.getString("uname");
        String latitude = bundle.getString("latitude");
        String longitude = bundle.getString("longitude");
       // double a=f.lat; 
        //TextView tv = new TextView(this);
       // tv.setText(stuff1);
       //setContentView(tv);
        a= Double.parseDouble(latitude);
        b= Double.parseDouble(longitude);
       
        LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.zoom);  
        @SuppressWarnings("deprecation")
		View zoomView = mapView.getZoomControls(); 
 
        zoomLayout.addView(zoomView, 
            new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, 
                LayoutParams.WRAP_CONTENT)); 
        mapView.displayZoomControls(true);
        mc = mapView.getController();
        p = new GeoPoint((int)(a * 1E6),(int)(b* 1E6));
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
        
        markers.addNewItem(p, "SPCE", "SPCE");
        
        
       
	}
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	class InterestingLocations extends ItemizedOverlay <OverlayItem> {
    	//Location loc;
    	//private List<OverlayItem> locations = new ArrayList<OverlayItem>();
    	private ArrayList<OverlayItem> items;
    	Context c;
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
				Log.i("hii4","hello4");
		        
			       
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
	      canvas.drawText(stuff+" is here!", point.x+mRadius, point.y, paint);
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
