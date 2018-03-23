package com.tarashor.androidapps.horizontalitemspaginationview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tarashor.androidapps.widgets.PaginationAdapter;
import com.tarashor.androidapps.widgets.PaginationRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BehaviorItemsActivity extends AppCompatActivity {

    public static final int PAGES_COUNT = 1;
    public static final int ITEMS_COUNT = PAGES_COUNT*60;

    private ItemsAdapter adapter;

    private Model model;

    private GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior);

        layoutManager = new GridLayoutManager(this, 3);

        PaginationRecyclerView itemsView = findViewById(R.id.items_view);
        itemsView.setLayoutManager(layoutManager);

        adapter = new ItemsAdapter();
        itemsView.setAdapter(adapter);

        model = new Model(ITEMS_COUNT, PAGES_COUNT);
        model.generateNextPage();
        adapter.setItems(model.getItems());
        adapter.setIsDataLoaded(model.isAllDataLoaded());

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

    public static class ItemsAdapter extends PaginationAdapter<Item>{

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
    }
}
