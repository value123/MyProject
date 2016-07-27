package com.example.retrofitrx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.retrofitrx.api.ApiService;
import com.example.retrofitrx.api.RxApiService;
import com.example.retrofitrx.entity.IpInfo;
import com.lib.utils.ThreadUtil;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitRxActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://ip.taobao.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//如果是使用Rx+retrofit需要设置CallAdapter
                .build();


    }

    @OnClick({R.id.btn_synchronous, R.id.btn_asynchronous})
    public void onClick(View view) {
        RxApiService apiService = retrofit.create(RxApiService.class);
        Observable<IpInfo> ipInfoObservable = apiService.getIpInfo("210.75.225.254");
        switch (view.getId()) {
            case R.id.btn_synchronous:
                doObserval(ipInfoObservable);
                break;
            case R.id.btn_asynchronous:
                doObserval(ipInfoObservable);
                break;
        }
    }

    private void doObserval(Observable<IpInfo> ipInfoObservable) {
        //1,默认任务执行在当前线程,回调在当前线程
        // 2.如果只指定执行线程,那么回调也会在指定线程中
        // 3.一般网络请求可以进行如下指定
        ipInfoObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<IpInfo>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted isMainthread = " + ThreadUtil.isMainThread());
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError isMainthread = " + ThreadUtil.isMainThread2() + ">>>e="+e.getMessage().toString());
            }

            @Override
            public void onNext(IpInfo ipInfo) {
                Log.d(TAG, "onNext isMainthread = " + ThreadUtil.isMainThread2()+"   ipInfo = " + ipInfo.toString());
            }
        });


    }


}
