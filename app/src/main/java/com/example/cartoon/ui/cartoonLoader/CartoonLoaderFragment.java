package com.example.cartoon.ui.cartoonLoader;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.cartoon.R;
import com.example.cartoon.widget.CircleProgress;


public class CartoonLoaderFragment extends Fragment {
    private PageEntity myEntity;

    private View view;
    private CircleProgress progress;
    private ImageView page;

    public static CartoonLoaderFragment getInstance(PageEntity entity) {
        CartoonLoaderFragment fragment = new CartoonLoaderFragment();
        Bundle args = new Bundle();
        args.putParcelable("PageEntity", entity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myEntity = getArguments().getParcelable("PageEntity");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_page, container, false);
        initView();
        return view;
    }

    @Override
    public void onDestroyView() {
        view = null;
        super.onDestroyView();
    }

    private void initView() {
        page = view.findViewById(R.id.pageView);
        progress = view.findViewById(R.id.pageView_loading);
        progress.startAnim();
        String imgsrc = myEntity.getImgSrc();
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .error(R.drawable.clear)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(view)
                .load(imgsrc)
                .apply(options)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progress.stopAnim();
                        progress.setVisibility(View.INVISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progress.stopAnim();
                        progress.setVisibility(View.INVISIBLE);
                        return false;
                    }
                }).into(page);
    }
}
