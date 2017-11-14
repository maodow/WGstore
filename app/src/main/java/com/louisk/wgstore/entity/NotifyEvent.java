package com.louisk.wgstore.entity;

/**
 * Created by Admin on 2017/11/7.
 *
 */

public class NotifyEvent {
    private String mMsg;
    public NotifyEvent(String msg) {
        mMsg = msg;
    }
    public String getMsg(){
        return mMsg;
    }
}
