package com.tarashor.androidapps.widgets.behavior;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.tarashor.androidapps.widgets.FooterBarLayout;

/**
 * Created by Taras on 23.03.2018.
 */

public class FooterBarLayoutBehavior extends CoordinatorLayout.Behavior<FooterBarLayout> {


    public FooterBarLayoutBehavior(){}
    public FooterBarLayoutBehavior(Context context, AttributeSet attrs){
        super(context, attrs);
    }



    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FooterBarLayout child, View dependency) {
        return dependency instanceof NestedScrollingParent2;

    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FooterBarLayout child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        //super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        Log.v("FooterBarLayoutBehavior", "nested scroll");
    }
}
