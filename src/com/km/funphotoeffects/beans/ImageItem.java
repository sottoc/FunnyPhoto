package com.km.funphotoeffects.beans;


public class ImageItem
{

    private String imagePath;
    private String title;

    public ImageItem(String s, String s1)
    {
        title = s;
        imagePath = s1;
    }

    public String getImagePath()
    {
        return imagePath;
    }

    public String getTitle()
    {
        return title;
    }

    public void setImagePath(String s)
    {
        imagePath = s;
    }

    public void setTitle(String s)
    {
        title = s;
    }
}
