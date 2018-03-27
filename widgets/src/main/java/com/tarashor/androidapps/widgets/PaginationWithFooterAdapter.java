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

public abstract class PaginationWithFooterAdapter<TItem> extends PaginationAdapter<TItem> {
    protected static final int FOOTER_ITEM = 2;

    public boolean isFooterShown() {
        return isFooterShown;
    }

    protected boolean isFooterShown;

    protected void initState() {
        super.initState();
        isFooterShown = false;
    }

    public void setIsFooterShown(boolean isFooterShown) {
        if (this.isFooterShown != isFooterShown) {
            this.isFooterShown = isFooterShown;
            if (this.isFooterShown) {
                notifyItemInserted(getFooterItemPosition());
            } else{
                notifyItemRemoved(getFooterItemPosition());
            }
        }

    }

    protected int getLoadingItemPosition() {
        if (!isFooterShown)
            return super.getLoadingItemPosition();
        else {
            return getItemCount() - 2;
        }
    }

    public int getFooterItemPosition() {
        return getItemCount() - 1;
    }

    @Override
    public int getItemCount() {
        int itemsCount = super.getItemCount();
        return isFooterShown ? itemsCount + 1 : itemsCount;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == getFooterItemPosition() && isFooterShown) ? FOOTER_ITEM : super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case FOOTER_ITEM:
                viewHolder = createFooterViewHolder(parent);
                break;
            default:
                viewHolder = super.onCreateViewHolder(parent, viewType);
                break;
        }
        return viewHolder;
    }

    protected abstract FooterViewHolder createFooterViewHolder(ViewGroup parent);

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View view) {
            super(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case FOOTER_ITEM:
                bindFooterViewHolder(viewHolder);
            default:
                super.onBindViewHolder(viewHolder, position);
                break;
        }
    }

    protected void bindFooterViewHolder(RecyclerView.ViewHolder viewHolder){

    }

}
