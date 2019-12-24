package com.example.cartoon.homePage;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.cartoon.BaseActivity;
import com.example.cartoon.R;
import com.example.cartoon.model.Bean.Cartoon;
import com.example.cartoon.model.Util.JsoupUtil;

import java.util.ArrayList;
import java.util.List;

import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;


/**
 *  主页
 */
public class MainActivity extends BaseActivity {
    List<Cartoon> cartoons = new ArrayList<>();
    SearchView searchView;
    RecyclerView recyclerView;
    SearchCartoonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView = findViewById(R.id.search_view);
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                cartoons.clear();
                adapter.notifyDataSetChanged();
                getCartoons(string);
            }
        });
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchCartoonAdapter(cartoons);
        recyclerView.setAdapter(adapter);
    }

    private void getCartoons(final String string) {
        JsoupUtil.getsingleton().search(string, new JsoupUtil.Callback() {
            @Override
            public synchronized void success(final List<Cartoon> cartoon) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cartoons.addAll(cartoon);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}
