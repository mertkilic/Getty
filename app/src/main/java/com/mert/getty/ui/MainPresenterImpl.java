package com.mert.getty.ui;

import android.databinding.ObservableBoolean;
import android.view.KeyEvent;
import android.widget.TextView;

import com.mert.getty.data.GettyService;
import com.mert.getty.data.api.GettyClientConfig;
import com.mert.getty.data.model.GettyResponse;
import com.mert.getty.util.AndroidUtils;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mert Kilic on 22.7.2017.
 */

public class MainPresenterImpl implements MainPresenter {

    private MainView mainView;
    private GettyService service;

    private ObservableBoolean loading = new ObservableBoolean(false);

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
                loading.set(false);
                mainView.onImagesLoaded(response.body().getImages());
            }

            @Override
            public void onFailure(Call<GettyResponse> call, Throwable t) {
                loading.set(false);
                mainView.onError();
            }
        });
    }

    public ObservableBoolean getLoading() {
        return loading;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        CharSequence query = v.getText();
        if (query.length() >= 3) {
            search(query.toString(), 1);
            AndroidUtils.hideKeyboard(v);
            return true;
        }
        return false;
    }
}
