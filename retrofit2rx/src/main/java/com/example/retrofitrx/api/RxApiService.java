package com.example.retrofitrx.api;

import com.example.retrofitrx.entity.IpInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by shenwenjie on 27/7/2016.
 */
public interface RxApiService {

    @GET("/service/getIpInfo.php")
    Observable<IpInfo> getIpInfo(@Query("ip") String ip);
}
