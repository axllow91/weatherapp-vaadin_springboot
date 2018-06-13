package com.mrn.weatherapp.controller;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WeatherService {
    // client
    private OkHttpClient httpClient;

    // response obj
    private Response response;

    private String cityName;
    private String unit;

    private static final String FIRST_PART_API_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static final String UNITS_URL = "&units=";
            private static final String FINAL_PART_API_URL = "&appid=6fda4447272143aaf31386545c693fb5";


    // names of JSON OBJECTS in the object array
    private static final String MAIN_JSONOBJ = "main";
    private static final String WIND_JSONOBJ = "wind";
    private static final String SYS_JSONOBJ = "sys";


    //
    public JSONObject getWeather() {

        httpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(FIRST_PART_API_URL + getCityName() + UNITS_URL + getUnit() + FINAL_PART_API_URL)
                .build();

        try {
            // getting the response with the new request and by executing it
            response = httpClient.newCall(request).execute();

            // return the response in form of a new JSONObject
            if (response.body() != null) {
                return new JSONObject(response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // return null if no response
        return null;
    }

    public JSONArray getWeatherArray() {
        try {
            JSONArray weatherJSONArray;
            weatherJSONArray = getWeather().getJSONArray("weather");
            return weatherJSONArray;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getMainObject() throws JSONException {
        // get the main json object
        return getWeather().getJSONObject(MAIN_JSONOBJ);
    }

    public JSONObject getWind() throws JSONException {
        // get the wind object
        return getWeather().getJSONObject(WIND_JSONOBJ);
    }

    public JSONObject getSYS() throws JSONException {
        // get the sunset obj
        return getWeather().getJSONObject(SYS_JSONOBJ);
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
