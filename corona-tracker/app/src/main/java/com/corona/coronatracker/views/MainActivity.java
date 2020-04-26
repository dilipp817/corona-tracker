package com.corona.coronatracker.views;

import androidx.lifecycle.ViewModelProvider;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.corona.coronatracker.R;
import com.corona.coronatracker.di.scope.ViewModelFactory;
import com.corona.coronatracker.viewmodels.MainActivityViewModel;
import javax.inject.Inject;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    private MainActivityViewModel viewModel;
    private Context context;

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

    private void setObservers() {
        TextView tvData = findViewById(R.id.tvData);
        viewModel.updateApiStatus.observe(this, appUpdateResponse -> {
            switch (appUpdateResponse.status) {
                case ERROR:
//                    init();
                    break;
                case SUCCESS:
                    tvData.setText(appUpdateResponse.data.toString());
                    break;
            }
        });
    }
}
