package com.km.funphotoeffects.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.*;

import com.km.funphotoeffects.R;
import com.km.funphotoeffects.beans.ImageItem;
import com.km.funphotoeffects.utils.TypeFaceUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter
{
    static class ViewHolder
    {

        ImageView image;
        TextView imageTitle;
        RelativeLayout relativeLayout;

        ViewHolder()
        {
        }
    }


    private Context context;
    private ArrayList data;
    private ImageLoader imageLoader;
    private int layoutResourceId;
    ImageLoader loader;
    private DisplayImageOptions options;

    public GridViewAdapter(Context context1, int i, ArrayList arraylist)
    {
        super(context1, i, arraylist);
        data = new ArrayList();
        layoutResourceId = i;
        context = context1;
        data = arraylist;
        imageLoader = ImageLoader.getInstance();
        com.nostra13.universalimageloader.core.DisplayImageOptions.Builder builder = (new com.nostra13.universalimageloader.core.DisplayImageOptions.Builder()).showStubImage(0x108003f).showImageForEmptyUri(0x108003f);
        //builder.setVideoType(false);
        options = builder.build();
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        View view1 = view;
        ViewHolder viewholder;
        ImageItem imageitem;
        if(view1 == null)
        {
            view1 = ((Activity)context).getLayoutInflater().inflate(layoutResourceId, viewgroup, false);
            viewholder = new ViewHolder();
            viewholder.imageTitle = (TextView)view1.findViewById(R.id.text);
            viewholder.image = (ImageView)view1.findViewById(R.id.image);
            viewholder.relativeLayout = (RelativeLayout)view1.findViewById(R.id.layout_grid_item);
            TypeFaceUtil.setViewGroupTypeFace(context, viewholder.relativeLayout, "QUICKSAND-REGULAR.OTF");
            view1.setTag(viewholder);
        } else
        {
            viewholder = (ViewHolder)view1.getTag();
        }
        imageitem = (ImageItem)data.get(i);
        viewholder.imageTitle.setText(imageitem.getTitle());
        try
        {
            imageLoader.displayImage((new StringBuilder("file://")).append(imageitem.getImagePath()).toString(), viewholder.image, options, new SimpleImageLoadingListener() {
                public void onLoadingComplete(String s, View view2, Bitmap bitmap)
                {
                    super.onLoadingComplete(s, view2, bitmap);
                }
            });
        }
        catch(Exception exception) { }
        view1.setLayoutParams(new android.widget.AbsListView.LayoutParams(-1, context.getResources().getDisplayMetrics().heightPixels / 3));
        return view1;
    }
}
