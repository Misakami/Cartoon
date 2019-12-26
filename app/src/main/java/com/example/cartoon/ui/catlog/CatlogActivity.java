package com.example.cartoon.ui.catlog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.cartoon.BaseActivity;
import com.example.cartoon.R;
import com.example.cartoon.model.Bean.Cartoon;
import com.example.cartoon.model.Bean.NetCartoonCache;
import com.example.cartoon.model.Util.BlurUtil;
import com.example.cartoon.model.Util.JsoupUtil;
import com.example.cartoon.model.MyDiffCallback;
import com.example.cartoon.model.Util.LogUtil;
import com.jaeger.library.StatusBarUtil;

import java.util.Collections;

/**
 * 漫画目录
 */
public class CatlogActivity extends BaseActivity {
    public static String CHOSECARTOON = "KATONG";
    private ImageView cartoonImg;
    private TextView cartoonInfo;
    private TextView swtich;
    private RecyclerView recyclerView;
    private Cartoon cartoon;
    private CatlogAdapter adapter;
    private ImageView cartoon_blurImage;
    private boolean read = true;
    private CatlogViewModel viewModel;
    private TextView join;
    private Button readButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageView(this, cartoon_blurImage);
        setContentView(R.layout.activity_catlog);
        ViewModelProviders.of(this).get(CatlogViewModel.class);
        initObserve();
        initdata();
        initview();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getCartoons(cartoon);
        adapter.refresh();
    }

    private void initObserve() {
        viewModel = new CatlogViewModel();
        viewModel.cartoon.observe(this, new Observer<Cartoon>() {
            @Override
            public void onChanged(final Cartoon cartoons) {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        cartoonInfo.setText(cartoons.getIntroduction());
                        cartoon.setCatalogsTitle(cartoons.getCatalogsTitle());
                        cartoon.setCatalogsUrl(cartoons.getCatalogsUrl());
                        LogUtil.Log("cartoon",""+cartoons.getCatalogsUrl().size());
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        viewModel.toast.observe(this, new Observer<String>() {
            @Override
            public void onChanged(final String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CatlogActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void initdata() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(CHOSECARTOON);
        assert bundle != null;
        cartoon = bundle.getParcelable(CHOSECARTOON);
    }

    private void initview() {
        cartoonImg = findViewById(R.id.cartoon_img);
        cartoonInfo = findViewById(R.id.cartoon_info);
        TextView title = findViewById(R.id.cartoon_title);
        recyclerView = findViewById(R.id.catlog_recycle);
        TextView lastUpdate = findViewById(R.id.cartoon_lastUpdate);
        swtich = findViewById(R.id.switch_read);
        cartoon_blurImage = findViewById(R.id.cartoon_blurImage);
        join = findViewById(R.id.catLog_join_cartoon);
        readButton = findViewById(R.id.catLog_read);

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

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.joinCartoons();
                LogUtil.Log("书架","加入书架");
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
                        cartoon_blurImage.setImageBitmap(BlurUtil.blurBitmap(CatlogActivity.this, resource));
                    }
                });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CatlogAdapter(cartoon);
        recyclerView.setAdapter(adapter);

    }
}

