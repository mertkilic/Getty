package com.mert.getty.ui;

import com.mert.getty.data.GettyService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mert Kilic on 19.7.2017.
 */

@Module
public class MainActivityModule {

    @Provides
    MainPresenter provideMainPresenter(GettyService service){
        return new MainPresenterImpl(service);
    }
}
