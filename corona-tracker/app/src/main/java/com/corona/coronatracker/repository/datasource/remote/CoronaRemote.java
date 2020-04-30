package com.corona.coronatracker.repository.datasource.remote;

import org.json.JSONArray;
import org.json.JSONObject;

import io.reactivex.Observable;

public interface CoronaRemote {
    Observable<JSONArray> getCoronaDetails();
}
