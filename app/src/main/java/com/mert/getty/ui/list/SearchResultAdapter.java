package com.mert.getty.ui.list;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mert.getty.R;
import com.mert.getty.data.model.Image;
import com.mert.getty.databinding.SearchItemBinding;


/**
 * Created by Mert Kilic on 23.7.2017.
 */

public class SearchResultAdapter extends DataBoundListAdapter<Image, SearchItemBinding> {

    private final android.databinding.DataBindingComponent dataBindingComponent;
    private final SearchItemCallback callback;

    public SearchResultAdapter(DataBindingComponent dataBindingComponent, SearchItemCallback callback) {
        this.dataBindingComponent = dataBindingComponent;
        this.callback = callback;
    }

    @Override
    protected SearchItemBinding createBinding(ViewGroup parent) {
        SearchItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.search_item, parent, false, dataBindingComponent);
        binding.setCallback(callback);
        return binding;
    }

    @Override
    protected void bind(SearchItemBinding binding, Image item) {
        binding.setImage(item);
    }

    public interface SearchItemCallback {
        void onClick(Image image);
    }
}
