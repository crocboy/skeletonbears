package com.skeletonbears.filedata;

import java.io.InputStream;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.util.Log;

import com.skeletonbears.level.LevelData;

/** Loads levels from an XML file **/
public class LevelLoader 
{
	/* Static String's used for attribute identification */
	public static final String ATTRIBUTE_NAME = "name";
	public static final String ATTRIBUTE_BACKGROUND = "background";
	public static final String ATTRIBUTE_SPEED = "speed";
	
	
	
	/** Return a level for a given filename, or null if any errors occured **/
	public static LevelData ReadLevelData(String name, Context c)
	{
		try
		{
			name = name.toLowerCase(Locale.ENGLISH);
			LevelData level = new LevelData();
			
			/* XMLPullParser stuff */
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	        factory.setNamespaceAware(false);
	        XmlPullParser xpp = factory.newPullParser();
	        
	        InputStream inputStream = c.getAssets().open("levels/" + name + ".lvl");
	        xpp.setInput(inputStream, null);
	        
	        int eventType = xpp.getEventType();
	        while (eventType != XmlPullParser.END_DOCUMENT) 
	        {
	        	if(eventType == XmlPullParser.START_TAG)
	        	{
	        		if(xpp.getName().equals("level"))
	        		{
	        			level.Name = xpp.getAttributeValue(null, ATTRIBUTE_NAME);
	        			level.Background = xpp.getAttributeValue(null, ATTRIBUTE_BACKGROUND);
	        			level.Speed = Float.parseFloat(xpp.getAttributeValue(null, ATTRIBUTE_SPEED));
	        		}
	        	}
	        	eventType = xpp.next();
	        }
	        
	        Log.d("Debug", "Successfully read " + name);
			return level;
		} 
		catch(Exception e)
		{
			Log.e("Error", "ReadLevel() Error: " + String.valueOf(e));
			return null;
		}
	}
}
