package com.mert.getty.data;

import com.mert.getty.data.model.GettyResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Mert Kilic on 18.7.2017.
 */

public interface GettyService {
    @GET("search/images")
    Call<GettyResponse> search(@Query("phrase") String keyword, @Query("page_size") int pageSize, @Query("page") int page);
}
