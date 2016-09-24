package com.jackson.timepicker;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.jackson.timepicker.adapters.DxNumericWheelAdapter;
import com.jackson.timepicker.adapters.NumericWheelAdapter;
import com.jackson.timepicker.config.PickerConfig;
import com.jackson.timepicker.data.source.TimeRepository;
import com.jackson.timepicker.utils.NameValuePair;
import com.jackson.timepicker.utils.PickerContants;
import com.jackson.timepicker.utils.Utils;
import com.jackson.timepicker.wheel.OnWheelChangedListener;
import com.jackson.timepicker.wheel.WheelView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by jzxiang on 16/4/20.
 */
public class DxTimeWheel {
    private static final String TAG = "DxTimeWheel";
    Context mContext;

    WheelView yearMonth, day;
    NumericWheelAdapter mDayAdapter;
    DxNumericWheelAdapter mYearMonthAdapter;

    PickerConfig mPickerConfig;
    TimeRepository mRepository;
    OnWheelChangedListener yearMonthListener = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            updateDays();
        }
    };
    OnWheelChangedListener dayListener = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
//            updateDays();
        }
    };

    public DxTimeWheel(View view, PickerConfig pickerConfig) {
        mPickerConfig = pickerConfig;

        mRepository = new TimeRepository(pickerConfig);
        mContext = view.getContext();
        initialize(view);
    }

    public void initialize(View view) {
        initView(view);
        initYearMonth();
        initDay();
    }


    private void initYearMonth() {
        int minYear = mRepository.getMinYear();
        int maxYear = mRepository.getMaxYear();

        mDayAdapter = new NumericWheelAdapter(mContext, minYear, maxYear, PickerContants.DX_FORMAT, mPickerConfig.mDay);
        mDayAdapter.setConfig(mPickerConfig);

        mYearMonthAdapter = new DxNumericWheelAdapter(mContext, new NameValuePair("1970", "1"), new NameValuePair("2100", "12"));
        yearMonth.setViewAdapter(mYearMonthAdapter);
        int currentItemIndex = calculateCurrentItemIndex(1970, 1);
        yearMonth.setCurrentItem((currentItemIndex>0)?currentItemIndex:0);
    }

    private int calculateCurrentItemIndex(int startYear,int startMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        return 12*(year-startYear)+(month+1)-startMonth;
    }

    void initDay() {
        updateDays();
        int curYear = getCurrentYear();
        int curMonth = getCurrentMonth();

        int minDay = mRepository.getMinDay(curYear, curMonth);
        day.setCurrentItem(mRepository.getDefaultCalendar().day - minDay);
        day.setCyclic(mPickerConfig.cyclic);
    }


    void initView(View view) {
        yearMonth = (WheelView) view.findViewById(R.id.year_month);
        day = (WheelView) view.findViewById(R.id.day);
        yearMonth.addChangingListener(yearMonthListener);
        day.addChangingListener(dayListener);
    }

    void updateYearAndMonth() {
        int curYear = getCurrentYear();
        int minMonth = mRepository.getMinMonth(curYear);
        int maxMonth = mRepository.getMaxMonth(curYear);
        mDayAdapter = new NumericWheelAdapter(mContext, minMonth, maxMonth, PickerContants.FORMAT, mPickerConfig.mMonth);
        mDayAdapter.setConfig(mPickerConfig);
        day.setViewAdapter(mDayAdapter);

    }

    void updateDays() {
        if (day.getVisibility() == View.GONE)
            return;

        int curYear = getCurrentYear();
        int curMonth = getCurrentMonth();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + yearMonth.getCurrentItem());
        calendar.set(Calendar.MONTH, curMonth);

        int maxDay = mRepository.getMaxDay(curYear, curMonth);
        int minDay = mRepository.getMinDay(curYear, curMonth);
        mDayAdapter = new NumericWheelAdapter(mContext, minDay, maxDay, PickerContants.FORMAT, mPickerConfig.mDay);
        mDayAdapter.setConfig(mPickerConfig);
        mDayAdapter.setIsCurrentYearAndMonth(Utils.isCurrentYearAndMonth(curYear,curMonth));
        day.setViewAdapter(mDayAdapter);

        if (mRepository.isMinMonth(curYear, curMonth)) {
            day.setCurrentItem(0, true);
        }

        int dayCount = mDayAdapter.getItemsCount();
        if (day.getCurrentItem() >= dayCount) {
            day.setCurrentItem(dayCount - 1, true);
        }
    }


    /**
     * 获取时间选择器上面的Year
     * @return
     */
    public int getCurrentYear() {
        Log.d(TAG,"year="+(mRepository.getMinYear() + yearMonth.getCurrentItem()/12));
        return mRepository.getMinYear() + yearMonth.getCurrentItem()/12;
    }

    public int getCurrentMonth() {
        int curYear = getCurrentYear();
        Log.d(TAG,"month="+(yearMonth.getCurrentItem()-(curYear - mRepository.getMinYear())*12 + mRepository.getMinMonth(curYear)));
        return yearMonth.getCurrentItem()-(curYear - mRepository.getMinYear())*12 + mRepository.getMinMonth(curYear);
//        return mRepository.getMinMonth(curYear)+yearMonth.getCurrentItem()%12-1;
    }

    public int getCurrentDay() {
        Log.d(TAG,"day="+(day.getCurrentItem()+1));
        return day.getCurrentItem()+1;
    }

    public String getCurrentDate(){
        return String.format(PickerContants.FORMAT, getCurrentYear())+String.format(PickerContants.FORMAT, getCurrentMonth())+String.format(PickerContants.FORMAT, getCurrentDay());
    }

}
