package com.martsforever.owa.leancloud_push_practice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.LogUtil;


import java.util.Iterator;
import java.util.Set;

public class MyCustomReceiver extends BroadcastReceiver {
    private static final String TAG = "MyCustomReceiver";
    private HandleMessage handleMessage;
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.log.d(TAG, "Get Broadcat");
        try {
            String action = intent.getAction();
            String channel = intent.getExtras().getString("com.avos.avoscloud.Channel");
            //获取消息内容
            JSONObject jsonObject = JSON.parseObject(intent.getExtras().getString("com.avos.avoscloud.Data"));
            if (jsonObject != null) {

                Log.d(TAG, "got action " + action + " on channel " + channel + " with:");
                Set<String> itr = jsonObject.keySet();
                for (String key : itr) {
                    Log.d(TAG, "..." + key + " => " + jsonObject.getString(key));
                }
                if (handleMessage!=null){
                    handleMessage.receiveMessage(jsonObject);
                }else {
                    System.out.println("handleMessage is null");
                }
            }
        } catch (JSONException e) {
            Log.d(TAG, "JSONException: " + e.getMessage());
        }
    }

    interface HandleMessage{
        public void receiveMessage(JSONObject jsonObject);
    }

    public void setHandleMessage(HandleMessage handleMessage) {
        this.handleMessage = handleMessage;
    }
}