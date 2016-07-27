package com.example.retrofitrx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.retrofitrx.api.ApiService;
import com.example.retrofitrx.entity.IpInfo;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @InjectView(R.id.btn_synchronous)
    Button btnSynchronous;
    @InjectView(R.id.btn_asynchronous)
    Button btnAsynchronous;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://ip.taobao.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ButterKnife.inject(this);

    }

    @OnClick({R.id.btn_synchronous, R.id.btn_asynchronous})
    public void onClick(View view) {
        ApiService apiService = retrofit.create(ApiService.class);
        Call<IpInfo> ipInfoCall = apiService.getIpInfo("210.75.225.254");
        switch (view.getId()) {
            case R.id.btn_synchronous:
                sendSynchronousRequest(ipInfoCall);
                break;
            case R.id.btn_asynchronous:
                sendAsynchronousRequest(ipInfoCall);
                break;
        }
    }

    private void sendAsynchronousRequest(Call<IpInfo> ipInfoCall) {
        ipInfoCall.enqueue(new Callback<IpInfo>() {
            @Override
            public void onResponse(Call<IpInfo> call, Response<IpInfo> response) {
                Log.e(TAG, "sendAsynchronousRequest onResponse >>> " + response.body().toString());
            }

            @Override
            public void onFailure(Call<IpInfo> call, Throwable t) {
                Log.e(TAG, "sendAsynchronousRequest onFailure >>> " + t.getMessage().toString());
            }
        });
    }

    /**
     * android.os.NetworkOnMainThreadException
     * @param ipInfoCall
     */
    private void sendSynchronousRequest(final Call<IpInfo> ipInfoCall) {
        new Thread(){
            @Override
            public void run() {
                try {
                    Response<IpInfo> ipInfoResponse = ipInfoCall.execute();
                    String body = ipInfoResponse.body().toString();//获取返回体的字符串
                    Log.e(TAG, body);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "sendSynchronousRequest failed >>> " + e.getMessage().toString());
                }

            }
        }.start();


    }

}
