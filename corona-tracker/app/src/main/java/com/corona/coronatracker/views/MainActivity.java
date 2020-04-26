package com.corona.coronatracker.views;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.Bundle;
import com.corona.coronatracker.R;
import com.corona.coronatracker.adapters.CoronaCaseAdapter;
import com.corona.coronatracker.di.scope.ViewModelFactory;
import com.corona.coronatracker.viewmodels.MainActivityViewModel;
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
        setUi();
    }

    private void setUi() {
        mAdapter = new CoronaCaseAdapter(viewModel.dummyModelList);
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
//                    init();
                    break;
                case SUCCESS:
//                    tvData.setText(appUpdateResponse.data.toString());
                    break;
            }
        });
    }
}
