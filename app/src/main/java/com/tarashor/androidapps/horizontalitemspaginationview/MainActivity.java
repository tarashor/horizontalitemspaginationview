package com.tarashor.androidapps.horizontalitemspaginationview;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tarashor.androidapps.widgets.HorizontalItemsPaginationView;
import com.tarashor.androidapps.widgets.PaginationAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final int TOTAL_COUNT = 1;
    public static final int PAGE_COUNT = 1;
    public static final int DELAY_MILLIS = 1500;
    private List<Item> items = new ArrayList<>();
    private Handler handler = new Handler();

    private ItemsAdapter testAdapter;
    private boolean isCleared = false;
    private HorizontalItemsPaginationView horizontalItemsPaginationView;
    private SwitchCompat clearSwitchCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        horizontalItemsPaginationView = findViewById(R.id.items_view);
        //horizontalItemsPaginationView.setVisibility(View.GONE);
        horizontalItemsPaginationView.setRightPadding(20);

        testAdapter = new ItemsAdapter();
        testAdapter.setDataLoader(new PaginationAdapter.DataLoader() {
            @Override
            public void loadMoreData(int itemsCountLoaded, final PaginationAdapter adapter) {
                loadNextPage();
            }
        });
        horizontalItemsPaginationView.setAdapter(testAdapter);

        Button startButton = findViewById(R.id.button_start_loading);
        clearSwitchCompat = findViewById(R.id.clear_switch);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoadingData();
            }
        });

        startLoadingData();

    }

    private void startLoadingData() {
        horizontalItemsPaginationView.setVisibility(View.VISIBLE);
        testAdapter.setIsAllDataLoaded(false);
        testAdapter.loadMoreData();
    }

    private void loadNextPage() {
        handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (clearSwitchCompat.isChecked()){
                        items.clear();
                        testAdapter.setItems(items);
                        testAdapter.setIsAllDataLoaded(true);
                        horizontalItemsPaginationView.setVisibility(View.GONE);
                    } else {
                        items.addAll(generateItems(PAGE_COUNT));
                        testAdapter.setItems(items);
                        testAdapter.setIsAllDataLoaded(items.size() == TOTAL_COUNT);
                        horizontalItemsPaginationView.setVisibility(View.VISIBLE);
                    }

            }
        }, DELAY_MILLIS);
    }

    private void startAddingPages() {

    }

    private List<Item> generateItems(int count) {
        Random randomRed = new Random();
        Random randomBlue = new Random();
        Random randomGreen = new Random();
        List<Item> items = new ArrayList<>();
        for(int i = 0; i < count; i++){
            items.add(new Item(Color.argb(255, randomRed.nextInt(256), randomGreen.nextInt(256), randomBlue.nextInt(256))));
        }
        return items;
    }

    public static class Item {
        private int color;
        private String name;

        public Item(int color) {
            this.color = color;
            this.name = String.valueOf(color);
        }

        public int getColor() {
            return color;
        }

        public String getName() {
            return name;
        }
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
