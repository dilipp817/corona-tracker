package com.corona.coronatracker.viewmodels;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.corona.coronatracker.dummydata.DummyDataProvider;
import com.corona.coronatracker.dummydata.DummyModel;
import com.corona.coronatracker.repository.interfaces.CoronaRepo;
import com.corona.coronatracker.repository.webservice.model.Response;

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
    public MutableLiveData<Response<JSONObject>> updateApiStatus = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public List<DummyModel> dummyModelList = new ArrayList<>();

    @Inject
    public MainActivityViewModel(Application application, CoronaRepo coronaRepo) {
        this.application = application;
        this.coronaRepo = coronaRepo;
    }


    public void checkCoronaUpdate() {
        dummyModelList = DummyDataProvider.prepareDummyModelData();     //this is dummy data to test viewpager
        updateApiStatus.postValue(Response.loading());
        Disposable disposable = coronaRepo.getCoronaDetails()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (appUpdate) -> updateApiStatus.postValue(Response.success(appUpdate)),
                        (e) -> updateApiStatus.postValue(Response.error(e))
                );
        compositeDisposable.add(disposable);
    }
}
