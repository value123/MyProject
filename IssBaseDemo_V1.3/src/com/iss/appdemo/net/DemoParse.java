package com.iss.appdemo.net;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.iss.appdemo.bean.DistrictInfo;

public class DemoParse {
	protected Context mContext;
	private Gson gson;

	public DemoParse(Context context) {
		mContext = context;
		gson = new Gson();
	}

	public DistrictInfo parseDistrictInfoTest(String json) throws JSONException {
		DistrictInfo districtInfo = null;
		long fastjsonTime = 0;
		long ggsonTime = 0;
		long localTime = 0;
		int count = 100;
		for(int i=0;i<count;i++){
			long start1 = System.currentTimeMillis();
			districtInfo = JSON.parseObject(json, DistrictInfo.class);
			long end1 = System.currentTimeMillis();
			long dotime1 = end1-start1;
			fastjsonTime = fastjsonTime+dotime1;
			Log.i("liaowenxin","fastjson one time:"+dotime1);
			
			long start2 = System.currentTimeMillis();
			districtInfo = gson.fromJson(json, DistrictInfo.class);
			long end2 = System.currentTimeMillis();
			long dotime2 = end2-start2;
			ggsonTime = ggsonTime+dotime2;
			Log.i("liaowenxin","GsonTime one time:"+dotime2);
			
			long start3 = System.currentTimeMillis();
			districtInfo = new DistrictInfo();
			JSONObject jsonObj = new JSONObject(json);
			districtInfo.parseJSON(jsonObj);
			long end3 = System.currentTimeMillis();
			long dotime3 = end3-start3;
			localTime = localTime+dotime3;
			Log.i("liaowenxin","localTime one time:"+dotime3);
		}
		Log.i("liaowenxin","fastjson total time:"+fastjsonTime+" ,ggsonTime  total time:"+ ggsonTime+" ,localTime  total time:"+ localTime);
		return districtInfo;
	}
	
	public DistrictInfo parseDistrictInfo(String json) throws JSONException {
		return new DistrictInfo().parseJSON(gson, json);
	}

}
