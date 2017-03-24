package com.km.funphotoeffects.stickers;

import com.km.funphotoeffects.R;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil
{

    public static final String BACKGROUND_INDEX = "background_index";
    public static final String FONT_COLOR = "FONT_COLOR";
    public static final String FONT_FACE_NAME = "FONT_FACE_NAME";
    public static final String FONT_FACE_PATH = "FONT_FACE_PATH";
    public static final String FONT_FACE_SPINNER_POSITION = "FONT_FACE_SPINNER_POSITION";
    public static final String FONT_SIZE = "font_size";
    public static final String FONT_SIZE_SPINNER_POSITION = "FONT_SIZE_SPINNER_POSITION";
    public static final String NAME = "name";

    public PreferenceUtil()
    {
    }

    public static int getBackgroundIndex(Context context)
    {
        return context.getSharedPreferences(context.getString(R.string.app_name), 0).getInt("background_index", 0);
    }

    public static int getFontColor(Context context)
    {
        return context.getSharedPreferences(context.getString(R.string.app_name), 0).getInt("FONT_COLOR", 0xff000000);
    }

    public static String getFontFaceName(Context context)
    {
        return context.getSharedPreferences(context.getString(R.string.app_name), 0).getString("FONT_FACE_NAME", "");
    }

    public static String getFontFacePath(Context context)
    {
        return context.getSharedPreferences(context.getString(R.string.app_name), 0).getString("FONT_FACE_PATH", "");
    }

    public static int getFontFaceSpinnerPosition(Context context)
    {
        return context.getSharedPreferences(context.getString(R.string.app_name), 0).getInt("FONT_FACE_SPINNER_POSITION", 0);
    }

    public static String getFontSize(Context context)
    {
        return context.getSharedPreferences(context.getString(R.string.app_name), 0).getString("font_size", "12");
    }

    public static int getFontSizeSpinnerPosition(Context context)
    {
        return context.getSharedPreferences(context.getString(R.string.app_name), 0).getInt("FONT_SIZE_SPINNER_POSITION", 0);
    }

    public static String getName(Context context)
    {
        return context.getSharedPreferences(context.getString(R.string.app_name), 0).getString("name", "");
    }

    public static void setBackgroundIndex(Context context, int i)
    {
        android.content.SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.app_name), 0).edit();
        editor.putInt("background_index", i);
        editor.commit();
    }

    public static void setFontColor(Context context, int i)
    {
        android.content.SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.app_name), 0).edit();
        editor.putInt("FONT_COLOR", i);
        editor.commit();
    }

    public static void setFontFaceName(Context context, String s)
    {
        android.content.SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.app_name), 0).edit();
        editor.putString("FONT_FACE_NAME", s);
        editor.commit();
    }

    public static void setFontFacePath(Context context, String s)
    {
        android.content.SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.app_name), 0).edit();
        editor.putString("FONT_FACE_PATH", s);
        editor.commit();
    }

    public static void setFontFaceSpinnerPosition(Context context, int i)
    {
        android.content.SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.app_name), 0).edit();
        editor.putInt("FONT_FACE_SPINNER_POSITION", i);
        editor.commit();
    }

    public static void setFontSize(Context context, String s)
    {
        android.content.SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.app_name), 0).edit();
        editor.putString("font_size", s);
        editor.commit();
    }

    public static void setFontSizeSpinnerPosition(Context context, int i)
    {
        android.content.SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.app_name), 0).edit();
        editor.putInt("FONT_SIZE_SPINNER_POSITION", i);
        editor.commit();
    }

    public static void setName(Context context, String s)
    {
        android.content.SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.app_name), 0).edit();
        editor.putString("name", s);
        editor.commit();
    }
}
