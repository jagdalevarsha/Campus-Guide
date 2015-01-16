package com.campusguide;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import android.util.Log;
import android.widget.Toast;

public class c2dmregister extends Service
{
	
	static String uname;
			
	@Override
	public IBinder onBind(Intent arg0) 
	{
		return null;
	}
	
	
	@Override
	public void onStart(Intent intent, int startid) 
	{
		
		Log.i("uname ","started");
		Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
		//uname=intent.getExtras().getString(uname);
 	  // Log.i("uname ",uname);
		Intent registrationIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
        registrationIntent.putExtra("app", PendingIntent.getBroadcast(getBaseContext(), 0, new Intent(), 0)); 
        registrationIntent.putExtra("sender", "varnik.project@gmail.com");
        Log.i("WelcomeScreen","varnik.project@gmail.com");
        startService(registrationIntent);   
        Log.i("Recieve","1");  
        
	}

}
