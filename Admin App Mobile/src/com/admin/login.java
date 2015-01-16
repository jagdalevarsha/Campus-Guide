package com.admin;

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
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends Activity{
	Button btnSignIn;
	EditText euname,epasswd;
	public String uname;
	String passwd,valResult;
	static String UNAME;
	static HttpClient httpclient;
	//static String IPADD="http://bhavanscampusguide.spacedupon.com/"; 
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        btnSignIn=(Button)this.findViewById(R.id.buttonSignIn);
        euname=(EditText)this.findViewById(R.id.txtUserName);
		epasswd=(EditText)this.findViewById(R.id.txtPassword);
		
		
		btnSignIn.setOnClickListener(new OnClickListener() 
	    {
	    	public void onClick(View view) 
	        {
	    		Log.i("button","pressed");
	    		uname=euname.getText().toString();
            	passwd=epasswd.getText().toString();
	    		
            	
            	
            	if(uname.equalsIgnoreCase("admin"))
            	{	
		            	if(isValidStr(uname)==0)
		            	{
		            		Log.i("uname","valid");
			            		if(isValidStr(passwd)==0)
			                	{
			            			 Log.i("pawsd","valid");
			            			 
			            			 getServerData(); //fetching data
			            			 Log.i("gtsrerverdata","pressed");
			                         validate();
			            			
			                         Log.i("validate","pressed");
			                	}
			                	else if (isValidStr(passwd)==1)
			                	{
			                		MessageBox("Please enter a password");		
			                	}
			                	else
			                	{
			                		MessageBox("No special characters should be used for password");
			                		epasswd.setText("");
			                	}
		            	}
		            	else if (isValidStr(uname)==1)
		            	{
		            		MessageBox("Please enter a user name");		
		            	}
		            	else
		            	{
		            		MessageBox("No special characters should be used for username");
		            		euname.setText("");
		            	}
            	}
            	
            	else
            	{
            		
            		MessageBox("Illegal username");
            		euname.setText("");
            		
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
	
	
	private void getServerData() 
    {
    	   
    	   InputStream is = null;
  
    	  
    	    
    	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    	    nameValuePairs.add(new BasicNameValuePair("uname",uname));
    	    nameValuePairs.add(new BasicNameValuePair("passwd",passwd));

    	    Log.i(uname,passwd);
    	   
    	    
    	    try{
    	            httpclient = new DefaultHttpClient();
    	            HttpPost httppost = new HttpPost("http://bhavanscampusguide.spacedupon.com/loginAdmin.php");
    	            Log.i("dun","connection");
    	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    	            Log.i("dun1","connection1");
    	            HttpResponse response = httpclient.execute(httppost);
    	            HttpEntity entity = response.getEntity();
    	            is = entity.getContent();
    	            Log.i("dun","connection1");

    	    }catch(Exception e){
    	            Log.e("log_tag", "Error in http connection "+e.toString());
    	    }

    	   
    	    try{
    	            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
    	            StringBuilder sb = new StringBuilder();
    	            String line = null;
    	            while ((line = reader.readLine()) != null) {
    	                    sb.append(line + "\n");
    	            }
    	            is.close();
    	            valResult=sb.toString();
    	            Log.i("dun","connection2");
    	            valResult=valResult.trim();
    	            Log.i("res",valResult);
    	            
    	    }catch(Exception e){
    	            Log.e("log_tag", "Error converting result "+e.toString());
    	    }
    	    
    	    
    	    
    	}
	
	
	private void validate()
	   {
		   	   
		   if(valResult.equalsIgnoreCase("true"))
	   	{
			 Log.i("hii","im validated");
			   
			   UNAME=euname.getText().toString();
			   
			   
			   Intent intent=new Intent(login.this, adminApp.class);
	     	  Log.i("intent","intent");
			   startActivity(intent);
			   Log.i("intent","started");
			   
			   
			   
			   
			   
	   	}
	   	
	   	else
	   	{
	   		MessageBox("Illegal username or password.. Enter again");
	   		//euname.setText("");
	   		epasswd.setText("");
	   		
	   	}		   
	   }
	
	

}
