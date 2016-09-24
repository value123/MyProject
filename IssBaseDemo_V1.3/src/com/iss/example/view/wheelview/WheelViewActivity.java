package com.iss.example.view.wheelview;

import java.io.IOException;

import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.iss.app.IssActivity;
import com.iss.appdemo.R;
import com.iss.appdemo.bean.District;
import com.iss.appdemo.bean.DistrictInfo;
import com.iss.appdemo.net.DemoAsyncTask;
import com.iss.appdemo.net.DemoNetLib;
import com.iss.appdemo.view.ShopWheelViewDialog;
import com.iss.appdemo.view.ShopWheelViewDialog.ConfirmAction;
import com.iss.view.common.ToastAlone;

public class WheelViewActivity extends IssActivity {
	
	private Button button;
	private ShopWheelViewDialog dialog;
	private GetDataTask mTask;
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wheelview);
	}

	@Override
	protected void setListener() {
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.show(new ConfirmAction() {
					
					@Override
					public void doAction(District root, District child) {
						textView.setText(root.name+"|"+child.name);
					}
				});
			}
		});
	}

	@Override
	protected void initData() {
		dialog = new ShopWheelViewDialog(this);
		mTask = new GetDataTask(this);
		mTask.execute();
	}

	@Override
	protected void initView() {
		button = (Button)findViewById(R.id.picker_button_show);
		textView = (TextView)findViewById(R.id.picker_text_check);
	}
	
	class GetDataTask extends DemoAsyncTask<String, String, DistrictInfo>{

		public GetDataTask(Activity activity) {
			super(activity);
		}

		@Override
		protected DistrictInfo doInBackground(String... params) {
			DistrictInfo info = null;
			try {
				info = DemoNetLib.getInstance(getApplicationContext()).getDistrictInfo();
			} catch (IOException e) {
			    exception = getResources().getString(R.string.exception_network);
			} catch (JSONException e) {
			    exception = getResources().getString(R.string.exception_json);
			}
			return info;
		}

		@Override
		protected void onPostExecute(DistrictInfo result) {
			super.onPostExecute(result);
			if(exception!=null){
				ToastAlone.showToast(getApplicationContext(), exception, Toast.LENGTH_SHORT).show();
				return;
			}
			dialog.setData(result);
		}
		
		
		
	}

}
