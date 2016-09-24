package com.jackson.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by shenwenjie on 21/6/2016.
 */
public class MainActivity extends FragmentActivity implements PullRefreshLayout.OnRefreshListener, OnDateSetListener {

    private static final String TAG = "MainActivity";
    @InjectView(R.id.listView)
    ListView listView;

    String[] items = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L","M","N","O","P","Q"};
    @InjectView(R.id.pullRefreshLayout)
    PullRefreshLayout pullRefreshLayout;
    @InjectView(R.id.text)
    TextView text;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        pullRefreshLayout.setOnRefreshListener(this);
        text.setText(ApplicationUtil.getDeviceInfo(this));
        Log.d(TAG,ApplicationUtil.getDeviceInfo(this));
        Log.d(TAG,"isDebug = " + BuildConfig.DEBUG);

        showTimePickerDialog();
    }

    private void showTimePickerDialog() {
        long tenYears = 1000*60*60*24*365*10;
        TimePickerDialog mDialogAll = new TimePickerDialog.Builder()
                .setCallBack(this)
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
        mDialogAll.show(getSupportFragmentManager(),"111");
    }

    @Override
    public void onRefresh() {
        Toast.makeText(MainActivity.this, "onRefresh", Toast.LENGTH_SHORT).show();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Arrays.copyOf(items, items.length);
        items[items.length - 1] = "刷新了一项";
        adapter.notifyDataSetChanged();
        pullRefreshLayout.stopRefresh();
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {

    }
}
