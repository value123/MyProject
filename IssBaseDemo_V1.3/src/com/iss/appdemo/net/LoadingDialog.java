package com.iss.appdemo.net;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.TextView;

import com.iss.app.AbsDialog;
import com.iss.appdemo.R;

public class LoadingDialog extends AbsDialog {
	private Activity mActivity;
	private TextView loadingText;

	public LoadingDialog(Activity context) {
		super(context, R.style.dialog_normal);
		mActivity = context;
		setContentView(R.layout.loading);
		setProperty(1,1);
		setCancelable(false);
	}
	
	public void setLoadingMessage(String message){
		if(!TextUtils.isEmpty(message)){
			loadingText.setText(message);
		}
	}

	public void show(String message) {
		if(!TextUtils.isEmpty(message)){
			loadingText.setText(message);
		}
		super.show();
	}

	public void close() {
		if (!mActivity.isFinishing()) {
			mActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (isShowing()) {
						dismiss();
					}
				}
			});
		}
	}


    @Override
    protected void initView() {
        loadingText = (TextView) findViewById(R.id.loading_text);
    }

    @Override
    protected void initData() {
        
    }

    @Override
    protected void setListener() {
        
    }

}
