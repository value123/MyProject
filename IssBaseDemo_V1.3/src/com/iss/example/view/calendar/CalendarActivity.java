/**
 * 日历demo view
 * @author perry
 * @ create time 2012-11-16
 * */

package com.iss.example.view.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.iss.app.IssActivity;
import com.iss.appdemo.R;
import com.iss.utils.TimeUtils;
import com.iss.view.calendar.CalendarPickerView;
import com.iss.view.calendar.CalendarPickerView.SelectionMode;
import com.iss.view.common.ToastAlone;

@SuppressLint("SimpleDateFormat")
public class CalendarActivity extends IssActivity {


    private CalendarPickerView calendar;

    private RadioGroup radioGroup_mode;

    private Button button_done;

    Calendar nextYear;

    Calendar lastYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
    }

    @Override
    protected void initView() {
        calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
        radioGroup_mode = (RadioGroup) findViewById(R.id.radioGroup_mode);
        button_done = (Button) findViewById(R.id.button_done);
    }

    @Override
    protected void initData() {
        nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 2);

        lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -2);

        calendar.init(lastYear.getTime(), nextYear.getTime()) //
                .inMode(SelectionMode.SINGLE) //
                .withSelectedDate(new Date());
    }

    @Override
    protected void setListener() {
        radioGroup_mode.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton_single:
                        calendar.init(new Date(), nextYear.getTime()) //
                                .inMode(SelectionMode.SINGLE) //
                                .withSelectedDate(new Date());

                        break;
                    case R.id.radioButton_multi:
                        Calendar today = Calendar.getInstance();
                        ArrayList<Date> dates = new ArrayList<Date>();
                        for (int i = 0; i < 5; i++) {
                            today.add(Calendar.DAY_OF_MONTH, 3);
                            dates.add(today.getTime());
                        }
                        calendar.init(new Date(), nextYear.getTime()) //
                                .inMode(SelectionMode.MULTIPLE) //
                                .withSelectedDates(dates);
                        break;
                    case R.id.radioButton_range:
                        Calendar today2 = Calendar.getInstance();
                        ArrayList<Date> dates2 = new ArrayList<Date>();
                        today2.add(Calendar.DATE, 3);
                        dates2.add(today2.getTime());
                        today2.add(Calendar.DATE, 5);
                        dates2.add(today2.getTime());
                        calendar.init(new Date(), nextYear.getTime()) //
                                .inMode(SelectionMode.RANGE) //
                                .withSelectedDates(dates2);
                        break;
                    case R.id.radioButton_dialog:
                        new CalendarDialog(CalendarActivity.this).show();
                        break;
                }

            }
        });

        button_done.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Date> dateList = calendar.getSelectedDates();
                StringBuffer sb = new StringBuffer();
                for (Date date : dateList) {
                    String dateStr = TimeUtils.getFormatTime(date.getTime(), "yyyy-MM-dd");
                    sb.append(dateStr);
                    sb.append("|");
                }
                if (sb.length() > 0) {
                    sb.deleteCharAt(sb.length() - 1);
                }
                ToastAlone.showToast(CalendarActivity.this, "Selected: " + sb.toString(),
                        Toast.LENGTH_LONG);
            }
        });

    }

}
