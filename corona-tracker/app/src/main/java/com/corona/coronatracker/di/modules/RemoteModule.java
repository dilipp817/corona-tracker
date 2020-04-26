package com.corona.coronatracker.di.modules;

import android.content.Context;

import com.corona.coronatracker.repository.datasource.remote.CoronaRemote;
import com.corona.coronatracker.repository.datasource.remote.CoronaRemoteImpl;
import com.corona.coronatracker.repository.webservice.service.ApiServiceFactory;
import com.corona.coronatracker.repository.webservice.service.ServiceInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RemoteModule {
    @Singleton
    @Provides
    ServiceInterceptor provideService(Context context) {
        return new ServiceInterceptor(ApiServiceFactory.getApiService(context), context);
    }

    @Singleton
    @Provides
    static CoronaRemote provideCoronaRemote(ServiceInterceptor serviceInterceptor) {
        return new CoronaRemoteImpl(serviceInterceptor);
    }
}
