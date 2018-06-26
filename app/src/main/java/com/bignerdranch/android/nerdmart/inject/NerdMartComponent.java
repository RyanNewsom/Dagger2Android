package com.bignerdranch.android.nerdmart.inject;

import com.bignerdranch.android.nerdmart.NerdMartApplication;

import javax.inject.Singleton;

import dagger.Component;

//Pulls together the modules it wants to use for DI and initializes Dagger
@Singleton
@Component(modules = {NerdMartApplicationModule.class, NerdMartCommonModule.class, NerdMartServiceModule.class})
public interface NerdMartComponent extends NerdMartGraph {
    final class Initializer {
        private Initializer() {
            throw new AssertionError("No instances.");
        }

        //Creates a nerd mart graph implementation
        public static NerdMartGraph init(NerdMartApplication app) {
            return DaggerNerdMartComponent.builder()
                    .nerdMartApplicationModule(new NerdMartApplicationModule(app))
                    .build();
        }
    }
}
