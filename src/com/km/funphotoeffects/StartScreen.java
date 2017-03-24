package com.km.funphotoeffects;

import android.app.Activity;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.analytics.Tracker;
import java.io.File;

// Referenced classes of package com.km.funphotoeffects:
//            FunPhotoScreen, ApplicationController, GalleryActivity, YourCreationActivity

public class StartScreen extends Activity
{

    public static final int REQUEST_GALLERY_IMAGE = 1;
    boolean isCamera;
    boolean isGallery;

    public StartScreen()
    {
        isGallery = false;
        isCamera = false;
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
                    Intent intent1 = new Intent(this, com.km.funphotoeffects.FunPhotoScreen.class);
                    intent1.setData(Uri.fromFile(new File(s)));
                    intent1.putExtra("original_image_path", s);
                    intent1.putExtra("isGallery", true);
                    startActivity(intent1);
                    finish();
                }
            }
        	break;
        }
        super.onActivityResult(i, j, intent);
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.start);
        Tracker tracker = ((ApplicationController)getApplication()).getTracker();
        tracker.setScreenName("StartScreen");
        tracker.send((new com.google.android.gms.analytics.HitBuilders.AppViewBuilder()).build());
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
                Toast.makeText(this, getString(R.string.no_creation), 1).show();
                return;
            }
        } else
        {
            Toast.makeText(this, getString(R.string.no_creation), 1).show();
            return;
        }
    }
}
