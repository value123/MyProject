
package com.iss.example.view.waterfall;

import android.os.Bundle;
import android.view.View;

import com.iss.app.IssActivity;
import com.iss.appdemo.R;
import com.iss.appdemo.adapter.WaterAdapter;
import com.iss.example.imageloader.Images;
import com.iss.view.waterfall.NetImageView;
import com.iss.view.waterfall.WaterView;
import com.iss.view.waterfall.WaterView.OnResetViewDataListener;

public class WaterfallNoRefreshActivity extends IssActivity {

    private WaterView waterView;

    private WaterAdapter waterAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterfall_norefresh);
    }

    @Override
    protected void initView() {
        waterView = (WaterView) findViewById(R.id.waterview);

    }

    @Override
    protected void initData() {
        waterAdapter = new WaterAdapter(this);
        waterView.setAdapter(waterAdapter);
        waterAdapter.addItem(Images.imageThumbUrls, true);

    }

    @Override
    protected void setListener() {
        waterView.setOnResetViewDataListener(new OnResetViewDataListener() {

            @Override
            public void onRecycleData(View view) {
                if (view instanceof NetImageView) {
                    ((NetImageView) view).recycle(true);
                }

            }

            @Override
            public void onLoadData(View view) {
                if (view instanceof NetImageView) {
                    ((NetImageView) view).reload(false);
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        waterView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        waterView.onResume();
    }

}
