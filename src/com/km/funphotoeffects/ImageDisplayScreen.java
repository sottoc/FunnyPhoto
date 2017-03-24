package com.km.funphotoeffects;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.analytics.Tracker;
import java.io.File;

// Referenced classes of package com.km.funphotoeffects:
//            ApplicationController

public class ImageDisplayScreen extends Activity
{

    private static final String TAG = "KM";
    String imagePath;
    private ImageView imageView;

    public ImageDisplayScreen()
    {
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.activity_image_display_screen);
        imageView = (ImageView)findViewById(R.id.imageview_showimage);
        imagePath = getIntent().getStringExtra("imgPath");
        imageView.setImageBitmap(BitmapFactory.decodeFile(imagePath));
        imageView.invalidate();
        Tracker tracker = ((ApplicationController)getApplication()).getTracker();
        tracker.setScreenName("ImageDisplayScreen");
        tracker.send((new com.google.android.gms.analytics.HitBuilders.AppViewBuilder()).build());
    }

    public void onDelete(View view)
    {
        (new android.app.AlertDialog.Builder(this)).setIcon(0x1080027).setTitle("Delete File ?").setMessage("Are you sure you want to delete Your Creation?. You will lose it permanently.").setPositiveButton("Yes", new android.content.DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialoginterface, int i)
            {
                if((new File(imagePath)).delete())
                {
                    Toast.makeText(ImageDisplayScreen.this, "Creation deleted.", 0).show();
                }
                finish();
            }
        }).setNegativeButton("No", null).show();
    }

    public boolean onKeyDown(int i, KeyEvent keyevent)
    {
        return super.onKeyDown(i, keyevent);
    }

    public void onShare(View view)
    {
        try
        {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("image/png");
            intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(imagePath)));
            startActivity(Intent.createChooser(intent, "My Photo"));
            return;
        }
        catch(Exception exception)
        {
            Log.v("KM", "Error sharing photo");
        }
    }
}
