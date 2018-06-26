package com.bignerdranch.android.nerdmart.inject;

import android.content.Context;

import com.bignerdranch.android.nerdmart.model.service.NerdMartServiceManager;
import com.bignerdranch.android.nerdmartservice.service.NerdMartService;
import com.bignerdranch.android.nerdmartservice.service.NerdMartServiceInterface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

//Like a factory it provides the things that dagger will inject. It knows how to build them.
@Module
public class NerdMartApplicationModule {
    private Context mApplicationContext;

    public NerdMartApplicationModule(Context context) {
        mApplicationContext = context.getApplicationContext();
    }

    @Provides
    NerdMartServiceInterface provideNerdMartServiceInterface() {
        return new NerdMartService();
    }

    @Provides
    @Singleton
    NerdMartServiceManager provideNerdMartServiceManager(NerdMartServiceInterface nerdMartServiceInterface) {
        return new NerdMartServiceManager(nerdMartServiceInterface);
    }
}
