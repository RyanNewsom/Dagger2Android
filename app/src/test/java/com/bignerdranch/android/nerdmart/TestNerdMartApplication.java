package com.bignerdranch.android.nerdmart;

import com.bignerdranch.android.nerdmart.inject.TestNerdMartComponent;

public class TestNerdMartApplication extends NerdMartApplication {

    @Override
    protected void setupDagger() {
       mComponent = TestNerdMartComponent.Initializer.init(this);
    }
}
