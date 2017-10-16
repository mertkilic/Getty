package com.mert.getty.data;

import com.mert.getty.data.model.GettyResponse;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Mert Kilic on 18.7.2017.
 */

public interface GettyService {
    @GET("search/images")
    Observable<Response<GettyResponse>> search(@Query("phrase") String keyword, @Query("page_size") int pageSize, @Query("page") int page);
}
