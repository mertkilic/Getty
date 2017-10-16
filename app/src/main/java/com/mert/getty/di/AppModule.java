package com.mert.getty.di;

import android.app.Application;
import android.content.Context;

import com.mert.getty.data.GettyService;
import com.mert.getty.data.api.GettyApiInterceptor;
import com.mert.getty.data.api.GettyClientConfig;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mert.getty.data.api.GettyClientConfig.BASE_ENDPOINT;

/**
 * Created by Mert Kilic on 18.7.2017.
 */
@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    GettyService provideGettyService(OkHttpClient client) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_ENDPOINT)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(GettyService.class);
    }

    @Provides
    @Singleton
    Cache provideCache(Context context) {
        File cacheFile = new File(context.getFilesDir(), GettyClientConfig.CACHE_NAME);
        if (!cacheFile.exists()) {
            cacheFile.mkdir();
        }
        return new Cache(cacheFile, GettyClientConfig.CACHE_SIZE);
    }

    @Provides
    @Singleton
    GettyApiInterceptor provideCacheInterceptor() {
        return new GettyApiInterceptor();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(GettyApiInterceptor interceptor, Cache cache) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(interceptor)
                .build();
    }
}
