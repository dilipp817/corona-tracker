package com.corona.coronatracker.di.modules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {
    @ContributesAndroidInjector
    abstract com.corona.coronatracker.views.MainActivity contributeMainActivity();
}
