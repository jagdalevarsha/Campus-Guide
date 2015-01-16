package com.campusguide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Pattern;



public class register extends Activity

{
	  Button btnSubmit,btnChkavail;
	  EditText enewname,enewpasswd,enewuname,enewrepasswd;
	  CheckBox ecomp,etronix,egeneral;
	  String newname,newpasswd,newuname,newrepasswd,newunameavail;
	  boolean comp,tronix,general,flag;
	  String scomp,stronix,sgeneral,result,retuname;
	  int i;
	  
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    	
	    	super.onCreate(savedInstanceState);
	        setContentView(R.layout.register);
	        
	        btnSubmit=(Button)this.findViewById(R.id.buttonSubmit);
	        enewname=(EditText)this.findViewById(R.id.txtNewName);
	        enewuname=(EditText)this.findViewById(R.id.txtNewUserName);
	        enewpasswd=(EditText)this.findViewById(R.id.txtNewPassword);
	        enewrepasswd=(EditText)this.findViewById(R.id.txtNewRePassword);
	        ecomp= (CheckBox)this.findViewById(R.id.chkComputer);
	        etronix= (CheckBox)this.findViewById(R.id.chkElectronics);
	        egeneral= (CheckBox)this.findViewById(R.id.chkGeneral);
	        
	       btnChkavail= (Button)this.findViewById(R.id.buttonCheckAvail);
	       
	       btnChkavail.setOnClickListener(new OnClickListener() 
       	{
       		public void onClick(View view) 
       		{
       			newunameavail=enewuname.getText().toString();
       			result = chkAvailUname(newunameavail);
       			
       			if((result.equalsIgnoreCase(newunameavail)))
       			 {
       			 MessageBox("This username is not available.....Please try a different username");
       			enewuname.setText("");
       			 }
       			else
       			{
       				MessageBox("This username is available.....");
       			}
       		}
       	});
	        
	        	btnSubmit.setOnClickListener(new OnClickListener() 
	        	{
	        		public void onClick(View view) 
	        		{
	            	
		            	newname=enewname.getText().toString();
		            	
		            	Log.i(newname,"name");
		            	
		            	Log.i(""+isValidStr(newname),"name");
		            	
		            	if(isValidStr(newname)==0)
		            	{
		            		
		            		            newuname=enewuname.getText().toString();
						            	
		            		            Log.i(""+isValidStr(newuname),"uname");
		            		            
				            			if(isValidStr(newuname)==0)
				            			{
						            			 newpasswd=enewpasswd.getText().toString();
										         
					            		         Log.i(""+isValidStr(newpasswd),"pass");
						            			 if(isValidStr(newpasswd)==0)
						            			 {
						            					newrepasswd=enewrepasswd.getText().toString();
												        
						            		            Log.i(""+isValidStr(newrepasswd),"repass");
						            					 if(isValidStr(newrepasswd)==0)
								            			 {
						            							comp=ecomp.isChecked();
												            	tronix=etronix.isChecked();
												            	general=egeneral.isChecked();
												            	
												            	scomp = ""+comp;//new Boolean(comp).toString();
												            	stronix = new Boolean(tronix).toString();
												            	sgeneral = new Boolean(general).toString();
												            	
												            	flag=validatePassword();
												            	
												            	if(flag)
												            	{
									            		            Log.i("flag",""+flag);
												            	    putServerData();
									            		            Log.i("dun","1");
												            	    Intent intent =new Intent(register.this, login.class);
									            		            Log.i("dun","2");
												            	    startActivity(intent);     
									            		            Log.i("dun","3");
												            	}
								            			 }
						            					 else if (isValidStr(newrepasswd)==1)
									            			{
											            		MessageBox("Retype the password correctly");		            		
											            	}
						            					 else
											            	{
											            		MessageBox("No special characters should be used for password");
											            		enewrepasswd.setText("");
											            	}
						            			 }
						            			 else if (isValidStr(newpasswd)==1)
							            			{
									            		MessageBox("Password cannot be blank......Please enter a password");		            		
									            	}
						            			 
						            			 else
									            	{
									            		MessageBox("No special characters should be used for password");
									            		enewpasswd.setText("");
									            	}
						            	}
				            			
				            			else if (isValidStr(newuname)==1)
				            			{
						            		MessageBox("Please enter a user name");		            		
						            	}
				            			else
						            	{
						            		MessageBox("No special characters should be used for username");
						            		enewuname.setText("");
						            	}

		            	}
		            	
		            	else if (isValidStr(newname)==1)
		            	{
		            		MessageBox("Please enter the name of user");
		            		
		            	}
		            	
		            	else
		            	{
		            		MessageBox("No special characters should be used for name of user");
		            		enewname.setText("");
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
	  
	  
	  private boolean validatePassword()
	   {
		   
		   if(!(newpasswd.equalsIgnoreCase(newrepasswd)))
	   	     {
			   MessageBox("Password is not same.... Enter again");
			  
			   enewpasswd.setText("");
		   	   enewrepasswd.setText("");
			  
			   return false;
		   		
	   	      }
		  
		   else
		       return true;
	     		
	   	}		   
	  
	  public void MessageBox(String message){   
		   	Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
		   	} 
	  
	  
	  private void putServerData()
	  {
		  InputStream is = null;
	      
		  ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
      	nameValuePairs.add(new BasicNameValuePair("uname",newuname));
      	nameValuePairs.add(new BasicNameValuePair("name",newname));
      	nameValuePairs.add(new BasicNameValuePair("passwd",newpasswd));
      	
      	nameValuePairs.add(new BasicNameValuePair("computer",scomp));
      	nameValuePairs.add(new BasicNameValuePair("electronics",stronix));
      	nameValuePairs.add(new BasicNameValuePair("general",sgeneral));
      	
      	Log.i("Dun",nameValuePairs.toString());
      	
      	try
	   	 {
	           HttpClient httpclientregister = new DefaultHttpClient();
	           Log.i("Dun",nameValuePairs.toString());
	           HttpPost httppost = new HttpPost(login.IPADD+"register.php");
	           Log.i("Dun",nameValuePairs.toString());
	           httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	           Log.i("Dun",nameValuePairs.toString());
	           HttpResponse response = httpclientregister.execute(httppost);
	           Log.i("Dun",nameValuePairs.toString());
	           HttpEntity entity = response.getEntity();
	           Log.i("Dun",nameValuePairs.toString());
	           is = entity.getContent();
	           Log.i("Dun",nameValuePairs.toString());
	            
	       }
	   	
	   	catch(Exception e)
	   	{
	           Log.e("log_tag", "Error in http connection "+e.toString());
	    }
       
	   	
      	
	  }
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  String chkAvailUname(String str)
	  {
		  
		  InputStream is = null;
		  retuname="";
   	   String res = "";
   	    
   	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
   	    nameValuePairs.add(new BasicNameValuePair("uname",str));

   	   
   	    try{
   	            HttpClient httpclientregister = new DefaultHttpClient();
   	            HttpPost httppost = new HttpPost(login.IPADD+"chkAvailUname.php");
   	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
   	            HttpResponse response = httpclientregister.execute(httppost);
   	            HttpEntity entity = response.getEntity();
   	            is = entity.getContent();

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
   	            res=sb.toString();
   	    }catch(Exception e){
   	            Log.e("log_tag", "Error converting result "+e.toString());
   	    }
   	   
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
   	                    
   	                    retuname = json_data.getString("uname");      
   	                    
   	            }
   	    }}catch(JSONException e){
   	            Log.e("log_tag", "Error parsing data "+e.toString());
   	    }
   	    
		  return retuname;
		  
		  
		  
		  
		  
	  }
	  
	  
	  
	  
	  
	  
	  
	  

}


