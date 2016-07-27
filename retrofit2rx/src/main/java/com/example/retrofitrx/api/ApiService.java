package com.example.retrofitrx.api;

import com.example.retrofitrx.entity.IpInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by shenwenjie on 27/7/2016.
 */
public interface ApiService {

    @GET("/service/getIpInfo.php")
    Call<IpInfo> getIpInfo(@Query("ip")String ip);
}
