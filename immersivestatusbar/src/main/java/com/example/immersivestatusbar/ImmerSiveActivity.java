package com.example.immersivestatusbar;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.lib.utils.DisplayUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ImmerSiveActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immer_sive);
        ButterKnife.inject(this);
        initToolBar();

    }

    private void initToolBar() {
        toolbar.setNavigationIcon(R.mipmap.arrow_left_circle);
        toolbar.setTitle("压缩布局");
        compat();
    }

    public void compat() {

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
        /*if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            //将状态栏设置成全透明
            int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if ((params.flags & bits) == 0) {
                params.flags |= bits;
                //如果是取消全透明，params.flags &= ~bits;
                window.setAttributes(params);
            }
            //设置contentview为fitsSystemWindows
            ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
            viewGroup.getChildAt(0).setFitsSystemWindows(true);
            //给statusbar着色
            View view = new View(this);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.getStatusHeight(this)));
            view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            viewGroup.addView(view);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //亲儿子里面遇到的问题，不加fitsSystemWindows直接变成全透明样式了
            //设置contentview为fitsSystemWindows
            ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
            if (viewGroup.getChildAt(0) != null) {
                viewGroup.getChildAt(0).setFitsSystemWindows(true);
            }
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }*/

    }

    /**
     * 计算状态栏颜色
     * @param color color值
     * @param alpha alpha值
     * @return 最终的状态栏颜色
     */
    private static int calculateStatusColor(int color, int alpha) {
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }
}
