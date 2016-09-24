
package com.iss.example.view.animation;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.iss.appdemo.R;
import com.iss.appdemo.activity.EgActivity;
import com.iss.appdemo.adapter.DemoAdapter;
import com.iss.appdemo.bean.Demo;
import com.iss.example.view.animation.apidemos.AnimationCloning;
import com.iss.example.view.animation.apidemos.AnimationLoading;
import com.iss.example.view.animation.apidemos.AnimationSeeking;
import com.iss.example.view.animation.apidemos.AnimatorEvents;
import com.iss.example.view.animation.apidemos.BouncingBalls;
import com.iss.example.view.animation.apidemos.CustomEvaluator;
import com.iss.example.view.animation.apidemos.MultiPropertyAnimation;
import com.iss.example.view.animation.apidemos.ReversingAnimation;

public class EgApiDemos extends EgActivity {
    private ListView listView;

    private DemoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_list);
    }

    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.demo_list);
    }

    @Override
    protected void initData() {
        adapter = new DemoAdapter(this);
        listView.setAdapter(adapter);

        ArrayList<Demo> demoList = new ArrayList<Demo>();
        demoList.add(new Demo("Animation Cloning", AnimationCloning.class));
        demoList.add(new Demo("Animation Loading", AnimationLoading.class));
        demoList.add(new Demo("Animation Seeking", AnimationSeeking.class));
        demoList.add(new Demo("Animation Events", AnimatorEvents.class));
        demoList.add(new Demo("Bouncing Balls", BouncingBalls.class));
        demoList.add(new Demo("Custom Evaluator", CustomEvaluator.class));
        demoList.add(new Demo("MultiProperty Animation", MultiPropertyAnimation.class));
        demoList.add(new Demo("Reversing Animation", ReversingAnimation.class));
        adapter.addItems(demoList, true);
    }

    @Override
    protected void setListener() {
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Demo demo = adapter.getItem(arg2);
                startActivity(demo);
            }
        });

    }


}
