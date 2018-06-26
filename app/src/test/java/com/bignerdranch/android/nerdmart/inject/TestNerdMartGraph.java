package com.bignerdranch.android.nerdmart.inject;

import com.bignerdranch.android.nerdmart.model.service.NerdMartServiceManagerTest;

public interface TestNerdMartGraph extends NerdMartGraph {
    void inject(NerdMartServiceManagerTest nerdMartServiceManagerTest);
}
