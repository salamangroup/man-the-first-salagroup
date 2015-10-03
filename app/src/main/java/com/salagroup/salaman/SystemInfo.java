package com.salagroup.salaman;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Leon on 03/10/2015.
 */

@SuppressLint("SimpleDateFormat")
public class SystemInfo {

    public static String getCurrentDatetime(boolean withTimezone) {

        SimpleDateFormat dateFormatGmt = new SimpleDateFormat(Constant.DATETIME_FORMAT_TIMEZONE);

        String datetime = dateFormatGmt.format(new Date());

        if (withTimezone) {

            return datetime;

        } else {

            return datetime.substring(0, datetime.lastIndexOf(" "));
        }
    }

    public static String getCurrentDate_VietnamFormat() {

        SimpleDateFormat dateFormatGmt = new SimpleDateFormat(Constant.DATE_FORMAT_VIETNAM);

        return dateFormatGmt.format(new Date());
    }

    public static String getCurrenTime(boolean format24h) {

        SimpleDateFormat dateFormatGmt;

        if (format24h) {

            dateFormatGmt = new SimpleDateFormat(Constant.TIME_FORMAT_24H);

        } else {
            dateFormatGmt = new SimpleDateFormat(Constant.TIME_FORMAT_AMPM);
        }

        return dateFormatGmt.format(new Date());
    }
}
