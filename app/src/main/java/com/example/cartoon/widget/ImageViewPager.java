package com.example.cartoon.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class ImageViewPager extends ViewPager {
    boolean show = false;
    boolean notUse = false;
    int topHeight = 0;
    int butoom = 0;
    int weight = 0;
    int x;
    int y;
    public ImageViewPager(@NonNull Context context) {
        super(context);
    }

    public ImageViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (show){
            int y = (int) ev.getY();
            if (y < topHeight || y > butoom ){
                notUse = true;
                return false;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (notUse){
            notUse = false;
            return false;
        }
        if (ev.getAction() == MotionEvent.ACTION_UP||ev.getAction() == MotionEvent.ACTION_CANCEL){
            Log.i("viewpager", "onTouchEvent: "+getScrollX()+" "+getScrollY());
            if (getScrollX() == 0 && getScrollY() == 0){
                int x = (int) ev.getX();
                if (x > weight/3 && x < weight/3*2){
                    notUse = true;
                    return false;
                }
            }
        }
        return super.onTouchEvent(ev);
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public int getTopHeight() {
        return topHeight;
    }

    public void setTopHeight(int topHeight) {
        this.topHeight = topHeight;
    }

    public int getButoom() {
        return butoom;
    }

    public void setButoom(int butoom) {
        this.butoom = butoom;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
