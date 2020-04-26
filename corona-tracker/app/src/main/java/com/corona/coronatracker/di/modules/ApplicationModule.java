package com.corona.coronatracker.di.modules;

import android.app.Application;
import android.content.Context;

import com.corona.coronatracker.CoronaTrackerApplication;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public interface ApplicationModule {

    @Binds
    Context bindContext(CoronaTrackerApplication application);

    @Singleton
    @Binds
    Application bindApplication(CoronaTrackerApplication application);
}
