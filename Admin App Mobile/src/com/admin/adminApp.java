package com.admin;

import android.app.Activity;
import android.widget.ImageButton;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;


public class adminApp extends Activity
{
	
		ImageButton btnmap,btnfriend,btnbook,btnupdate;
		
		@Override
		    public void onCreate(Bundle savedInstanceState) 
		    {
		    	
		    	super.onCreate(savedInstanceState);
		        setContentView(R.layout.utility);
		        
		        btnbook= (ImageButton)this.findViewById(R.id.book);
		        btnupdate=(ImageButton)this.findViewById(R.id.update);

		        btnbook.setOnClickListener(new OnClickListener() 
		        {
		            public void onClick(View view) 
		            {
		            	Intent intent =new Intent(adminApp.this, addBook.class);  
		               	startActivity(intent);  
		            }
		        }
		    	);
		        
		    	btnupdate.setOnClickListener(new OnClickListener() 
		        {
		            public void onClick(View view) 
		            {
		            	Intent intent =new Intent(adminApp.this, pushUpdates.class);  
		               	startActivity(intent);  
		            }
		        }
		    	);	    	
		    }
}




