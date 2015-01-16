package com.campusguide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class utility extends Activity 

{
	ImageButton btnmap,btnfriend,btnbook,btnaskdir,btnsettings,btnupdate;
	Button btnLogOut;
	TextView txtUser;
	
	String username;
	@Override
	    public void onCreate(Bundle savedInstanceState) {
		Bundle bundle = getIntent().getExtras();
		username = bundle.getString("username");
	    	super.onCreate(savedInstanceState);
	        setContentView(R.layout.utility);
	        btnmap=(ImageButton)this.findViewById(R.id.map);
	        btnfriend= (ImageButton)this.findViewById(R.id.friend);
	        btnbook= (ImageButton)this.findViewById(R.id.book);
	        btnaskdir= (ImageButton)this.findViewById(R.id.direction);
	        btnsettings= (ImageButton)this.findViewById(R.id.settings);
	        btnupdate=(ImageButton)this.findViewById(R.id.update);
	        btnLogOut=(Button)this.findViewById(R.id.btnLogOut);
	        txtUser = (TextView)this.findViewById(R.id.txtUser);
	        
	        txtUser.setText("Hi "+username);
	        
	    	btnmap.setOnClickListener(new OnClickListener() 
	        {
	            public void onClick(View view) 
	            {
	            	Intent intent =new Intent(utility.this, viewMap.class); 
	            	 Bundle bundle = new Bundle();
	            	bundle.putString("username",username);
	            	intent.putExtras(bundle);
	               	startActivity(intent);  
	            }
	        }
	    	);
	    	
	    	btnfriend.setOnClickListener(new OnClickListener() 
	        {
	            public void onClick(View view) 
	            {
	            	Intent intent =new Intent(utility.this, FindFriends.class);  
	               	startActivity(intent);  
	            }
	        }
	    	);
	        
	    	btnbook.setOnClickListener(new OnClickListener() 
	        {
	            public void onClick(View view) 
	            {
	            	Intent intent =new Intent(utility.this, searchBook.class);  
	               	startActivity(intent);  
	            }
	        }
	    	);
	    	
	    	btnaskdir.setOnClickListener(new OnClickListener() 
	        {
	            public void onClick(View view) 
	            {
	            	Intent intent =new Intent(utility.this, askme1.class);  
	            	Bundle bundle = new Bundle();
	            	bundle.putString("username",username);
	            	intent.putExtras(bundle);
	               	startActivity(intent);  
	            }
	        }
	    	);
	    	
	    	
	    	btnsettings.setOnClickListener(new OnClickListener() 
	        {
	            public void onClick(View view) 
	            {
	            	Intent intent =new Intent(utility.this, Settings.class);  
	            	Bundle bundle = new Bundle();
	            	bundle.putString("username",username);
	            	intent.putExtras(bundle);
	               	startActivity(intent);  
	            }
	        }
	    	);
	    	
	    	btnupdate.setOnClickListener(new OnClickListener() 
	        {
	            public void onClick(View view) 
	            {
	            	Intent intent =new Intent(utility.this, update.class);  
	            	startActivity(intent);  
	            }
	        }
	    	);
	    	
	    	btnLogOut.setOnClickListener(new OnClickListener() 
	        {
	            public void onClick(View view) 
	            {
	            	
	            	login.clearPref();
	            	 login.clearRememberMe();
	            	Intent intent =new Intent(utility.this, login.class);  
	            	startActivity(intent);  
	            }
	        }
	    	);
	    	
	    	
	    }
}

