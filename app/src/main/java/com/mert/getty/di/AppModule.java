package com.mert.getty.di;

import android.app.Application;
import android.content.Context;

import com.mert.getty.data.GettyService;
import com.mert.getty.data.api.GettyClientConfig;
import com.mert.getty.util.StateManager;
import com.mert.getty.util.StateManagerImpl;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mert.getty.data.api.GettyClientConfig.BASE_ENDPOINT;

/**
 * Created by Mert Kilic on 18.7.2017.
 */
@Module
public class AppModule {

    private static final String CACHE_CONTROL = "Cache-Control";
    private static final String PRAGMA = "Pragma";
    private static final String API_KEY = "Api-Key";

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
    @Named("offlineCacheInterceptor")
    Interceptor provideOfflineCacheInterceptor(final StateManager stateManager) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader(API_KEY, GettyClientConfig.getApiKey()).build();

                if (!stateManager.isConnected()) {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(GettyClientConfig.CACHE_MAX_STALE, TimeUnit.DAYS)
                            .build();

                    request = request.newBuilder()
                            .cacheControl(cacheControl)
                            .build();
                }
                return chain.proceed(request);
            }
        };
    }

    @Singleton
    @Provides
    @Named("cacheInterceptor")
    Interceptor provideCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());

                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(GettyClientConfig.CACHE_MAX_AGE, TimeUnit.MINUTES)
                        .build();

                return response.newBuilder()
                        .removeHeader(PRAGMA)
                        .removeHeader(CACHE_CONTROL)
                        .header(CACHE_CONTROL, cacheControl.toString())
                        .build();
            }
        };
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(@Named("cacheInterceptor") Interceptor cacheInterceptor,
                                     @Named("offlineCacheInterceptor") Interceptor offlineCacheInterceptor,
                                     Cache cache) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(cacheInterceptor)
                .addInterceptor(offlineCacheInterceptor)
                .build();
    }

    @Provides
    @Singleton
    public StateManager provideStateManager(StateManagerImpl stateManager) {
        return stateManager;
    }
}
