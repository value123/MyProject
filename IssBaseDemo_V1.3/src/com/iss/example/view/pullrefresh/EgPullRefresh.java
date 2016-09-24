
package com.iss.example.view.pullrefresh;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.iss.appdemo.R;
import com.iss.appdemo.activity.DemoDescImagesActivity;
import com.iss.appdemo.activity.EgActivity;
import com.iss.appdemo.adapter.DemoAdapter;
import com.iss.appdemo.bean.Demo;

public class EgPullRefresh extends EgActivity {
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
        demoList.add(new Demo("说明", DemoDescImagesActivity.class).setDrawable(new int[] {
            R.drawable.p_pullrefresh_1
        }));
        demoList.add(new Demo("Demo1:ListView", PullToRefreshListActivity.class));
        demoList.add(new Demo("Demo2:ExpandableListView", PullToRefreshExpandableListActivity.class));
        demoList.add(new Demo("Demo3:GridView", PullToRefreshGridActivity.class));
        demoList.add(new Demo("Demo4:WebView", PullToRefreshWebViewActivity.class));
        demoList.add(new Demo("Demo5:ScrollView", PullToRefreshScrollViewActivity.class));
        demoList.add(new Demo("Demo6:Horizontal ScrollView",
                PullToRefreshHorizontalScrollViewActivity.class));
        demoList.add(new Demo("Demo7:WebView Advanced", PullToRefreshWebView2Activity.class));
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
