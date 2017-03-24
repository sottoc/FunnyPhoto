package com.km.funphotoeffects.utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.km.funphotoeffects.R;
import com.km.funphotoeffects.beans.EffectSelectListener;

public class UtilUIEffectMenu
{

    public UtilUIEffectMenu()
    {
    }

    public static void loadEffects(Context context, LinearLayout linearlayout, final EffectSelectListener listener, int ai[])
    {
        int i = (int)context.getResources().getDimension(R.dimen.texture_view_size);
        if(linearlayout != null)
        {
            linearlayout.removeAllViewsInLayout();
        }
        int j = 0;
        do
        {
            if(j >= ai.length)
            {
                return;
            }
            android.widget.LinearLayout.LayoutParams layoutparams = new android.widget.LinearLayout.LayoutParams(i, i);
            final ImageView imageView = new ImageView(context);
            layoutparams.setMargins(20, 5, 30, 5);
            imageView.setLayoutParams(layoutparams);
            imageView.setImageResource(ai[j]);
            imageView.setId(ai[j]);
            imageView.setOnClickListener(new android.view.View.OnClickListener() {
                public void onClick(View view)
                {
                    listener.onEffectSelect(imageView.getId());
                }
            });
            linearlayout.addView(imageView);
            j++;
        } while(true);
    }
}
