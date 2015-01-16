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

public class searchBookbyCategory extends Activity 
{
	public EditText etxtCategory;
	public ImageButton btnSearchCategory;
	public String category,result;
	public ListView listofBooksbyCategory;
	public ListAdapter listadapter;
	
	public  ArrayList<String> myArr;

	public TextView txtCount;
	
	public   String res = "";
	   
	
	public void onCreate(Bundle savedInstanceState) 
	{
			
		super.onCreate(savedInstanceState);
		
        setContentView(R.layout.searchbookbycategory);
        
        etxtCategory=(EditText)this.findViewById(R.id.txtCategory);        
        btnSearchCategory=(ImageButton)this.findViewById(R.id.buttonSearchCategory);
        txtCount=(TextView)this.findViewById(R.id.txtCountBookbyCategory);
        listofBooksbyCategory=(ListView)this.findViewById(R.id.listBookbyCategory);
        
        btnSearchCategory.setOnClickListener(new OnClickListener() 
       	{
       		public void onClick(View view) 
       		{
       			category = etxtCategory.getText().toString();
       			
       			 			
       			if(isValidStr(category)==0)
            	{
       				result = getBookbyCategory(category);
           			txtCount.setText("");
           			listadapter = new ArrayAdapter(searchBookbyCategory.this,android.R.layout.simple_list_item_1,myArr.toArray());
           			listofBooksbyCategory.setAdapter(listadapter);
           			
           			if(!(result.equalsIgnoreCase("")))
           			 {
           				
           				
           				//len = listofBooks.length;
           				//listofBooks = myArr.toArray();
           				
           				txtCount.setText("Total Search Results: " +myArr.size());
           				//MessageBox("There is book authored by this writer"+result);
           				listadapter = new ArrayAdapter(searchBookbyCategory.this,android.R.layout.simple_list_item_1,myArr.toArray());
           				
           				listofBooksbyCategory.setAdapter(listadapter);
           				
           				
           				//listofBooksbyAuthor.setAdapter(new ArrayAdapter(searchbookbyCategory.this,android.R.layout.simple_list_item_1,listofBooks));     				
           			 }
           			else
           			{
           				MessageBox("There is no book in this Category.......Please try a different Category");
               			etxtCategory.setText("");
           		    }
       				
            	}
            	else 
            	{
            		MessageBox("Please enter a category ");		
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
	
 	public String getBookbyCategory(String str)
	  {
		  
		  InputStream is = null;
		  String returnstr="";
		  myArr = new ArrayList<String>();
	   
	    
	      ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	      nameValuePairs.add(new BasicNameValuePair("category",str));

	      try
	      {
	           // HttpClient httpclient = new DefaultHttpClient();
	            HttpPost httppost = new HttpPost(login.IPADD+"searchbyCategory.php");
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
