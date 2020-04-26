package com.corona.coronatracker.repository.datasource.remote;

import com.corona.coronatracker.repository.webservice.service.ServiceInterceptor;

import org.json.JSONObject;

import io.reactivex.Observable;

public class CoronaRemoteImpl implements CoronaRemote {
    private ServiceInterceptor serviceInterceptor;

    public CoronaRemoteImpl(ServiceInterceptor serviceInterceptor) {
        this.serviceInterceptor = serviceInterceptor;
    }

    @Override
    public Observable<JSONObject> getCoronaDetails() {
        return serviceInterceptor.getCoronaDetails();
    }
}
