package com.martsforever.owa.leancloud_push_practice.handle;

/**
 * Created by OWA on 2017/4/7.
 */

public class Message {

    public Message() {
    }

    public Message(String handleClassName) {
        this.handleClassName = handleClassName;
    }

    private String handleClassName;

    public String getHandleClassName() {
        return handleClassName;
    }

    public void setHandleClassName(String handleClassName) {
        this.handleClassName = handleClassName;
    }
}
