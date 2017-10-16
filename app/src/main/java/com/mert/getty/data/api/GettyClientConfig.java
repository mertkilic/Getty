package com.mert.getty.data.api;

/**
 * Created by Mert Kilic on 18.7.2017.
 */

public class GettyClientConfig {
    static {
        System.loadLibrary("apikey");
    }

    public native static String getApiKey();

    public static String BASE_ENDPOINT = "https://api.gettyimages.com/v3/";
    public static int PAGE_SIZE = 20;

    public static final String CACHE_NAME = "http-cache";

    public static final int NETWORK_CONNECTION_TIMEOUT = 10; // 10 sec
    public static final long CACHE_SIZE = 5 * 1024 * 1024; // 5 MB
}
