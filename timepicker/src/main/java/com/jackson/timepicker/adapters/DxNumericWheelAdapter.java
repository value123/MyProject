/*
 *  Copyright 2011 Yuri Kanivets
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.jackson.timepicker.adapters;

import android.content.Context;
import android.text.TextUtils;

import com.jackson.timepicker.utils.NameValuePair;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Numeric Wheel adapter.
 */
public class DxNumericWheelAdapter extends AbstractWheelTextAdapter {

 /*   *//**
     * The default min value
     *//*
    public static final NameValuePair DEFAULT_MAX_VALUE = 9;

    *//**
     * The default max value
     *//*
    private static final NameValuePair DEFAULT_MIN_VALUE 0= ;*/

    // Values
    private NameValuePair endValuePair;
    private NameValuePair startValuePair;

    private Date startDate;
    private Date endDate;

    private int startYear,startMonth,endYear,endMonth;

    // format
    private String format;
    //unit
    private String unit;


    /**
     * Constructor
     *
     * @param context the current context
     */
   /* public DxNumericWheelAdapter(Context context) {
        this(context, DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE);
    }*/

    /**
     * Constructor
     *
     * @param context  the current context
     * @param minValue the wheel min value
     * @param maxValue the wheel max value
     */
    public DxNumericWheelAdapter(Context context, NameValuePair minValue, NameValuePair maxValue) {
        this(context, minValue, maxValue, null);
    }

    /**
     * Constructor
     *
     * @param context  the current context
     * @param minValue the wheel min value
     * @param maxValue the wheel max value
     * @param format   the format string
     */
    public DxNumericWheelAdapter(Context context, NameValuePair minValue, NameValuePair maxValue, String format) {
        this(context, minValue, maxValue, format, null);
    }

    /**
     * Constructor
     *
     * @param context  the current context
     * @param minValuePair the wheel min value
     * @param endValuePair the wheel max value
     * @param format   the format string
     * @param unit     the wheel unit value
     */
    public DxNumericWheelAdapter(Context context, NameValuePair minValuePair, NameValuePair endValuePair, String format, String unit) {
        super(context);
        this.startValuePair = minValuePair;
        this.endValuePair = endValuePair;
        this.format = format;
        this.unit = unit;
        initDate(startValuePair,endValuePair);
    }

    private void initDate(NameValuePair startValuePair, NameValuePair endValuePair) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        startYear = Integer.parseInt(startValuePair.getName());
        startMonth = Integer.parseInt(startValuePair.getValue());
        endYear = Integer.parseInt(endValuePair.getName());
        endMonth = Integer.parseInt(endValuePair.getValue());
        try {
            startDate = dateFormat.parse(startYear+"-"+parseMonth(startMonth)+"-01");
            endDate = dateFormat.parse(endYear+"-"+parseMonth(endMonth)+"-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    String parseMonth(int month){
        if(month<10){
            return "0"+month;
        }else{
            return ""+month;
        }
    }

    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < getItemsCount()) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR,startYear);
            calendar.set(Calendar.MONTH,startMonth);
            calendar.add(Calendar.MONTH,index-1);
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");

            return format.format(calendar.getTime());
        }
        return null;
    }

    /**
     * calculate item between yyyy年MM月-yyyy年MM月之间一共几个月
     * @return
     */
    @Override
    public int getItemsCount() {
        return 12*(endYear-startYear)+endMonth-startMonth;
    }


}
