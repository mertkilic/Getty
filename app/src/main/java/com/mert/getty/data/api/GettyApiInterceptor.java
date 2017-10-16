package com.mert.getty.data.api;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mert Kilic on 16.10.2017.
 */

public class GettyApiInterceptor implements Interceptor {

    private static final int CACHE_MAX_STALE = 7; // 7 day

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
            CacheControl cacheControl = new CacheControl.Builder()
                    .maxStale(CACHE_MAX_STALE, TimeUnit.DAYS)
                    .build();

            request = request.newBuilder()
                    .addHeader("Api-Key", GettyClientConfig.getApiKey())
                    .cacheControl(cacheControl)
                    .build();

        return chain.proceed(request);
    }
}
