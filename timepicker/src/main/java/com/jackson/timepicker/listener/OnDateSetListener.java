package com.jackson.timepicker.listener;


import com.jackson.timepicker.TimePickerDialog;

/**
 * Created by jzxiang on 16/4/20.
 */
public interface OnDateSetListener {

    void onDateSet(TimePickerDialog timePickerView, long millseconds);
}
