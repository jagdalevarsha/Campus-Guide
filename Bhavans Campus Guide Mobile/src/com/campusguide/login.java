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
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class login extends Activity {
	
	static String IPADD="http://bhavanscampusguide.spacedupon.com/"; 
	
	Button btnSignUp,btnSignIn,btnVisitor;
	CheckBox rememberMe;
	public String uname;
	String passwd,retuname,retpasswd,bestProvider,valResult;
	EditText euname,epasswd;
	static String UNAME;
	static String REGID;
	static String DEVICEID;
	static ArrayList<String> MYARRAY;
	static String UPDATESTR;
	static SharedPreferences pref;
	static SharedPreferences.Editor editor;
	static SharedPreferences prefLoggedIn;
	static SharedPreferences.Editor editorLoggedIn;
	static HttpClient httpclient;
	public boolean LOGGEDIN;
	
 	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	
    	
    	Log.i("dun","0");
    	prefLoggedIn = getSharedPreferences("prefLoggedIn", 0);
    	Log.i("dun","-1");
    	editorLoggedIn = prefLoggedIn.edit();
    	Log.i("dun","-2");
    	getrememberMe();
    	Log.i("dun","-3");
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        MYARRAY = new ArrayList<String>();
        
        
        Log.i("dun","1");
        pref = getSharedPreferences("pref", 0);
        
		editor = pref.edit();
		
		Log.i("dun","2");
		getPref();
		Log.i("dun","3");
		
		
        btnSignUp=(Button)this.findViewById(R.id.buttonSignUp);
        btnSignIn=(Button)this.findViewById(R.id.buttonSignIn);
        btnVisitor=(Button)this.findViewById(R.id.buttonVistorView);
        euname=(EditText)this.findViewById(R.id.txtUserName);
		epasswd=(EditText)this.findViewById(R.id.txtPassword);
		rememberMe=(CheckBox)this.findViewById(R.id.chkRememberMe);
		
		
        btnSignUp.setOnClickListener(new OnClickListener() 
        {
            public void onClick(View view) 
            {
            	Intent intent =new Intent(login.this, register.class);  
            	startActivity(intent);     
      	     }
        });
        
        btnSignIn.setOnClickListener(new OnClickListener() 
        {
        	public void onClick(View view) 
            {
            	
            	uname=euname.getText().toString();
            	passwd=epasswd.getText().toString();
            	
            	if(isValidStr(uname)==0)
            	{
	            		if(isValidStr(passwd)==0)
	                	{
	            			 getServerData(); //fetching data
	                         
	                         validate();
	                	}
	                	else if (isValidStr(uname)==1)
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
        });
        
        btnVisitor.setOnClickListener(new OnClickListener() 
        {
            public void onClick(View view) 
            {
            	Intent intent =new Intent(login.this, visitorView.class);  
            	startActivity(intent);     
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
    
    static void storePref() 
    {
    	UPDATESTR = MYARRAY.toString();
    	Log.i("Converted",UPDATESTR);
    	editor.putString("0", UPDATESTR);
    	Log.i("editor",pref.getString("0", "not here"));
		editor.commit();
		Log.i("editor",pref.getString("0", "not here"));
		Log.i("dun",pref.getString("0", "not here"));
		
		
    }
    
    static void getPref()
    {
    	 String str1 = pref.getString("0","");
         Log.i("str1",str1);
         String str2;
         String strArr[];
         if(!(str1.equals("")))
         {
         	str2 = str1.substring(1,str1.length()-1);
         	Log.i("str2",str2);
         	strArr = str2.split(",");
         	Log.i("strarr",strArr.toString());
             for(int i=0;i<strArr.length;i++)
     	       MYARRAY.add(strArr[i]);            
             Log.i("MyARRAY",MYARRAY.toString());
         }   
        	
        	
    }
    
   private void validate()
   {
	   	   
	   if(valResult.equalsIgnoreCase("true"))
   	{
		 
		   
		   UNAME=euname.getText().toString();
		   
		   
		   if(rememberMe.isChecked()) 
		   {
			   putrememberMe();
			   
		   }
		   
		   Intent intent1=new Intent(login.this, c2dmregister.class);
     	  
		   startService(intent1);
		   
		   startService(new Intent(this, ServiceGPS.class));
		   
		   Intent intent =new Intent(login.this, utility.class);  
		   Bundle bundle = new Bundle();
       	   bundle.putString("username",uname);
       	   intent.putExtras(bundle);
		   startActivity(intent);  
		   
		   
   	}
   	
   	else
   	{
   		MessageBox("Illegal username or password.. Enter again");
   		euname.setText("");
   		epasswd.setText("");
   		
   	}		   
   }
    
   public void MessageBox(String message){   
   	Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
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
    	            HttpPost httppost = new HttpPost(IPADD+"login.php");
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
    
    
    private void putrememberMe()
    {
    	
    	Log.i("Staring ","remember me");
    	editorLoggedIn.putString("0", uname);
    	Log.i("editor",""+prefLoggedIn.getString("0", "false"));
		editorLoggedIn.commit();
		Log.i("editor",""+prefLoggedIn.getString("0", "false"));
		//getPref();
		Log.i("editor",""+prefLoggedIn.getString("0", "false"));
		
    	
    	
    }
    
    
    private void getrememberMe()
    {
    	
    	
    
    	Log.i("Staring ","get remember me");
        
    	String boolLogged = prefLoggedIn.getString("0", "false");
        Log.i("str1",""+ boolLogged);
        
        if(!(boolLogged.trim().equalsIgnoreCase("false")))
        {
        	 Intent intent =new Intent(login.this, utility.class);  
  		   Bundle bundle = new Bundle();
         	   bundle.putString("username",boolLogged);
         	   intent.putExtras(bundle);
  		   startActivity(intent);  
        	
        }   
    	
    }
    
    static void clearRememberMe()
    {
    	
    	
    	Log.i("Staring ","clear remember me");
    	editorLoggedIn.clear();
    	editorLoggedIn.commit();
    	String bool1 = prefLoggedIn.getString("0", "false");
    	Log.i("str1",""+ bool1);
    	
    	
    }
    
    
    static void clearPref()
    {
    	
    	Log.i("Staring ","clear pref");
    	editor.clear();
    	editor.commit();
    	String bool2 = pref.getString("0", "false");
    	Log.i("str1",""+ bool2);
    	
    }
      
    
    
   

}

