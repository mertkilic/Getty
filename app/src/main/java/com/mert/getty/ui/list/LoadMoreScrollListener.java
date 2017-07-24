package com.mert.getty.ui.list;

import android.databinding.ObservableBoolean;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Mert Kilic on 24.7.2017.
 */

public abstract class LoadMoreScrollListener extends RecyclerView.OnScrollListener {
    private RecyclerView.LayoutManager mLayoutManager;
    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private ObservableBoolean loading = new ObservableBoolean(false);
    private int currentPage = 1;

    public LoadMoreScrollListener(RecyclerView.LayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        super.onScrolled(view, dx, dy);

        visibleItemCount = view.getChildCount();
        totalItemCount = mLayoutManager.getItemCount();

        if (mLayoutManager instanceof LinearLayoutManager) {
            firstVisibleItem = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
        } else if (mLayoutManager instanceof GridLayoutManager) {
            firstVisibleItem = ((GridLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
        }

        if (totalItemCount == 0)
            return;

        if(dy > 0)
        {
            visibleItemCount = mLayoutManager.getChildCount();
            totalItemCount = mLayoutManager.getItemCount();

            if (!loading.get())
            {
                if ( (visibleItemCount + firstVisibleItem) >= totalItemCount)
                {
                    currentPage++;
                    onLoadMore(currentPage);
                }
            }
        }
    }

    public void setLoading(ObservableBoolean loading) {
        this.loading = loading;
    }

    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int page);

}
