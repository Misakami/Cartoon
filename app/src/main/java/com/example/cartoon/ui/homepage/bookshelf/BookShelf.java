package com.example.cartoon.ui.homepage.bookshelf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cartoon.R;
import com.example.cartoon.model.Bean.NetCartoon;

import java.util.List;

public class BookShelf extends Fragment {
    private boolean isLodaed = false;
    private RecyclerView recyclerView;
    private BookShelfAdapter adapter;
    private BookShelfViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookshelf, container, false);
        recyclerView = view.findViewById(R.id.bookshelf_rv);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(BookShelfViewModel.class);
        adapter = new BookShelfAdapter();
        initObservers();
    }

    private void initObservers() {
        viewModel = new BookShelfViewModel();
        viewModel.netCartoonUpdate.observe(this, new Observer<List<NetCartoon>>() {
            @Override
            public void onChanged(List<NetCartoon> netCartoons) {
                adapter.refresh(netCartoons);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isLodaed){
            loadDate();
        }
    }

    private void loadDate() {
        viewModel.getDatebase();
    }
}
