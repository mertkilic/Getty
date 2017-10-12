package com.mert.getty.ui;

import android.content.DialogInterface;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.mert.getty.R;
import com.mert.getty.data.model.Image;
import com.mert.getty.databinding.ActivityMainBinding;
import com.mert.getty.databinding.ToastCaptionBinding;
import com.mert.getty.ui.list.SearchResultAdapter;
import com.mert.getty.ui.list.SpaceItemDecorator;
import com.mert.getty.util.AndroidUtils;

import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements MainView, TextView.OnEditorActionListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    MainPresenter presenter;

    private ActivityMainBinding mainBinding;
    private ToastCaptionBinding toastBinding;
    private DataBindingComponent dataBindingComponent = new SearchDataBindingComponent(this);
    private SearchResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main, dataBindingComponent);
        mainBinding.setPresenter(((MainPresenterImpl) presenter));
        adapter = new SearchResultAdapter(dataBindingComponent, new SearchResultAdapter.SearchItemCallback() {
            @Override
            public void onClick(Image image) {
                showCaption(image.getCaption());
            }
        });
        mainBinding.gettyList.addItemDecoration(new SpaceItemDecorator(getResources().getDimensionPixelSize(R.dimen.space_grid_item)));
        mainBinding.gettyList.setAdapter(adapter);
        mainBinding.searchView.setOnEditorActionListener(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        CharSequence query = v.getText();
        if (query.length() >= 3) {
            presenter.search(query.toString(), 1);
            AndroidUtils.hideKeyboard(v);
            return true;
        }
        presenter.resetQuery();
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return mainBinding.gettyList.getLayoutManager();
    }

    @Override
    public void clear() {
        adapter.clear();
    }

    @Override
    public void onImagesLoaded(final List<Image> images) {
        adapter.updateItems(images);
    }

    @Override
    public void onError(Throwable t) {
        String errorMessage;
        if (t instanceof UnknownHostException) {
            errorMessage = getString(R.string.no_internet);
        } else errorMessage = t.getMessage();

        new AlertDialog.Builder(this)
                .setMessage(errorMessage)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
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
