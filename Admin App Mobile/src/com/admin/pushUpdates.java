package com.admin;

import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class pushUpdates extends Activity {
    /** Called when the activity is first created. */
    String update,updateType;
    EditText eupdate;
    Button btnPushUpdate;
    private RadioButton rbcomp;
    private RadioButton rbelec;
    private RadioButton rbgen;
   
    
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.pushupdates);
        
        eupdate = (EditText)this.findViewById(R.id.update);
        btnPushUpdate =(Button)this.findViewById(R.id.btnPushUpdate);
        rbcomp=(RadioButton)findViewById(R.id.rbcomp);
        rbelec=(RadioButton)findViewById(R.id.rbelec);
        rbgen=(RadioButton)findViewById(R.id.rbgen);
        
       
        btnPushUpdate.setOnClickListener(new OnClickListener() 
        {
            public void onClick(View view) 
            {
            	 Log.i("Pushing data","2222");
            	 update = eupdate.getText().toString();
            	 
            	 if(isValidStr(update)==0)
            	 {
		            	 Log.i("Pushed data",update);
		            	 
		            	 if(rbcomp.isChecked()==true)
		            		 {
		            		   updateType="computer";
		            		   
		            		   pushUpdate();
		            		 }
		            	 else if(rbelec.isChecked()==true)
		            		 {
		            		 updateType="electronics";
		            		 pushUpdate();
		            		 }
		            	 
		            	 else if(rbgen.isChecked()==true)
		            		 {
		            		 updateType="general";
		            		 pushUpdate();
		            		 }
		            	 
		            	 else
		            	 {
		            		 Toast.makeText(pushUpdates.this, "Select type of Update", Toast.LENGTH_LONG).show();
		            		 
		            	 }           	 
            	 }
            	 
            	 else
	       			{
	       				MessageBox("Update cannot be blank!!!!!");	
	       			}
      	    }
        });
    }
	
	 public void MessageBox(String message){   
		   	Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
		   	} 
	 private int isValidStr(String str)
	  {
		  
		  if(str.equals(""))
		     return 1;
		  
		 return 0;
	  }
	
	public void pushUpdate()
	{
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    	nameValuePairs.add(new BasicNameValuePair("update",update));
    	nameValuePairs.add(new BasicNameValuePair("type",updateType));
    	
    	
    	 Log.i("Dun","1");
	    try
	    {
	           //login.httpclient = new DefaultHttpClient();
	           HttpPost httppost = new HttpPost("http://bhavanscampusguide.spacedupon.com/sendNotification.php");
	           httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	           HttpResponse response = login.httpclient.execute(httppost);
	       
	           Toast.makeText(pushUpdates.this, "The update has been pushed !!!", Toast.LENGTH_LONG).show();
	    }
	   	
	   	catch(Exception e)
	   	{
	           Log.e("log_tag", "Error in http connection "+e.toString());
	    }
       
	}
}