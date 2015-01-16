package com.campusguide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


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

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class askme1 extends MapActivity {
	MapView mapView;
	MapController mc;
	GeoPoint p,srcGeoPoint,destGeoPoint;
	EditText src,dest;
	Button askdirbtn,currlocbtn,refresh;
	String source,destination;
	double latsource,lngsource,latdestination,lngdestination;
	MapView myMap;
	Geocoder gc;
	double lat;
    double lng;
    String uname;
    JSONObject jsrc,jdest;
    boolean flag=false;
    Canvas ca;
    boolean shadow;
    private final int mRadius = 5;
    MyPositionOverlay positionOverlay;
    String stuff;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.askdir);
        
       
        final MapView mapView = (MapView) findViewById(R.id.myMapView1);
        
        //MapView myMap = (MapView) findViewById(R.id.myMapView1);
        Bundle bundle = getIntent().getExtras();
		uname = bundle.getString("username");
		getLatitudeLongitude();
		mapView.setSatellite(true);
        mc = mapView.getController();
        Log.i("lat","lng");
        
       p = new GeoPoint((int)(lat * 1E6),(int)(lng* 1E6));
        mc.animateTo(p);
        mc.setZoom(20);
        Drawable marker=getResources().getDrawable(R.drawable.mapmarker);
        marker.setBounds(0, 0, marker.getIntrinsicWidth(),
         marker.getIntrinsicHeight());
        final List<Overlay> overlays = mapView.getOverlays();
        InterestingLocations markers = new InterestingLocations(marker);
        positionOverlay = new MyPositionOverlay();
        
        overlays.add(positionOverlay);
        overlays.add(markers);
        markers.addNewItem(p, "SPCE", "SPCE");
       
        src=(EditText)this.findViewById(R.id.Src);
		dest=(EditText)this.findViewById(R.id.Dest);
		askdirbtn=(Button)this.findViewById(R.id.askDir);
		currlocbtn=(Button)this.findViewById(R.id.sourceLoc);
		refresh=(Button)this.findViewById(R.id.refresh);
		//mc.setZoom(15); 
	    gc= new Geocoder(this);
	    
	    
	    refresh.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            	//overlays.clear();
            	mapView.getOverlays().clear();
            	mapView.postInvalidate();
            	
            	mapView.setSatellite(true);
                mc = mapView.getController();
                p = new GeoPoint((int)(lat * 1E6),(int)(lng* 1E6));
                mc.animateTo(p);
                mc.setZoom(20);
                Drawable marker=getResources().getDrawable(R.drawable.mapmarker);
                marker.setBounds(0, 0, marker.getIntrinsicWidth(),
                 marker.getIntrinsicHeight());
                final List<Overlay> overlays = mapView.getOverlays();
                InterestingLocations markers = new InterestingLocations(marker);
                positionOverlay = new MyPositionOverlay();
                
                overlays.add(positionOverlay);
                overlays.add(markers);
                markers.addNewItem(p, "SPCE", "SPCE");
                
                src.setEnabled(true);
                src.setText("");
            	dest.setText("");

            }
	    });
	    
	    currlocbtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            	src.setEnabled(false);
            	flag=true;
            	//srcGeoPoint = new GeoPoint((int)(lat*1E6),(int)(lng*1E6));
            	Toast.makeText(getBaseContext(), 
                        lat + "," + 
                        lng , Toast.LENGTH_SHORT).show();
            	}
            });
	       
	    
	    
	    askdirbtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            
	   /* Toast.makeText(getBaseContext(), 
                src + "," + 
                des , Toast.LENGTH_SHORT).show();*/
	    if(flag)
	    {
	    	destination=dest.getText().toString();
	    	String [] d=destination.split(" ");
	    	String des=destination.replaceAll(" ", "+");
	    	jdest=getLocationInfo(des);
	    	srcGeoPoint = new GeoPoint((int)(lat*1E6),(int)(lng*1E6));
	    	destGeoPoint = getGeoPoint(jdest);
	    	mc.setCenter(destGeoPoint);
        	DrawPath(srcGeoPoint, destGeoPoint, Color.GREEN, mapView);
        	mc.animateTo(srcGeoPoint);
        	mc.setZoom(20);
	    }
	    else{
	    	
	    	source =src.getText().toString();
	    	destination=dest.getText().toString();
	    	
	    	String [] s=source.split(" ");
	    	String [] d=destination.split(" ");
	    	String src=source.replaceAll(" ", "+");
	    	String des=destination.replaceAll(" ", "+");
		    jsrc=getLocationInfo(src);
		    jdest=getLocationInfo(des);
	    	srcGeoPoint = getGeoPoint(jsrc);
	    	destGeoPoint = getGeoPoint(jdest);
	    	mc.setCenter(destGeoPoint);
        	DrawPath(srcGeoPoint, destGeoPoint, Color.GREEN, mapView);
        	mc.animateTo(srcGeoPoint);
        	mc.setZoom(20);
	    }
      }
            });
    
    }
    
    
    public void MessageBox(String message){   
       	Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
       	}
    
    private void DrawPath(GeoPoint src,GeoPoint dest, int color, MapView mMapView01)
    {
    // connect to map web service
    StringBuilder urlString = new StringBuilder();
    urlString.append("http://maps.google.com/maps?f=d&hl=en");
    urlString.append("&saddr=");//from
    urlString.append( Double.toString((double)src.getLatitudeE6()/1.0E6 ));
    urlString.append(",");
    urlString.append( Double.toString((double)src.getLongitudeE6()/1.0E6 ));
    urlString.append("&daddr=");//to
    urlString.append( Double.toString((double)dest.getLatitudeE6()/1.0E6 ));
    urlString.append(",");
    urlString.append( Double.toString((double)dest.getLongitudeE6()/1.0E6 ));
    urlString.append("&ie=UTF8&0&om=0&output=kml");
    Log.d("xxx","URL="+urlString.toString());
    // get the kml (XML) doc. And parse it to get the coordinates(direction route).
    Document doc = null;
    HttpURLConnection urlConnection= null;
    URL url = null;
    try
    { 
    url = new URL(urlString.toString());
    urlConnection=(HttpURLConnection)url.openConnection();
    urlConnection.setRequestMethod("GET");
    urlConnection.setDoOutput(true);
    urlConnection.setDoInput(true);
    urlConnection.connect(); 

    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    doc = db.parse(urlConnection.getInputStream()); 

    if(doc.getElementsByTagName("GeometryCollection").getLength()>0)
    {
    //String path = doc.getElementsByTagName("GeometryCollection").item(0).getFirstChild().getFirstChild().getNodeName();
    String path = doc.getElementsByTagName("GeometryCollection").item(0).getFirstChild().getFirstChild().getFirstChild().getNodeValue() ;
    Log.d("xxx","path="+ path);
    String [] pairs = path.split(" "); 
    String[] lngLat = pairs[0].split(","); // lngLat[0]=longitude lngLat[1]=latitude lngLat[2]=height
    // src
    GeoPoint startGP = new GeoPoint((int)(Double.parseDouble(lngLat[1])*1E6),(int)(Double.parseDouble(lngLat[0])*1E6));
    mMapView01.getOverlays().add(new MyOverLay(startGP,startGP,1));
    GeoPoint gp1;
    GeoPoint gp2 = startGP; 
    for(int i=1;i<pairs.length;i++) // the last one would be crash
    {
    lngLat = pairs[i].split(",");
    gp1 = gp2;
    // watch out! For GeoPoint, first:latitude, second:longitude
    gp2 = new GeoPoint((int)(Double.parseDouble(lngLat[1])*1E6),(int)(Double.parseDouble(lngLat[0])*1E6));
    mMapView01.getOverlays().add(new MyOverLay(gp1,gp2,2,color));
    Log.d("xxx","pair:" + pairs[i]);
    }
    mMapView01.getOverlays().add(new MyOverLay(dest,dest, 3)); // use the default color
    } 
    }
    catch (MalformedURLException e)
    {
    e.printStackTrace();
    }
    catch (IOException e)
    {
    e.printStackTrace();
    }
    catch (ParserConfigurationException e)
    {
    e.printStackTrace();
    }
    catch (SAXException e)
    {
    e.printStackTrace();
    }
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
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

	    //http post
	    try{
	            //HttpClient httpclient = new DefaultHttpClient();
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
	            Log.i("Dun",res);
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
	
	public static JSONObject getLocationInfo(String address) {
		
		
		HttpGet httpGet = new HttpGet("http://maps.google.com/maps/api/geocode/json?address=" + address+ "ka&sensor=false");
		HttpClient client = new DefaultHttpClient();
		HttpResponse response;
		StringBuilder stringBuilder = new StringBuilder();
		Log.i("hii","hello");
		try {
			response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			InputStream stream = entity.getContent();
			int b;
			while ((b = stream.read()) != -1) {
				stringBuilder.append((char) b);
			}
			
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject(stringBuilder.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonObject;
	}
	
	public static GeoPoint getGeoPoint(JSONObject jsonObject) {

		 Double lon = new Double(0);
		 Double lat1 = new Double(0);

		try {

			lon = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
				.getJSONObject("geometry").getJSONObject("location")
				.getDouble("lng");

			lat1 = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
				.getJSONObject("geometry").getJSONObject("location")
				.getDouble("lat");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new GeoPoint((int) (lat1 * 1E6), (int) (lon * 1E6));

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
	      canvas.drawText("you r here", point.x+mRadius, point.y, paint);
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