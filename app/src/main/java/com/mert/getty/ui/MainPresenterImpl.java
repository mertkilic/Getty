package com.mert.getty.ui;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.widget.TextView;

import com.mert.getty.BR;
import com.mert.getty.data.GettyService;
import com.mert.getty.data.api.GettyClientConfig;
import com.mert.getty.data.model.GettyResponse;
import com.mert.getty.ui.list.LoadMoreScrollListener;
import com.mert.getty.util.AndroidUtils;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mert Kilic on 22.7.2017.
 */

public class MainPresenterImpl extends BaseObservable implements MainPresenter {

    private String TAG = MainPresenterImpl.class.getSimpleName();

    private MainView mainView;
    private GettyService service;
    private ObservableBoolean loading = new ObservableBoolean(false);
    private LoadMoreScrollListener scrollListener;
    private String query;

    @Inject
    MainPresenterImpl(MainView mainView, GettyService service) {
        this.mainView = mainView;
        this.service = service;
    }

    @Override
    public void search(String keyword, int page) {
        loading.set(true);
        service.search(keyword, GettyClientConfig.PAGE_SIZE, page).enqueue(new Callback<GettyResponse>() {
            @Override
            public void onResponse(Call<GettyResponse> call, Response<GettyResponse> response) {
                mainView.onImagesLoaded(response.body().getImages());
                loading.set(false);
            }

            @Override
            public void onFailure(Call<GettyResponse> call, Throwable t) {
                mainView.onError();
                loading.set(false);
            }
        });
    }

    public ObservableBoolean getLoading() {
        return loading;
    }

    public RecyclerView.OnScrollListener getOnScrollListener() {
        if(scrollListener == null){
            scrollListener = new LoadMoreScrollListener(mainView.getLayoutManager()) {
                @Override
                public void onLoadMore(int page) {
                    search(query,page);
                }
            };
            scrollListener.setLoading(loading);
        }
        return scrollListener;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        CharSequence query = v.getText();
        if (query.length() >= 3) {
            search(query.toString(), 1);
            this.query = query.toString();
            AndroidUtils.hideKeyboard(v);
            return true;
        }
        this.query = "";
        return false;
    }
}
