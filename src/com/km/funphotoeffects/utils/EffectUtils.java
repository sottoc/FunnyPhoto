package com.km.funphotoeffects.utils;

import android.content.Context;
import android.graphics.*;
import android.support.v8.renderscript.*;
import android.util.Log;
import java.lang.reflect.Array;

public class EffectUtils
{

    public EffectUtils()
    {
    }

    public static Bitmap fastblur(Bitmap bitmap, int i)
    {
        int j;
        int k;
        int ai[];
        int l;
        int i1;
        int k1;
        int ai1[];
        int ai2[];
        int ai3[];
        int ai4[];
        int i2;
        int ai5[];
        int j2;
        int i10;
        int j10;
        int k10;
        int l10;
        int i11;
        int j11;
        int k11;
        int l11;
        int i12;
        int j12;
        int k12;
        int l3;
        int i4;
        int j4;
        int k4;
        int l4;
        int i5;
        int j5;
        int k5;
        int l5;
        int i6;
        int j6;
        int ai8[];
        int k6;
        int l6;
        int i7;
        int j7;
        int k7;
        int l7;
        int ai9[];
        int i8;
        int j8;
        int k8;
        int l8;
        int i9;
        int j9;
        int k9;
        int ai10[];
        int l2;
        int i3;
        int ai7[][];
        int j3;
        int k3;
        if(i < 1)
        {
            return null;
        }
        j = bitmap.getWidth();
        k = bitmap.getHeight();
        ai = new int[j * k];
        Log.e("pix", (new StringBuilder(String.valueOf(j))).append(" ").append(k).append(" ").append(ai.length).toString());
        bitmap.getPixels(ai, 0, j, 0, 0, j, k);
        l = j - 1;
        i1 = k - 1;
        int j1 = j * k;
        k1 = 1 + (i + i);
        ai1 = new int[j1];
        ai2 = new int[j1];
        ai3 = new int[j1];
        ai4 = new int[Math.max(j, k)];
        int l1 = k1 + 1 >> 1;
        i2 = l1 * l1;
        ai5 = new int[i2 * 256];
        j2 = 0;
        while(true){
	        int k2 = i2 * 256;
	        if(j2 < k2){
	        	ai5[j2] = j2 / i2;
	            j2++;
	        }else{
	        	break;
	        }
        }
        l2 = 0;
        i3 = 0;
        int ai6[] = {
            k1, 3
        };
        ai7 = (int[][])Array.newInstance(Integer.TYPE, ai6);
        j3 = i + 1;
        k3 = 0;
        while(k3 < k){
        	l3 = 0;
            i4 = 0;
            j4 = 0;
            k4 = 0;
            l4 = 0;
            i5 = 0;
            j5 = 0;
            k5 = 0;
            l5 = 0;
            i6 = -i;
            while(i6 <= i){
            	j6 = ai[l2 + Math.min(l, Math.max(i6, 0))];
                ai8 = ai7[i6 + i];
                ai8[0] = (0xff0000 & j6) >> 16;
                ai8[1] = (0xff00 & j6) >> 8;
                ai8[2] = j6 & 0xff;
                k6 = j3 - Math.abs(i6);
                j4 += k6 * ai8[0];
                i4 += k6 * ai8[1];
                l3 += k6 * ai8[2];
                if(i6 > 0)
                {
                    l5 += ai8[0];
                    k5 += ai8[1];
                    j5 += ai8[2];
                } else
                {
                    i5 += ai8[0];
                    l4 += ai8[1];
                    k4 += ai8[2];
                }
                i6++;
            }
            l6 = i;
            i7 = 0;
            while(i7 < j)
            {
            	ai1[l2] = ai5[j4];
                ai2[l2] = ai5[i4];
                ai3[l2] = ai5[l3];
                j7 = j4 - i5;
                k7 = i4 - l4;
                l7 = l3 - k4;
                ai9 = ai7[(k1 + (l6 - i)) % k1];
                i8 = i5 - ai9[0];
                j8 = l4 - ai9[1];
                k8 = k4 - ai9[2];
                if(k3 == 0)
                {
                    ai4[i7] = Math.min(1 + (i7 + i), l);
                }
                l8 = ai[i3 + ai4[i7]];
                ai9[0] = (0xff0000 & l8) >> 16;
                ai9[1] = (0xff00 & l8) >> 8;
                ai9[2] = l8 & 0xff;
                i9 = l5 + ai9[0];
                j9 = k5 + ai9[1];
                k9 = j5 + ai9[2];
                j4 = j7 + i9;
                i4 = k7 + j9;
                l3 = l7 + k9;
                l6 = (l6 + 1) % k1;
                ai10 = ai7[l6 % k1];
                i5 = i8 + ai10[0];
                l4 = j8 + ai10[1];
                k4 = k8 + ai10[2];
                l5 = i9 - ai10[0];
                k5 = j9 - ai10[1];
                j5 = k9 - ai10[2];
                l2++;
                i7++;
            }
            i3 += j;
            k3++;
        }
        int l9 = 0;
        while(true){
	        if(l9 >= j)
	        {
	            Log.e("pix", (new StringBuilder(String.valueOf(j))).append(" ").append(k).append(" ").append(ai.length).toString());
	            return Bitmap.createBitmap(ai, 0, j, j, k, android.graphics.Bitmap.Config.ARGB_8888);
	        }
	        i10 = 0;
	        j10 = 0;
	        k10 = 0;
	        l10 = 0;
	        i11 = 0;
	        j11 = 0;
	        k11 = 0;
	        l11 = 0;
	        i12 = 0;
	        j12 = j * -i;
	        k12 = -i;
	        
	        int j13;
	        int k13;
	        int l13;
	        while(k12 <= i){
	        	int l12 = l9 + Math.max(0, j12);
	            int ai11[] = ai7[k12 + i];
	            ai11[0] = ai1[l12];
	            ai11[1] = ai2[l12];
	            ai11[2] = ai3[l12];
	            int i13 = j3 - Math.abs(k12);
	            k10 += i13 * ai1[l12];
	            j10 += i13 * ai2[l12];
	            i10 += i13 * ai3[l12];
	            if(k12 > 0)
	            {
	                i12 += ai11[0];
	                l11 += ai11[1];
	                k11 += ai11[2];
	            } else
	            {
	                j11 += ai11[0];
	                i11 += ai11[1];
	                l10 += ai11[2];
	            }
	            if(k12 < i1)
	            {
	                j12 += j;
	            }
	            k12++;
	        }
	        j13 = l9;
	        k13 = i;
	        l13 = 0;
	        while(l13 < k)
	        {
	        	ai[j13] = 0xff000000 & ai[j13] | ai5[k10] << 16 | ai5[j10] << 8 | ai5[i10];
	            int i14 = k10 - j11;
	            int j14 = j10 - i11;
	            int k14 = i10 - l10;
	            int ai12[] = ai7[(k1 + (k13 - i)) % k1];
	            int l14 = j11 - ai12[0];
	            int i15 = i11 - ai12[1];
	            int j15 = l10 - ai12[2];
	            if(l9 == 0)
	            {
	                ai4[l13] = j * Math.min(l13 + j3, i1);
	            }
	            int k15 = l9 + ai4[l13];
	            ai12[0] = ai1[k15];
	            ai12[1] = ai2[k15];
	            ai12[2] = ai3[k15];
	            int l15 = i12 + ai12[0];
	            int i16 = l11 + ai12[1];
	            int j16 = k11 + ai12[2];
	            k10 = i14 + l15;
	            j10 = j14 + i16;
	            i10 = k14 + j16;
	            k13 = (k13 + 1) % k1;
	            int ai13[] = ai7[k13];
	            j11 = l14 + ai13[0];
	            i11 = i15 + ai13[1];
	            l10 = j15 + ai13[2];
	            i12 = l15 - ai13[0];
	            l11 = i16 - ai13[1];
	            k11 = j16 - ai13[2];
	            j13 += j;
	            l13++;
	        }
	        l9++;
        }
    }

    public static Bitmap scriptIntrinsicBlur(Context context, Bitmap bitmap, float f)
    {
        if(f > 0.0F){
        	if(f > 25F)
            {
                f = 25F;
            }
        }else
        	f = 0.1F;
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), android.graphics.Bitmap.Config.ARGB_8888);
        RenderScript renderscript = RenderScript.create(context);
        Allocation allocation = Allocation.createFromBitmap(renderscript, bitmap);
        Allocation allocation1 = Allocation.createFromBitmap(renderscript, bitmap1);
        ScriptIntrinsicBlur scriptintrinsicblur = ScriptIntrinsicBlur.create(renderscript, Element.U8_4(renderscript));
        scriptintrinsicblur.setInput(allocation);
        scriptintrinsicblur.setRadius(f);
        scriptintrinsicblur.forEach(allocation1);
        allocation1.copyTo(bitmap1);
        renderscript.destroy();
        return bitmap1;
    }

    public static Bitmap toGrayScale(Bitmap bitmap)
    {
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), android.graphics.Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap1);
        Paint paint = new Paint(1);
        ColorMatrix colormatrix = new ColorMatrix();
        colormatrix.setSaturation(0.0F);
        paint.setColorFilter(new ColorMatrixColorFilter(colormatrix));
        canvas.drawBitmap(bitmap, 0.0F, 0.0F, paint);
        return bitmap1;
    }
}
