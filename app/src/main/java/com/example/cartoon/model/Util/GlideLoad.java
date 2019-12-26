package com.example.cartoon.model.Util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.cartoon.R;

public class GlideLoad {
    public static void loadImage(Context context, String url, ImageView view){
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .error(R.drawable.clear)
                .placeholder(R.drawable.back)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        com.bumptech.glide.Glide.with(context)
                .load(url)
                .thumbnail(0.1f)
                .apply(options)
                .into(view);
    }
}
