package com.mert.getty.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Mert Kilic on 18.7.2017.
 */
@Module
public class AppModule {

    @Provides
    @Singleton
    Retrofit provideRetrofit(){
       return null;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(){
        return null;
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }
}
