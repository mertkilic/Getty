package com.mert.getty.util;

import android.content.Context;

import javax.inject.Inject;

/**
 * Created by Mert Kilic on 17.10.2017.
 */

public class StateManagerImpl implements StateManager {

    Context context;

    @Inject
    public StateManagerImpl(Context context) {
        this.context = context;
    }

    @Override
    public boolean isConnected() {
        return AndroidUtils.isConnected(context);
    }
}
