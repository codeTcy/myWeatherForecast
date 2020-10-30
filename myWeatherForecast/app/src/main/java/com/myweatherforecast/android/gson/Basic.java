package com.myweatherforecast.android.gson;

import com.google.gson.annotations.SerializedName;


/**
 * JSON中一些字段可能不太适合直接作为java字段来命名
 * 使用SerializedName注解的形式来让JSON 字段和java 字段之间建立映射关系
 */
public class Basic {

    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update {
        @SerializedName("loc")
        public String updateTieme;
    }


}
