package com.mert.getty.data.api;

import android.util.Log;

import com.mert.getty.data.GettyService;
import com.mert.getty.data.model.GettyResponse;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mert Kilic on 18.7.2017.
 */

public class GettyApiClient {

    private final GettyService service;

    @Inject
    public GettyApiClient(GettyService service) {
        this.service = service;
    }

    public void search(String keyword, int page) {
        search(keyword, GettyClientConfig.PAGE_SIZE, page);
    }

    public void search(String keyword, int pageSize, int page) {
        service.search(keyword, pageSize, page).enqueue(new Callback<GettyResponse>() {
            @Override
            public void onResponse(Call<GettyResponse> call, Response<GettyResponse> response) {
                Log.d("", "");
            }

            @Override
            public void onFailure(Call<GettyResponse> call, Throwable t) {
                Log.d("", "");

            }
        });
    }
}
