package com.mert.getty.di;

import android.app.Application;
import android.content.Context;

import com.mert.getty.ui.MainActivityComponent;
import com.mert.getty.data.GettyService;
import com.mert.getty.data.api.GettyApiClient;
import com.mert.getty.data.api.GettyApiInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mert.getty.data.api.GettyClientConfig.BASE_ENDPOINT;

/**
 * Created by Mert Kilic on 18.7.2017.
 */
@Module(subcomponents = {
        MainActivityComponent.class})
public class AppModule {

    @Provides
    @Singleton
    GettyService provideGettyService(OkHttpClient client) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_ENDPOINT)
                .client(client)
                .build().create(GettyService.class);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Interceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor).build();
    }

    @Provides
    @Singleton
    Interceptor provideInterceptor() {
        return new GettyApiInterceptor();
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }
}
