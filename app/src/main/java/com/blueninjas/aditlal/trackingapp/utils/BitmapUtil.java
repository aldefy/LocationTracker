package com.blueninjas.aditlal.trackingapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by aditlal on 11/05/15.
 */
public class BitmapUtil {

    public static final int ATTACH_GALLERY = 1;
    public static final int ATTACH_CAMERA = 2;
  /*  public static int GALLERY_PICTURE = 10;
    public static int CAMERA_REQUEST = 12;*/

    public static void openGallery(Object c) {

        if (c instanceof Activity) {

            if (Build.VERSION.SDK_INT < 19) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                if (IntentUtil.isAvailable((Context) c, intent))
                    ((Activity) c).startActivityForResult(
                            Intent.createChooser(intent, "Choose Picture"), ATTACH_GALLERY);
                else
                    Toast.makeText((Context) c, "No Gallery app installed , install one from play store", Toast.LENGTH_SHORT).show();


            } else {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (IntentUtil.isAvailable((Context) c, intent))
                    ((Activity) c).startActivityForResult(intent, ATTACH_GALLERY);
                else
                    Toast.makeText((Context) c, "No Gallery app installed , install one from play store", Toast.LENGTH_SHORT).show();


            }
        } else if (c instanceof Fragment) {
            Fragment fragment = (Fragment) c;
            if (Build.VERSION.SDK_INT < 19) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                if (IntentUtil.isAvailable(fragment.getActivity(), intent))
                    fragment.startActivityForResult(
                            Intent.createChooser(intent, "Choose Picture"), ATTACH_GALLERY);
                else
                    Toast.makeText(fragment.getActivity(), "No Gallery app installed , install one from play store", Toast.LENGTH_SHORT).show();


            } else {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (IntentUtil.isAvailable(fragment.getActivity(), intent))
                    fragment.startActivityForResult(intent, ATTACH_GALLERY);
                else
                    Toast.makeText(fragment.getActivity(), "No Gallery app installed , install one from play store", Toast.LENGTH_SHORT).show();


            }
        }
    }

    public static void openCamera(Object c) {
        Intent pictureActionIntent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (c instanceof Activity) {
            if (IntentUtil.isAvailable((Context) c, pictureActionIntent))
                ((Activity) c).startActivityForResult(pictureActionIntent,
                        ATTACH_CAMERA);
            else
                Toast.makeText((Context) c, "No Camera App available, install one from play store", Toast.LENGTH_SHORT).show();
        } else if (c instanceof Fragment) {
            Fragment fragment = (Fragment) c;
            if (IntentUtil.isAvailable(fragment.getActivity(), pictureActionIntent))
                fragment.startActivityForResult(pictureActionIntent,
                        ATTACH_CAMERA);
            else
                Toast.makeText(fragment.getActivity(), "No Camera App available, install one from play store", Toast.LENGTH_SHORT).show();
        }
    }

//    public static void openGalleryWithCropOption(Object c, int x, int y, int outputX, int outputY) {
//
//        if (c instanceof Activity) {
//
//            if (Build.VERSION.SDK_INT < 19) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.putExtra("crop", true);
//                intent.putExtra("aspectX", x);
//                intent.putExtra("aspectY", y);
//                intent.putExtra("outputX", outputX);
//                intent.putExtra("outputY", outputY);
//                intent.putExtra("return-data", true);
//                if (IntentUtil.isAvailable((Context) c, intent))
//                    ((Activity) c).startActivityForResult(
//                            Intent.createChooser(intent, "Choose Picture"), ATTACH_GALLERY);
//                else
//                    Toast.makeText((Context) c, "No Gallery app installed , install one from play store", Toast.LENGTH_SHORT).show();
//
//
//            } else {
//                Intent intent = new Intent(
//                        Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                intent.putExtra("crop", true);
//                intent.putExtra("aspectX", x);
//                intent.putExtra("aspectY", y);
//                intent.putExtra("outputX", outputX);
//                intent.putExtra("outputY", outputY);
//                intent.putExtra("return-data", true);
//                if (IntentUtil.isAvailable((Context) c, intent))
//                    ((Activity) c).startActivityForResult(intent, ATTACH_GALLERY);
//                else
//                    Toast.makeText((Context) c, "No Gallery app installed , install one from play store", Toast.LENGTH_SHORT).show();
//
//
//            }
//
//
//        } else if (c instanceof Fragment) {
//            Fragment fragment = (Fragment) c;
//            if (Build.VERSION.SDK_INT < 19) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.putExtra("crop", true);
//                intent.putExtra("aspectX", x);
//                intent.putExtra("aspectY", y);
//                intent.putExtra("outputX", outputX);
//                intent.putExtra("outputY", outputY);
//                intent.putExtra("return-data", true);
//                if (IntentUtil.isAvailable(fragment.getActivity(), intent))
//                    fragment.startActivityForResult(
//                            Intent.createChooser(intent, "Choose Picture"), ATTACH_GALLERY);
//                else
//                    Toast.makeText(fragment.getActivity(), "No Gallery app installed , install one from play store", Toast.LENGTH_SHORT).show();
//
//
//            } else {
//                Intent intent = new Intent(
//                        Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                intent.putExtra("crop", true);
//                intent.putExtra("aspectX", x);
//                intent.putExtra("aspectY", y);
//                intent.putExtra("outputX", outputX);
//                intent.putExtra("outputY", outputY);
//                intent.putExtra("return-data", true);
//                if (IntentUtil.isAvailable(fragment.getActivity(), intent))
//                    fragment.startActivityForResult(intent, ATTACH_GALLERY);
//                else
//                    Toast.makeText(fragment.getActivity(), "No Gallery app installed , install one from play store", Toast.LENGTH_SHORT).show();
//
//
//            }
//        }
//
////        intent.putExtra("return-data", true);
//    }

//    public static void openCameraWithCropOption(Object c, int x, int y, int outputX, int outputY) {
//
//        Intent pictureActionIntent = new Intent(
//                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        pictureActionIntent.putExtra("crop", true);
//        pictureActionIntent.putExtra("aspectX", x);
//        pictureActionIntent.putExtra("aspectY", y);
//        pictureActionIntent.putExtra("outputX", outputX);
//        pictureActionIntent.putExtra("outputY", outputY);
//        pictureActionIntent.putExtra("return-data", true);
//        if (c instanceof Activity) {
//            if (IntentUtil.isAvailable((Context) c, pictureActionIntent))
//                ((Activity) c).startActivityForResult(pictureActionIntent,
//                        CAMERA_REQUEST);
//            else
//                Toast.makeText((Context) c, "No Camera App available, install one from play store", Toast.LENGTH_SHORT).show();
//        } else if (c instanceof Fragment) {
//            Fragment fragment = (Fragment) c;
//            if (IntentUtil.isAvailable(fragment.getActivity(), pictureActionIntent))
//                fragment.startActivityForResult(pictureActionIntent,
//                        CAMERA_REQUEST);
//            else
//                Toast.makeText(fragment.getActivity(), "No Camera App available, install one from play store", Toast.LENGTH_SHORT).show();
//        }
//
//    }

    public static Bitmap changeImageColor(Bitmap sourceBitmap, int color) {
        Bitmap resultBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0,
                sourceBitmap.getWidth() - 1, sourceBitmap.getHeight() - 1);
        Paint p = new Paint();
        p.setColor(color);
         ColorFilter filter = new LightingColorFilter(color, 1);

        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(resultBitmap, 0, 0, p);
        return resultBitmap;
    }


    public static Drawable covertBitmapToDrawable(Context context, Bitmap bitmap) {
        Drawable d = new BitmapDrawable(context.getResources(), bitmap);
        return d;
    }

    public static Bitmap convertDrawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }


}
