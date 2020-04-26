package com.mn.okhttp3;


import com.google.gson.Gson;
import com.mn.okhttp3.callback.IGenericsSerializator;

/**
 * Created by jz on 2016/6/23.
 */
public class JsonGenericsSerializator implements IGenericsSerializator {
    Gson mGson = new Gson();
    @Override
    public <T> T transform(String response, Class<T> classOfT) {
        return mGson.fromJson(response, classOfT);
    }
}
