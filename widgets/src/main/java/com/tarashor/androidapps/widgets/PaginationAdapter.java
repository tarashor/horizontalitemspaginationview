package com.tarashor.androidapps.widgets;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Taras on 24.11.2017.
 */

public abstract class PaginationAdapter<TItem> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM = 0;
    public static final int LOADING_ITEM = 1;

    protected DataLoader dataLoader;

    protected boolean isDataLoading;
    protected boolean isAllDataLoaded;

    protected List<TItem> items;

    public PaginationAdapter() {
        items = new ArrayList<>();
        initState();
    }

    private void initState() {
        isDataLoading = false;
        isAllDataLoaded = true;
    }

    public void clearAllItems() {
        initState();
        items.clear();
        notifyDataSetChanged();
    }

    public void loadMoreData() {
        if (!isDataLoading && !isAllDataLoaded) {
            if (dataLoader != null) {
                isDataLoading = true;
                dataLoader.loadMoreData(items.size(), this);
            }
        }
    }

    public void setItems(List<TItem> newItems) {
        isDataLoading = false;

        items.clear();
        if (newItems != null){
            items.addAll(newItems);
        }
        notifyDataSetChanged();
    }

    public void setIsAllDataLoaded(boolean isAllDataLoaded) {
        if (this.isAllDataLoaded != isAllDataLoaded) {
            this.isAllDataLoaded = isAllDataLoaded;
            if (!this.isAllDataLoaded) {
                notifyItemInserted(getItemCount() - 1);
            } else{
                notifyItemRemoved(getItemCount() - 1);
            }
        }

    }

    public void setDataLoader(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }


    @Override
    public int getItemCount() {
        int booksCount = items.size();
        return isAllDataLoaded ? booksCount : booksCount + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == getItemCount() - 1 && !isAllDataLoaded) ? LOADING_ITEM : ITEM;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case ITEM:
                viewHolder = createItemViewHolder(parent);
                break;
            case LOADING_ITEM:
                viewHolder = createLoadingViewHolder(parent);
                break;
            default:
                break;
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case ITEM:
                bindItemViewHolder(viewHolder, position);
                break;
            case LOADING_ITEM:
                bindLoadingViewHolder(viewHolder);
            default:
                break;
        }
    }

    private void bindLoadingViewHolder(RecyclerView.ViewHolder viewHolder) {
    }

    protected abstract RecyclerView.ViewHolder createItemViewHolder(ViewGroup parent);
    protected abstract void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position);
    protected abstract LoadingViewHolder createLoadingViewHolder(ViewGroup parent);

    public static View createViewFromLayout(ViewGroup parent, @LayoutRes int layoutId){
        return LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
    }


    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(View view) {
            super(view);
        }
    }

    public interface DataLoader {
        void loadMoreData(int itemsCountLoaded, PaginationAdapter adapter);
    }


}
