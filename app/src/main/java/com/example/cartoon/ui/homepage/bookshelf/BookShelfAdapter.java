package com.example.cartoon.ui.homepage.bookshelf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cartoon.R;
import com.example.cartoon.model.Bean.NetCartoon;
import com.example.cartoon.model.Util.GlideLoad;

import java.util.List;

public class BookShelfAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<NetCartoon> cartoons;
    private Context context;

    public void refresh(List<NetCartoon> cartoons){
        this.cartoons = cartoons;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_bookshelf,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder){
            ((ViewHolder) holder).onBindview(position);
        }
    }

    @Override
    public int getItemCount() {
        return cartoons.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView bookshelf_crv;
        private ImageView bookshelf_new;
        private TextView bookshelf_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookshelf_crv = itemView.findViewById(R.id.bookshelf_crv);
            bookshelf_new = itemView.findViewById(R.id.bookshelf_new);
            bookshelf_name = itemView.findViewById(R.id.bookshelf_name);
        }

        public void onBindview(int position) {
            NetCartoon netCartoon = cartoons.get(position);
            Object tag = bookshelf_crv.getTag();
            if (tag != null && (int) tag != position) {
                //如果tag不是Null,并且同时tag不等于当前的position。
                //说明当前的viewHolder是复用来的
                Glide.with(itemView.getContext()).clear(bookshelf_crv);
            }
            if (netCartoon.getCoverSrc() != null){
                GlideLoad.loadImage(itemView.getContext(),netCartoon.getUrl(),bookshelf_crv);
            }
            bookshelf_name.setText(netCartoon.getCartoonName());
            if (netCartoon.getLastReadLast() < netCartoon.getCatalogsSize()){
                bookshelf_new.setVisibility(View.VISIBLE);
            }else {
                bookshelf_new.setVisibility(View.INVISIBLE);
            }
        }
    }
}
