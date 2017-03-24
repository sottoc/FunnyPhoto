package com.km.funphotoeffects;

import android.app.Application;
import com.google.android.gms.analytics.*;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ApplicationController extends Application
{

    public static final String LOG_TAG = "KM";
    private static final String PROPERTY_ID = "UA-64082526-17";
    private static ApplicationController applicationController = null;
    private Tracker tracker;

    public ApplicationController()
    {
    }

    private static void checkInstance()
    {
        if(applicationController == null)
        {
            throw new IllegalStateException("Application not yet created !");
        } else
        {
            return;
        }
    }

    public static ApplicationController getInstance()
    {
        checkInstance();
        return applicationController;
    }

    public Tracker getTracker()
    {
    	try{
	        synchronized(this){
	            Tracker tracker1;
	            if(tracker == null)
	            {
	                GoogleAnalytics googleanalytics = GoogleAnalytics.getInstance(this);
	                googleanalytics.getLogger().setLogLevel(0);
	                tracker = googleanalytics.newTracker("UA-64082526-17");
	                tracker.enableAdvertisingIdCollection(true);
	            }
	            tracker1 = tracker;
	            return tracker1;
	        }
    	}catch(Exception exception){
    		try{
    			throw exception;
    		}catch(Exception e){
    			return null;
    		}
    	}
    }

    public void onCreate()
    {
        super.onCreate();
        applicationController = this;
        com.nostra13.universalimageloader.core.ImageLoaderConfiguration imageloaderconfiguration = (new com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder(getApplicationContext())).threadPoolSize(3).threadPriority(3).memoryCacheSize(0x16e360).denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator()).build();
        ImageLoader.getInstance().init(imageloaderconfiguration);
    }

}
