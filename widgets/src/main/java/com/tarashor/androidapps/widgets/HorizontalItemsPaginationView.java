package com.tarashor.androidapps.widgets;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Taras on 24.11.2017.
 */

public class HorizontalItemsPaginationView extends LinearLayout {
    protected static final int LAYOUT_ID = R.layout.horizontal_items_pagination_view;

    protected LinearLayoutManager mLayoutManager;
    protected RecyclerView mRecyclerView;
    private int mRightPadding;
    private PaginationAdapter mAdapter;

    public HorizontalItemsPaginationView(Context context) {
        super(context);
        initInflate();
        initInstances();
        initControl();
    }

    public HorizontalItemsPaginationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initControl();
        initWithAttrs(attrs);
    }

    public HorizontalItemsPaginationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initControl();
        initWithAttrs(attrs);
    }


    protected void initWithAttrs(AttributeSet attrs) {

    }

    protected void initControl() {

        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                                           @Override
                                           public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                                               if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
                                                   outRect.right = mRightPadding;
                                               }
                                           }
                                       });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                checkLastItemVisible();
            }
        });
    }

    private void checkLastItemVisible() {
        int visibleItemCount = mLayoutManager.getChildCount();
        int totalItemCount = mLayoutManager.getItemCount();
        int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
            mAdapter.loadMoreData();
        }
    }

    protected void initInstances() {
        mRecyclerView = findViewById(R.id.items_recycler_view);
    }

    protected void initInflate() {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null){
            layoutInflater.inflate(LAYOUT_ID, this);
        }
    }

    public <VH extends RecyclerView.ViewHolder> void setAdapter(PaginationAdapter<VH> adapter) {
        this.mAdapter = adapter;
        mRecyclerView.setAdapter(adapter);
    }

    public void scrollToTheFirstItem(){
        mRecyclerView.scrollToPosition(0);
    }

    public void setRightPadding(int rightPadding) {
        this.mRightPadding = rightPadding;
    }

}
