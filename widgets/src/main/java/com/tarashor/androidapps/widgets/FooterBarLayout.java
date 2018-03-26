package com.tarashor.androidapps.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.tarashor.androidapps.widgets.behavior.FooterBarLayoutBehavior;

/**
 * Created by Taras on 23.03.2018.
 */

@CoordinatorLayout.DefaultBehavior(FooterBarLayoutBehavior.class)
public class FooterBarLayout extends FrameLayout {
    public FooterBarLayout(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
        setTop(0);
    }

    public FooterBarLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
}
