package com.iss.appdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.iss.appdemo.R;
import com.iss.appdemo.view.SwitchButton;
import com.iss.appdemo.view.SwitchButton.OnSwitchListener;

public class SwitchButtonActivity extends Activity {

	private SwitchButton mTakeSwitchBtn;
	private OnSwitchListener takeSwitchListener = new OnSwitchListener() {
		
		@Override
		public void onSwitched(boolean isSwitchOn) {
			if(!isSwitchOn) {
				Toast.makeText(SwitchButtonActivity.this, "first", Toast.LENGTH_SHORT).show();
				left.setTextColor(getResources().getColor(R.color.color_white));
				right.setTextColor(getResources().getColor(R.color.color_black));
			} else {
				Toast.makeText(SwitchButtonActivity.this, "second", Toast.LENGTH_SHORT).show();
				left.setTextColor(getResources().getColor(R.color.color_black));
				right.setTextColor(getResources().getColor(R.color.color_white));
			}
		}
	};
	private TextView left;
	private TextView right;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.switch_button);
		mTakeSwitchBtn = (SwitchButton) findViewById(R.id.switch_btn1);
		mTakeSwitchBtn.setImageResource(R.drawable.switch_on);
		mTakeSwitchBtn.setOnSwitchListener(takeSwitchListener );
		left = (TextView)findViewById(R.id.take_car_left);
		right = (TextView)findViewById(R.id.take_car_right);
	}

}
