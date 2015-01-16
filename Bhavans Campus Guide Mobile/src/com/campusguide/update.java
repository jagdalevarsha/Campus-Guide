package com.campusguide;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;


public class update extends Activity
{
	static public TextView mytext;
	static public ListView listofUpdates;
	static public ListAdapter listadapter;
	
	static public String cuname;
	
	public Button btnclear; 
	
	@Override
	  public void onCreate(Bundle savedInstanceState) 
	  {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.update);
	         
	        
	        btnclear=(Button)this.findViewById(R.id.btnClear);
	        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            
            notificationManager.cancel(10001);
            
	        mytext = (TextView) findViewById(R.id.txtUpdate);
	        listofUpdates=(ListView)findViewById(R.id.listofUpdates);
	       
	        
	        setList(this);
	        
	        btnclear.setOnClickListener(new OnClickListener() 
	        {
	            public void onClick(View view) 
	            {
	            	login.MYARRAY.clear();
	            	login.storePref();
	            	listadapter = new ArrayAdapter(update.this,android.R.layout.simple_list_item_1,login.MYARRAY.toArray());
	    	        
	    			listofUpdates.setAdapter(listadapter); 
	            }
	        }
	    	);
	    }
	
	 public static void setList(Context context)
	 {
		 try{
		 
		   listadapter  = new ArrayAdapter(context.getApplicationContext(),android.R.layout.simple_list_item_1,login.MYARRAY.toArray());
	       
			listofUpdates.setAdapter(listadapter); 
			
		 }
		 
		 catch (Exception e)
		 
		 {
			 
			 Log.i("error in update",e.toString());
		 }
	 }
	 
	 
 	
}
