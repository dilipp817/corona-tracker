package com.corona.coronatracker.di.modules;

import com.corona.coronatracker.repository.datasource.remote.CoronaRemote;
import com.corona.coronatracker.repository.implementation.CoronaRepoImpl;
import com.corona.coronatracker.repository.interfaces.CoronaRepo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Singleton
    @Provides
    static CoronaRepo provideOrganisationRepo(CoronaRemote organisationRemote) {
        return new CoronaRepoImpl(organisationRemote);
    }
}
