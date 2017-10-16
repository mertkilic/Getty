package com.mert.getty.data.api;

import android.support.annotation.NonNull;

import com.mert.getty.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mert Kilic on 18.7.2017.
 */

public class GettyApiInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder().addHeader("Api-Key", GettyClientConfig.getApiKey());
        return chain.proceed(builder.build());
    }
}
