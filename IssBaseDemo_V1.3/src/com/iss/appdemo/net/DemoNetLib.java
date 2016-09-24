package com.iss.appdemo.net;

import java.io.IOException;

import org.json.JSONException;

import com.iss.appdemo.bean.DistrictInfo;

import android.content.Context;

public class DemoNetLib {
	private static DemoNetLib mLib;
	protected DemoRequest mRequest;
	protected DemoParse mParse;
	protected Context mContext;

	private DemoNetLib(Context context) {
		mRequest = new DemoRequest(context);
		mParse = new DemoParse(context);
		mContext = context;
	}

	public synchronized static DemoNetLib getInstance(Context context) {
		if (mLib == null) {
			mLib = new DemoNetLib(context);
		}
		return mLib;
	}
	
	public DistrictInfo getDistrictInfo() throws IOException, JSONException{
		String json = mRequest.getDistrictInfoRequest();
		return mParse.parseDistrictInfo(json);
	}


}
