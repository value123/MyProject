package com.example.scroller;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends Activity implements View.OnTouchListener {

    private static final String TAG = "MainActivity";
    @InjectView(R.id.textView)
    TextView textView;
    @InjectView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @InjectView(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        findViewById(R.id.relativeLayout).setOnTouchListener(this);
        initWebView();
    }

    private void initWebView() {
        WebSettings webViewSettings = webView.getSettings();
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webViewSettings.setJavaScriptEnabled(true);
        webViewSettings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= 16) {
            webViewSettings.setAllowFileAccessFromFileURLs(true);
            webViewSettings.setAllowUniversalAccessFromFileURLs(true);
        }

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        if (Build.VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        webView.loadUrl("http://www.baidu.com");

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {


        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                /*int y = (int) motionEvent.getRawY();
                int x = (int) motionEvent.getRawX();//相对于自身左顶点的X*/
                int x = (int) motionEvent.getX();//相对于自身左顶点的X
                int y = (int) motionEvent.getY();//相对于自身左顶点的Y
                Log.d(TAG, "x=" + x + ">>y=" + y);
                startScroll(x, y);
//                int[] locations = new int[2];
//                relativeLayout.getLocationOnScreen(locations);
//                Log.d(TAG,"x="+x+">>y="+y+">>locations[1] = " +locations[1]);
//                startScroll(x, (int) (y-locations[1]));
                break;
            case MotionEvent.ACTION_UP:
                startScroll(0, 0);
                break;
        }
        return true;
    }

    /**
     * view的scroll事件其实是针对于子view的,
     *
     * @param x 相对距离 x = dx = startX-currentX;
     * @param y 相对距离 y = dy = startY-currentY;
     */
    private void startScroll(int x, int y) {
        relativeLayout.scrollTo(-x, -y);
    }
}
