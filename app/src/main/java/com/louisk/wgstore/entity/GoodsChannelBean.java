package com.louisk.wgstore.entity;

/**
 * des:商品分类
 *
 */
public class GoodsChannelBean {
    private String channelId;
    private String channelName;

    public GoodsChannelBean(String channelId, String channelName){
        this.channelId=channelId;
        this.channelName=channelName;
    }
    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
