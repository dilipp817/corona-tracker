package com.corona.coronatracker.viewmodels;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Database;

import com.corona.coronatracker.database.AppExecutors;
import com.corona.coronatracker.database.CoronaDatabase;
import com.corona.coronatracker.dummydata.DummyDataProvider;
import com.corona.coronatracker.dummydata.DummyModel;
import com.corona.coronatracker.models.DistrictData;
import com.corona.coronatracker.repository.interfaces.CoronaRepo;
import com.corona.coronatracker.repository.webservice.model.Response;
import com.corona.coronatracker.utils.ApiResponseConvertor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivityViewModel extends ViewModel {

    private Application application;
    private CoronaRepo coronaRepo;
    public MutableLiveData<Response<JSONArray>> updateApiStatus = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<List<DistrictData>> districtData = new MutableLiveData<>();

    @Inject
    public MainActivityViewModel(Application application, CoronaRepo coronaRepo) {
        this.application = application;
        this.coronaRepo = coronaRepo;
    }

    public void checkCoronaUpdate(CoronaDatabase database, String stateCode) {
        if (stateCode == null) {
            updateApiStatus.postValue(Response.loading());
            Disposable disposable = coronaRepo.getCoronaDetails()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            (coronaUpdate) -> {
                                insertCoronaData(database, coronaUpdate);
                                populateCoronaData(database, null);
                            });
            compositeDisposable.add(disposable);
        }
        else {
            populateCoronaData(database, stateCode);
        }

    }

    public void insertCoronaData(CoronaDatabase database, JSONArray allCoronaData) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            try {
                database.districtDataDao().insertAllDistrictData(ApiResponseConvertor.extractCoronaData(allCoronaData));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void populateCoronaData(CoronaDatabase database, String stateCode) {
        AppExecutors.getInstance().diskIO().execute(() -> {
        districtData.postValue(database.districtDataDao().getAllDistrictData());
        });
    }
}
