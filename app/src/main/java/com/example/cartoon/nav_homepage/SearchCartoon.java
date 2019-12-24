package com.example.cartoon.nav_homepage;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cartoon.R;
import com.example.cartoon.model.Bean.Cartoon;
import com.example.cartoon.model.Util.JsoupUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class SearchCartoon extends Fragment {
    List<Cartoon> cartoons = new ArrayList<>();
    RecyclerView recyclerView;
    SearchCartoonAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_search, container, false);
        final SearchView searchView = inflate.findViewById(R.id.view_search);
        setUnderLinetransparent(searchView);
        TextView textView = inflate.findViewById(R.id.search_src_text);
        textView.setBackgroundColor(Color.WHITE);

        recyclerView = inflate.findViewById(R.id.fragment_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SearchCartoonAdapter(cartoons);
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i(TAG, "onClick: "+ searchView.getQuery());
                cartoons.clear();
                adapter.notifyDataSetChanged();
                getCartoons(searchView.getQuery().toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return inflate;
    }

    private void getCartoons(final String string) {
        JsoupUtil.getsingleton().search(string, new JsoupUtil.Callback() {
            @Override
            public synchronized void success(final List<Cartoon> cartoon) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cartoons.addAll(cartoon);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }


    private void setUnderLinetransparent(SearchView searchView){
        try {
            Class<?> argClass = searchView.getClass();
            // mSearchPlate是SearchView父布局的名字
            Field ownField = argClass.getDeclaredField("mSearchPlate");
            ownField.setAccessible(true);
            View mView = (View) ownField.get(searchView);
            mView.setBackgroundColor(Color.TRANSPARENT);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
