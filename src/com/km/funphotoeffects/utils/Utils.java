package com.km.funphotoeffects.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import java.io.File;
import java.io.FileOutputStream;

// Referenced classes of package com.km.funphotoeffects.utils:
//            BitmapUtil

public class Utils
{

    public Utils()
    {
    }

    public static Bitmap cropShape(Bitmap bitmap, Bitmap bitmap1)
    {
        int i = bitmap1.getWidth();
        int j = bitmap1.getHeight();
        Log.e("photofilter", (new StringBuilder("photo :")).append(i).append("x").append(j).toString());
        Bitmap bitmap2 = BitmapUtil.fitToViewByRect(bitmap, i, j);
        Log.e("photofilter", (new StringBuilder("shape :")).append(bitmap2.getWidth()).append("x").append(bitmap2.getHeight()).toString());
        Bitmap bitmap3 = Bitmap.createBitmap(i, j, android.graphics.Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap3);
        Paint paint = new Paint(1);
        paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(bitmap1, 0.0F, 0.0F, null);
        canvas.drawBitmap(bitmap2, 0.0F, 0.0F, paint);
        paint.setXfermode(null);
        return bitmap3;
    }

    public static Bitmap getBitmap(int i, Resources resources)
    {
        return BitmapFactory.decodeResource(resources, i);
    }

    public static int getDeviceHeight(Context context)
    {
        Point point = new Point();
        Display display = ((WindowManager)context.getSystemService("window")).getDefaultDisplay();
        if(android.os.Build.VERSION.SDK_INT >= 11)
        {
            display.getSize(point);
            return point.y;
        } else
        {
            return display.getWidth();
        }
    }

    public static int getDeviceWidth(Context context)
    {
        Point point = new Point();
        Display display = ((WindowManager)context.getSystemService("window")).getDefaultDisplay();
        if(android.os.Build.VERSION.SDK_INT >= 11)
        {
            display.getSize(point);
            return point.x;
        } else
        {
            return display.getWidth();
        }
    }

    public static boolean saveImage(Bitmap bitmap, int i, int j, String s)
    {
        File file = new File((new StringBuilder()).append(Environment.getExternalStorageDirectory()).append("/").append("Fun Photo Effects").append("/").toString());
        if(!file.exists())
        {
            file.mkdirs();
        }
        File file1 = new File(file, (new StringBuilder(String.valueOf(s))).append(".png").toString());
        try
        {
            FileOutputStream fileoutputstream = new FileOutputStream(file1);
            bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, fileoutputstream);
            fileoutputstream.flush();
            fileoutputstream.close();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    public static String saveTempImage(Bitmap bitmap, int i, int j, String s)
    {
        File file1;
        File file = new File((new StringBuilder()).append(Environment.getExternalStorageDirectory()).append("/").append("dexati/temp").append("/").toString());
        if(!file.exists())
        {
            file.mkdirs();
        }
        file1 = new File(file, (new StringBuilder(String.valueOf(s))).append(".png").toString());
        boolean flag = true;
        try{
	        FileOutputStream fileoutputstream = new FileOutputStream(file1);
	        bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, fileoutputstream);
	        fileoutputstream.flush();
	        fileoutputstream.close();
        }catch(Exception e){
        	flag = false;
        }
        if(flag)
            return file1.getAbsolutePath();
        else
            return null;
    }
}
