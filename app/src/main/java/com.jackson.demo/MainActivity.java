package com.jackson.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by shenwenjie on 21/6/2016.
 */
public class MainActivity extends FragmentActivity {

    @InjectView(R.id.listView)
    ListView listView;

    String[] items = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

    }
}
