package com.km.funphotoeffects;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import com.google.android.gms.analytics.Tracker;
import com.km.funphotoeffects.beans.ImageItem;
import com.km.funphotoeffects.gallery.GridViewAdapter;
import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.*;

// Referenced classes of package com.km.funphotoeffects:
//            ApplicationController, ImageDisplayScreen

public class YourCreationActivity extends Activity
{

    private static final String TAG = "KM";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MMM dd hh:mm aaa");
    private GridViewAdapter customGridAdapter;
    private GridView gridView;
    private ArrayList imageItems;

    public YourCreationActivity()
    {
        imageItems = new ArrayList();
    }

    private void getData()
    {
        File file;
        imageItems = new ArrayList();
        file = new File((new StringBuilder()).append(Environment.getExternalStorageDirectory()).append("/").append("Fun Photo Effects").toString());
        if(!file.exists())
        	return;
        File afile[];
        int i;
        afile = file.listFiles(new FilenameFilter() {
            public boolean accept(File file1, String s1)
            {
                return s1.toLowerCase().endsWith(".png") || s1.toLowerCase().endsWith(".jpg");
            }
        });
        Arrays.sort(afile, new Comparator() {
            public int compare(File file1, File file2)
            {
                return Long.valueOf(file2.lastModified()).compareTo(Long.valueOf(file1.lastModified()));
            }

            public int compare(Object obj, Object obj1)
            {
                return compare((File)obj, (File)obj1);
            }
        });
        i = 0;
        while(i < afile.length){
        	afile[i].getName().substring(0, afile[i].getName().lastIndexOf("."));
            Long long1 = Long.valueOf(afile[i].lastModified());
            String s = sdf.format(new Date(long1.longValue()));
            imageItems.add(new ImageItem(s, afile[i].getAbsolutePath()));
            i++;
        }
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_your_photo);
        Tracker tracker = ((ApplicationController)getApplication()).getTracker();
        tracker.setScreenName("YourCreationActivity");
        tracker.send((new com.google.android.gms.analytics.HitBuilders.AppViewBuilder()).build());
    }

    public boolean onKeyDown(int i, KeyEvent keyevent)
    {
        return super.onKeyDown(i, keyevent);
    }

    public void onResume()
    {
        super.onResume();
        long l = System.currentTimeMillis();
        getData();
        if(imageItems.size() < 1)
        {
            finish();
        }
        gridView = (GridView)findViewById(R.id.gridView);
        TextView textview = (TextView)findViewById(R.id.textWarn);
        Log.v("KM", (new StringBuilder("Total Items = ")).append(imageItems.size()).toString());
        if(imageItems.size() > 0)
        {
            textview.setVisibility(8);
            customGridAdapter = new GridViewAdapter(this, R.layout.row_grid, imageItems);
            gridView.setAdapter(customGridAdapter);
            gridView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView adapterview, View view, int i, long l1)
                {
                    Intent intent = new Intent(YourCreationActivity.this, com.km.funphotoeffects.ImageDisplayScreen.class);
                    intent.putExtra("imgPath", ((ImageItem)imageItems.get(i)).getImagePath());
                    startActivity(intent);
                }
            });
        } else
        {
            gridView.setVisibility(8);
            textview.setVisibility(0);
        }
        Log.v("KM", (new StringBuilder("Time Taken ms=")).append(System.currentTimeMillis() - l).toString());
    }


}
