package com.example.immersivestatusbar;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ElasticCollapsingToolBarActivity extends AppCompatActivity implements View.OnTouchListener{

    private static final String TAG = "Collapsing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**  * 图片全屏透明状态栏（图片位于状态栏下面）  *  * @param activity  */
        setContentView(R.layout.activity_elastic_collapsing_tool_bar);

        ButterKnife.inject(this);

    }

    private void initToolBar() {
//        toolbar.setNavigationIcon(R.mipmap.arrow_left_circle);
//        toolbar.setTitle("压缩布局");

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.setY(event.getY());
        return true;
    }
}
