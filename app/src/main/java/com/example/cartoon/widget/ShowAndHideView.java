package com.example.cartoon.widget;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import static android.content.ContentValues.TAG;

public class ShowAndHideView extends LinearLayout {
    private ObjectAnimator slideAnimator = null;
    private long duration = 500;
    private TimeInterpolator interpolator = new FastOutSlowInInterpolator();


    public ShowAndHideView(Context context) {
        super(context);
    }

    public ShowAndHideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ShowAndHideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ShowAndHideView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private ObjectAnimator initAnimation(String propertyName,float from,float to){
        Log.i(TAG, "initAnimation: "+from+"  "+to);
        slideAnimator = ObjectAnimator.ofFloat(this,propertyName,from,to);
        slideAnimator.setDuration(duration);
        slideAnimator.setInterpolator(interpolator);
        return slideAnimator;
    }

    private void startAnimation(final  ObjectAnimator animator){
        post(new Runnable() {
            @Override
            public void run() {
                clearSlideAnimation();
                setVisibility(VISIBLE);
                animator.start();
            }
        });
    }

    public void clearSlideAnimation() {
        if (slideAnimator != null && slideAnimator.isRunning()) {
            slideAnimator.cancel();
        }
    }

    public void slideDownIn() {
        startAnimation(initAnimation("translationY",getHeight(),0));
    }
    public void slideDownOut(){
        startAnimation(initAnimation("translationY",0,getHeight()));
    }

    public void slideUpIn(){
        startAnimation(initAnimation("translationY",-getHeight(),0));
    }

    public void slideUpOut() {
        startAnimation(initAnimation("translationY",0,-getHeight()));
    }
}
