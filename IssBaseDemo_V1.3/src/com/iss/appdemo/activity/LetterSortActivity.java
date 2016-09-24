/**
 *  右侧字幕列表快速定位demo view
 * @author perry
 * @ create time 2012-11-16
 * */
package com.iss.appdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.iss.appdemo.R;
import com.iss.loghandler.ErrorHandler;
import com.iss.utils.PhoneInfo;
import com.iss.view.common.ToastAlone;

public class LetterSortActivity extends Activity {

	/**
	 * 单独的toast使用测试demo
	 */
	Button buttonToast ;
	
	Button buttonError ;
	
	Button buttonChannel;
	
	TextView textViewHello;
	
	Activity activity;

	
	
	public static int TOAST_COUNT;
	
	String channelIdStr;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ErrorHandler.registerNewErrorHandler(this);
        ErrorHandler.enableEmailReports("perry.li@itotem.com.cn", "itotem App test");
        channelIdStr = PhoneInfo.getMetaData(this, null);
        activity = this;
        initView();
        setListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    private void initView(){
    	 buttonToast = (Button)findViewById(R.id.button_toast_alone);
    	 buttonError = (Button)findViewById(R.id.button_error_handler);
    	 buttonChannel = (Button)findViewById(R.id.button_channel);
    	 textViewHello = (TextView)findViewById(R.id.text_hello);
    	 textViewHello.setText(PhoneInfo.getPhoneNumber(LetterSortActivity.this));
    }
    private void setListener(){
    	 buttonToast.setOnClickListener(buttonToastListener);
    	 buttonError.setOnClickListener(buttonErrorListener);
    	 buttonChannel.setOnClickListener(buttonChannelListener);
    }
    OnClickListener buttonToastListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ToastAlone.showToast(activity, "点击ToastAlone次数显示:"+TOAST_COUNT++, Toast.LENGTH_SHORT);
		}
	};
	
	OnClickListener buttonErrorListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String str = null;
			str.toString();
		}
	};
	/**
	 * 获得channelId
	 */
	private OnClickListener buttonChannelListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ToastAlone.showToast(LetterSortActivity.this, "当前的ChannelId:"+channelIdStr, Toast.LENGTH_SHORT);
		}
	};
    
}
