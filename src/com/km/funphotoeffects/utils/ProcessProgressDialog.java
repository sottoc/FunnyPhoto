package com.km.funphotoeffects.utils;

import com.km.funphotoeffects.R;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.widget.ImageView;

public class ProcessProgressDialog
{

    private Dialog dialog;
    AnimationDrawable frameAnimation;
    private Activity mContext;

    public ProcessProgressDialog(Activity activity)
    {
        mContext = activity;
        initDialog();
    }

    private void initDialog()
    {
        dialog = new Dialog(mContext, 0x103000f);
        dialog.requestWindowFeature(1);
        android.view.View view = mContext.getLayoutInflater().inflate(R.layout.process_dialog, null);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        ImageView imageview = (ImageView)dialog.findViewById(R.id.imageViewProcess);
        frameAnimation = (AnimationDrawable)imageview.getDrawable();
        frameAnimation.setCallback(imageview);
        frameAnimation.setVisible(true, true);
        frameAnimation.start();
        dialog.show();
    }

    public void dismissDialog()
    {
        if(frameAnimation != null)
        {
            frameAnimation.stop();
        }
        dialog.dismiss();
    }
}
