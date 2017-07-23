package com.mert.getty.util;

import android.databinding.BindingConversion;
import android.view.View;

/**
 * Created by Mert Kilic on 23.7.2017.
 */

public class GettyBindingAdapter {

    @BindingConversion
    public static int getVisibility(boolean visible) {
        return visible ? View.VISIBLE : View.GONE;
    }
}
