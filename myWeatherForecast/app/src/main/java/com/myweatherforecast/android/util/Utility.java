package com.myweatherforecast.android.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.myweatherforecast.android.db.City;
import com.myweatherforecast.android.db.Country;
import com.myweatherforecast.android.db.Province;
import com.myweatherforecast.android.gson.Weather;

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

    /**
     * 将返回的JSON数据解析成Weather实体类
     */
    public static Weather handleWeatherResponse(String response) {
        try {
            //通过JSONObject 和 JSONArray 将天气数据中的主体内容解析出来
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String WeatherContent = jsonArray.getJSONObject(0).toString();





            //由于之前已经按照上面的数据格式定义过相应的GSON实体类，因此只需要通过调用fromJson()方法
            //就能直接将JSON数据转为Weather对象了
            return new Gson().fromJson(WeatherContent, Weather.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
