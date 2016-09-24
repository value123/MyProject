package com.example.immersivestatusbar;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ElasticCollapsingToolBarActivity extends AppCompatActivity implements View.OnTouchListener {

    private static final String TAG = "Collapsing";
    @InjectView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @InjectView(R.id.first)
    View first;
    @InjectView(R.id.second)
    View second;
    @InjectView(R.id.text1)
    TextView text1;
    @InjectView(R.id.text2)
    TextView text2;
    @InjectView(R.id.btn)
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**  * 图片全屏透明状态栏（图片位于状态栏下面）  *  * @param activity  */
        setContentView(R.layout.activity_elastic_collapsing_tool_bar);

        ButterKnife.inject(this);
        coordinatorLayout.setOnTouchListener(this);
        initView();
    }

    private void initView() {

        Gson gson = new Gson();//new一个Gson对象
        //json字符串
        String json = "{\"name\":\"guolicheng\"}";
        //new 一个Product对象
        //将一个json字符串转换为java对象
        Product product = gson.fromJson(json, new TypeToken<Product>() {
        }.getType());
        //输出
        System.out.println("Name:" + product.getName());
        System.out.println("Id:" + product.getId());
        text1.setText(product.getName());
        text2.setText(product.getId()+"");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateView();
            }
        });
    }

    private void updateView() {

        Gson gson = new Gson();//new一个Gson对象
        //json字符串
        String json = "{\"name\":\"guolicheng\",\"id\":123456,\"date\":\"2013-4-13 12:36:54\"}";
        //new 一个Product对象
        //将一个json字符串转换为java对象
        Product product = gson.fromJson(json, new TypeToken<Product>() {
        }.getType());
        //输出
        System.out.println("Name:" + product.getName());
        System.out.println("Id:" + product.getId()+"");
        text1.setText(product.getName());
        text2.setText(product.getId()+"");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView();
            }
        });
    }

    private void initToolBar() {
//        toolbar.setNavigationIcon(R.mipmap.arrow_left_circle);
//        toolbar.setTitle("压缩布局");

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        first.setY(event.getY());
        Log.d(TAG, "onTouch = " + event.getY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                return false;
        }

        return true;
    }
}
