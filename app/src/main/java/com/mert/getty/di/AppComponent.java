package com.mert.getty.di;

import android.app.Application;

import com.mert.getty.GettyApp;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by Mert Kilic on 18.7.2017.
 */

public interface AppComponent {
    void inject(GettyApp app);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
