package com.salagroup.salaman.helper;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Leon on 03/10/2015.
 */

@SuppressLint("SimpleDateFormat")
public class SystemInfoHelper {

    /**
     *
     * @param withTimezone True if get TimeZone
     * @return "yyyy-MM-dd HH:mm:ss zzz" = "2015-10-03 19:30:00 GMT+7:00" or "yyyy-MM-dd HH:mm:ss" = "2015-10-03 19:30:00"
     */
    public static String getCurrentDatetime(boolean withTimezone) {

        SimpleDateFormat dateFormatGmt = new SimpleDateFormat(Constant.DATETIME_FORMAT_TIMEZONE);

        String datetime = dateFormatGmt.format(new Date());

        if (withTimezone) {

            return datetime;

        } else {

            return datetime.substring(0, datetime.lastIndexOf(" "));
        }
    }

    /**
     *
     * @return "dd-MM-yyyy" = "03/10/2015"
     */
    public static String getCurrentDate_VietnamFormat() {

        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATE_FORMAT_VIETNAM);

        return dateFormat.format(new Date());
    }

    /**
     *
     * @param format24h True if get 24PM format
     * @return "HH:mm:ss" = "19:30:00" or "hh:mm:ss a" = "07:30:00 PM"
     */
    public static String getCurrenTime(boolean format24h) {

        SimpleDateFormat dateFormat;

        if (format24h) {

            dateFormat = new SimpleDateFormat(Constant.TIME_FORMAT_24H);

        } else {
            dateFormat = new SimpleDateFormat(Constant.TIME_FORMAT_AMPM);
        }

        return dateFormat.format(new Date());
    }
}
