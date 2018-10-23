package com.imooc.smartbulter.entity;

/**
 * Created by Administrator on 2018/4/23.
 * 对话列表的实体
 */

public class ChatListData {
    //type 区分左右
    private int type;
    //文本
    private String text;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
