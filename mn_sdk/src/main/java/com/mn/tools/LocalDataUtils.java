package com.mn.tools;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import MNSDK.MNOpenSDK;

/**
 * Created by hjz on 2017/8/31.
 */

public class LocalDataUtils {

    private static String TAG = LocalDataUtils.class.getSimpleName();
    private static String errMsg = "appkey ,appSecret or domain is not set, please see the MNOpenSDK.initWithKeyAndSecret(Context,String,String) method";

    public static void setUseId(String userId) {
        try {
            SharedPreferences preferences = MNOpenSDK.mContext.getSharedPreferences(TAG, MNOpenSDK.mContext.MODE_PRIVATE);
            Editor edit = preferences.edit();
            edit.putString("mn_useId", userId);
            edit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getUseId() {
        String tem = "";
        if (MNOpenSDK.mContext == null) {
            Log.e("BaseHelper", errMsg);
            return "";
        }
        try {
            SharedPreferences preferences = MNOpenSDK.mContext.getSharedPreferences(TAG, MNOpenSDK.mContext.MODE_PRIVATE);
            tem = preferences.getString("mn_useId", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tem;
    }


    public static void setUserName(String username) {
        try {
            SharedPreferences preferences = MNOpenSDK.mContext.getSharedPreferences(TAG, MNOpenSDK.mContext.MODE_PRIVATE);
            Editor edit = preferences.edit();
            edit.putString("mn_username", username);
            edit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getUserName() {
        String tem = "";
        if (MNOpenSDK.mContext == null) {
            Log.e("BaseHelper", errMsg);
            return "";
        }
        try {
            SharedPreferences preferences = MNOpenSDK.mContext.getSharedPreferences(TAG, MNOpenSDK.mContext.MODE_PRIVATE);
            tem = preferences.getString("mn_username", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tem;
    }

    public static void setPassword(String password) {
        try {
            SharedPreferences preferences = MNOpenSDK.mContext.getSharedPreferences(TAG, MNOpenSDK.mContext.MODE_PRIVATE);
            Editor edit = preferences.edit();
            edit.putString("mn_password", password);
            edit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getPassword() {
        String tem = "";
        if (MNOpenSDK.mContext == null) {
            Log.e("BaseHelper", errMsg);
            return "";
        }
        try {
            SharedPreferences preferences = MNOpenSDK.mContext.getSharedPreferences(TAG, MNOpenSDK.mContext.MODE_PRIVATE);
            tem = preferences.getString("mn_password", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tem;
    }
}
