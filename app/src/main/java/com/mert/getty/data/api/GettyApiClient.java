package com.mert.getty.data.api;

import com.mert.getty.data.GettyService;

import javax.inject.Inject;

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
    }
}
