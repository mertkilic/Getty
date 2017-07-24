package com.mert.getty.ui;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.mert.getty.R;
import com.mert.getty.data.model.Image;
import com.mert.getty.databinding.ActivityMainBinding;
import com.mert.getty.databinding.ToastCaptionBinding;
import com.mert.getty.ui.list.SearchResultAdapter;
import com.mert.getty.ui.list.SpaceItemDecorator;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    MainPresenter presenter;

    private ActivityMainBinding mainBinding;
    private ToastCaptionBinding toastBinding;
    private DataBindingComponent dataBindingComponent = new SearchDataBindingComponent(this);
    private SearchResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main, dataBindingComponent);
        mainBinding.setPresenter(((MainPresenterImpl) presenter));
        adapter = new SearchResultAdapter(dataBindingComponent, image -> showCaption(image.getCaption()));
        mainBinding.gettyList.addItemDecoration(new SpaceItemDecorator(getResources().getDimensionPixelSize(R.dimen.space_grid_item)));

        mainBinding.gettyList.setAdapter(adapter);
        adapter.add(new ArrayList<>());
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return mainBinding.gettyList.getLayoutManager();
    }

    @Override
    public void onImagesLoaded(final List<Image> images) {
        adapter.add(images);
        mainBinding.executePendingBindings();
    }

    @Override
    public void onError(Throwable t) {
        String errorMessage;
        if (t instanceof UnknownHostException) {
            errorMessage = getString(R.string.no_internet);
        } else errorMessage = t.getMessage();

        new AlertDialog.Builder(this)
                .setMessage(errorMessage)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void showCaption(String description) {
        if (TextUtils.isEmpty(description))
            return;

        if (toastBinding == null) {
            toastBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.toast_caption, mainBinding.root, false);
        }

        toastBinding.toastText.setText(description);

        Toast toast = new Toast(this);
        toast.setView(toastBinding.getRoot());
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0,
                getResources().getDimensionPixelSize(R.dimen.space_grid_item));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
