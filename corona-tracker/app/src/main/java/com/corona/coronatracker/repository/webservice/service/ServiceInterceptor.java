package com.corona.coronatracker.repository.webservice.service;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import io.reactivex.Observable;

public class ServiceInterceptor {
    private ApiService apiService;
    private Context context;

    public ServiceInterceptor(ApiService apiService, Context context) {
        this.apiService = apiService;
        this.context = context;
    }

    public Observable<JSONArray> getCoronaDetails() {
        Log.d("aaaaaaaa", "fdfdf");
        return apiService.getCoronaDetails().map(jsonElement -> new JSONArray(jsonElement.toString()));
    }
}
