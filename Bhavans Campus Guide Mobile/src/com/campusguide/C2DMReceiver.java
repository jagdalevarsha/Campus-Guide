package com.campusguide;

import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;


public class C2DMReceiver extends BroadcastReceiver 

{
	
	private Context context;
	String RegId,deviceId;
	SharedPreferences settings;
	String uname;
	String message;
	
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		this.context=context;
	
	Log.i("Recieve","2");
    //login.mytext.setText("Intent Received!");
    Log.i("It works ","1");
		
		this.context=context;
		
	
        if (intent.getAction().equals("com.google.android.c2dm.intent.REGISTRATION"))
        {
        	
        	handleRegistration(context, intent);
        } 
        else if (intent.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) 
        {
        	
        	handleMessage(context, intent);
        }
		
	}
	
	private void handleRegistration(Context context, Intent intent)
    {

		

		Log.i("It works ","4");
		String registration = intent.getStringExtra("registration_id"); 
        if (intent.getStringExtra("error") != null)
        {
        	Log.i("It works ",intent.getStringExtra("error"));
        	//login.mytext.setText("There was an error with your device registration!");
                 	
        	String error = intent.getStringExtra("error");
        	
        		    
        	if(error.equalsIgnoreCase("SERVICE_NOT_AVAILABLE"))
        	{
        		Log.i("It works ","SERVICE_NOT_AVAILABLE");
        		//login.mytext.setText( "SERVICE_NOT_AVAILABLE");
		    	
		    }
		    else if(error.equalsIgnoreCase("ACCOUNT_MISSING"))
		    {
		    	Log.i("It works ","7");
		    	//login.mytext.setText("ACCOUNT_MISSING.......");
		    }
		    else if(error.equalsIgnoreCase("AUTHENTICATION_FAILED"))
		    {
		    	Log.i("It works ","8");
		    	//login.mytext.setText("AUTHENTICATION_FAILED");
		    }
		    else if(error.equalsIgnoreCase("TOO_MANY_REGISTRATIONS"))
		    {
		    	Log.i("It works ","9");
		    	//login.mytext.setText("TOO_MANY_REGISTRATIONS");
		    }
		    else if(error.equalsIgnoreCase("INVALID_SENDER"))
		    {
		    	Log.i("It works ","10");
		    	//login.mytext.setText("INVALID_SENDER");
		    }
		    else if(error.equalsIgnoreCase("PHONE_REGISTRATION_ERROR"))
		    {
		    	Log.i("It works ","11");
		    	//login.mytext.setText("PHONE_REGISTRATION_ERROR");
		    }
  	
            } 
        else if (intent.getStringExtra("unregistered") != null)
        {
        	Log.i("It works ","12");    	
        	//login.mytext.setText("You have been unregistered!");
        } 
        else if (registration != null) 
        {
           

            RegId = registration; 
            
            login.REGID = RegId;
            
            Log.i("reg",String.valueOf(RegId));
            //login.mytext.setText("Your registration code is: " + RegId);
            
            //login.MYARRAY.add(login.REGID);
            Log.i("It works ","13");
            deviceId = Secure.getString(context.getContentResolver(),Secure.ANDROID_ID); 
            
            //login.DEVICEID = deviceId;
            //login.MYARRAY.add(deviceId);
           // login.myArr.add(deviceId);
            Log.i("It works ","14");
			
			//login.listadapter = new ArrayAdapter(context,android.R.layout.simple_list_item_1,login.myArr.toArray());
			
			Log.i("It works ","15");
			//login.listofUpdates.setAdapter(login.listadapter);
			Log.i("It works ","16");
			
            uname = c2dmregister.uname;
            Log.i("It works ","17");
    		updateRegId();           
            
            
        }
    }
	public void updateRegId()
	{
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    	nameValuePairs.add(new BasicNameValuePair("reg_id",RegId));
    	nameValuePairs.add(new BasicNameValuePair("device_id",deviceId));
    	nameValuePairs.add(new BasicNameValuePair("uname",login.UNAME));
    	Log.i("It works ","18");

	    try
	    {
	          // HttpClient httpclient = new DefaultHttpClient();
	           HttpPost httppost = new HttpPost(login.IPADD+"uploadRegId.php");
	           Log.i("It works ","19");
	           httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	           HttpResponse response = login.httpclient.execute(httppost);
	           Log.i("It works ","20");
	    }
	   	
	   	catch(Exception e)
	   	{
	           Log.e("log_tag", "Error in http connection "+e.toString());
	    }
       
	}
    private void handleMessage(Context context, Intent intent)
    {
    	
    	//login.mytext.setText("You have been alerted!");
    	Log.e("C2DM", "Message: Fantastic!!!");
		
		Bundle extras = intent.getExtras();
		if (extras != null) 
		{
			try{
			Log.e("Message",""+extras.size());
			Log.e("Message1",""+extras.toString());
			Log.e("Message is",""+extras.getString("message"));
			MessageBox(extras.getString("message")+" is the message by "+ extras.getString("title"));
			login.MYARRAY.add(extras.getString("message"));
			
			Log.i("sss",login.MYARRAY.toString());
			
			login.storePref();
			
			Log.i("dun",login.pref.getString("0", "not"));
			
			Log.i("going to list ada",login.MYARRAY.toString());
			
			update.setList(context);
			
			Log.e("ffffff123","fffffffffff");
			addDefaultNotification();
			//utility.changeicontoNotRead();
			Log.e("ffffff","fffffffffff");
			//update.listadapter = new ArrayAdapter(update.this,android.R.layout.simple_list_item_1,login.myArr.toArray());
			message=extras.getString("message");	
			//update.listofUpdates.setAdapter(login.listadapter);
			}
			catch (Exception e)
			 
			 {
				 
				 Log.i("errorin c2dm",e.toString());
			 }
        }
    }
    
    public void MessageBox(String message)
    {   
	   	Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
	}
    
    private void addDefaultNotification()
    {
NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        
        int icon = R.drawable.icon;
        Log.e("ffffff1","fffffffffff");
        CharSequence text = message;
        CharSequence contentTitle = "New Update from Campus Guide!!!";
        CharSequence contentText = message;
        Log.e("ffffff2","fffffffffff");
        long when = System.currentTimeMillis();
        Log.e("ffffff3","fffffffffff");
        
        Intent intent = new Intent(context, update.class);
        Log.e("ffffff4","fffffffffff");
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);
        Log.e("ffffff5","fffffffffff");
        Notification notification = new Notification(icon,text,when);
        Log.e("ffffff6","fffffffffff");
        long[] vibrate = {0,100,200,300};
        Log.e("ffffff7","fffffffffff");
        notification.vibrate = vibrate;
        Log.e("ffffff8","fffffffffff");
        notification.ledARGB = Color.RED;
        Log.e("ffffff9","fffffffffff");
        notification.ledOffMS = 300;
        Log.e("ffffff10","fffffffffff");
        notification.ledOnMS = 300;
        Log.e("ffffff11","fffffffffff");
        notification.defaults |= Notification.DEFAULT_LIGHTS;
        Log.e("ffffff12","fffffffffff");
        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
        Log.e("ffffff13","fffffffffff");
        notificationManager.notify(10001, notification);
        Log.e("ffffff14","fffffffffff");
        
    }
}
