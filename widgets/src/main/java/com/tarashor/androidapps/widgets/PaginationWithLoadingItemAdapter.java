package com.tarashor.androidapps.widgets;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Taras on 24.11.2017.
 */

public abstract class PaginationWithLoadingItemAdapter extends PaginationAdapter<RecyclerView.ViewHolder> {
    public static final int ITEM = 0;
    public static final int LOADING_ITEM = 1;

    private DataLoader dataLoader;

    protected boolean isDataLoading;
    protected boolean isAllDataLoaded;
    private boolean isLoadingItemAdded;

    private void initState() {
        isDataLoading = false;
        isAllDataLoaded = false;
        isLoadingItemAdded = false;
    }

    public void loadMoreData() {
        if (!isDataLoading && !isAllDataLoaded) {
            addLoadingItem();
            if (dataLoader != null) {
                isDataLoading = true;
                dataLoader.loadMoreData(getRealItemsCount(), this);
            }
        }
    }

    protected abstract int getRealItemsCount();

    @Override
    public int getItemCount() {
        return isLoadingItemAdded ? getRealItemsCount() + 1: getRealItemsCount();
    }

    public void setDataLoader(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }


    public void addLoadingItem() {
        if (!isLoadingItemAdded) {
            isLoadingItemAdded = true;
            notifyItemInserted(getItemCount() - 1);
        }
    }

    public void removeLoadingItem() {
        if (isLoadingItemAdded) {
            isLoadingItemAdded = false;
            notifyItemRemoved(getItemCount() - 1);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == getItemCount() - 1 && isLoadingItemAdded) ? LOADING_ITEM : ITEM;
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
