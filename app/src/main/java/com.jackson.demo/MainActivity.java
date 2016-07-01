package com.jackson.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by shenwenjie on 21/6/2016.
 */
public class MainActivity extends Activity implements PullRefreshLayout.OnRefreshListener {

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
}
