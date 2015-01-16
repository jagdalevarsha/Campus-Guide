package com.campusguide;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

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

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class FindFriends extends MapActivity{
	EditText txtfriend;
	String friend;
	Button submit;
	String result,retuname,visibility;
	double lat,longi;
	//private MapController mapController;
	//private MapView mapView;

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputfindfriend);
        txtfriend=(EditText)this.findViewById(R.id.txtFriendNametoSearch);
        
        submit=(Button)this.findViewById(R.id.buttonsearchFriendSubmit);
        
        submit.setOnClickListener(new OnClickListener() 
       	{
        	public void onClick(View view) 
       		{
       			friend=txtfriend.getText().toString();
       			
       			if(isValidStr(friend)==0)
            	{
       				result = chkAvailUname(friend);
       				
       				if(visibility.equalsIgnoreCase("true"))
           			{
    		       			if((result.equalsIgnoreCase(friend)))
    		       			 {
    		       				MessageBox("This username is available.....");
    		       				Intent intent =new Intent(FindFriends.this, ShowFriends.class);  
    		       				Bundle bundle = new Bundle();
    		                	bundle.putString("uname",result);
    		                	bundle.putString("latitude",lat+"");
    		                	bundle.putString("longitude",longi+"");
    		                	intent.putExtras(bundle);
    		                	startActivity(intent);    
    		       				//Intent intent =new Intent(FindFriends.this, ShowFriend.class);  
    		                	//startActivity(intent);    
    		       				//ShowFriend s= new ShowFriend();
    		       				//s.plotFriend((int)lat,(int)longi);
    		       			 }
    		       			else
    		       			{
    		       				MessageBox("This username is not available.....Please try a different username");
    		           			txtfriend.setText("");
    		       				
    		       				//plot();
    		       				//MessageBox("This username is available.....");
    		       			}
           			}
           			else
           			{
           					MessageBox("this friend is invisible....");
           			}	
            	}
            	else if (isValidStr(friend)==1)
            	{
            		MessageBox("Please enter a user name");		
            	}
            	else
            	{
            		MessageBox("No special characters should be used for username");
            		txtfriend.setText("");
            	}
       			
       			
       			
       			
       		}
       	});
        
	}

	
	private int isValidStr(String str)
	  {
		  
		  if(str.equals(""))
		     return 1;
		  
		  else 
		  {
			      Pattern p = Pattern.compile("^[A-Za-z0-9]+$"); 
			       
			           if(!(p.matcher(str).matches()))
			           {
			        	return 2;   			        	   
			           }	          
			  
		  }
		  
		  return 0;
	  }
	
	
	

	  String chkAvailUname(String str)
	  {
		  
		  InputStream is = null;
		  retuname="";
 	   String res = "";
 	    
 	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
 	    nameValuePairs.add(new BasicNameValuePair("uname",str));

 	    //http post
 	    try{
 	            //HttpClient httpclient = new DefaultHttpClient();
 	            HttpPost httppost = new HttpPost(login.IPADD+"chkfriend.php");
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
 	                    retuname = json_data.getString("uname"); 
 	                    lat = json_data.getDouble("latitude");
 	                    longi = json_data.getDouble("longitude");
 	                    visibility=json_data.getString("visibility");

 	                    
 	            }
 	    }}catch(JSONException e){
 	            Log.e("log_tag", "Error parsing data "+e.toString());
 	    }
 	    
		  return retuname;
		  
		  
		  
		  
		  
	  }
	  
	  
	  /*void plot()
	  {
		  setContentView(R.layout.map);
		  GeoPoint p = new GeoPoint((int)(lat * 1E6),(int)(longi * 1E6));
		  //mapController.animateTo(p);
		  mapController.setCenter(p);
		  mapController.setZoom(16);                
          mapView.invalidate();
	  }*/
	  
	  
	  public void MessageBox(String message){   
		   	Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
		   	} 
	  
	  
	  
	  
	  
	  
	  
	  
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
