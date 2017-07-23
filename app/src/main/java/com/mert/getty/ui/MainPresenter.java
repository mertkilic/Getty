package com.mert.getty.ui;

import android.widget.TextView;

/**
 * Created by Mert Kilic on 22.7.2017.
 */

public interface MainPresenter extends TextView.OnEditorActionListener {
    void search(String keyword,int page);
}
