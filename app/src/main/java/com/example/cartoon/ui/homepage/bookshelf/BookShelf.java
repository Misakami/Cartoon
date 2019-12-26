package com.example.cartoon.ui.homepage.bookshelf;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cartoon.R;
import com.example.cartoon.model.Bean.Cartoon;
import com.example.cartoon.model.Bean.NetCartoon;
import com.example.cartoon.model.Bean.NetCartoonCache;
import com.example.cartoon.model.Util.Const;
import com.example.cartoon.model.Util.LogUtil;
import com.example.cartoon.ui.cartoonLoader.ImageViewActivity;


import java.util.Date;
import java.util.List;
import java.util.Objects;

public class BookShelf extends Fragment implements BookShelfAdapter.OnNetCartoonClickListen {
    private RecyclerView recyclerView;
    private BookShelfAdapter adapter;
    private BookShelfViewModel viewModel;
    private SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookshelf, container, false);
        recyclerView = view.findViewById(R.id.bookshelf_rv);
        refreshLayout = view.findViewById(R.id.bookshelf_ref);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUpdate();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(BookShelfViewModel.class);
        adapter = new BookShelfAdapter(recyclerView, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        initObservers();
    }

    private void initObservers() {
        viewModel = new BookShelfViewModel();
        viewModel.netCartoonUpdate.observe(this, new Observer<List<NetCartoon>>() {
            @Override
            public void onChanged(List<NetCartoon> netCartoons) {
                adapter.refresh(netCartoons);
                refreshLayout.setRefreshing(false);
            }
        });
        viewModel.netCartoonCache.observe(this, new Observer<List<NetCartoonCache>>() {
            @Override
            public void onChanged(List<NetCartoonCache> netCartoonCaches) {
                adapter.setCaches(netCartoonCaches);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDate();
    }

    private void loadDate() {
        viewModel.getDatebase();
    }

    @Override
    public void onClick(NetCartoon netCartoon, NetCartoonCache cache) {
        netCartoon.setTime(new Date().getTime());
        netCartoon.setLastReadLast(netCartoon.getCatalogsSize());
        viewModel.click(netCartoon);

        Cartoon cartoon = new Cartoon(netCartoon, cache);
        Intent intent = new Intent(BookShelf.this.getContext(), ImageViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Const.CARTOON, cartoon);
        intent.putExtra(Const.INTENT, bundle);
        Objects.requireNonNull(getContext()).startActivity(intent);
    }

    @Override
    public void onLongClick(final NetCartoon netCartoon, NetCartoonCache cache) {
        if (getContext() == null) return;
        new AlertDialog.Builder(getContext())
                .setTitle("是否删除"+netCartoon.getCartoonName())
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.delectCartoon(netCartoon);
                        loadDate();
                    }
                })
                .setCancelable(true)
                .show();
    }

    private void getUpdate(){
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                viewModel.getUpdate();
            }
        });
    }
}
