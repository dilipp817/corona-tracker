package com.corona.coronatracker.di.modules;

import androidx.lifecycle.ViewModel;

import com.corona.coronatracker.di.scope.ViewModelKey;
import com.corona.coronatracker.viewmodels.MainActivityViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel.class)
    abstract ViewModel mainActivityViewModel(MainActivityViewModel signInViewModel);
}
