package com.blueninjas.aditlal.trackingapp.utils;

import java.util.Date;

/**
 * Created by aditlal on 06/07/15.
 */
public class DateUtil {

    public static int getDaysDifference(Date fromDate, Date toDate) {
        if (fromDate == null || toDate == null)
            return 0;

        return (int) ((toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24));
    }

}
