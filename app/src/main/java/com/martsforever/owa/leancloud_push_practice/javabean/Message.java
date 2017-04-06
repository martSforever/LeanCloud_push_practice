package com.martsforever.owa.leancloud_push_practice.javabean;

import com.avos.avoscloud.AVUser;

/**
 * Created by OWA on 2017/4/6.
 */

public class Message {

    /*table name*/
    public  static final String TABLE_MESSAGE = "MESSAGE";

    /*sender*/
    private AVUser sender;
    /*receiver*/
    private AVUser receiver;

}
