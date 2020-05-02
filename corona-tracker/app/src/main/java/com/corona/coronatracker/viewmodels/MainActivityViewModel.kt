package com.corona.coronatracker.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.corona.coronatracker.database.AppExecutors
import com.corona.coronatracker.database.CoronaDatabase
import com.corona.coronatracker.models.DistrictData
import com.corona.coronatracker.repository.interfaces.CoronaRepo
import com.corona.coronatracker.repository.webservice.model.Response
import com.corona.coronatracker.utils.ApiResponseConvertor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.util.*
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val application: Application, private val coronaRepo: CoronaRepo) : ViewModel() {
    var updateApiStatus = MutableLiveData<Response<JSONArray>>()
    private val compositeDisposable = CompositeDisposable()
    @JvmField
    var districtData = MutableLiveData<List<DistrictData>>()
    @JvmField
    var totalConfirmed = MutableLiveData<Int>()
    @JvmField
    var totalActive = MutableLiveData<Int>()
    @JvmField
    var totalDeceased = MutableLiveData<Int>()
    @JvmField
    var totalRecovered = MutableLiveData<Int>()
    var stateData: MutableList<DistrictData> = ArrayList()
    fun checkCoronaUpdate(database: CoronaDatabase, stateCode: String?) {
        if (stateCode == null) {
            updateApiStatus.postValue(Response.loading())
            val disposable = coronaRepo.coronaDetails
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { coronaUpdate: JSONArray? ->
                        insertCoronaData(database, coronaUpdate)
                        populateCoronaData(database, null)
                    }
            compositeDisposable.add(disposable)
        } else {
            populateCoronaData(database, stateCode)
        }
    }

    fun insertCoronaData(database: CoronaDatabase, allCoronaData: JSONArray?) {
        AppExecutors.getInstance().diskIO().execute {
            try {
                database.districtDataDao().deleteAllData()
                database.districtDataDao().insertAllDistrictData(ApiResponseConvertor.extractCoronaData(allCoronaData))
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

    fun populateCoronaData(database: CoronaDatabase, stateCode: String?) {
        AppExecutors.getInstance().diskIO().execute { if (stateCode == null) processStateWise(database.districtDataDao().allDistrictData) else processDistrictWise(database.districtDataDao().allDistrictData) }
    }

    suspend fun getStateList(database: CoronaDatabase): List<String> {
        return database.districtDataDao().stateList
    }

    fun addStatesToList(database: CoronaDatabase) = viewModelScope.launch  {
        for (stateName in getStateList(database)) {
            stateData.add(populateStateWiseData(database, stateName))
        }
        //        mutableStateData.postValue(stateData);
        districtData.postValue(stateData)
    }

    fun populateStateWiseData(database: CoronaDatabase, stateName: String?): DistrictData {
        var totalConfirmed = 0
        var totalActive = 0
        var totalDeceased = 0
        var totalRecovered = 0
        val list = database.districtDataDao().getAllStateData(stateName)
        for (districtData in list) {
            totalConfirmed += districtData.confirmedCount
            totalActive += districtData.activeCount
            totalDeceased += districtData.deceasedCount
            totalRecovered += districtData.recoveredCount
        }
        return DistrictData("India", "IN", stateName, totalActive, totalConfirmed, totalDeceased, totalRecovered, 0, 0, 0)
    }

    fun processStateWise(allCoronaData: List<DistrictData>) {
        var totalConfirmed = 0
        var totalActive = 0
        var totalDeceased = 0
        var totalRecovered = 0
        for (districtData in allCoronaData) {
            totalConfirmed += districtData.confirmedCount
            totalActive += districtData.activeCount
            totalDeceased += districtData.deceasedCount
            totalRecovered += districtData.recoveredCount
        }
        //        districtData.postValue(allCoronaData);
    }

    fun processDistrictWise(allCoronaData: List<DistrictData?>?) {}

}