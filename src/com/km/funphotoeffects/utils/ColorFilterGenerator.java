package com.km.funphotoeffects.utils;

import android.graphics.*;

public class ColorFilterGenerator
{

    private static double DELTA_INDEX[] = {
        0.0D, 0.01D, 0.02D, 0.040000000000000001D, 0.050000000000000003D, 0.059999999999999998D, 0.070000000000000007D, 0.080000000000000002D, 0.10000000000000001D, 0.11D, 
        0.12D, 0.14000000000000001D, 0.14999999999999999D, 0.16D, 0.17000000000000001D, 0.17999999999999999D, 0.20000000000000001D, 0.20999999999999999D, 0.22D, 0.23999999999999999D, 
        0.25D, 0.27000000000000002D, 0.28000000000000003D, 0.29999999999999999D, 0.32000000000000001D, 0.34000000000000002D, 0.35999999999999999D, 0.38D, 0.40000000000000002D, 0.41999999999999998D, 
        0.44D, 0.46000000000000002D, 0.47999999999999998D, 0.5D, 0.53000000000000003D, 0.56000000000000005D, 0.58999999999999997D, 0.62D, 0.65000000000000002D, 0.68000000000000005D, 
        0.70999999999999996D, 0.73999999999999999D, 0.77000000000000002D, 0.80000000000000004D, 0.82999999999999996D, 0.85999999999999999D, 0.89000000000000001D, 0.92000000000000004D, 0.94999999999999996D, 0.97999999999999998D, 
        1.0D, 1.0600000000000001D, 1.1200000000000001D, 1.1799999999999999D, 1.24D, 1.3D, 1.3600000000000001D, 1.4199999999999999D, 1.48D, 1.54D, 
        1.6000000000000001D, 1.6599999999999999D, 1.72D, 1.78D, 1.8400000000000001D, 1.8999999999999999D, 1.96D, 2D, 2.1200000000000001D, 2.25D, 
        2.3700000000000001D, 2.5D, 2.6200000000000001D, 2.75D, 2.8700000000000001D, 3D, 3.2000000000000002D, 3.3999999999999999D, 3.6000000000000001D, 3.7999999999999998D, 
        4D, 4.2999999999999998D, 4.7000000000000002D, 4.9000000000000004D, 5D, 5.5D, 6D, 6.5D, 6.7999999999999998D, 7D, 
        7.2999999999999998D, 7.5D, 7.7999999999999998D, 8D, 8.4000000000000004D, 8.6999999999999993D, 9D, 9.4000000000000004D, 9.5999999999999996D, 9.8000000000000007D, 
        10D
    };

    public ColorFilterGenerator()
    {
    }

    public static void adjustBrightness(ColorMatrix colormatrix, float f)
    {
        float f1 = cleanValue(f, 100F);
        if(f1 == 0.0F)
        {
            return;
        } else
        {
            colormatrix.postConcat(new ColorMatrix(new float[] {
                1.0F, 0.0F, 0.0F, 0.0F, f1, 0.0F, 1.0F, 0.0F, 0.0F, f1, 
                0.0F, 0.0F, 1.0F, 0.0F, f1, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 
                0.0F, 0.0F, 0.0F, 0.0F, 1.0F
            }));
            return;
        }
    }

    public static ColorFilter adjustColor(int i, int j, int k)
    {
        ColorMatrix colormatrix = new ColorMatrix();
        adjustContrast(colormatrix, j);
        adjustBrightness(colormatrix, i);
        adjustSaturation(colormatrix, k);
        return new ColorMatrixColorFilter(colormatrix);
    }

    public static void adjustContrast(ColorMatrix colormatrix, int i)
    {
        int j = (int)cleanValue(i, 100F);
        if(j == 0)
        {
            return;
        }
        float f2;
        float af[];
        if(j < 0)
        {
            f2 = 127 + 127 * (j / 100);
        } else
        {
            float f = j % 1;
            float f1;
            if(f == 0.0F)
            {
                f1 = (float)DELTA_INDEX[j];
            } else
            {
                f1 = (float)DELTA_INDEX[j << 0] * (1.0F - f) + f * (float)DELTA_INDEX[1 + (j << 0)];
            }
            f2 = 127F + f1 * 127F;
        }
        af = new float[25];
        af[0] = f2 / 127F;
        af[1] = 0.0F;
        af[2] = 0.0F;
        af[3] = 0.0F;
        af[4] = 0.5F * (127F - f2);
        af[5] = 0.0F;
        af[6] = f2 / 127F;
        af[7] = 0.0F;
        af[8] = 0.0F;
        af[9] = 0.5F * (127F - f2);
        af[10] = 0.0F;
        af[11] = 0.0F;
        af[12] = f2 / 127F;
        af[13] = 0.0F;
        af[14] = 0.5F * (127F - f2);
        af[15] = 0.0F;
        af[16] = 0.0F;
        af[17] = 0.0F;
        af[18] = 1.0F;
        af[19] = 0.0F;
        af[20] = 0.0F;
        af[21] = 0.0F;
        af[22] = 0.0F;
        af[23] = 0.0F;
        af[24] = 1.0F;
        colormatrix.postConcat(new ColorMatrix(af));
    }

    public static void adjustHue(ColorMatrix colormatrix, float f)
    {
        float f1 = 3.141593F * (cleanValue(f, 180F) / 180F);
        if(f1 == 0.0F)
        {
            return;
        } else
        {
            float f2 = (float)Math.cos(f1);
            float f3 = (float)Math.sin(f1);
            float af[] = new float[25];
            af[0] = 0.213F + f2 * (1.0F - 0.213F) + f3 * -0.213F;
            af[1] = 0.715F + f2 * -0.715F + f3 * -0.715F;
            af[2] = 0.072F + f2 * -0.072F + f3 * (1.0F - 0.072F);
            af[3] = 0.0F;
            af[4] = 0.0F;
            af[5] = 0.213F + f2 * -0.213F + 0.143F * f3;
            af[6] = 0.715F + f2 * (1.0F - 0.715F) + 0.14F * f3;
            af[7] = 0.072F + f2 * -0.072F + -0.283F * f3;
            af[8] = 0.0F;
            af[9] = 0.0F;
            af[10] = 0.213F + f2 * -0.213F + f3 * -(1.0F - 0.213F);
            af[11] = 0.715F + f2 * -0.715F + f3 * 0.715F;
            af[12] = 0.072F + f2 * (1.0F - 0.072F) + f3 * 0.072F;
            af[13] = 0.0F;
            af[14] = 0.0F;
            af[15] = 0.0F;
            af[16] = 0.0F;
            af[17] = 0.0F;
            af[18] = 1.0F;
            af[19] = 0.0F;
            af[20] = 0.0F;
            af[21] = 0.0F;
            af[22] = 0.0F;
            af[23] = 0.0F;
            af[24] = 1.0F;
            colormatrix.postConcat(new ColorMatrix(af));
            return;
        }
    }

    public static void adjustSaturation(ColorMatrix colormatrix, float f)
    {
        float f1 = cleanValue(f, 100F);
        if(f1 == 0.0F)
        {
            return;
        }
        float f2;
        float f3;
        float af[];
        if(f1 > 0.0F)
        {
            f2 = (3F * f1) / 100F;
        } else
        {
            f2 = f1 / 100F;
        }
        f3 = 1.0F + f2;
        af = new float[25];
        af[0] = f3 + 0.3086F * (1.0F - f3);
        af[1] = 0.6094F * (1.0F - f3);
        af[2] = 0.082F * (1.0F - f3);
        af[3] = 0.0F;
        af[4] = 0.0F;
        af[5] = 0.3086F * (1.0F - f3);
        af[6] = f3 + 0.6094F * (1.0F - f3);
        af[7] = 0.082F * (1.0F - f3);
        af[8] = 0.0F;
        af[9] = 0.0F;
        af[10] = 0.3086F * (1.0F - f3);
        af[11] = 0.6094F * (1.0F - f3);
        af[12] = f3 + 0.082F * (1.0F - f3);
        af[13] = 0.0F;
        af[14] = 0.0F;
        af[15] = 0.0F;
        af[16] = 0.0F;
        af[17] = 0.0F;
        af[18] = 1.0F;
        af[19] = 0.0F;
        af[20] = 0.0F;
        af[21] = 0.0F;
        af[22] = 0.0F;
        af[23] = 0.0F;
        af[24] = 1.0F;
        colormatrix.postConcat(new ColorMatrix(af));
    }

    protected static float cleanValue(float f, float f1)
    {
        return Math.min(f1, Math.max(-f1, f));
    }

}
