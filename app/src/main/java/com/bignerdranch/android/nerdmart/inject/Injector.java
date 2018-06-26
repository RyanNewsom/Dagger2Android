package com.bignerdranch.android.nerdmart.inject;

import android.content.Context;

import com.bignerdranch.android.nerdmart.NerdMartConstants;

public final class Injector {
    private Injector() {
        throw new AssertionError("No instances.");
    }

    @SuppressWarnings("ResourceType")
    public static NerdMartGraph obtain(Context context) {
        return (NerdMartGraph) context.getApplicationContext().getSystemService(NerdMartConstants.INJECTOR_SERVICE);
    }

    public static boolean matchesService(String name) {
        return NerdMartConstants.INJECTOR_SERVICE.equals(name);
    }
}
