package com.example.immersivestatusbar;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.lib.utils.DisplayUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CollapsingToolBarActivity extends AppCompatActivity {

    private static final String TAG = "Collapsing";
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**  * 图片全屏透明状态栏（图片位于状态栏下面）  *  * @param activity  */
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Log.d(TAG, "Build.VERSION= " + Build.VERSION.SDK_INT);
            setContentView(R.layout.activity_collapsing_tool_bar);
        } else {
            Log.d(TAG, "Build.VERSION= " + Build.VERSION.SDK_INT);
            setContentView(R.layout.activity_collapsing_tool_bar);
        }
        ButterKnife.inject(this);

        initToolBar();
    }

    private void initToolBar() {
//        toolbar.setNavigationIcon(R.mipmap.arrow_left_circle);
//        toolbar.setTitle("压缩布局");

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            //将状态栏设置成全透明
            int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if ((params.flags & bits) == 0) {
                params.flags |= bits;
                //如果是取消全透明，params.flags &= ~bits;
                window.setAttributes(params);
            }
            /*int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            params.flags |= bits;
            //如果是取消全透明，params.flags &= ~bits;
            window.setAttributes(params);*/
            if(null != toolbar){
                toolbar.setBackgroundColor(Color.TRANSPARENT);
            }
        }else if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.setStatusBarColor(Color.TRANSPARENT);
            if(null != toolbar){
                toolbar.setBackgroundColor(Color.TRANSPARENT);
            }
        }

//        int statusHeight = DisplayUtil.getStatusHeight(this);
//        CollapsingToolbarLayout.LayoutParams layoutParams = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
//        layoutParams.topMargin = statusHeight;
    }
}
