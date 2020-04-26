package com.corona.coronatracker.di.component;

import com.corona.coronatracker.CoronaTrackerApplication;
import com.corona.coronatracker.di.modules.ActivityBuilderModule;
import com.corona.coronatracker.di.modules.AndroidInjectionModule;
import com.corona.coronatracker.di.modules.ApplicationModule;
import com.corona.coronatracker.di.modules.CacheModule;
import com.corona.coronatracker.di.modules.FragmentBuilderModule;
import com.corona.coronatracker.di.modules.LocalStorageModule;
import com.corona.coronatracker.di.modules.RemoteModule;
import com.corona.coronatracker.di.modules.RepositoryModule;
import com.corona.coronatracker.di.modules.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(
        modules = {
                AndroidInjectionModule.class,
                ViewModelModule.class,
                ActivityBuilderModule.class,
                FragmentBuilderModule.class,
                RepositoryModule.class,
                ApplicationModule.class,
                CacheModule.class,
                LocalStorageModule.class,
                RemoteModule.class
        }
)
public interface AppComponent {

    @Component.Builder
    interface Builder {
        AppComponent build();

        @BindsInstance
        Builder application(CoronaTrackerApplication application);
    }

    void inject(CoronaTrackerApplication application);
}