package com.corona.coronatracker.views;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.corona.coronatracker.R;
import com.corona.coronatracker.adapters.CoronaCaseAdapter;
import com.corona.coronatracker.di.scope.ViewModelFactory;
import com.corona.coronatracker.models.DistrictData;
import com.corona.coronatracker.utils.ApiResponseConvertor;
import com.corona.coronatracker.viewmodels.MainActivityViewModel;
import org.json.JSONException;
import java.util.List;
import javax.inject.Inject;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    private MainActivityViewModel viewModel;
    private Context context;
    private CoronaCaseAdapter mAdapter;

    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        viewModel = new ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel.class);
        setObservers();
        viewModel.checkCoronaUpdate();
    }

    private void setUi(List<DistrictData> coronaCaseList) {
        mAdapter = new CoronaCaseAdapter(coronaCaseList);
        RecyclerView.LayoutManager mLayoutManager  = new LinearLayoutManager(this);
        RecyclerView rv_details = findViewById(R.id.rv_details);
        rv_details.setLayoutManager(mLayoutManager);
        rv_details.setItemAnimator(new DefaultItemAnimator());
        rv_details.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void setObservers() {
        viewModel.updateApiStatus.observe(this, appUpdateResponse -> {
            switch (appUpdateResponse.status) {
                case ERROR:
                    appUpdateResponse.e.printStackTrace();
                    Log.d("track", "error occored");
                    break;
                case SUCCESS:
                    try {
                        setUi(ApiResponseConvertor.extractCoronaData(appUpdateResponse.data));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        });
    }
}
