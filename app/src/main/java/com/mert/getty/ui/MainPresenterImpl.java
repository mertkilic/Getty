package com.mert.getty.ui;

import android.databinding.ObservableBoolean;
import android.support.v7.widget.RecyclerView;

import com.mert.getty.data.GettyService;
import com.mert.getty.data.api.GettyClientConfig;
import com.mert.getty.data.model.GettyResponse;
import com.mert.getty.ui.list.LoadMoreScrollListener;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mert Kilic on 22.7.2017.
 */

public class MainPresenterImpl implements MainPresenter {

    private String TAG = MainPresenterImpl.class.getSimpleName();

    private MainView mainView;
    private GettyService service;
    private ObservableBoolean loading = new ObservableBoolean(false);
    private LoadMoreScrollListener scrollListener;
    private String query = "";

    @Inject
    MainPresenterImpl(GettyService service) {
        this.service = service;
    }

    @Override
    public void search(String query, int page) {
        if (!this.query.equals(query))
            mainView.clear();

        loading.set(true);
        service.search(query, GettyClientConfig.PAGE_SIZE, page).enqueue(new Callback<GettyResponse>() {
            @Override
            public void onResponse(Call<GettyResponse> call, Response<GettyResponse> response) {
                if (response.isSuccessful()) {
                    mainView.onImagesLoaded(response.body().getImages());
                    loading.set(false);
                } else {
                    mainView.onError(new Throwable("Server error has occured"));
                    loading.set(false);
                }
            }

            @Override
            public void onFailure(Call<GettyResponse> call, Throwable t) {
                mainView.onError(t);
                loading.set(false);
            }
        });
        this.query = query;
    }

    @Override
    public void resetQuery() {
        this.query = "";
    }

    public ObservableBoolean getLoading() {
        return loading;
    }

    public RecyclerView.OnScrollListener getOnScrollListener() {
        if (scrollListener == null) {
            scrollListener = new LoadMoreScrollListener(mainView.getLayoutManager()) {
                @Override
                public void onLoadMore(int page) {
                    search(query, page);
                }
            };
            scrollListener.setLoading(loading);
        }
        return scrollListener;
    }

    @Override
    public void attachView(MainView view) {
        mainView = view;
    }

    @Override
    public void detachView() {
        mainView = null;
    }
}
