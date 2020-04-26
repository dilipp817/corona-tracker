package com.corona.coronatracker.di.scope;

import androidx.lifecycle.ViewModel;

import dagger.MapKey;

@MapKey
public @interface ViewModelKey {
    Class<? extends ViewModel> value();
}
