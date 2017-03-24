package com.km.funphotoeffects;

import android.app.Activity;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import com.google.android.gms.analytics.Tracker;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// Referenced classes of package com.km.funphotoeffects:
//            FunPhotoScreen, ApplicationController, GalleryActivity, YourCreationActivity, 
//            StartScreen

public class MainActivity extends Activity
{

    public static final int OPEN_CAMERA_REQUEST = 2;
    public static final int REQUEST_GALLERY_IMAGE = 1;
    LinearLayout button_layout;
    boolean isCamera;
    boolean isGallery;
    private String mCurrentPhotoPath;
    long startAdTime;

    public MainActivity()
    {
        isGallery = false;
        isCamera = false;
        startAdTime = 0L;
    }

    private File createImageFile()
        throws IOException
    {
        String s = (new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)).format(new Date());
        File file = File.createTempFile((new StringBuilder("Photo_")).append(s).append("_").toString(), ".jpg", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
        mCurrentPhotoPath = file.getAbsolutePath();
        return file;
    }

    private void dispatchTakePictureIntent()
    {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if(intent.resolveActivity(getPackageManager()) == null)
        	return;
        File file;
        try{
        	File file1 = createImageFile();
        	file = file1;
        }catch(IOException ioexception){
        	file = null;
        }
        if(file != null)
        {
            intent.putExtra("output", Uri.fromFile(file));
            startActivityForResult(intent, 2);
        }
    }

    private void galleryAddPic()
    {
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile(new File(mCurrentPhotoPath)));
        sendBroadcast(intent);
    }

    public static String getPath(Context context, Uri uri)
    {
        String as[] = {
            "_data"
        };
        Cursor cursor = context.getContentResolver().query(uri, as, null, null, null);
        if(cursor == null)
        {
            return null;
        } else
        {
            int i = cursor.getColumnIndexOrThrow("_data");
            cursor.moveToFirst();
            return cursor.getString(i);
        }
    }

    protected void onActivityResult(int i, int j, Intent intent)
    {
        switch(i){
        default:
        	break;
        case 1:
        	if(j == -1 && intent != null)
            {
                String s = intent.getStringExtra("original_image_path");
                if(!TextUtils.isEmpty(s))
                {
                    Intent intent2 = new Intent(this, com.km.funphotoeffects.FunPhotoScreen.class);
                    intent2.setData(Uri.fromFile(new File(s)));
                    intent2.putExtra("original_image_path", s);
                    intent2.putExtra("isGallery", true);
                    startActivity(intent2);
                }
            }
        	break;
        case 2:
        	if(j == -1 && !TextUtils.isEmpty(mCurrentPhotoPath))
            {
                Uri uri = Uri.fromFile(new File(mCurrentPhotoPath));
                galleryAddPic();
                if(uri != null)
                {
                    Intent intent1 = new Intent(this, com.km.funphotoeffects.FunPhotoScreen.class);
                    intent1.setData(Uri.fromFile(new File(mCurrentPhotoPath)));
                    intent1.putExtra("original_image_path", mCurrentPhotoPath);
                    intent1.putExtra("isGallery", false);
                    startActivity(intent1);
                }
            } else
            {
                Toast.makeText(getApplicationContext(), "Photo is not created", 0).show();
            }
        	break;
        }
        super.onActivityResult(i, j, intent);
    }

    public void onAd1(View view)
    {
        Tracker tracker = ((ApplicationController)getApplication()).getTracker();
        tracker.send((new com.google.android.gms.analytics.HitBuilders.EventBuilder()).setCategory("CrossPromotion").setAction("App1").setLabel("App1").build());
        tracker.setScreenName("CrossPromote");
        tracker.send((new com.google.android.gms.analytics.HitBuilders.AppViewBuilder()).build());
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.km.camera3d&referrer=utm_source%3Dcrosspromotion%26utm_m" +
"edium%3Dstartpage%26utm_campaign%3Dcom.km.funphotoeffects"
));
        intent.setFlags(0x10000000);
        startActivity(intent);
    }

    public void onAd2(View view)
    {
        Tracker tracker = ((ApplicationController)getApplication()).getTracker();
        tracker.send((new com.google.android.gms.analytics.HitBuilders.EventBuilder()).setCategory("CrossPromotion").setAction("App2").setLabel("App2").build());
        tracker.setScreenName("CrossPromote");
        tracker.send((new com.google.android.gms.analytics.HitBuilders.AppViewBuilder()).build());
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.km.collagemania&referrer=utm_source%3Dcrosspromotion%26u" +
"tm_medium%3Dstartpage%26utm_campaign%3Dcom.km.funphotoeffects"
));
        intent.setFlags(0x10000000);
        startActivity(intent);
    }

    public void onAd3(View view)
    {
        Tracker tracker = ((ApplicationController)getApplication()).getTracker();
        tracker.send((new com.google.android.gms.analytics.HitBuilders.EventBuilder()).setCategory("CrossPromotion").setAction("App3").setLabel("App3").build());
        tracker.setScreenName("CrossPromote");
        tracker.send((new com.google.android.gms.analytics.HitBuilders.AppViewBuilder()).build());
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.km.fruitface&referrer=utm_source%3Dcrosspromotion%26utm_" +
"medium%3Dstartpage%26utm_campaign%3Dcom.km.funphotoeffects"
));
        intent.setFlags(0x10000000);
        startActivity(intent);
    }

    public void onAd4(View view)
    {
        Tracker tracker = ((ApplicationController)getApplication()).getTracker();
        tracker.send((new com.google.android.gms.analytics.HitBuilders.EventBuilder()).setCategory("CrossPromotion").setAction("App4").setLabel("App4").build());
        tracker.setScreenName("CrossPromote");
        tracker.send((new com.google.android.gms.analytics.HitBuilders.AppViewBuilder()).build());
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.km.raindropphotos&referrer=utm_source%3Dcrosspromotion%2" +
"6utm_medium%3Dstartpage%26utm_campaign%3Dcom.km.funphotoeffects"
));
        intent.setFlags(0x10000000);
        startActivity(intent);
    }

    public void onAd5(View view)
    {
        Tracker tracker = ((ApplicationController)getApplication()).getTracker();
        tracker.send((new com.google.android.gms.analytics.HitBuilders.EventBuilder()).setCategory("CrossPromotion").setAction("App5").setLabel("App5").build());
        tracker.setScreenName("CrossPromote");
        tracker.send((new com.google.android.gms.analytics.HitBuilders.AppViewBuilder()).build());
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.km.makemetall&referrer=utm_source%3Dcrosspromotion%26utm" +
"_medium%3Dstartpage%26utm_campaign%3Dcom.km.funphotoeffects"
));
        intent.setFlags(0x10000000);
        startActivity(intent);
    }

    public void onAd6(View view)
    {
        Tracker tracker = ((ApplicationController)getApplication()).getTracker();
        tracker.send((new com.google.android.gms.analytics.HitBuilders.EventBuilder()).setCategory("CrossPromotion").setAction("App6").setLabel("App6").build());
        tracker.setScreenName("CrossPromote");
        tracker.send((new com.google.android.gms.analytics.HitBuilders.AppViewBuilder()).build());
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.km.dreamyphotos&referrer=utm_source%3Dcrosspromotion%26u" +
"tm_medium%3Dstartpage%26utm_campaign%3Dcom.km.funphotoeffects"
));
        intent.setFlags(0x10000000);
        startActivity(intent);
    }

    public void onCamera(View view)
    {
        isGallery = false;
        isCamera = true;
        dispatchTakePictureIntent();
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        startAdTime = System.currentTimeMillis();
        Tracker tracker = ((ApplicationController)getApplication()).getTracker();
        tracker.setScreenName("MainActivity");
        tracker.send((new com.google.android.gms.analytics.HitBuilders.AppViewBuilder()).build());
        TextView textview = (TextView)findViewById(R.id.bottom_label);
        SpannableString spannablestring = new SpannableString("About Ads");
        spannablestring.setSpan(new UnderlineSpan(), 0, spannablestring.length(), 0);
        textview.setText(spannablestring);
        textview.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View view)
            {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.dexati.com/adcross.html"));
                intent.setFlags(0x10000000);
                startActivity(intent);
            }
        });
    }

    public void onGallery(View view)
    {
        startActivityForResult(new Intent(this, com.km.funphotoeffects.GalleryActivity.class), 1);
    }

    public boolean onKeyDown(int i, KeyEvent keyevent)
    {
        return super.onKeyDown(i, keyevent);
    }

    public void onViewCreation(View view)
    {
        String s = (new StringBuilder()).append(Environment.getExternalStorageDirectory()).append("/").append("Fun Photo Effects").toString();
        File file = new File(s);
        if(file.exists() && file.listFiles().length > 0)
        {
            if((new File(s)).exists())
            {
                startActivity(new Intent(this, com.km.funphotoeffects.YourCreationActivity.class));
                return;
            } else
            {
                startActivity(new Intent(this, com.km.funphotoeffects.StartScreen.class));
                return;
            }
        } else
        {
            startActivity(new Intent(this, com.km.funphotoeffects.StartScreen.class));
            return;
        }
    }
}
