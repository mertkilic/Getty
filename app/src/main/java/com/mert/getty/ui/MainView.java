package com.mert.getty.ui;

import android.support.v7.widget.RecyclerView;

import com.mert.getty.data.model.Image;

import java.util.List;

/**
 * Created by Mert Kilic on 22.7.2017.
 */

public interface MainView {

    boolean isListEmpty();

    void onImagesLoaded(List<Image> images);

    void onError();

    RecyclerView.LayoutManager getLayoutManager();
}
