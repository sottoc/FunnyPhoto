package com.km.funphotoeffects;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.LinearLayout;
import com.google.android.gms.analytics.Tracker;
import com.km.funphotoeffects.gallery.GalleryView;

// Referenced classes of package com.km.funphotoeffects:
//            ApplicationController

public class GalleryActivity extends Activity
    implements com.km.funphotoeffects.gallery.GalleryView.SetImagePath
{

    public static int finish = 0;
    private GalleryView mGalleryPhoto;
    private LinearLayout mLinearLayoutGalleryPhotos;

    public GalleryActivity()
    {
    }

    public void onBackPressed()
    {
        setResult(0);
        finish();
        super.onBackPressed();
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.gallery);
        mLinearLayoutGalleryPhotos = (LinearLayout)findViewById(R.id.layoutGalleryPhoto);
        mGalleryPhoto = new GalleryView(this, mLinearLayoutGalleryPhotos, this);
        finish = 0;
        Tracker tracker = ((ApplicationController)getApplication()).getTracker();
        tracker.setScreenName("GalleryActivity");
        tracker.send((new com.google.android.gms.analytics.HitBuilders.AppViewBuilder()).build());
    }

    public boolean onKeyDown(int i, KeyEvent keyevent)
    {
        return super.onKeyDown(i, keyevent);
    }

    protected void onResume()
    {
        mGalleryPhoto.refresh();
        if(finish == -1)
        {
            finish();
        }
        super.onResume();
    }

    public void setPath(String s)
    {
        Intent intent = new Intent();
        intent.putExtra("original_image_path", s);
        setResult(-1, intent);
        finish();
    }

}
