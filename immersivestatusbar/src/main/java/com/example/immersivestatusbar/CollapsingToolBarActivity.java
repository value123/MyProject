package com.example.immersivestatusbar;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lib.utils.DisplayUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CollapsingToolBarActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsing_tool_bar);
        ButterKnife.inject(this);

        initToolBar();
    }

    private void initToolBar() {
        toolbar.setNavigationIcon(R.mipmap.arrow_left_circle);
        toolbar.setTitle("压缩布局");

        int statusHeight = DisplayUtil.getStatusHeight(this);
        CollapsingToolbarLayout.LayoutParams layoutParams = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
        layoutParams.topMargin = statusHeight;
    }
}
