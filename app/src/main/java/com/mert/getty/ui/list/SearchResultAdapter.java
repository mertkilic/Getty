package com.mert.getty.ui.list;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
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

    public SearchResultAdapter(DataBindingComponent dataBindingComponent) {
        this.dataBindingComponent = dataBindingComponent;
    }

    @Override
    protected SearchItemBinding createBinding(ViewGroup parent) {
        return DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.search_item, parent, false,dataBindingComponent);
    }

    @Override
    protected void bind(SearchItemBinding binding, Image item) {
        binding.setImage(item);
    }
}
