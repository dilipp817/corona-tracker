package com.corona.coronatracker.repository.interfaces;

import org.json.JSONArray;

import io.reactivex.Observable;

public interface CoronaRepo {
    Observable<JSONArray> getCoronaDetails();
}
