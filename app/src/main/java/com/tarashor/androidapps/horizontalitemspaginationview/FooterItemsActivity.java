package com.tarashor.androidapps.horizontalitemspaginationview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tarashor.androidapps.widgets.PaginationAdapter;
import com.tarashor.androidapps.widgets.PaginationRecyclerView;
import com.tarashor.androidapps.widgets.PaginationWithFooterAdapter;

public class FooterItemsActivity extends AppCompatActivity {

    public static final int PAGES_COUNT = 1;
    public static final int ITEMS_COUNT = PAGES_COUNT*6+1;
    public static final int COLUMNS = 3;

    private ItemsAdapter adapter;

    private Model model;

    private GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footer_item);

        layoutManager = new GridLayoutManager(this, COLUMNS);


        PaginationRecyclerView itemsView = findViewById(R.id.items_view);
        itemsView.setLayoutManager(layoutManager);

        adapter = new ItemsAdapter();
        itemsView.setAdapter(adapter);

        model = new Model(ITEMS_COUNT, PAGES_COUNT);
        model.generateNextPage();
        adapter.setItems(model.getItems());
        adapter.setIsDataLoaded(model.isAllDataLoaded());
        adapter.setIsFooterShown(true);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter.isFooterShown() && adapter.getFooterItemPosition() == position){
                    return COLUMNS;
                } else {
                    return 1;
                }
            }
        });

    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder
    {
        private FrameLayout container = null;
        private TextView text = null;

        public ItemViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.item_color_view);
            text = itemView.findViewById(R.id.item_name_view);
        }

        public void bind(Item item) {
            container.setBackgroundColor(item.getColor());
            text.setText(item.getName());
        }
    }

    public static class ItemsAdapter extends PaginationWithFooterAdapter<Item> {

        @Override
        protected RecyclerView.ViewHolder createItemViewHolder(ViewGroup parent) {
            View view = PaginationAdapter.createViewFromLayout(parent, R.layout.item_layout);
            return new ItemViewHolder(view);
        }

        @Override
        protected void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
            itemViewHolder.bind(items.get(position));
        }

        @Override
        protected LoadingViewHolder createLoadingViewHolder(ViewGroup parent) {
            View view = PaginationAdapter.createViewFromLayout(parent, R.layout.loading_item_layout);
            return new LoadingViewHolder(view);
        }

        @Override
        protected FooterViewHolder createFooterViewHolder(ViewGroup parent) {
            View view = PaginationAdapter.createViewFromLayout(parent, R.layout.footer_item_layout);
            return new FooterViewHolder(view);
        }
    }
}
