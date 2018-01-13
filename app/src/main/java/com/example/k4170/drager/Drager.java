package com.example.k4170.drager;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by K4170 on 2018-01-13.
 */

public class Drager extends LinearLayout {

    private static final String TAG = "Drager";
    private ViewDragHelper viewDragHelper;
    private View view0;
    private View view1;
    private View view2;
    private Point point = new Point();

    public Drager(Context context) {
        this(context, null);
    }

    public Drager(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Drager(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        viewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return left;
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
            }

            @Override
            public void onViewDragStateChanged(int state) {
                Log.d(TAG, "onViewDragStateChanged: state" + state);

                super.onViewDragStateChanged(state);
            }

            @Override
            public void onViewCaptured(View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
                if (capturedChild == view1) {
                    point.x = capturedChild.getLeft();
                    point.y = capturedChild.getTop();
                }
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if (releasedChild == view1) {
//                    viewDragHelper.smoothSlideViewTo(view1, point.x, point.y);
                    viewDragHelper.settleCapturedViewAt(point.x,point.y);
                    invalidate();
                }
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return 0;
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        view0 = getChildAt(0);
        view1 = getChildAt(1);
        view2 = getChildAt(2);
    }

    @Override
    public void computeScroll() {
            super.computeScroll();
        if (viewDragHelper.continueSettling(true)){
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        viewDragHelper.processTouchEvent(event);
        return true;
    }


}
