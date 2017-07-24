package com.mert.getty.ui;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.mert.getty.R;
import com.mert.getty.data.model.Image;
import com.mert.getty.databinding.ActivityMainBinding;
import com.mert.getty.ui.list.LoadMoreScrollListener;
import com.mert.getty.ui.list.SearchResultAdapter;
import com.mert.getty.ui.list.SpaceItemDecorator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    MainPresenter presenter;

    private ActivityMainBinding binding;
    private DataBindingComponent dataBindingComponent = new SearchDataBindingComponent(this);
    private SearchResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main, dataBindingComponent);
        binding.setPresenter(((MainPresenterImpl) presenter));
        adapter = new SearchResultAdapter(dataBindingComponent);
        binding.gettyList.addItemDecoration(new SpaceItemDecorator(getResources().getDimensionPixelSize(R.dimen.space_grid_item)));

        binding.gettyList.setAdapter(adapter);
        adapter.add(new ArrayList<Image>());
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return binding.gettyList.getLayoutManager();
    }

    @Override
    public boolean isListEmpty() {
        return adapter.getItemCount() == 0;
    }

    @Override
    public void onImagesLoaded(final List<Image> images) {
        adapter.add(images);
        binding.executePendingBindings();
    }

    @Override
    public void onError() {
        Log.d(TAG, "onError: ");
    }
}
