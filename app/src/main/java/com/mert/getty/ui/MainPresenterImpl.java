package com.mert.getty.ui;

import android.databinding.ObservableBoolean;
import android.support.v7.widget.RecyclerView;

import com.mert.getty.data.GettyService;
import com.mert.getty.data.api.GettyClientConfig;
import com.mert.getty.data.model.GettyResponse;
import com.mert.getty.ui.list.LoadMoreScrollListener;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by Mert Kilic on 22.7.2017.
 */

public class MainPresenterImpl implements MainPresenter {

    private String TAG = MainPresenterImpl.class.getSimpleName();

    private MainView mainView;
    private GettyService service;
    private ObservableBoolean loading = new ObservableBoolean(false);
    private CompositeDisposable disposables = new CompositeDisposable();
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

        DisposableObserver<Response<GettyResponse>> searchDisposableObserver = getSearchDisposableObserver();

        service.search(query, GettyClientConfig.PAGE_SIZE, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchDisposableObserver);

        disposables.add(searchDisposableObserver);

        this.query = query;
    }

    private DisposableObserver<Response<GettyResponse>> getSearchDisposableObserver() {
        return new DisposableObserver<Response<GettyResponse>>() {
            @Override
            public void onNext(@NonNull Response<GettyResponse> response) {
                if (response.isSuccessful()) {
                    mainView.onImagesLoaded(response.body().getImages());
                } else {
                    mainView.onError(new Throwable("Server error has occurred"));
                }
                loading.set(false);
            }

            @Override
            public void onError(@NonNull Throwable t) {
                mainView.onError(t);
                loading.set(false);
            }

            @Override
            public void onComplete() {

            }
        };
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
        disposables.clear();
        mainView = null;
    }
}
