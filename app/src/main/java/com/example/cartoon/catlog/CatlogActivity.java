package com.example.cartoon.catlog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.cartoon.BaseActivity;
import com.example.cartoon.R;
import com.example.cartoon.model.Bean.Cartoon;
import com.example.cartoon.model.Util.BlurUtil;
import com.example.cartoon.model.Util.JsoupUtil;
import com.example.cartoon.model.MyDiffCallback;
import com.jaeger.library.StatusBarUtil;

import java.util.Collections;

/**
 * 漫画目录
 */
public class CatlogActivity extends BaseActivity {
    public static String CHOSECARTOON = "KATONG";
    private ImageView cartoonImg;
    private TextView cartoonInfo;
    private TextView title;
    private TextView lastUpdate;
    private TextView swtich;
    private RecyclerView recyclerView;
    private Cartoon cartoon;
    private CatlogAdapter adapter;
    private ImageView cartoon_blurImage;
    private boolean read = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageView(this, cartoon_blurImage);
        setContentView(R.layout.activity_catlog);
        cartoonImg = findViewById(R.id.cartoon_img);
        cartoonInfo = findViewById(R.id.cartoon_info);
        title = findViewById(R.id.cartoon_title);
        recyclerView = findViewById(R.id.catlog_recycle);
        lastUpdate = findViewById(R.id.cartoon_lastUpdate);
        swtich = findViewById(R.id.switch_read);
        cartoon_blurImage = findViewById(R.id.cartoon_blurImage);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(CHOSECARTOON);
        assert bundle != null;
        cartoon = bundle.getParcelable(CHOSECARTOON);
        title.setText(cartoon.getTitle());
        lastUpdate.setText(cartoon.getLastUpdates());
        swtich.setText("正序");
        swtich.setClickable(true);
        swtich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                read = !read;
                swtich.setText(read ? "正序" : "倒序");
                Cartoon resver = new Cartoon();
                resver.getCatalogsTitle().addAll(adapter.getCartoon().getCatalogsTitle());
                resver.getCatalogsUrl().addAll(adapter.getCartoon().getCatalogsUrl());
                Collections.reverse(resver.getCatalogsTitle());
                Collections.reverse(resver.getCatalogsUrl());
                DiffUtil.DiffResult result;
                result = DiffUtil.calculateDiff(new MyDiffCallback(resver, resver));
                result.dispatchUpdatesTo(adapter);
                adapter.setCartoon(resver);
            }
        });

        RequestOptions options = new RequestOptions()
                .fitCenter()
                .error(R.drawable.clear)
                .placeholder(R.drawable.back)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(this)
                .load(cartoon.getCoverSrc())
                .thumbnail(0.1f)
                .apply(options)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        cartoonImg.setImageDrawable(resource);
                        cartoon_blurImage.setImageBitmap(BlurUtil.blurBitmap(CatlogActivity.this,resource));
                    }
                });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CatlogAdapter(cartoon);
        recyclerView.setAdapter(adapter);
        getCartoons(cartoon);
    }

    private void getCartoons(final Cartoon cartoons) {
        JsoupUtil.getsingleton().catalog(cartoons, new JsoupUtil.LogCallback() {
            @Override
            public void success(final Cartoon cartoons) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cartoonInfo.setText(cartoons.getIntroduction());
                        cartoon.setCatalogsTitle(cartoons.getCatalogsTitle());
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}

