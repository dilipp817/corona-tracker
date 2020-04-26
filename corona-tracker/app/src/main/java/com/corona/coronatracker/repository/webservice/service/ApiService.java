package com.corona.coronatracker.repository.webservice.service;

import com.google.gson.JsonElement;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/data.json")
    Observable<JsonElement> getCoronaDetails();

}
