package com.km.funphotoeffects.views;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.km.funphotoeffects.R;
import com.km.funphotoeffects.beans.AppConstant;
import com.km.funphotoeffects.stickers.*;
import com.km.funphotoeffects.utils.BitmapUtil;

import java.util.ArrayList;

public class CollageView extends View
    implements com.km.funphotoeffects.stickers.CustomTouch.CommonListener
{
    public static interface ClickListener
    {

        public abstract void onClickListener(Object obj, com.km.funphotoeffects.stickers.CustomTouch.PointInfo pointinfo, int i);
    }


    private static final int UI_MODE_ANISOTROPIC_SCALE = 2;
    private static final int UI_MODE_ROTATE = 1;
    public final float FRAME_SIZE_HEIGHT;
    public final float FRAME_SIZE_WIDTH;
    public final float TRANSPARENT_SIZE;
    private float centerX1;
    private float centerX2;
    private float centerY1;
    private float centerY2;
    private com.km.funphotoeffects.stickers.CustomTouch.PointInfo currTouchPoint;
    private float degree1;
    private float degree2;
    public boolean flagLeftImage;
    public boolean flagRightImage;
    private boolean isCircleClip;
    private ImageObject leftImgObject;
    private Bitmap mBlurImage;
    private Bitmap mFrameBmp;
    private ArrayList mImages;
    private int mIndex;
    private ArrayList mLayers;
    private ClickListener mListener;
    private Paint mPaint;
    private boolean mShowDebugInfo;
    private float mTransparenth;
    private float mTransparentw;
    private int mUIMode;
    private CustomTouch multiTouchController;
    private Rect rect1;
    private Rect rect2;
    private RectF rectTranparentArea;
    private ImageObject rightImgObject;
    private Rect unScaledRect;

    public CollageView(Context context)
    {
        super(context);
        mLayers = new ArrayList();
        mImages = new ArrayList();
        multiTouchController = new CustomTouch(this);
        currTouchPoint = new com.km.funphotoeffects.stickers.CustomTouch.PointInfo();
        mShowDebugInfo = true;
        mUIMode = 1;
        mPaint = new Paint();
        FRAME_SIZE_HEIGHT = 720F;
        FRAME_SIZE_WIDTH = 720F;
        TRANSPARENT_SIZE = 1000F;
        mIndex = 0;
        flagLeftImage = false;
        flagRightImage = false;
        setBackgroundColor(0xff000000);
        if(android.os.Build.VERSION.SDK_INT >= 11)
        {
            setLayerType(1, null);
        }
    }

    public CollageView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        mLayers = new ArrayList();
        mImages = new ArrayList();
        multiTouchController = new CustomTouch(this);
        currTouchPoint = new com.km.funphotoeffects.stickers.CustomTouch.PointInfo();
        mShowDebugInfo = true;
        mUIMode = 1;
        mPaint = new Paint();
        FRAME_SIZE_HEIGHT = 720F;
        FRAME_SIZE_WIDTH = 720F;
        TRANSPARENT_SIZE = 1000F;
        mIndex = 0;
        flagLeftImage = false;
        flagRightImage = false;
        if(android.os.Build.VERSION.SDK_INT >= 11)
        {
            setLayerType(1, null);
        }
    }

    public CollageView(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        mLayers = new ArrayList();
        mImages = new ArrayList();
        multiTouchController = new CustomTouch(this);
        currTouchPoint = new com.km.funphotoeffects.stickers.CustomTouch.PointInfo();
        mShowDebugInfo = true;
        mUIMode = 1;
        mPaint = new Paint();
        FRAME_SIZE_HEIGHT = 720F;
        FRAME_SIZE_WIDTH = 720F;
        TRANSPARENT_SIZE = 1000F;
        mIndex = 0;
        flagLeftImage = false;
        flagRightImage = false;
        if(android.os.Build.VERSION.SDK_INT >= 11)
        {
            setLayerType(1, null);
        }
    }

    private void adjustPic()
    {
        if(leftImgObject != null)
        {
            leftImgObject.setPos(rect1.left, rect1.top, 1.0F, 1.0F, 0.0F);
        }
        if(rightImgObject != null)
        {
            rightImgObject.setPos(rect2.left, rect2.top, 1.0F, 1.0F, 0.0F);
        }
    }

    private void calculateNewRect()
    {
        int i = (rect1.left * mFrameBmp.getWidth()) / unScaledRect.left;
        int j = (rect1.right * mFrameBmp.getWidth()) / unScaledRect.left;
        int k = (rect1.top * mFrameBmp.getHeight()) / unScaledRect.top;
        int l = (rect1.bottom * mFrameBmp.getHeight()) / unScaledRect.top;
        rect1.left = i;
        rect1.right = j;
        rect1.top = k;
        rect1.bottom = l;
        mTransparentw = (mTransparentw / (float)unScaledRect.left) * (float)mFrameBmp.getWidth();
        mTransparenth = (mTransparenth / (float)unScaledRect.top) * (float)mFrameBmp.getWidth();
        int i1 = (rect2.left * mFrameBmp.getWidth()) / unScaledRect.left;
        int j1 = (rect2.right * mFrameBmp.getWidth()) / unScaledRect.left;
        int k1 = (rect2.top * mFrameBmp.getHeight()) / unScaledRect.top;
        int l1 = (rect2.bottom * mFrameBmp.getHeight()) / unScaledRect.top;
        rect2.left = i1;
        rect2.right = j1;
        rect2.top = k1;
        rect2.bottom = l1;
        rectTranparentArea = new RectF((float)rect1.left - mTransparentw / 2.0F, (float)rect1.top - mTransparenth / 2.0F, (float)rect1.right + mTransparentw / 2.0F, (float)rect1.bottom + mTransparenth / 2.0F);
    }

    private Bitmap getFramedPicture(float f, float f1)
    {
        Bitmap bitmap = Bitmap.createBitmap(mFrameBmp.getWidth(), mFrameBmp.getHeight(), android.graphics.Bitmap.Config.ARGB_8888);
        float f2 = (float)mFrameBmp.getWidth() / 720F;
        float f3 = (float)mFrameBmp.getHeight() / 720F;
        mTransparentw = f2 * mTransparentw;
        mTransparenth = f3 * mTransparenth;
        Canvas canvas = new Canvas(bitmap);
        (new Paint()).setColor(0xffff0000);
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_addphoto);
        (new Paint()).setColor(0xffff0000);
        rect1 = new Rect();
        rect1.left = (int)(f2 * centerX1) - bitmap1.getWidth() / 2;
        rect1.top = (int)(f3 * centerY1 - (float)(bitmap1.getHeight() / 2));
        rect1.right = (int)(f2 * centerX1) + bitmap1.getWidth() / 2;
        rect1.bottom = (int)(f3 * centerY1 - (float)(bitmap1.getHeight() / 2)) + bitmap1.getHeight();
        rect2 = new Rect();
        rect2.left = (int)(f2 * centerX2) - bitmap1.getWidth() / 2;
        rect2.top = (int)(f3 * centerY2 - (float)(bitmap1.getHeight() / 2));
        rect2.right = (int)(f2 * centerX2) + bitmap1.getWidth() / 2;
        rect2.bottom = (int)(f3 * centerY2 - (float)(bitmap1.getHeight() / 2)) + bitmap1.getHeight();
        if(!flagLeftImage)
        {
            canvas.drawBitmap(bitmap1, f2 * centerX1 - (float)(bitmap1.getWidth() / 2), f3 * centerY1 - (float)(bitmap1.getHeight() / 2), null);
        }
        if(!flagRightImage)
        {
            canvas.drawBitmap(bitmap1, f2 * centerX2 - (float)(bitmap1.getWidth() / 2), f3 * centerY2 - (float)(bitmap1.getHeight() / 2), null);
        }
        canvas.drawBitmap(mFrameBmp, 0.0F, 0.0F, null);
        if(bitmap1 != null)
        {
            bitmap1.recycle();
            System.gc();
        }
        return bitmap;
    }

    private void printOnScreen(Canvas canvas)
    {
        if(!currTouchPoint.isDown())
        	return;
        float af[];
        float af1[];
        float af2[];
        int i;
        int j;
        mPaint.setColor(0xff00ff00);
        mPaint.setStrokeWidth(1.0F);
        mPaint.setStyle(android.graphics.Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        af = currTouchPoint.getXs();
        af1 = currTouchPoint.getYs();
        af2 = currTouchPoint.getPressures();
        i = Math.min(currTouchPoint.getNumTouchPoints(), 2);
        j = 0;
        while(j < i){
        	canvas.drawCircle(af[j], af1[j], 2.0F * (20F * af2[j]), mPaint);
            j++;
        }
        if(i == 2)
        {
            mPaint.setStrokeWidth(2.0F);
            canvas.drawLine(af[0], af1[0], af[1], af1[1], mPaint);
        }
    }

    public void addLeftPic(ImageObject imageobject)
    {
        leftImgObject = imageobject;
        mImages.add(leftImgObject);
    }

    public void addRightPic(ImageObject imageobject)
    {
        rightImgObject = imageobject;
        mImages.add(rightImgObject);
    }

    public void changeTextProperty(String s, String s1, int i, int j)
    {
        TextObject textobject = (TextObject)mImages.get(0);
        textobject.setText(s);
        textobject.setTextColor(j);
        textobject.setTextSize(i);
        textobject.setFontFace(s1);
        if(mFrameBmp != null)
        {
            mFrameBmp.recycle();
            mFrameBmp = null;
        }
        mFrameBmp = BitmapFactory.decodeResource(getResources(), AppConstant.BACKGROUND_COLLAGE[mIndex]);
        initFrameVars();
        invalidate();
    }

    public void delete(ImageObject imageobject)
    {
        mImages.remove(imageobject);
        int i = 0;
        do
        {
            if(i >= getLayers().size())
            {
                invalidate();
                return;
            }
            if(((Layer)getLayers().get(i)).getPosition() == imageobject.getPosition())
            {
                getLayers().remove(imageobject.getPosition());
            }
            i++;
        } while(true);
    }

    public Bitmap getBitmap()
    {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), android.graphics.Bitmap.Config.ARGB_8888);
        draw(new Canvas(bitmap));
        return bitmap;
    }

    public Bitmap getBlurImage()
    {
        return mBlurImage;
    }

    public float getDistance(float f, float f1, float f2, float f3)
    {
        return (float)Math.sqrt(Math.pow(Math.abs(f - f2), 2D) + Math.pow(Math.abs(f1 - f3), 2D));
    }

    public Object getDraggableObjectAtPoint(com.km.funphotoeffects.stickers.CustomTouch.PointInfo pointinfo)
    {
        float f;
        float f1;
        int i;
        Object obj = null;
        f = pointinfo.getX();
        f1 = pointinfo.getY();
        i = -1 + mImages.size();
        while(i >= 0){
        	obj = mImages.get(i);
            if(!(obj instanceof TextObject))
            {
            	if((obj instanceof ImageObject) && ((ImageObject)obj).containsPoint(f, f1))
                	return obj;
            }else if(((TextObject)obj).containsPoint(f, f1))
            	return obj;
            i--;
        }
        return null;
    }

    public ArrayList getImages()
    {
        return mImages;
    }

    public ArrayList getLayers()
    {
        return mLayers;
    }

    public int getLayersCount()
    {
        return mLayers.size();
    }

    public Rect getLeftRect()
    {
        return rect1;
    }

    public void getPositionAndScale(Object obj, com.km.funphotoeffects.stickers.ImageObject.PositionAndScale positionandscale)
    {
        if(obj instanceof TextObject)
        {
            TextObject textobject = (TextObject)obj;
            float f5 = textobject.getCenterX();
            float f6 = textobject.getCenterY();
            boolean flag3;
            float f7;
            boolean flag4;
            float f8;
            float f9;
            int j;
            boolean flag5;
            if((2 & mUIMode) == 0)
            {
                flag3 = true;
            } else
            {
                flag3 = false;
            }
            f7 = (textobject.getScaleX() + textobject.getScaleY()) / 2.0F;
            if((2 & mUIMode) != 0)
            {
                flag4 = true;
            } else
            {
                flag4 = false;
            }
            f8 = textobject.getScaleX();
            f9 = textobject.getScaleY();
            j = 1 & mUIMode;
            flag5 = false;
            if(j != 0)
            {
                flag5 = true;
            }
            positionandscale.set(f5, f6, flag3, f7, flag4, f8, f9, flag5, textobject.getAngle());
            return;
        }
        ImageObject imageobject = (ImageObject)obj;
        float f = imageobject.getCenterX();
        float f1 = imageobject.getCenterY();
        boolean flag;
        float f2;
        boolean flag1;
        float f3;
        float f4;
        int i;
        boolean flag2;
        if((2 & mUIMode) == 0)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        f2 = (imageobject.getScaleX() + imageobject.getScaleY()) / 2.0F;
        if((2 & mUIMode) != 0)
        {
            flag1 = true;
        } else
        {
            flag1 = false;
        }
        f3 = imageobject.getScaleX();
        f4 = imageobject.getScaleY();
        i = 1 & mUIMode;
        flag2 = false;
        if(i != 0)
        {
            flag2 = true;
        }
        positionandscale.set(f, f1, flag, f2, flag1, f3, f4, flag2, imageobject.getAngle());
    }

    public Rect getRightRect()
    {
        return rect2;
    }

    public int getStickersCount()
    {
        return mImages.size();
    }

    public void init(Object obj)
    {
        Layer layer;
        int i;
        if((obj instanceof ImageObject) || (obj instanceof TextObject))
        {
            mImages.clear();
            mImages.add(obj);
            return;
        }
        layer = new Layer();
        i = 0;
        int j;
        while(i < mImages.size())
        {
        	if(!(!((ImageObject)mImages.get(i)).isSticker() && mImages.size() != i + 1)){
        		ArrayList arraylist = mImages;
                if(i != 0);
                arraylist.add(i, obj);
                break;
        	}
        	i++;
        }
        if(mImages.size() == 0)
        {
            mImages.add(obj);
        }
        if(getLayers().size() == 0)
        {
            j = 0;
        } else
        {
            j = -1 + getLayers().size();
        }
        layer.setPosition(j);
        layer.setThumb(Bitmap.createScaledBitmap(((ImageObject)obj).getBitmap(), 50, 50, true));
        getLayers().add(layer);
        return;
    }

    public void initFrameVars()
    {
        mPaint = new Paint();
        switch(mIndex){
        default:
        	break;
        case 0:
        	isCircleClip = true;
            mTransparentw = 438F;
            mTransparenth = 438F;
            centerX1 = 350F;
            centerY1 = 394F;
            degree1 = 0.0F;
            centerX2 = 0.0F;
            centerY2 = 0.0F;
            degree2 = 0.0F;
            break;
        case 1:
        	isCircleClip = false;
            mTransparentw = 379F;
            mTransparenth = 315F;
            centerX1 = 277F;
            centerY1 = 480F;
            degree1 = 0.0F;
            centerX2 = 0.0F;
            centerY2 = 0.0F;
            degree2 = 0.0F;
            break;
        case 2:
        	isCircleClip = false;
            mTransparentw = 275F;
            mTransparenth = 440F;
            centerX1 = 465F;
            centerY1 = 389F;
            degree1 = 0.0F;
            centerX2 = 0.0F;
            centerY2 = 0.0F;
            degree2 = 0.0F;
            break;
        case 3:
        	isCircleClip = true;
            mTransparentw = 314F;
            mTransparenth = 314F;
            centerX1 = 392F;
            centerY1 = 499F;
            degree1 = 0.0F;
            centerX2 = 0.0F;
            centerY2 = 0.0F;
            degree2 = 0.0F;
            break;
        case 4:
        	isCircleClip = true;
            mTransparentw = 331F;
            mTransparenth = 375F;
            centerX1 = 222F;
            centerY1 = 435F;
            degree1 = 0.0F;
            centerX2 = 0.0F;
            centerY2 = 0.0F;
            degree2 = 0.0F;
            break;
        case 5:
        	isCircleClip = false;
            mTransparentw = 437F;
            mTransparenth = 440F;
            centerX1 = 258F;
            centerY1 = 460F;
            degree1 = 0.0F;
            centerX2 = 0.0F;
            centerY2 = 0.0F;
            degree2 = 0.0F;
            break;
        case 6:
        	isCircleClip = true;
            mTransparentw = 463F;
            mTransparenth = 463F;
            centerX1 = 286F;
            centerY1 = 417F;
            degree1 = 0.0F;
            centerX2 = 0.0F;
            centerY2 = 0.0F;
            degree2 = 0.0F;
            break;
        case 7:
        	isCircleClip = false;
            mTransparentw = 260F;
            mTransparenth = 350F;
            centerX1 = 350F;
            centerY1 = 475F;
            degree1 = 0.0F;
            centerX2 = 0.0F;
            centerY2 = 0.0F;
            degree2 = 0.0F;
            break;
        }
        getFramedPicture(degree1, degree2);
        unScaledRect.left = mFrameBmp.getWidth();
        unScaledRect.top = mFrameBmp.getHeight();
        mFrameBmp = BitmapUtil.fitToViewByScale(mFrameBmp, getWidth(), getHeight());
        calculateNewRect();
    }

    public boolean isCollageCreated()
    {
        return flagLeftImage;
    }

    public void loadImages(Context context, float f, float f1, RectF rectf)
    {
        android.content.res.Resources resources;
        int i;
        resources = context.getResources();
        i = mImages.size();
        if(!(mImages.get(i - 1) instanceof TextObject)){
        	if(mImages.get(i - 1) instanceof ImageObject)
            {
                ImageObject imageobject = (ImageObject)mImages.get(i - 1);
                int ai[] = new int[2];
                ai[0] = (int)f;
                ai[1] = (int)f1;
                imageobject.load(resources, ai);
            }
        }else
        	((TextObject)mImages.get(i - 1)).load(resources, f, f1, rectf);
        if(mFrameBmp != null)
        {
            mFrameBmp.recycle();
            mFrameBmp = null;
        }
        mFrameBmp = BitmapFactory.decodeResource(getResources(), AppConstant.BACKGROUND_COLLAGE[mIndex]);
        initFrameVars();
        invalidate();
    }

    protected void onDetachedFromWindow()
    {
        if(mFrameBmp != null)
        {
            mFrameBmp.recycle();
            mFrameBmp = null;
        }
        if(leftImgObject != null)
        {
            delete(leftImgObject);
        }
        System.gc();
        super.onDetachedFromWindow();
    }

    protected void onDraw(Canvas canvas)
    {
        canvas.save();
        if(mBlurImage != null)
        {
            canvas.drawBitmap(mBlurImage, 0.0F, 0.0F, null);
        }
        Path path = new Path();
        int i;
        int j;
        if(isCircleClip)
        {
            path.addCircle(rectTranparentArea.centerX(), rectTranparentArea.centerY(), rectTranparentArea.width() / 2.0F, android.graphics.Path.Direction.CCW);
        } else
        {
            path.addRect(rectTranparentArea, android.graphics.Path.Direction.CCW);
        }
        canvas.clipPath(path);
        i = mImages.size();
        j = 0;
        do
        {
            if(j >= i)
            {
                canvas.restore();
                if(mFrameBmp != null)
                {
                    canvas.drawBitmap(mFrameBmp, 0.0F, 0.0F, null);
                }
                if(mShowDebugInfo)
                {
                    printOnScreen(canvas);
                }
                return;
            }
            if(mImages.get(j) instanceof TextObject)
            {
                ((TextObject)mImages.get(j)).draw(canvas);
            } else
            {
                ((ImageObject)mImages.get(j)).draw(canvas);
            }
            j++;
        } while(true);
    }

    public void onLongClick(Object obj, com.km.funphotoeffects.stickers.CustomTouch.PointInfo pointinfo)
    {
        if(!(obj instanceof TextObject) && (!(obj instanceof ImageObject) || !obj.equals(leftImgObject)) && (obj instanceof ImageObject))
        {
            obj.equals(rightImgObject);
        }
        if(-1 != -1 && mListener != null)
        {
            mListener.onClickListener(obj, pointinfo, -1);
        }
    }

    protected void onSizeChanged(int i, int j, int k, int l)
    {
        super.onSizeChanged(i, j, k, l);
        rect1 = new Rect();
        rect2 = new Rect();
        unScaledRect = new Rect();
        setBackground(mIndex);
    }

    public boolean onTouchEvent(MotionEvent motionevent)
    {
        if((rect1 == null || !rect1.contains((int)motionevent.getX(), (int)motionevent.getY()) || flagLeftImage) && (rect2 == null || !rect2.contains((int)motionevent.getX(), (int)motionevent.getY()) || flagRightImage) && (rect1 == null || !rect1.contains((int)motionevent.getX(), (int)motionevent.getY()) || !flagLeftImage) && rect2 != null)
        {
            if(!rect2.contains((int)motionevent.getX(), (int)motionevent.getY()));
        }
        return multiTouchController.onTouchEvent(motionevent);
    }

    public void rearrange(int i, int j, ImageObject imageobject)
    {
        mImages.remove(imageobject);
        mImages.add(j, imageobject);
        int k = 0;
        do
        {
            if(k >= getLayers().size())
            {
                invalidate();
                return;
            }
            ((ImageObject)mImages.get(k)).setPosition(k);
            ((Layer)getLayers().get(k)).setPosition(k);
            k++;
        } while(true);
    }

    public void removeLeftPic()
    {
        delete(leftImgObject);
        mFrameBmp = BitmapFactory.decodeResource(getResources(), AppConstant.BACKGROUND_COLLAGE[mIndex]);
        initFrameVars();
        invalidate();
    }

    public void removeListener()
    {
        mListener = null;
    }

    public void removeRightPic()
    {
        delete(rightImgObject);
        mFrameBmp = BitmapFactory.decodeResource(getResources(), AppConstant.BACKGROUND_COLLAGE[mIndex]);
        initFrameVars();
        invalidate();
    }

    public void removeText()
    {
        mImages.clear();
        if(mFrameBmp != null)
        {
            mFrameBmp.recycle();
            mFrameBmp = null;
        }
        mFrameBmp = BitmapFactory.decodeResource(getResources(), AppConstant.BACKGROUND_COLLAGE[mIndex]);
        initFrameVars();
        invalidate();
    }

    public void selectObject(Object obj, com.km.funphotoeffects.stickers.CustomTouch.PointInfo pointinfo)
    {
        currTouchPoint.set(pointinfo);
        invalidate();
    }

    public void setBackground(int i)
    {
        if(mFrameBmp != null)
        {
            mFrameBmp.recycle();
            mFrameBmp = null;
        }
        mFrameBmp = BitmapFactory.decodeResource(getResources(), AppConstant.BACKGROUND_COLLAGE[i]);
        mIndex = i;
        initFrameVars();
        adjustPic();
        invalidate();
    }

    public void setBlurImage(Bitmap bitmap)
    {
        mBlurImage = bitmap;
        invalidate();
    }

    public void setLayers(ArrayList arraylist)
    {
        mLayers = arraylist;
    }

    public void setOnLongClickListener(ClickListener clicklistener)
    {
        mListener = clicklistener;
    }

    public boolean setPositionAndScale(Object obj, com.km.funphotoeffects.stickers.ImageObject.PositionAndScale positionandscale, com.km.funphotoeffects.stickers.CustomTouch.PointInfo pointinfo)
    {
        currTouchPoint.set(pointinfo);
        boolean flag;
        if(obj instanceof ImageObject)
        {
            flag = ((ImageObject)obj).setPos(positionandscale);
        } else
        {
            flag = ((TextObject)obj).setPos(positionandscale);
        }
        if(flag)
        {
            invalidate();
        }
        return flag;
    }

    public void trackballClicked()
    {
        mUIMode = (1 + mUIMode) % 3;
        invalidate();
    }
}
