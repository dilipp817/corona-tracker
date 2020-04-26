package com.corona.coronatracker.repository.interfaces;

import org.json.JSONObject;

import io.reactivex.Observable;

public interface CoronaRepo {
    Observable<JSONObject> getCoronaDetails();
}
