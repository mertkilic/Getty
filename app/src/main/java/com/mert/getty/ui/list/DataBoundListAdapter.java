/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mert.getty.ui.list;

import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.mert.getty.data.model.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * A generic RecyclerView adapter that uses Data Binding & DiffUtil.
 *
 * @param <T> Type of the items in the list
 * @param <V> The of the ViewDataBinding
 */
public abstract class DataBoundListAdapter<T, V extends ViewDataBinding>
        extends RecyclerView.Adapter<DataBoundViewHolder<V>> {

    private List<T> items = new ArrayList<>();

    public void updateItems(List<T> items){
        List<T> oldItems = new ArrayList<>(this.items);
        this.items.addAll(items);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new GettyDiffUtil(oldItems, this.items));
        diffResult.dispatchUpdatesTo(this);
    }

    public void clear(){
        List<T> oldItems = new ArrayList<>(this.items);
        this.items.clear();
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new GettyDiffUtil(oldItems, this.items));
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public final DataBoundViewHolder<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        V binding = createBinding(parent);
        return new DataBoundViewHolder<>(binding);
    }

    protected abstract V createBinding(ViewGroup parent);

    @Override
    public final void onBindViewHolder(DataBoundViewHolder<V> holder, int position) {
        //noinspection ConstantConditions
        bind(holder.binding, items.get(position));
    }

    protected abstract void bind(V binding, T item);

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    private class GettyDiffUtil extends DiffUtil.Callback{

        List<T> oldItems;
        List<T> newItems;

        public GettyDiffUtil(List<T> oldItems, List<T> newItems) {
            this.oldItems = oldItems;
            this.newItems = newItems;
        }

        @Override
        public int getOldListSize() {
            return oldItems.size();
        }

        @Override
        public int getNewListSize() {
            return newItems.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            T oldItem = oldItems.get(oldItemPosition);
            T newItem = newItems.get(newItemPosition);
            return (oldItem.equals(newItem));
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return ((Image) oldItems.get(oldItemPosition)).getTitle().equals(((Image) newItems.get(newItemPosition)).getTitle());
        }
    }
}
