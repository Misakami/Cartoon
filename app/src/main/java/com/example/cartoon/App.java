package com.example.cartoon;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.squareup.leakcanary.LeakCanary;

public class App extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        if (BuildConfig.DEBUG){
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return;
            }
            LeakCanary.install(this);
        }
    }

    public static Context getContext() {
        return context;
    }

}
