package com.bignerdranch.android.nerdmart.inject;

import android.content.Context;

import com.bignerdranch.android.nerdmartservice.service.NerdMartService;
import com.bignerdranch.android.nerdmartservice.service.NerdMartServiceInterface;

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
}
