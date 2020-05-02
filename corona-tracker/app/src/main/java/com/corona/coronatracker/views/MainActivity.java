package com.corona.coronatracker.views;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.corona.coronatracker.R;
import com.corona.coronatracker.adapters.CoronaCaseAdapter;
import com.corona.coronatracker.database.AppExecutors;
import com.corona.coronatracker.database.CoronaDatabase;
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
    CoronaDatabase database;
    public static final String INTENT_CRETERIA = "criteria";
    public static final String COUNTRY = "country";
    public static final String STATE = "state";
    private String STATE_CODE = null;
    private boolean isCountryWise = true;
    private TextView totalConfirmed, totalActive, totalDeceased, totalRecovered;

    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        viewModel = new ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel.class);
        database = CoronaDatabase.getInstanse(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle.getString(INTENT_CRETERIA).equals(COUNTRY)) {
            isCountryWise = true;
        }
        else {
            isCountryWise = false;
            STATE_CODE = bundle.getString(STATE);
        }

        initiliseUi();
        setObservers();
        viewModel.checkCoronaUpdate(database, STATE_CODE);
        viewModel.addStatesToList(database);
    }

    private void initiliseUi() {
        totalConfirmed = findViewById(R.id.tv_totalConfirmed);
        totalActive = findViewById(R.id.tv_totalActive);
        totalDeceased = findViewById(R.id.tv_totalDied);
        totalRecovered = findViewById(R.id.tv_totalRecovered);
    }

    private void setObservers() {
        viewModel.districtData.observe(this, this::setUi);
        viewModel.totalConfirmed.observe(this, confirmedCases -> totalConfirmed.setText(String.valueOf(confirmedCases)));
        viewModel.totalActive.observe(this, activeCases -> totalActive.setText(String.valueOf(activeCases)));
        viewModel.totalDeceased.observe(this, deceasedCases -> totalDeceased.setText(String.valueOf(deceasedCases)));
        viewModel.totalRecovered.observe(this, recoveredCases -> totalRecovered.setText(String.valueOf(recoveredCases)));
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

    public static Intent getInstance(Context context, String criteria, String stateCode) {
        Intent intent = new Intent(context, MainActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString(INTENT_CRETERIA, criteria);
        bundle.putString(STATE, stateCode);
        intent.putExtras(bundle);
        return intent;
    }
}
