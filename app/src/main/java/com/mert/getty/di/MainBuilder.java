package com.mert.getty.di;

import com.mert.getty.ui.MainActivity;
import com.mert.getty.ui.MainActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Mert Kilic on 19.7.2017.
 */

@Module
public abstract class MainBuilder {

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    public abstract MainActivity bindMainActivity();
}