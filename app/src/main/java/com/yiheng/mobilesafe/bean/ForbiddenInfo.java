package com.yiheng.mobilesafe.bean;

/**
 * Created by Yiheng on 2016/11/4 0004.
 */

public class ForbiddenInfo {
    public static final int TYPE_NONE = 0;
    public static final int TYPE_NUMBER = 1;
    public static final int TYPE_SMS = 2;
    public static final int TYPE_ALL = 3;

   public String forbiddenNumber;
   public int forbiddenType;

    public ForbiddenInfo() {
    }

    public ForbiddenInfo(String forbiddenNumber, int forbiddenType) {

        this.forbiddenNumber = forbiddenNumber;
        this.forbiddenType = forbiddenType;
    }
}
