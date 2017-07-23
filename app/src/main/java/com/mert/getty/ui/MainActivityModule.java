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
    MainView provideMainView(MainActivity mainActivity){
        return mainActivity;
    }

    @Provides
    MainPresenter provideMainPresenter(MainView mainView, GettyService service){
        return new MainPresenterImpl(mainView, service);
    }
}
