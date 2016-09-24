
package com.iss.example.view.calendar;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;

import com.iss.app.AbsDialog;
import com.iss.appdemo.R;
import com.iss.view.calendar.CalendarPickerView;

public class CalendarDialog extends AbsDialog {
    CalendarPickerView calendarPickerView;

    public CalendarDialog(Context context) {
        super(context, R.style.dialog_menu);
        setContentView(R.layout.dialog_calendar);
        setProperty(1, 1);
    }

    @Override
    protected void initView() {
        calendarPickerView = (CalendarPickerView) findViewById(R.id.calendarPickerView);
    }

    @Override
    protected void initData() {

        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        calendarPickerView.init(new Date(), nextYear.getTime());
    }

    @Override
    protected void setListener() {

    }

}
