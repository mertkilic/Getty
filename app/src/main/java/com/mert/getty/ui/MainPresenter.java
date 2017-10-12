package com.mert.getty.ui;

import com.mert.getty.ui.base.BasePresenter;

/**
 * Created by Mert Kilic on 22.7.2017.
 */

public interface MainPresenter extends BasePresenter<MainView> {
    void search(String keyword,int page);
    void resetQuery();
}
