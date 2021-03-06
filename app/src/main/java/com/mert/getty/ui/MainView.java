package com.mert.getty.ui;

import android.support.v7.widget.RecyclerView;

import com.mert.getty.data.model.Image;
import com.mert.getty.ui.base.BaseView;

import java.util.List;

/**
 * Created by Mert Kilic on 22.7.2017.
 */

public interface MainView extends BaseView{

    void onImagesLoaded(List<Image> images);

    void onError(Throwable t);

    RecyclerView.LayoutManager getLayoutManager();

    void clear();
}
