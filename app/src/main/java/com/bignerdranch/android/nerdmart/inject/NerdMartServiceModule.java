package com.bignerdranch.android.nerdmart.inject;

import com.bignerdranch.android.nerdmart.model.DataStore;
import com.bignerdranch.android.nerdmart.model.service.NerdMartServiceManager;
import com.bignerdranch.android.nerdmartservice.model.NerdDataSource;
import com.bignerdranch.android.nerdmartservice.model.NerdMartDataSourceInterface;
import com.bignerdranch.android.nerdmartservice.service.NerdMartService;
import com.bignerdranch.android.nerdmartservice.service.NerdMartServiceInterface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

@Module
public class NerdMartServiceModule {

    @Provides
    @Singleton
    NerdMartServiceManager provideNerdMartServiceManager(NerdMartServiceInterface serviceInterface,
                                                         DataStore dataStore, Scheduler observeOnScheduler) {
        return new NerdMartServiceManager(serviceInterface, dataStore, observeOnScheduler);
    }

    @Provides
    @Singleton
    NerdMartServiceInterface provideNerdMartServiceInterface(
            NerdMartDataSourceInterface dataSourceInterface) {
        return new NerdMartService(dataSourceInterface);
    }
    @Provides
    @Singleton
    protected Scheduler provideScheduler() {
        return AndroidSchedulers.mainThread();
    }
    @Provides
    @Singleton
    NerdMartDataSourceInterface provideNerdMartDataSourceInterface() {
        return new NerdDataSource();
    }
}
