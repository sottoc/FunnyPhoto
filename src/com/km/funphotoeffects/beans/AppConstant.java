package com.km.funphotoeffects.beans;

import com.km.funphotoeffects.R;


public class AppConstant
{

    public static final String APP_FOLDER = "Fun Photo Effects";
    public static final String APP_TEMP_FOLDER = "dexati/temp";
    public static final int BACKGROUND_COLLAGE[] = {
        R.drawable.frame_1, R.drawable.frame_2, R.drawable.frame_3, R.drawable.frame_4, R.drawable.frame_5
    };
    public static final String EFFECT_NAME[] = {
        "Black and White", "Old Photo", "Sepia", "Amaro", "Lomo-Fi", "EarlyBird", "Original"
    };
    public static final String EXTRA_CROPPED_IMAGE_PATH = "image-path";
    public static final String EXTRA_FROM_GALLERY = "isGallery";
    public static final String EXTRA_IMAGE_URI = "imguri";
    public static final String EXTRA_ORIGINAL_IMAGE_PATH = "original_image_path";
    public static final String EXTRA_OUTPUT_IMAGE_PATH = "output_image_path";
    public static final int Effects_IC_ARRAY[] = {
        R.drawable.effect_blackandwhite_normal, R.drawable.effect_oldphoto_normal, R.drawable.effect_sepia_normal, R.drawable.effect_orignal_normal
    };
    public static final String FILE_EXTENSION = ".png";
    public static final String FILE_PREFIX = "FunPhotos_";
    public static final String FOLDER_COLLOAGES = "Collages";
    public static final String FOLDER_COMPLETE_IMAGE = "FunPhotos";
    public static final String FOLDER_SINGLE_FRAMES = "Frames";
    public static final int FRAME_IC_ARRAY[] = {
        R.drawable.ic_frame_1_pre, R.drawable.ic_frame_2_pre, R.drawable.ic_frame_3_pre, R.drawable.ic_frame_4_pre, R.drawable.ic_frame_5_pre
    };
    public static final int TYPE_COLLAGE = 11;
    public static final int TYPE_PHOTO = 10;

    public AppConstant()
    {
    }

}
