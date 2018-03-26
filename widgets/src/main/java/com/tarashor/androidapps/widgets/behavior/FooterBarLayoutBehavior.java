package com.tarashor.androidapps.widgets.behavior;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.tarashor.androidapps.widgets.FooterBarLayout;

/**
 * Created by Taras on 23.03.2018.
 */

public class FooterBarLayoutBehavior extends CoordinatorLayout.Behavior<FooterBarLayout> {

    public static final String TAG = "FooterBarLayoutBehavior";

    private static final int DIRECTION_UP = 1;
    private static final int DIRECTION_DOWN = -1;

    /* Tracking direction of user motion */
    private int mScrollingDirection;
    /* Tracking last threshold crossed */
    private int mScrollTrigger;

    /* Accumulated scroll distance */
    private int mScrollDistance;
    private int mMaxTop = -1;
    private int mMinTop = -1;

    public FooterBarLayoutBehavior(){}
    public FooterBarLayoutBehavior(Context context, AttributeSet attrs){
        super(context, attrs);
    }


    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FooterBarLayout child,
                                       @NonNull View directTargetChild, @NonNull View target,
                                       int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                  @NonNull FooterBarLayout child, @NonNull View target,
                                  int dx, int dy,
                                  @NonNull int[] consumed, int type) {

        int extent = ((RecyclerView)target).computeVerticalScrollExtent();
        int offset = ((RecyclerView)target).computeVerticalScrollOffset();
        int range = ((RecyclerView)target).computeVerticalScrollRange();
        if (dy > 0 && extent + offset == range){
            consumed[1] = dy;
        }

//        if (dy < 0 && child.getHeight() <= 0){
//            consumed[1] = dy;
//        }

        mMaxTop = coordinatorLayout.getHeight() - child.getMeasuredHeight();
        mMinTop = coordinatorLayout.getHeight();

        //Log.v(TAG, "mMinTop = " + mMinTop);
        //Log.v(TAG, "mMaxTop = " + mMaxTop);
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                               @NonNull FooterBarLayout child, @NonNull View target,
                               int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed,
                               int type) {
        //Consumed distance is the actual distance traveled by the scrolling view

        int newTop = child.getTop() - dyConsumed;
        Log.v(TAG, "newTop = " + newTop);

        Log.v(TAG, "dxUnconsumed = " + dxUnconsumed);
        if (newTop >= mMaxTop && newTop <= mMinTop){
            child.setTop(newTop);
        }
    }


//
//    //Called after the scrolling child handles the fling
//    @Override
//    public boolean onNestedFling(CoordinatorLayout coordinatorLayout,
//                                 View child, View target,
//                                 float velocityX, float velocityY,
//                                 boolean consumed) {
//        //We only care when the target view is already handling the fling
//        if (consumed) {
//            if (velocityY > 0 && mScrollTrigger != DIRECTION_UP) {
//                mScrollTrigger = DIRECTION_UP;
//                restartAnimator(child, getTargetHideValue(coordinatorLayout, child));
//            } else if (velocityY < 0 && mScrollTrigger != DIRECTION_DOWN) {
//                mScrollTrigger = DIRECTION_DOWN;
//                restartAnimator(child, 0f);
//            }
//        }
//
//        return false;
//    }
}
