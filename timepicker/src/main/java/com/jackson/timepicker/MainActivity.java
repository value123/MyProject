package com.jackson.timepicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jackson.timepicker.data.Type;
import com.jackson.timepicker.listener.OnDxDateSetListener;
import com.jackson.timepicker.utils.Utils;

public class MainActivity extends AppCompatActivity implements OnDxDateSetListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showTimePickerDialog();
    }

    private void showTimePickerDialog() {

        DXTimePickerDialog mDialogAll = new DXTimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId("Cancel")
                .setSureStringId("Sure")
                .setTitleStringId("TimePicker")
                .setYearText("Year")
                .setMonthText("Month")
                .setDayText("")
                .setHourText("Hour")
                .setMinuteText("Minute")
                .setCyclic(true)
                .setMinMillseconds(Utils.getTimeMillis("1970-01-01"))
                .setMaxMillseconds(Utils.getTimeMillis("2100-01-01"))
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.ALL)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(12)
                .build();
        mDialogAll.show(getSupportFragmentManager(),"timePickerDialog");
    }


    public void showTimeDialog(View view){
        showTimePickerDialog();
    }
    public void showTimeDialogNormal(View view){
        showTimePickerDialogNormal();
    }

    private void showTimePickerDialogNormal() {
        long tenYears = 1000*60*60*24*365*10l;
        TimePickerDialog mDialogAll = new TimePickerDialog.Builder()
                .setCallBack(null)
                .setCancelStringId("Cancel")
                .setSureStringId("Sure")
                .setTitleStringId("TimePicker")
                .setYearText("Year")
                .setMonthText("Month")
                .setDayText("Day")
                .setHourText("Hour")
                .setMinuteText("Minute")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis())
                .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.ALL)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(12)
                .build();
        mDialogAll.show(getSupportFragmentManager(),"timePickerDialog");
    }

    @Override
    public void onDateSet(String beginDate, String beginTime, String endDate, String endTime) {
        Log.d(TAG,"beginDate="+beginDate+">>beginTime="+beginTime+">>endDate="+endDate+">>endTime="+endTime);
    }
}
