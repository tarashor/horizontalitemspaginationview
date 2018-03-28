package com.tarashor.androidapps.widgets;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;


/**
 * Created by Taras on 24.11.2017.
 */

public abstract class SectionsWithPaginationAndFooterAdapter<TItem> extends PaginationWithFooterAdapter<TItem> {
    protected static final int NEW_SECTION_ITEM = 3;

    private SortedSet<Integer> positionOfNewSections = new TreeSet<>();

    public void startNewSectionHere(){
        int positionOnNewSection = items.size()-1;
        if (!positionOfNewSections.contains(positionOnNewSection)) {
            positionOfNewSections.add(positionOnNewSection);
            notifyItemRangeInserted(positionOnNewSection, 1);
        }
    }

    public void startNewSectionAtPos(int position){
        if (!positionOfNewSections.contains(position)) {
            positionOfNewSections.add(position);
            notifyItemRangeInserted(position, 1);
        }
    }

    public boolean isNewSectionPosition(int position){
        return positionOfNewSections.contains(position);
    }

    protected void initState() {
        super.initState();
        positionOfNewSections = new TreeSet<>();
    }

//    protected int getLoadingItemPosition() {
//        return super.getLoadingItemPosition() + positionOfNewSections.size();
//    }
//
//    public int getFooterItemPosition() {
//        return super.getFooterItemPosition() + positionOfNewSections.size();
//    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + positionOfNewSections.size();
    }

    @Override
    public int getItemViewType(int position) {
        return isNewSectionPosition(position) ? NEW_SECTION_ITEM : super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case NEW_SECTION_ITEM:
                viewHolder = createNewSectionViewHolder(parent);
                break;
            default:
                viewHolder = super.onCreateViewHolder(parent, viewType);
                break;
        }
        return viewHolder;
    }

    protected abstract RecyclerView.ViewHolder createNewSectionViewHolder(ViewGroup parent);

    private int adapterPositionToItemPosition(int adapterPosition){
        SortedSet<Integer> newSectionsBeforeItem = positionOfNewSections.headSet(adapterPosition);
        return adapterPosition - newSectionsBeforeItem.size();
    }

    @Override
    protected void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        int itemPosition = adapterPositionToItemPosition(position);
        bindItemViewHolderWithItemPosition(viewHolder, itemPosition);
    }

    protected abstract void bindItemViewHolderWithItemPosition(RecyclerView.ViewHolder viewHolder, int itemPosition);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case NEW_SECTION_ITEM:
                bindNewSectionViewHolder(viewHolder);
            default:
                super.onBindViewHolder(viewHolder, position);
                break;
        }
    }

    private void bindNewSectionViewHolder(RecyclerView.ViewHolder viewHolder) {

    }

}
