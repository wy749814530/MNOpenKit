package com.mn.okhttp3.builder;


import com.mn.okhttp3.OkHttpUtils;
import com.mn.okhttp3.request.OtherRequest;
import com.mn.okhttp3.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
