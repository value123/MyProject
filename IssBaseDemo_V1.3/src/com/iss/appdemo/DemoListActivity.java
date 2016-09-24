package com.iss.appdemo;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.iss.appdemo.activity.EgActivity;
import com.iss.appdemo.adapter.DemoAdapter;
import com.iss.appdemo.bean.Demo;
import com.iss.example.app.EgApp;
import com.iss.example.bean.EgBean;
import com.iss.example.db.EgDB;
import com.iss.example.httpclient.EgHttpClient;
import com.iss.example.imageloader.EgImageLoader;
import com.iss.example.loghandler.EgLogHandler;
import com.iss.example.view.animation.EgAnimation;
import com.iss.example.view.calendar.EgCalendar;
import com.iss.example.view.common.EgCommon;
import com.iss.example.view.leftgallery.EgLeftGallery;
import com.iss.example.view.orientationviewpager.EgOrientationVP;
import com.iss.example.view.photoview.EgPhotoView;
import com.iss.example.view.pullrefresh.EgPullRefresh;
import com.iss.example.view.waterfall.EgWaterfall;
import com.iss.example.view.wheelview.EgWheelView;

public class DemoListActivity extends EgActivity {
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
		demoList.add(new Demo("com.iss.app", EgApp.class));
		demoList.add(new Demo("com.iss.bean", EgBean.class));
		demoList.add(new Demo("com.iss.db", EgDB.class));
		demoList.add(new Demo("com.iss.httpclient", EgHttpClient.class));
		demoList.add(new Demo("com.iss.imageLoader", EgImageLoader.class));
		demoList.add(new Demo("com.iss.loghandler", EgLogHandler.class));
		demoList.add(new Demo("com.iss.view.calendar", EgCalendar.class));
		demoList.add(new Demo("com.iss.view.common", EgCommon.class));
		demoList.add(new Demo("com.iss.view.leftgallery", EgLeftGallery.class));
		demoList.add(new Demo("com.iss.view.photoview", EgPhotoView.class));
		demoList.add(new Demo("com.iss.view.pulltorefresh", EgPullRefresh.class));
		demoList.add(new Demo("com.iss.view.waterfall", EgWaterfall.class));
		demoList.add(new Demo("com.iss.view.wheel", EgWheelView.class));
		demoList.add(new Demo("Animation",EgAnimation.class));
		demoList.add(new Demo("OrientationViewPager",EgOrientationVP.class));
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
