package com.blueninjas.aditlal.trackingapp.utils;

/**
 * Created by aditlal on 31/12/14.
 */

import java.util.Locale;


/**
 * LanguageFactory
 */
public class LanguageFactory {


    public static Locale getLanguage(LanguageType languageType) {
        switch (languageType) {
            case EN:
                return new Locale("en", "US");
            case PL:
                return new Locale("pl", "PL");
            default:
                return new Locale("en", "US");
        }
    }
}