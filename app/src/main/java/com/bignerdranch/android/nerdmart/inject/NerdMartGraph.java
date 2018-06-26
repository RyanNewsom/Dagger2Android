package com.bignerdranch.android.nerdmart.inject;

import com.bignerdranch.android.nerdmart.NerdMartAbstractFragment;

//Tells Dagger who to inject into
public interface NerdMartGraph {
    void inject(NerdMartAbstractFragment fragment);
}
