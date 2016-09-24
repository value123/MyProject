package com.jackson.timepicker.utils;

import android.view.View;

import com.jackson.timepicker.data.WheelCalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by jzxiang on 16/4/20.
 */
public class Utils {

    public static boolean isTimeEquals(WheelCalendar calendar, int... params) {
        switch (params.length) {
            case 1:
                return calendar.year == params[0];
            case 2:
                return calendar.year == params[0] &&
                        calendar.month == params[1];
            case 3:
                return calendar.year == params[0] &&
                        calendar.month == params[1] &&
                        calendar.day == params[2];
            case 4:
                return calendar.year == params[0] &&
                        calendar.month == params[1] &&
                        calendar.day == params[2] &&
                        calendar.hour == params[3];
        }
        return false;
    }

    public static void hideViews(View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setVisibility(View.GONE);
        }
    }

    public static boolean isCurrentYearAndMonth(int year, int month){
        SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentTime = new Date();
        int currentYear = currentTime.getYear() + 1900;
        int currentMonth = currentTime.getMonth() +1;
        return year==currentYear && month == currentMonth;
    }

   /* public static int getCurrentDay(){
        SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentTime = new Date();
        int currentYear = currentTime.getYear() + 1900;
        int currentMonth = currentTime.getMonth() +1;
        return year==currentYear && month == currentMonth;
    }*/

    /**
     *
     * @param formatTime li:"1901-01-01"
     * @return
     */
    public static long getTimeMillis(String formatTime){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        try {
            Date date = df.parse(formatTime);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0l;
    }
}
