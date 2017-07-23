package com.mert.getty.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mert.getty.R;
import com.mert.getty.data.model.Image;
import com.mert.getty.databinding.ActivityMainBinding;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    MainPresenter presenter;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setPresenter(((MainPresenterImpl) presenter));
    }

    @Override
    public void onImagesLoaded(List<Image> images) {
        Log.d(TAG, "onImagesLoaded: ");
    }

    @Override
    public void onError() {
        Log.d(TAG, "onError: ");
    }
}
