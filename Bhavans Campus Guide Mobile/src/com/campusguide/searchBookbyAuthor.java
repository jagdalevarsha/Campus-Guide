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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class searchBookbyAuthor extends Activity 
{
	public EditText etxtAuthor;
	public ImageButton btnSearchAuthor;
	public String author,result;
	public ListView listofBooksbyAuthor;
	public ListAdapter listadapter;
	
	
	public  ArrayList<String> myArr;

	public TextView txtCount;
	
	public   String res = "";
	   
	
	public void onCreate(Bundle savedInstanceState) 
	{
			
		super.onCreate(savedInstanceState);
		
        setContentView(R.layout.searchbookbyauthor);
        
        etxtAuthor=(EditText)this.findViewById(R.id.txtAuthor);        
        btnSearchAuthor=(ImageButton)this.findViewById(R.id.buttonSearchAuthor);
        txtCount=(TextView)this.findViewById(R.id.txtCountBookbyAuthor);
        listofBooksbyAuthor=(ListView)this.findViewById(R.id.listBookbyAuthor);
        
        btnSearchAuthor.setOnClickListener(new OnClickListener() 
       	{
       		public void onClick(View view) 
       		{
       			author = etxtAuthor.getText().toString();
       			
       			if(isValidStr(author)==0)
            	{
       				result = getBookbyAuthor(author);
           			
       				Log.i("Dun",result);
           			txtCount.setText("");
           			listadapter = new ArrayAdapter(searchBookbyAuthor.this,android.R.layout.simple_list_item_1,myArr.toArray());
           			listofBooksbyAuthor.setAdapter(listadapter);
           			
           			if(!(result.equalsIgnoreCase("")))
           			 {
           				
           				
           				//len = listofBooks.length;
           				//listofBooks = myArr.toArray();
           				
           				txtCount.setText("Total Search Results: " +myArr.size());
           				//MessageBox("There is book authored by this writer"+result);
           				listadapter = new ArrayAdapter(searchBookbyAuthor.this,android.R.layout.simple_list_item_1,myArr.toArray());
           				
           				listofBooksbyAuthor.setAdapter(listadapter);
           				
           				
           				//listofBooksbyAuthor.setAdapter(new ArrayAdapter(searchbookbyauthor.this,android.R.layout.simple_list_item_1,listofBooks));     				
           			 }
           			else
           			{
           				MessageBox("There is no book authored by this writer.......Please try a different author name");
               			etxtAuthor.setText("");
           		    }
       				
            	}
            	else 
            	{
            		MessageBox("Please enter a author name");		
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
	
	
 	 public void MessageBox(String message)
 	 {   
	   	Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
	 } 
	
 	public String getBookbyAuthor(String str)
	  {
		  
		  InputStream is = null;
		  String returnstr="";
		  myArr = new ArrayList<String>();
	   
	    
	      ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	      nameValuePairs.add(new BasicNameValuePair("authorname",str));

	      try
	      {
	            //HttpClient httpclient = new DefaultHttpClient();
	            HttpPost httppost = new HttpPost(login.IPADD+"searchbyAuthor.php");
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	            HttpResponse response = login.httpclient.execute(httppost);
	            HttpEntity entity = response.getEntity();
	            is = entity.getContent();

          }
	      catch(Exception e)
	      {
	            Log.e("log_tag", "Error in http connection "+e.toString());
	      }

	      try
	      {
	            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
	            StringBuilder sb = new StringBuilder();
	            String line = null;
	            while ((line = reader.readLine()) != null) 
	            {
	                    sb.append(line + "\n");
	            }
	            is.close();
	            res=sb.toString();
	            
	      }
	      catch(Exception e)
	      {
	            Log.e("log_tag", "Error converting result "+e.toString());
	      }
	    
	      try
	      {
	      	    if(res.equalsIgnoreCase("null\n"))
	    	    {
	    	    	returnstr="";	    	    	
	    	    }
	    	    else
	    	    {
		    	    JSONArray jArray = new JSONArray(res);
		            for(int i=0;i<jArray.length();i++)
		            {
		                    JSONObject json_data = jArray.getJSONObject(i);
		                   
		                    returnstr = json_data.getString("bookname"); 
		                    myArr.add(returnstr);
		                   
	                }
	            }
	      }
	      catch(JSONException e)
	      {
	            Log.e("log_tag", "Error parsing data "+e.toString());
	      }
	    
	      return returnstr; 
        
        
        
        
        
  	  }	
}
