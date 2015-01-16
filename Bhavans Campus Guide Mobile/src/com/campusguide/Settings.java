package com.campusguide;

import java.io.InputStream;
import java.util.ArrayList;

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
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

public class Settings extends Activity {
	RadioButton visibleYes, visibleNo,updateYes,updateNo;
	Button visibilitybtn,updateprefbtn,updatebtn;
	CheckBox ecomp,etronix,egeneral;
	String scomp,stronix,sgeneral,svyes,svno,suyes,suno;
	boolean comp,tronix,general,flag,vyes,vno,uyes,uno;
	String uname;
	CharSequence a,b;
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        
        visibleYes=(RadioButton)findViewById(R.id.visibleYes);
        visibleNo=(RadioButton)findViewById(R.id.visibleNo);
        updateYes=(RadioButton)findViewById(R.id.updateYes);
        updateNo=(RadioButton)findViewById(R.id.updateNo);
        ecomp= (CheckBox)this.findViewById(R.id.chkComputer);
        etronix= (CheckBox)this.findViewById(R.id.chkElectronics);
        egeneral= (CheckBox)this.findViewById(R.id.chkGeneral);
        visibilitybtn=(Button)findViewById(R.id.buttonVisibility);
        updateprefbtn=(Button)findViewById(R.id.buttonUpdatePref);
        updatebtn=(Button)findViewById(R.id.buttonUpdates);
        //donebtn=(Button)findViewById(R.id.buttonDone);
        Bundle bundle = getIntent().getExtras();
		uname = bundle.getString("username");
		/*
		donebtn.setOnClickListener(new OnClickListener() 
       	{
       		public void onClick(View view) 
       		{
       			MessageBox("settings done according to ur preferences...");
       			Log.i("utility",stronix);
       			Intent intent =new Intent(Settings.this, login.class);  
            	startActivity(intent);   
       		}
       	}
		);
		*/
		
		visibilitybtn.setOnClickListener(new OnClickListener() 
       	{
       		public void onClick(View view) 
       		{
       			
            	vyes=visibleYes.isChecked();
            	vno=visibleNo.isChecked();
            	
                     	
            	
            	svyes = new Boolean(vyes).toString();
            	svno = new Boolean(vno).toString();
            	
            	/*Toast.makeText(getBaseContext(), 
                        a + "," + 
                        vno , Toast.LENGTH_SHORT).show(); */
            	
            	updatevisibility();
             	//MessageBox("settings done according to ur preferences...");
            	  
       		}
       	});
		
		
		updateprefbtn.setOnClickListener(new OnClickListener() 
       	{
       		public void onClick(View view) 
       		{
       			comp=ecomp.isChecked();
            	tronix=etronix.isChecked();
            	general=egeneral.isChecked();
            	
            	
                scomp = ""+comp;//new Boolean(comp).toString();
            	stronix = new Boolean(tronix).toString();
            	sgeneral = new Boolean(general).toString();
            	Log.i(scomp,stronix);
            	      	
            	updatepreferences();
             	//MessageBox("settings done according to ur preferences...");
            	  
       		}
       	});
		
		
		updatebtn.setOnClickListener(new OnClickListener() 
       	{
       		public void onClick(View view) 
       		{
       			
            	uyes=updateYes.isChecked();
            	uno=updateNo.isChecked();
            	
            	b=updateNo.getText();
            	
                
            	suyes = new Boolean(uyes).toString();
            	suno = new Boolean(uno).toString();
            	
            	update();
             	//MessageBox("settings done according to ur preferences...");
            	  
       		}
       	});
		
	}
	
	private void update()
	{
		InputStream is = null;
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		if(b.toString().equalsIgnoreCase("No")&&uno)
		{
			
			nameValuePairs.add(new BasicNameValuePair("updates","false"));
			
		}
		else
		{
			nameValuePairs.add(new BasicNameValuePair("updates","true"));
		}
		
		nameValuePairs.add(new BasicNameValuePair("uname",uname));
		
		try
	   	 {
	          // HttpClient httpclient = new DefaultHttpClient();
	           HttpPost httppost = new HttpPost(login.IPADD+"updatesettings.php");
	           httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	           HttpResponse response = login.httpclient.execute(httppost);
	           Log.i("connection","settings");
	           HttpEntity entity = response.getEntity();
	           is = entity.getContent();
	            
	       }
	   	
	   	catch(Exception e)
	   	{
	           Log.e("log_tag", "Error in http connection "+e.toString());
	    }
	}
	
	private void updatepreferences()
	{	
		InputStream is = null;
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		nameValuePairs.add(new BasicNameValuePair("uname",uname));
		
		nameValuePairs.add(new BasicNameValuePair("computer",scomp));
      	nameValuePairs.add(new BasicNameValuePair("electronics",stronix));
      	nameValuePairs.add(new BasicNameValuePair("general",sgeneral));
      	
      	try
	   	 {
	           HttpClient httpclient = new DefaultHttpClient();
	           HttpPost httppost = new HttpPost(login.IPADD+"updateprefsettings.php");
	           Log.i("connection","update");
	           httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	           HttpResponse response = login.httpclient.execute(httppost);
	           HttpEntity entity = response.getEntity();
	           is = entity.getContent();
	            
	       }
	   	
	   	catch(Exception e)
	   	{
	           Log.e("log_tag", "Error in http connection "+e.toString());
	    }
      
	}
	
	private void updatevisibility()
	{
		InputStream is = null;
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		if(a.toString().equalsIgnoreCase("Invisible")&&vno)
		{
			MessageBox("u dont want to be visible");
			nameValuePairs.add(new BasicNameValuePair("visibility","false"));
			
		}
		else
		{
			nameValuePairs.add(new BasicNameValuePair("visibility","true"));
		}
		nameValuePairs.add(new BasicNameValuePair("uname",uname));
		
		try
	   	 {
	           //HttpClient httpclient = new DefaultHttpClient();
	           HttpPost httppost = new HttpPost(login.IPADD+"visibilitysettings.php");
	           httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	           HttpResponse response = login.httpclient.execute(httppost);
	           HttpEntity entity = response.getEntity();
	           is = entity.getContent();
	            
	       }
	   	
	   	catch(Exception e)
	   	{
	           Log.e("log_tag", "Error in http connection "+e.toString());
	    }
		
	}
	

	public void MessageBox(String message){   
	   	Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
	   	}

}
