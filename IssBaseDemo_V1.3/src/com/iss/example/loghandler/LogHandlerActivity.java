package com.iss.example.loghandler;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.iss.app.IssActivity;
import com.iss.appdemo.R;
import com.iss.loghandler.ErrorHandler;

public class LogHandlerActivity extends IssActivity {
	private EditText editText_number1;
	private EditText editText_number2;
	private Button button_calculate;
	private TextView textView_result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loghandler);
		
		ErrorHandler.registerNewErrorHandler(this);
        ErrorHandler.enableEmailReports("wxliaoc@isoftstone.com", "iss App test");
	}

	@Override
	protected void initView() {
		editText_number1 = (EditText)findViewById(R.id.editText_number1);
		editText_number2 = (EditText)findViewById(R.id.editText_number2);
		button_calculate = (Button)findViewById(R.id.button_calculate);
		textView_result = (TextView)findViewById(R.id.textView_result);
	}

	@Override
	protected void initData() {

	}

	@Override
	protected void setListener() {
		button_calculate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
					String number1Str = editText_number1.getText().toString();
					String number2Str = editText_number2.getText().toString();
					int number1 = Integer.parseInt(number1Str);
					int number2 = Integer.parseInt(number2Str);
					int res = number1/number2;
					textView_result.setText(res+"");
				}catch(NumberFormatException e){
					
				}
			}
		});

	}

}
