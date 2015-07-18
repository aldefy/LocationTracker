package com.blueninjas.aditlal.trackingapp.utils;

import android.text.TextUtils;
import android.util.Base64;

import java.io.UnsupportedEncodingException;

public class SecurityUtil {
    public static final String DEFAULT_CHARSET = "UTF-8";
    private static final String TAG = SecurityUtil.class.getSimpleName();

    public static String encodeString(String string) {
        if (!TextUtils.isEmpty(string)) {
            try {
                return Base64.encodeToString(string.getBytes(DEFAULT_CHARSET), Base64.DEFAULT);
            } catch (UnsupportedEncodingException e) {
                Logger.e(TAG, "failed to encode string", e);
                return string;
            }
        }
        return string;
    }

    public static String decodeString(String string) {
        if (!TextUtils.isEmpty(string)) {
            try {
                byte[] bytes = Base64.decode(string, Base64.DEFAULT);
                return new String(bytes, DEFAULT_CHARSET);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return string;
    }
}
