package com.km.funphotoeffects.stickers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.Toast;

import com.km.funphotoeffects.R;
import com.km.funphotoeffects.utils.Utils;

import java.util.Calendar;

public class SaveTask extends AsyncTask
{

    private Bitmap bitmap;
    private boolean isSaved;
    private int mBitmapHeight;
    private int mBitmapWidth;
    private Activity mContext;
    private ProgressDialog mProgressDialog;

    public SaveTask(Activity activity, Bitmap bitmap1)
    {
        mContext = activity;
        mBitmapWidth = bitmap1.getWidth();
        mBitmapHeight = bitmap1.getHeight();
        bitmap = bitmap1;
        mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(activity.getString(R.string.txt_saving));
    }

    private Bitmap cropTransparentArea(Bitmap bitmap1)
    {
        int i;
        int j;
        int k;
        int l;
        int i1;
        i = bitmap1.getWidth();
        j = 0;
        k = bitmap1.getHeight();
        l = 0;
        i1 = 0;
        while(i1 < bitmap1.getWidth())
        {
        	int j1 = 0;
            while(j1 < bitmap1.getHeight())
            {
            	if(bitmap1.getPixel(i1, j1) != 0)
                {
                    if(k > j1)
                    {
                        k = j1;
                    }
                    if(l < j1)
                    {
                        l = j1;
                    }
                    if(i > i1)
                    {
                        i = i1;
                    }
                    if(j < i1)
                    {
                        j = i1;
                    }
                }
                j1++;
            }
            i1++;
        }
        int k1 = j - i;
        int l1 = l - k;
        if(k1 > 0 && l1 > 0)
        {
            bitmap1 = Bitmap.createBitmap(bitmap1, i, k, k1, l1);
        }
        return bitmap1;
    }

    protected Object doInBackground(Object aobj[])
    {
        return doInBackground((Void[])aobj);
    }

    protected Void doInBackground(Void avoid[])
    {
        if(mBitmapWidth != 0 && mBitmapHeight != 0)
        {
            bitmap = cropTransparentArea(bitmap);
            String s = (new StringBuilder(String.valueOf(mContext.getString(R.string.app_name)))).append("_").append(Calendar.getInstance().getTimeInMillis()).toString();
            isSaved = Utils.saveImage(bitmap, mBitmapWidth, mBitmapHeight, s);
        }
        return null;
    }

    protected void onPostExecute(Object obj)
    {
        onPostExecute((Void)obj);
    }

    protected void onPostExecute(Void void1)
    {
        if(mProgressDialog != null)
        {
            mProgressDialog.dismiss();
        }
        if(isSaved)
        {
            Toast.makeText(mContext, "Saving Success", 0).show();
        } else
        {
            Toast.makeText(mContext, "Saving Failed", 0).show();
        }
        super.onPostExecute(void1);
    }

    protected void onPreExecute()
    {
        if(mProgressDialog != null)
        {
            mProgressDialog.show();
        }
        super.onPreExecute();
    }
}
