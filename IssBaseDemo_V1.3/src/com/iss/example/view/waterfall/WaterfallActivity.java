package com.iss.example.view.waterfall;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.iss.app.IssActivity;
import com.iss.appdemo.R;
import com.iss.appdemo.adapter.WaterAdapter;
import com.iss.example.imageloader.Images;
import com.iss.view.pulltorefresh.PullToRefreshBase;
import com.iss.view.pulltorefresh.PullToRefreshBase.OnRefreshListener2;
import com.iss.view.pulltorefresh.PullToRefreshWaterView;
import com.iss.view.waterfall.NetImageView;
import com.iss.view.waterfall.WaterView;
import com.iss.view.waterfall.WaterView.OnResetViewDataListener;

public class WaterfallActivity extends IssActivity {

	private PullToRefreshWaterView waterView;
	private WaterAdapter waterAdapter;
	private GetPicTask mTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_waterfall);
	}

	@Override
	protected void initView() {
		waterView = (PullToRefreshWaterView) findViewById(R.id.waterview);

	}

	@Override
	protected void initData() {
		waterAdapter = new WaterAdapter(this);
		waterView.setAdapter(waterAdapter);
		waterAdapter.addItem(Images.imageThumbUrls, true);

	}

	@Override
	protected void setListener() {
		waterView.setOnResetViewDataListener(new OnResetViewDataListener() {

			@Override
			public void onRecycleData(View view) {
				if (view instanceof NetImageView) {
					((NetImageView) view).recycle(true);
				}

			}

			@Override
			public void onLoadData(View view) {
				if (view instanceof NetImageView) {
					((NetImageView) view).reload(false);
				}
			}
		});

		waterView.setOnRefreshListener(new OnRefreshListener2<WaterView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<WaterView> refreshView) {
                if (mTask != null) {
                    mTask.cancel(true);
                }
                mTask = new GetPicTask(true);
                mTask.execute();
                
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<WaterView> refreshView) {
                if (mTask != null) {
                    mTask.cancel(true);
                }
                mTask = new GetPicTask(false);
                mTask.execute();
                
            }

		});

	}

	@Override
	protected void onPause() {
		super.onPause();
		waterView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		waterView.onResume();
	}

	class GetPicTask extends AsyncTask<String, String, String> {
		private boolean refresh;

		public GetPicTask(boolean refresh) {
			this.refresh = refresh;
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "";
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			waterView.onRefreshComplete();
			waterAdapter.addItem(Images.imageThumbUrls, refresh);
		}

	}

}
