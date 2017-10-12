package com.mert.getty.ui.base;

/**
 * Created by Mert Kilic on 11.10.2017.
 */

public interface BasePresenter<V extends BaseView> {
    void attachView(V view);

    void detachView();
}
