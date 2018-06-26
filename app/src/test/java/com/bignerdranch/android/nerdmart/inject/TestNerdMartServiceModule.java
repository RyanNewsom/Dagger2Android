package com.bignerdranch.android.nerdmart.inject;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class TestNerdMartServiceModule extends NerdMartServiceModule {

    @Override
    protected Scheduler provideScheduler() {
        return Schedulers.trampoline();
    }
}
