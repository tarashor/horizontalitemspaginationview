package com.tarashor.androidapps.widgets;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Taras on 24.11.2017.
 */

public abstract class PaginationAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    public abstract void loadMoreData();


}
