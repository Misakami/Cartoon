package com.example.cartoon.ui.catlog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cartoon.model.Bean.Cartoon;
import com.example.cartoon.model.Bean.CartoonMark;
import com.example.cartoon.model.Util.CartoonMarkUtil;
import com.example.cartoon.model.Util.Const;
import com.example.cartoon.model.Util.LogUtil;
import com.example.cartoon.R;
import com.example.cartoon.ui.cartoonLoader.ImageViewActivity;


public class CatlogAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private Cartoon cartoon;
    private Cartoon baseCartoon;
    private boolean reServe = false;
    private int mark;

    public CatlogAdapter(Cartoon cartoon) {
        this.cartoon = cartoon;
        baseCartoon = cartoon;
        refresh();
    }

    public void setCartoon(Cartoon cartoon) {
        this.cartoon = cartoon;
        reServe = !reServe;
    }

    public Cartoon getCartoon() {
        return cartoon;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_catlog,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return cartoon.getCatalogsTitle().size();
    }

    public void refresh() {
        mark = CartoonMarkUtil.getMark(cartoon.getTitle(), cartoon.getType());
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.catlog);
        }

        public void onBind(final int index){
            if (index == mark){
                textView.setTextColor(Color.RED);
            }else{
                textView.setTextColor(Color.BLACK);
            }
            textView.setText(cartoon.getCatalogsTitle().get(index));
            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtil.Log("点击：",cartoon.getCatalogsUrl().get(index));
                    Intent intent = new Intent(context,ImageViewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Const.CARTOON,baseCartoon);
                    int i = reServe? baseCartoon.getCatalogsTitle().size()-1-index:index;
                    LogUtil.Mankelog("反转",i+" "+reServe+" "+baseCartoon.getCatalogsTitle());
                    CartoonMarkUtil.insertOrUpdateMark(i,baseCartoon.getTitle(),baseCartoon.getType());
                    bundle.putInt(Const.CARTOONNum,i);
                    intent.putExtra(Const.INTENT,bundle);
                    context.startActivity(intent);
                }
            });
        }
    }
}
