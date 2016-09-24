
package com.iss.appdemo.net;

import java.io.IOException;

import android.content.Context;

import com.iss.httpclient.NormalHttpClient;
import com.iss.httpclient.core.HttpRequestException;
import com.iss.httpclient.core.HttpResponse;
import com.iss.httpclient.core.ParameterList;
import com.iss.httpclient.core.ParameterList.HeaderParameter;
import com.iss.httpclient.core.ParameterList.StringParameter;
import com.iss.utils.StringUtil;

public class DemoRequest {

    /**
     * 总的接口地址
     */
    private String url = "http://api.shupeng.com";

    protected Context mContext;

    private NormalHttpClient mClient;

    public DemoRequest(Context context) {
        mClient = new NormalHttpClient();

        mContext = context;
    }


    public String getHotbookRequest(int p, int psize) throws HttpRequestException {
        ParameterList params = mClient.newParams();
        params.add(new StringParameter("p", p + ""));
        params.add(new StringParameter("psize", psize + ""));
        // 在书朋上申请到的key
        params.add(new HeaderParameter("user-agent", "ad5719bb81f30a34ce9bd22931c39370"));
        HttpResponse res = mClient.get(url + "/hotbook", params);
        return res.getBodyAsString();
    }

    public String getBoardRequest(int p, int psize) throws HttpRequestException {
        ParameterList params = mClient.newParams();
        params.add(new StringParameter("p", p + ""));
        params.add(new StringParameter("psize", psize + ""));
        // 在书朋上申请到的key
        params.add(new HeaderParameter("user-agent", "ad5719bb81f30a34ce9bd22931c39370"));
        HttpResponse res = mClient.get(url + "/board", params);
        return res.getBodyAsString();
    }

    public String getDistrictInfoRequest() throws IOException {
        return StringUtil.inputToString(mContext.getAssets().open("json.txt"), "");
    }

}
