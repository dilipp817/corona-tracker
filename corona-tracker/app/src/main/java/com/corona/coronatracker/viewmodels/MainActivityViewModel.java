package com.corona.coronatracker.viewmodels;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.corona.coronatracker.database.AppExecutors;
import com.corona.coronatracker.database.CoronaDatabase;
import com.corona.coronatracker.models.DistrictData;
import com.corona.coronatracker.repository.interfaces.CoronaRepo;
import com.corona.coronatracker.repository.webservice.model.Response;
import com.corona.coronatracker.utils.ApiResponseConvertor;

import org.json.JSONArray;
import org.json.JSONException;

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
    public MutableLiveData<Integer> totalConfirmed = new MutableLiveData<>();
    public MutableLiveData<Integer> totalActive = new MutableLiveData<>();
    public MutableLiveData<Integer> totalDeceased = new MutableLiveData<>();
    public MutableLiveData<Integer> totalRecovered = new MutableLiveData<>();
    public MutableLiveData<List<DistrictData>> mutableStateData = new MutableLiveData<>();

    public List<DistrictData> stateData = new ArrayList<>();
    public MutableLiveData<List<String>> stateListLD = new MutableLiveData<>();

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
        } else {
            populateCoronaData(database, stateCode);
        }

    }

    public void insertCoronaData(CoronaDatabase database, JSONArray allCoronaData) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            try {
                database.districtDataDao().deleteAllData();
                database.districtDataDao().insertAllDistrictData(ApiResponseConvertor.extractCoronaData(allCoronaData));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void populateCoronaData(CoronaDatabase database, String stateCode) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (stateCode == null)
                processStateWise(database.districtDataDao().getAllDistrictData());
            else
                processDistrictWise(database.districtDataDao().getAllDistrictData());
        });
    }

    public void getStateList(CoronaDatabase database) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            stateListLD.postValue(database.districtDataDao().getStateList());
        });
    }

    public void addStatesToList(CoronaDatabase database, List<String> stateList) {
        for (String stateName : stateList) {
            stateData.add(populateStateWiseData(database, stateName));
        }
//        mutableStateData.postValue(stateData);
        districtData.postValue(stateData);
    }

    public DistrictData populateStateWiseData(CoronaDatabase database, String stateName) {
        int totalConfirmed = 0;
        int totalActive = 0;
        int totalDeceased = 0;
        int totalRecovered = 0;
        List<DistrictData> list = new ArrayList<>();

        AppExecutors.getInstance().diskIO().execute(() -> {
            list.addAll(database.districtDataDao().getAllStateData(stateName));

        });

        for (DistrictData districtData : list) {
            totalConfirmed = totalConfirmed + districtData.getConfirmedCount();
            totalActive = totalActive + districtData.getActiveCount();
            totalDeceased = totalDeceased + districtData.getDeceasedCount();
            totalRecovered = totalRecovered + districtData.getRecoveredCount();
        }
        return new DistrictData("India", "IN", stateName, totalActive, totalConfirmed, totalDeceased, totalRecovered, 0, 0, 0);
    }

    public void processStateWise(List<DistrictData> allCoronaData) {
        int totalConfirmed = 0;
        int totalActive = 0;
        int totalDeceased = 0;
        int totalRecovered = 0;

        for (DistrictData districtData : allCoronaData) {
            totalConfirmed = totalConfirmed + districtData.getConfirmedCount();
            totalActive = totalActive + districtData.getActiveCount();
            totalDeceased = totalDeceased + districtData.getDeceasedCount();
            totalRecovered = totalRecovered + districtData.getRecoveredCount();
        }
//        districtData.postValue(allCoronaData);
    }

    public void processDistrictWise(List<DistrictData> allCoronaData) {

    }
}
