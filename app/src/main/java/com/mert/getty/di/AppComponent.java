package com.mert.getty.di;

import android.app.Application;

import com.mert.getty.GettyApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by Mert Kilic on 18.7.2017.
 */
@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class, MainBuilder.class})
public interface AppComponent extends AndroidInjector<GettyApp> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(GettyApp app);
}
