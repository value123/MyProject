
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
import com.iss.example.view.animation.droidflakes.Droidflakes;
import com.iss.example.view.animation.pathanimation.PathAnimationActivity;

public class EgAnimation extends EgActivity {
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
        demoList.add(new Demo("API Demos", EgApiDemos.class));
        demoList.add(new Demo("Droid Flakes", Droidflakes.class));
        demoList.add(new Demo("Path Animation", PathAnimationActivity.class));
        demoList.add(new Demo("Toggles", Toggles.class));
        demoList.add(new Demo("ViewPropertyAnimator Demo", VPADemo.class));
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
