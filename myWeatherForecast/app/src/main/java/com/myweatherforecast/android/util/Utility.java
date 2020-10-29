package com.myweatherforecast.android.util;

import android.text.TextUtils;

import com.myweatherforecast.android.db.City;
import com.myweatherforecast.android.db.Country;
import com.myweatherforecast.android.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {
    /**
     * 解析和处理服务器返回的省级json数据
     *
     * @param response
     * @return
     */
    public static boolean handleProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allProvinces = new JSONArray(response);//这里要catch一下
                for (int i = 0; i < allProvinces.length(); i++) {
                    JSONObject provinceObject = allProvinces.getJSONObject(i);//取json里面的对象
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();//.save()是DataSupport里面提供的方法
                    //save()方法将数据存储到数据库中
                }
                return true;//返回成功
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;//返回失败
    }


    /**
     * 解析和处理服务器返回的市级数据
     *
     * @param response
     * @param provinceId
     * @return
     */
    public static boolean handleCityResponse(String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCity = new JSONArray(response);
                for (int i = 0; i < allCity.length(); i++) {
                    JSONObject cityObject = allCity.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return false;
    }


    /**
     * 解析和处理服务器返回的县级数据
     *
     * @param response
     * @param cityId
     * @return
     */
    public static boolean handleCountResponse(String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCountries = new JSONArray(response);
                for (int i = 0; i < allCountries.length(); i++) {
                    JSONObject countryObject = allCountries.getJSONObject(i);
                    Country country = new Country();
                    country.setCountryName(countryObject.getString("name"));
                    country.setWeatherId(countryObject.getString("weather_id"));
                    country.setCityId(cityId);
                    country.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return false;
    }
}
