package com.iss.example.db;

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

public class EgDB extends EgActivity {
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
		demoList.add(new Demo("说明", DemoDescImagesActivity.class).setDrawable(new int[]{R.drawable.p_db_1,R.drawable.p_db_2}));
		demoList.add(new Demo("Demo:数据库使用示例", DBExampleActivity.class));
		adapter.addItems(demoList, true);
	}

	@Override
	protected void setListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Demo demo = adapter.getItem(arg2);
				startActivity(demo);
			}
		});

	}

}
