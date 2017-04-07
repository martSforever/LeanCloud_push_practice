package com.martsforever.owa.leancloud_push_practice;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVPush;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.LogUtil;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SendCallback;
import com.martsforever.owa.leancloud_push_practice.javabean.Person;
import com.martsforever.owa.leancloud_push_practice.util.ShowMessageUtil;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyCustomReceiver";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.log.d(TAG, "Get Broadcat");
        System.out.println("init");
        testPushService();
        registerReceiver();
        final EditText edit = (EditText) findViewById(R.id.edit);
        final EditText edit2 = (EditText) findViewById(R.id.edit2);

        Button button1 = (Button) findViewById(R.id.btn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMessageUtil.tosatFast("button1", MainActivity.this);
                /*push to all android device*/
                AVPush push = new AVPush();
                JSONObject object = new JSONObject();
                object.put("alert", "android android android android");
                push.setPushToAndroid(true);
                push.setData(object);
                push.sendInBackground(new SendCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            // push successfully.
                            ShowMessageUtil.tosatFast("push successful", MainActivity.this);
                        } else {
                            // something wrong.
                            ShowMessageUtil.tosatFast(e.getMessage(), MainActivity.this);
                        }
                    }
                });
            }
        });
        Button button2 = (Button) findViewById(R.id.btn2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String installationId = edit.getText().toString();
                /*push to specific android device|*/
                AVQuery pushQuery = AVInstallation.getQuery();
                // 假设 THE_INSTALLATION_ID 是保存在用户表里的 installationId，
                // 可以在应用启动的时候获取并保存到用户表
                AVPush push = new AVPush();
                String action = "com.avos.UPDATE_STATUS";
                String name = "weishengjian";
                String tag = "test leancloud push";
                JSONObject data;
                data = new JSONObject();
                data.put("action", action);
                data.put("name", name);
                data.put("tag", tag);
                data.put("message",edit2.getText().toString().trim());

                push.setData(data);
                push.setCloudQuery("select * from _Installation where installationId ='" + installationId + "'");
                push.sendInBackground(new SendCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            // push successfully.
                            ShowMessageUtil.tosatFast("push successful", MainActivity.this);
                        } else {
                            // something wrong.
                            ShowMessageUtil.tosatFast(e.getMessage(), MainActivity.this);
                        }
                    }
                });
            }
        });
    }

    /*test leancloud data store service*/
    private void testStoreService() {
        AVQuery<AVUser> query = new AVQuery<>(Person.TABLE_PERSON);
        query.getFirstInBackground(new GetCallback<AVUser>() {
            @Override
            public void done(AVUser user, AVException e) {
                if (e == null) {
                    System.out.println("ssssss");
                    ShowMessageUtil.tosatFast(user.getUsername(), MainActivity.this);
                } else {
                    ShowMessageUtil.tosatFast(e.getMessage(), MainActivity.this);
                }
            }
        });
    }

    /**
     * test leancloud push service
     */
    private void testPushService() {
        AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    System.out.println("save installation successful!");
                    String installationId = AVInstallation.getCurrentInstallation().getInstallationId();
                    System.out.println(installationId);
                    // 设置默认打开的 Activity
                    PushService.setDefaultPushCallback(MainActivity.this, Main2Activity.class);
                } else {
                    ShowMessageUtil.tosatFast(e.getMessage(), MainActivity.this);
                }
            }
        });
    }
    private void registerReceiver() {
        System.out.println("retgister receiver 1111111");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("com.avos.UPDATE_STATUS");
        MyCustomReceiver myCustomReceiver = new MyCustomReceiver();
        myCustomReceiver.setHandleMessage(new MyCustomReceiver.HandleMessage() {
            @Override
            public void receiveMessage(JSONObject jsonObject) {
                ShowMessageUtil.tosatFast(jsonObject.getString("message"),MainActivity.this);
            }
        });
        registerReceiver(myCustomReceiver, intentFilter);
    }
}
