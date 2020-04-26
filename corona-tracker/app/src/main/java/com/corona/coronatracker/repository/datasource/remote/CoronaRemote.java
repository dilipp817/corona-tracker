package com.corona.coronatracker.repository.datasource.remote;

import org.json.JSONObject;

import io.reactivex.Observable;

public interface CoronaRemote {
    Observable<JSONObject> getCoronaDetails();
}
