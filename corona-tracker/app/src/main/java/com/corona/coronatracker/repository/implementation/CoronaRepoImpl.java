package com.corona.coronatracker.repository.implementation;

import com.corona.coronatracker.repository.datasource.remote.CoronaRemote;
import com.corona.coronatracker.repository.interfaces.CoronaRepo;

import org.json.JSONObject;

import io.reactivex.Observable;

public class CoronaRepoImpl implements CoronaRepo {
    private CoronaRemote coronaRemote;

    public CoronaRepoImpl(CoronaRemote coronaRemote) {
        this.coronaRemote = coronaRemote;
    }

    @Override
    public Observable<JSONObject> getCoronaDetails() {
        return coronaRemote.getCoronaDetails();
    }
}
