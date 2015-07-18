package com.blueninjas.aditlal.trackingapp.utils;

import android.text.TextUtils;

/**
 * Created by aditlal on 25/05/15.
 */
public class StringUtils {

    public static String toCamelCase(final String init) {
        if (init == null)
            return null;

        final StringBuilder ret = new StringBuilder(init.length());

        for (final String word : init.split(" ")) {
            if (!word.isEmpty()) {
                ret.append(word.substring(0, 1).toUpperCase());
                ret.append(word.substring(1).toLowerCase());
            }
            if (!(ret.length() == init.length()))
                ret.append(" ");
        }

        return ret.toString();
    }

    public static String getXCharPerLine(String text, int n) {

        String tenCharPerLineString = "";
        while (text.length() > n) {

            String buffer = text.substring(0, n);
            tenCharPerLineString = tenCharPerLineString + buffer + "\n";
            text = text.substring(n);
        }

        tenCharPerLineString = tenCharPerLineString + text.substring(0);
        return tenCharPerLineString;
    }

    public static String getInitialsFromItemName(String title) {
        if (TextUtils.isEmpty(title))
            return "";
        String[] strings = title.split("(?<=[\\S])[\\S]*(\\s*)?");
        return TextUtils.join("", strings);

    }

    public static String getFirstInitialsFromItemName(String title) {
        if (TextUtils.isEmpty(title))
            return "";
        String[] strings = title.split("(?<=[\\S])[\\S]*(\\s*)?");
        return strings[0];

    }

    public static int getCountOfDot(String c) {
        String findStr = ".";
        int lastIndex = 0;
        int count = 0;

        while (lastIndex != -1) {

            lastIndex = c.indexOf(findStr, lastIndex);

            if (lastIndex != -1) {
                count++;
                lastIndex += findStr.length();
            }
        }
        return count;
    }
}
