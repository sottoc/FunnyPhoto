package com.km.funphotoeffects.stickers;

import android.graphics.Bitmap;
import java.io.Serializable;

public class Layer
    implements Serializable
{

    private static final long serialVersionUID = 1L;
    private int position;
    private String text;
    private Bitmap thumb;

    public Layer()
    {
    }

    public int getPosition()
    {
        return position;
    }

    public String getText()
    {
        return (new StringBuilder("Layer ")).append(1 + position).toString();
    }

    public Bitmap getThumb()
    {
        return thumb;
    }

    public void setPosition(int i)
    {
        position = i;
    }

    public void setText(String s)
    {
        text = s;
    }

    public void setThumb(Bitmap bitmap)
    {
        thumb = bitmap;
    }
}
