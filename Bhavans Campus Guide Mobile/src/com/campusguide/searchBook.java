package com.campusguide;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class searchBook extends TabActivity
{
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.searchbook);

	    Resources res = getResources(); 
	    TabHost tabHost = getTabHost();  
	    TabHost.TabSpec spec; 
	    Intent intent;  

	   
	    intent = new Intent().setClass(this, searchBookbyAuthor.class);

	   
	    spec = tabHost.newTabSpec("Authors").setIndicator("Authors",res.getDrawable(R.drawable.icon_tab)).setContent(intent);
	    	    tabHost.addTab(spec);

	   
	    intent = new Intent().setClass(this, searchBookbyCategory.class);
	    spec = tabHost.newTabSpec("Category").setIndicator("Category",res.getDrawable(R.drawable.icon_tab)).setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, searchBookbyTitle.class);
	    spec = tabHost.newTabSpec("Title").setIndicator("Title",res.getDrawable(R.drawable.icon_tab)).setContent(intent);
	    tabHost.addTab(spec);

	    tabHost.setCurrentTab(2);
	}

}
