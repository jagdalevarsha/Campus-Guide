package com.admin;

import java.io.InputStream;
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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addBook extends Activity {
	
	Button btnAddBook;
	EditText bookName,authorName,bookCategory;
	String name,author,category;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbook);
        
        btnAddBook=(Button)this.findViewById(R.id.buttonAddBook);
        bookName=(EditText)this.findViewById(R.id.txtBookName);
       authorName=(EditText)this.findViewById(R.id.txtAuthorName);
       bookCategory=(EditText)this.findViewById(R.id.txtBookCategory);
        
        
        btnAddBook.setOnClickListener(new OnClickListener() 
       	{
       		public void onClick(View view) 
       		{
       			
       			name=bookName.getText().toString();
       			if(isValidStr(name)==0)
            	{
		       			author=authorName.getText().toString();
		       			if(isValidStr(author)==0)
		            	{
			       			category=bookCategory.getText().toString();
			       			if(isValidStr(category)==0)
			            	{
				       			addBook();
				       			MessageBox("Book is added in Database");
				       			bookName.setText("");
				       			authorName.setText("");
				       			bookCategory.setText("");
			            	}
			       			else
			       			{
			       				MessageBox("Category name cannot be blank");	
			       			}
		            	}
		       			else
		       			{
		       				MessageBox("Author name cannot be blank");	
		       			}	
            	}
       			else
       			{
       				
       				MessageBox("Book name cannot be blank");	
       			}
       		}
       	});
    }
    
    private int isValidStr(String str)
	  {
		  
		  if(str.equals(""))
		     return 1;
		  
		 return 0;
	  }
    
    public void MessageBox(String message){   
	   	Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
	   	} 
    
    public void addBook()
    {
    	InputStream is = null;
	       // String result = "";
		  ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		  	nameValuePairs.add(new BasicNameValuePair("bookname",name));
		  	nameValuePairs.add(new BasicNameValuePair("authorname",author));
		  	nameValuePairs.add(new BasicNameValuePair("category",category));
		  	
		  	
		  	try
		   	 {
		           //login.httpclient = new DefaultHttpClient();
		           HttpPost httppost = new HttpPost("http://bhavanscampusguide.spacedupon.com/addbook.php");
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
}