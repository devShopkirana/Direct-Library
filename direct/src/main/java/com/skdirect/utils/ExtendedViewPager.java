package com.skdirect.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.skdirect.R;


public class ExtendedViewPager extends ViewPager {
    private float mInitialXValue = 0.0f;
    private SwipeDirection mDirection = SwipeDirection.All;


    public ExtendedViewPager(@NonNull Context context) {
        super(context);
    }

    public ExtendedViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isSwipeAllowed(event)) {
            try {
                return super.onTouchEvent(event);
            } catch (IllegalArgumentException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (isSwipeAllowed(event)) {
            try {
                return super.onInterceptTouchEvent(event);
            } catch (IllegalArgumentException e) {
                return false;
            }
        } else {
            return false;
        }
    }


    public void setAllowedSwipeDirection(SwipeDirection direction) {
        mDirection = direction;
    }


    private boolean isSwipeAllowed(MotionEvent event) {
        switch (mDirection) {
            case All:
                return true;
            case NONE:
                //disable any swipe
                return false;
            default:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mInitialXValue = event.getX();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        float diffX = event.getX() - mInitialXValue;
                        if (diffX > 0 && mDirection == SwipeDirection.RIGHT) {
                            // swipe from left to right detected
                            return false;
                        } else if (diffX < 0 && mDirection == SwipeDirection.LEFT) {
                            // swipe from right to left detected
                            return false;
                        } else {
                            return true;
                        }
                    default:
                        return true;
                }
        }
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ExtendedViewPager, 0, 0);
        int state = typedArray.getInt(R.styleable.ExtendedViewPager_swipe_direction, 0);
        mDirection = SwipeDirection.values()[state];
        typedArray.recycle();
    }


    public enum SwipeDirection {
        All, LEFT, RIGHT, NONE
    }


}
