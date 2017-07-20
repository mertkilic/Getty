package com.mert.getty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mert.getty.data.api.GettyApiClient;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity {

    @Inject
    GettyApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiClient.search("dog",1);
    }
}
