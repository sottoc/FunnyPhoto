package com.km.funphotoeffects;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.*;
import android.os.*;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.*;
import com.google.android.gms.analytics.Tracker;
import com.km.funphotoeffects.beans.AppConstant;
import com.km.funphotoeffects.beans.EffectSelectListener;
import com.km.funphotoeffects.effects.PhotoUtil;
import com.km.funphotoeffects.stickers.ImageObject;
import com.km.funphotoeffects.stickers.PreferenceUtil;
import com.km.funphotoeffects.stickers.SaveTask;
import com.km.funphotoeffects.stickers.ScalingUtilities;
import com.km.funphotoeffects.stickers.TextObject;
import com.km.funphotoeffects.utils.EffectUtils;
import com.km.funphotoeffects.utils.ProcessProgressDialog;
import com.km.funphotoeffects.utils.UtilUIEffectMenu;
import com.km.funphotoeffects.utils.Utils;
import com.km.funphotoeffects.views.CollageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import java.io.File;

// Referenced classes of package com.km.funphotoeffects:
//            GalleryActivity, ApplicationController

public class FunPhotoScreen extends Activity
    implements EffectSelectListener
{
    private class BlurOperation extends AsyncTask
    {
        protected Object doInBackground(Object aobj[])
        {
            return doInBackground((Bitmap[])aobj);
        }

        protected Void doInBackground(Bitmap abitmap[])
        {
            mBlurBitmap = EffectUtils.fastblur(abitmap[0], 25);
            mBlurBitmap = ScalingUtilities.createScaledBitmap(mBlurBitmap, mCollageView.getWidth(), mCollageView.getHeight(), com.km.funphotoeffects.stickers.ScalingUtilities.ScalingLogic.CROP);
            Utils.saveTempImage(mBlurBitmap, mBlurBitmap.getWidth(), mBlurBitmap.getHeight(), "blurbg");
            File file = new File(new File((new StringBuilder()).append(Environment.getExternalStorageDirectory()).append("/").append("dexati/temp").append("/").toString()), "blurbg.png");
            replacedBgPath = file.getAbsolutePath();
            return null;
        }

        protected void onPostExecute(Object obj)
        {
            onPostExecute((Void)obj);
        }

        protected void onPostExecute(Void void1)
        {
            processDialog.dismissDialog();
            mCollageView.setBlurImage(mBlurBitmap);
        }

        protected void onPreExecute()
        {
            processDialog = new ProcessProgressDialog(FunPhotoScreen.this);
        }

        private BlurOperation()
        {
            super();
        }

        BlurOperation(BlurOperation bluroperation)
        {
            this();
        }
    }

    private class EffectOperation extends AsyncTask
    {

        private int effect;
        private ProcessProgressDialog progressDialog;

        protected Bitmap doInBackground(String as[])
        {
            Bitmap bitmap = imageLoader.loadImageSync((new StringBuilder("file:///")).append(as[0]).toString(), options);
            switch(effect)
            {
            case 4: // '\004'
            default:
                return bitmap;

            case 1: // '\001'
                return PhotoUtil.toGray(bitmap);

            case 2: // '\002'
                return PhotoUtil.changeToOld(bitmap);

            case 3: // '\003'
                return PhotoUtil.toGreen(bitmap);
            }
        }

        protected Object doInBackground(Object aobj[])
        {
            return doInBackground((String[])aobj);
        }

        protected void onPostExecute(Bitmap bitmap)
        {
            progressDialog.dismissDialog();
            if(bitmap == null)
            	return;
            switch(mCurrentImage){
            default:
            	break;
            case 30:
            	mCollageView.setBlurImage(bitmap);
                return;
            case 40:
            	mCollageView.removeLeftPic();
                isFirstClicked = true;
                initLeftImage(bitmap);
                mCollageView.flagLeftImage = true;
                return;
            }
        }

        protected void onPostExecute(Object obj)
        {
            onPostExecute((Bitmap)obj);
        }

        protected void onPreExecute()
        {
            progressDialog = new ProcessProgressDialog(FunPhotoScreen.this);
        }

        public EffectOperation(int i)
        {
            super();
            effect = i;
        }
    }


    private static final int REQUEST_PHOTO_SELECTION = 11;
    private final int BACKGROUND_IMAGE = 30;
    private final int FOREGROUND_IMAGE = 40;
    protected BlurOperation blurTask;
    private Point displayPoint;
    private EffectOperation effectTask;
    private ImageLoader imageLoader;
    private boolean isFilterClicked;
    private boolean isFirstClicked;
    private boolean isSecondClicked;
    private ImageObject leftImage;
    public Bitmap mBlurBitmap;
    private CollageView mCollageView;
    private int mCurrentImage;
    private RelativeLayout mLayoutDoneBack;
    private LinearLayout mLayoutPhotoFilter;
    private LinearLayout mLayoutPhotoPopup;
    private RelativeLayout mLayoutSettingsTop;
    private DisplayImageOptions options;
    private String originalImagePath;
    private ProcessProgressDialog processDialog;
    private String replacedBgPath;
    private ImageObject rightImage;
    private TextObject textObject;

    public FunPhotoScreen()
    {
        mCurrentImage = 0;
        isFirstClicked = false;
        isSecondClicked = false;
    }

    private static Point getDisplaySize(Context context)
    {
        Point point = new Point();
        DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics();
        point.x = displaymetrics.widthPixels;
        point.y = displaymetrics.heightPixels;
        return point;
    }

    private void initFilters()
    {
        UtilUIEffectMenu.loadEffects(this, (LinearLayout)findViewById(R.id.texture_layout), this, AppConstant.Effects_IC_ARRAY);
    }

    private void initImageLoader()
    {
        imageLoader = ImageLoader.getInstance();
        com.nostra13.universalimageloader.core.DisplayImageOptions.Builder builder = (new com.nostra13.universalimageloader.core.DisplayImageOptions.Builder()).showStubImage(0x108003f).showImageForEmptyUri(0x108003f);
        builder.considerExifParams(true);
        options = builder.build();
    }

    private void initLeftImage(Bitmap bitmap)
    {
        leftImage = new ImageObject(bitmap, getResources());
        mCollageView.flagLeftImage = true;
        mCollageView.addLeftPic(leftImage);
        Rect rect = mCollageView.getLeftRect();
        mCollageView.loadImages(this, rect.left - bitmap.getWidth() / 2, rect.top - bitmap.getHeight() / 2, null);
    }

    private void initRightImage(Bitmap bitmap)
    {
        rightImage = new ImageObject(bitmap, getResources());
        mCollageView.flagRightImage = true;
        mCollageView.addRightPic(rightImage);
        Rect rect = mCollageView.getRightRect();
        mCollageView.loadImages(this, rect.left - bitmap.getWidth() / 2, rect.top - bitmap.getHeight() / 2, null);
    }

    private void initViews()
    {
        mLayoutPhotoPopup = (LinearLayout)findViewById(R.id.layout_popup_photo);
        mLayoutPhotoFilter = (LinearLayout)findViewById(R.id.layout_popup_filter);
        mLayoutSettingsTop = (RelativeLayout)findViewById(R.id.layoutsettings);
        mLayoutDoneBack = (RelativeLayout)findViewById(R.id.layoutdoneback);
    }

    private void openGallery()
    {
        startActivityForResult(new Intent(this, com.km.funphotoeffects.GalleryActivity.class), 11);
    }

    public void initName()
    {
        int i = PreferenceUtil.getFontColor(this);
        float f = (int)(Float.parseFloat(PreferenceUtil.getFontSize(this)) * getResources().getDisplayMetrics().density);
        String s = PreferenceUtil.getName(getApplicationContext());
        if(textObject == null)
        {
            textObject = new TextObject(s, PreferenceUtil.getFontFacePath(this), (int)f, i, null, getResources(), this);
            textObject.setText(true);
            mCollageView.init(textObject);
            int j = mCollageView.getWidth() / 2;
            int k = mCollageView.getHeight() / 2;
            RectF rectf = new RectF(j, k - 50, j + 100, k);
            mCollageView.loadImages(this, rectf.centerX(), rectf.centerY(), rectf);
            return;
        } else
        {
            mCollageView.changeTextProperty(s, PreferenceUtil.getFontFacePath(this), (int)f, i);
            return;
        }
    }

    protected void onActivityResult(int i, int j, Intent intent)
    {
        super.onActivityResult(i, j, intent);
        switch(i){
        default:
        	break;
        case 11:
        	if(j == -1 && intent != null)
            {
                final String resultPath = intent.getStringExtra("original_image_path");
                if(resultPath != null)
                {
                    ImageSize imagesize = new ImageSize(mCollageView.getWidth(), mCollageView.getHeight());
                    imageLoader.loadImage((new StringBuilder("file:///")).append(resultPath).toString(), imagesize, options, new ImageLoadingListener() {
                        public void onLoadingCancelled(String s, View view)
                        {
                        }

                        public void onLoadingComplete(String s, View view, Bitmap bitmap)
                        {
                            processDialog.dismissDialog();
                            if(bitmap == null)
                            	return;
                            switch(mCurrentImage){
                            default:
                            	break;
                            case 30:
                            	replacedBgPath = resultPath;
                                blurTask = new BlurOperation(null);
                                blurTask.execute(new Bitmap[] {
                                    bitmap
                                });
                                return;
                            case 40:
                            	originalImagePath = resultPath;
                                mCollageView.getClass();
                                mCollageView.getClass();
                                Bitmap bitmap1 = ScalingUtilities.createScaledBitmap(bitmap, 1000, 1000, com.km.funphotoeffects.stickers.ScalingUtilities.ScalingLogic.FIT);
                                isFirstClicked = true;
                                initLeftImage(bitmap1);
                                mCollageView.flagLeftImage = true;
                                return;
                            }
                        }

                        public void onLoadingFailed(String s, View view, FailReason failreason)
                        {
                        }

                        public void onLoadingStarted(String s, View view)
                        {
                            processDialog = new ProcessProgressDialog(FunPhotoScreen.this);
                            switch(mCurrentImage){
                            default:
                            	break;
                            case 30:
                            	if(mBlurBitmap != null)
                                {
                                    mBlurBitmap.recycle();
                                    mBlurBitmap = null;
                                    System.gc();
                                }
                            	return;
                            case 40:
                            	if(leftImage != null)
                                {
                                    mCollageView.removeLeftPic();
                                }
                            	return;
                            }
                        }
                    });
                }
            }
        	break;
        }
        if(isFirstClicked)
        {
            isFirstClicked = false;
        }
        if(isSecondClicked)
        {
            isSecondClicked = false;
        }
    }

    public void onBackPressed()
    {
        if(mLayoutPhotoPopup.getVisibility() == 0)
        {
            mLayoutPhotoPopup.setVisibility(8);
            return;
        }
        if(mLayoutPhotoFilter.getVisibility() == 0)
        {
            mLayoutPhotoFilter.setVisibility(8);
            return;
        }
        if(isFilterClicked)
        {
            findViewById(R.id.imgViewBack).performClick();
            return;
        }
        super.onBackPressed();
    }

    public void onClick(View view)
    {
        switch(view.getId()){
        default:case R.id.layoutdoneback:case R.id.collageView:case R.id.layout_popup_photo:case R.id.layout_popup_filter:
        	return;
        case R.id.imgViewGallery:
        	mLayoutPhotoFilter.setVisibility(8);
            if(mLayoutPhotoPopup.getVisibility() == 0)
                mLayoutPhotoPopup.setVisibility(8);
            else
                mLayoutPhotoPopup.setVisibility(0);
            return;
        case R.id.imgViewSetting:
        	mLayoutPhotoPopup.setVisibility(8);
            if(mLayoutPhotoFilter.getVisibility() == 0)
                mLayoutPhotoFilter.setVisibility(8);
            else
                mLayoutPhotoFilter.setVisibility(0);
            return;
        case R.id.imgViewSave:
        	if(mCollageView.isCollageCreated())
            {
                (new SaveTask(this, mCollageView.getBitmap())).execute(new Void[0]);
                return;
            }
            if(!mCollageView.isCollageCreated())
            {
                Toast.makeText(this, "Please Add picture on frame", 0).show();
            }
            return;
        case R.id.imgViewBack:
        	isFilterClicked = false;
            switch(mCurrentImage){
            default:
            	break;
            case 30:
            	ImageSize imagesize = new ImageSize(mCollageView.getWidth(), mCollageView.getHeight());
                imageLoader.loadImage((new StringBuilder("file:///")).append(replacedBgPath).toString(), imagesize, options, new ImageLoadingListener() {
                    public void onLoadingCancelled(String s, View view1)
                    {
                    }

                    public void onLoadingComplete(String s, View view1, Bitmap bitmap)
                    {
                        processDialog.dismissDialog();
                        mCollageView.setBlurImage(bitmap);
                    }

                    public void onLoadingFailed(String s, View view1, FailReason failreason)
                    {
                    }

                    public void onLoadingStarted(String s, View view1)
                    {
                        processDialog = new ProcessProgressDialog(FunPhotoScreen.this);
                    }
                });
            	break;
            case 40:
            	mCollageView.getClass();
                mCollageView.getClass();
                ImageSize imagesize1 = new ImageSize(1000, 1000);
                imageLoader.loadImage((new StringBuilder("file:///")).append(originalImagePath).toString(), imagesize1, options, new ImageLoadingListener() {
                    public void onLoadingCancelled(String s, View view1)
                    {
                    }

                    public void onLoadingComplete(String s, View view1, Bitmap bitmap)
                    {
                        processDialog.dismissDialog();
                        mCollageView.removeLeftPic();
                        isFirstClicked = true;
                        initLeftImage(bitmap);
                        mCollageView.flagLeftImage = true;
                    }

                    public void onLoadingFailed(String s, View view1, FailReason failreason)
                    {
                    }

                    public void onLoadingStarted(String s, View view1)
                    {
                        processDialog = new ProcessProgressDialog(FunPhotoScreen.this);
                    }
                });
                break;
            }
            UtilUIEffectMenu.loadEffects(this, (LinearLayout)findViewById(R.id.texture_layout), this, AppConstant.FRAME_IC_ARRAY);
            mLayoutSettingsTop.setVisibility(0);
            mLayoutDoneBack.setVisibility(8);
            return;
        case R.id.imgViewDone:
        	isFilterClicked = false;
            UtilUIEffectMenu.loadEffects(this, (LinearLayout)findViewById(R.id.texture_layout), this, AppConstant.FRAME_IC_ARRAY);
            mLayoutSettingsTop.setVisibility(0);
            mLayoutDoneBack.setVisibility(8);
            return;
        case R.id.textView_change_background_photo:
        	isFilterClicked = false;
            mLayoutPhotoPopup.setVisibility(8);
            mCurrentImage = 30;
            openGallery();
            return;
        case R.id.textView_change_foreground_photo:
        	isFilterClicked = false;
            mLayoutPhotoPopup.setVisibility(8);
            mCurrentImage = 40;
            openGallery();
            return;
        case R.id.textView_background_filter:
        	isFilterClicked = true;
            initFilters();
            mLayoutPhotoFilter.setVisibility(8);
            mLayoutSettingsTop.setVisibility(8);
            mLayoutDoneBack.setVisibility(0);
            mCurrentImage = 30;
            return;
        case R.id.textView_foreground_filter:
        	isFilterClicked = true;
            initFilters();
            mLayoutPhotoFilter.setVisibility(8);
            mLayoutSettingsTop.setVisibility(8);
            mLayoutDoneBack.setVisibility(0);
            mCurrentImage = 40;
            return;
        }
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.activity_create_collage);
        displayPoint = getDisplaySize(this);
        initImageLoader();
        initViews();
        mCollageView = (CollageView)findViewById(R.id.collageView);
        android.view.ViewGroup.LayoutParams layoutparams = mCollageView.getLayoutParams();
        layoutparams.width = displayPoint.x;
        layoutparams.height = displayPoint.x;
        mCollageView.setLayoutParams(layoutparams);
        mCollageView.getViewTreeObserver().addOnGlobalLayoutListener(new android.view.ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout()
            {
                mCollageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                Intent intent = getIntent();
                if(intent != null)
                {
                    originalImagePath = intent.getStringExtra("original_image_path");
                    if(!TextUtils.isEmpty(originalImagePath))
                    {
                        ImageSize imagesize = new ImageSize(mCollageView.getWidth(), mCollageView.getHeight());
                        imageLoader.loadImage((new StringBuilder("file:///")).append(originalImagePath).toString(), imagesize, options, new ImageLoadingListener() {
                            public void onLoadingCancelled(String s, View view)
                            {
                            }

                            public void onLoadingComplete(String s, View view, Bitmap bitmap)
                            {
                                processDialog.dismissDialog();
                                mCollageView.getClass();
                                mCollageView.getClass();
                                Bitmap bitmap1 = ScalingUtilities.createScaledBitmap(bitmap, 1000, 1000, com.km.funphotoeffects.stickers.ScalingUtilities.ScalingLogic.FIT);
                                if(bitmap1 != null)
                                {
                                    isFirstClicked = true;
                                    initLeftImage(bitmap1);
                                    mCollageView.flagLeftImage = true;
                                }
                                if(isFirstClicked)
                                {
                                    isFirstClicked = false;
                                }
                                blurTask = new BlurOperation(null);
                                blurTask.execute(new Bitmap[] {
                                    bitmap1
                                });
                            }

                            public void onLoadingFailed(String s, View view, FailReason failreason)
                            {
                            }

                            public void onLoadingStarted(String s, View view)
                            {
                                processDialog = new ProcessProgressDialog(FunPhotoScreen.this);
                            }
                        });
                    }
                }
            }
        });
        UtilUIEffectMenu.loadEffects(this, (LinearLayout)findViewById(R.id.texture_layout), this, AppConstant.FRAME_IC_ARRAY);
        Tracker tracker = ((ApplicationController)getApplication()).getTracker();
        tracker.setScreenName("FunPhotoScreen");
        tracker.send((new com.google.android.gms.analytics.HitBuilders.AppViewBuilder()).build());
    }

    protected void onDestroy()
    {
        if(blurTask != null && !blurTask.isCancelled())
        {
            blurTask.cancel(true);
        }
        if(effectTask != null && !effectTask.isCancelled())
        {
            effectTask.cancel(true);
        }
        if(mBlurBitmap != null && !mBlurBitmap.isRecycled())
        {
            mBlurBitmap.recycle();
            mBlurBitmap = null;
            System.gc();
        }
        mCollageView.removeListener();
        imageLoader.clearDiskCache();
        imageLoader.clearMemoryCache();
        super.onDestroy();
    }

    public void onEffectSelect(int i)
    {
        switch(i)
        {
        default:
            return;

        case R.drawable.ic_frame_1_pre: 
            mCollageView.setBackground(0);
            return;

        case R.drawable.ic_frame_2_pre:
            mCollageView.setBackground(1);
            return;

        case R.drawable.ic_frame_3_pre:
            mCollageView.setBackground(2);
            return;

        case R.drawable.ic_frame_4_pre:
            mCollageView.setBackground(3);
            return;

        case R.drawable.ic_frame_5_pre:
            mCollageView.setBackground(4);
            return;

        case R.drawable.effect_blackandwhite_normal: 
            switch(mCurrentImage)
            {
            default:
                return;

            case 30: // '\036'
                effectTask = new EffectOperation(1);
                EffectOperation effectoperation5 = effectTask;
                String as5[] = new String[1];
                as5[0] = replacedBgPath;
                effectoperation5.execute(as5);
                return;

            case 40: // '('
                effectTask = new EffectOperation(1);
                break;
            }
            EffectOperation effectoperation4 = effectTask;
            String as4[] = new String[1];
            as4[0] = originalImagePath;
            effectoperation4.execute(as4);
            return;

        case R.drawable.effect_oldphoto_normal: 
            switch(mCurrentImage)
            {
            default:
                return;

            case 30: // '\036'
                effectTask = new EffectOperation(2);
                EffectOperation effectoperation3 = effectTask;
                String as3[] = new String[1];
                as3[0] = replacedBgPath;
                effectoperation3.execute(as3);
                return;

            case 40: // '('
                effectTask = new EffectOperation(2);
                break;
            }
            EffectOperation effectoperation2 = effectTask;
            String as2[] = new String[1];
            as2[0] = originalImagePath;
            effectoperation2.execute(as2);
            return;

        case R.drawable.effect_orignal_normal: 
            switch(mCurrentImage)
            {
            default:
                return;

            case 30: // '\036'
                ImageSize imagesize1 = new ImageSize(mCollageView.getWidth(), mCollageView.getHeight());
                imageLoader.loadImage((new StringBuilder("file:///")).append(replacedBgPath).toString(), imagesize1, options, new ImageLoadingListener() {
                    public void onLoadingCancelled(String s, View view)
                    {
                    }

                    public void onLoadingComplete(String s, View view, Bitmap bitmap)
                    {
                        processDialog.dismissDialog();
                        mCollageView.setBlurImage(bitmap);
                    }

                    public void onLoadingFailed(String s, View view, FailReason failreason)
                    {
                    }

                    public void onLoadingStarted(String s, View view)
                    {
                        processDialog = new ProcessProgressDialog(FunPhotoScreen.this);
                    }
                });
                return;

            case 40: // '('
                mCollageView.getClass();
                break;
            }
            mCollageView.getClass();
            ImageSize imagesize = new ImageSize(1000, 1000);
            imageLoader.loadImage((new StringBuilder("file:///")).append(originalImagePath).toString(), imagesize, options, new ImageLoadingListener() {
                public void onLoadingCancelled(String s, View view)
                {
                }

                public void onLoadingComplete(String s, View view, Bitmap bitmap)
                {
                    processDialog.dismissDialog();
                    mCollageView.removeLeftPic();
                    isFirstClicked = true;
                    initLeftImage(bitmap);
                    mCollageView.flagLeftImage = true;
                }

                public void onLoadingFailed(String s, View view, FailReason failreason)
                {
                }

                public void onLoadingStarted(String s, View view)
                {
                    processDialog = new ProcessProgressDialog(FunPhotoScreen.this);
                }
            });
            return;

        case R.drawable.effect_sepia_normal: 
            switch(mCurrentImage)
            {
            default:
                return;

            case 30: // '\036'
                effectTask = new EffectOperation(3);
                EffectOperation effectoperation1 = effectTask;
                String as1[] = new String[1];
                as1[0] = replacedBgPath;
                effectoperation1.execute(as1);
                return;

            case 40: // '('
                effectTask = new EffectOperation(3);
                break;
            }
            break;
        }
        EffectOperation effectoperation = effectTask;
        String as[] = new String[1];
        as[0] = originalImagePath;
        effectoperation.execute(as);
    }

    void removeCroppedImageFromSDCard(String s)
    {
        if(!TextUtils.isEmpty(s))
        {
            File file = new File(s);
            if(file.exists())
            {
                file.delete();
            }
        }
    }













}
