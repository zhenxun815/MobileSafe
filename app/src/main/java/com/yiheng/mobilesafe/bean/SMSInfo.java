package com.yiheng.mobilesafe.bean;

/**
 * Created by Yiheng on 2016/11/7 0007.
 */

public class SMSInfo {
    /**
     * address : address
     * date : 1
     * read : 1
     * type : 1
     * body : body
     */

    public String address;
    public long date;
    public int read;
    public int type;
    public String body;

    public SMSInfo() {
    }

    public SMSInfo(String address, long date, int read, int type, String body) {
        this.address = address;
        this.date = date;
        this.read = read;
        this.type = type;
        this.body = body;
    }

    @Override
    public String toString() {
        return "SMSInfo{" +
                "address='" + address + '\'' +
                ", date=" + date +
                ", read=" + read +
                ", type=" + type +
                ", body='" + body + '\'' +
                '}';
    }
}
