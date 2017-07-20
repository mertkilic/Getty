package com.mert.getty;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by Mert Kilic on 19.7.2017.
 */
@Subcomponent(modules = MainActivityModule.class)
public interface MainActivityComponent extends AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity> {
    }
}
