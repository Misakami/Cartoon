package com.example.cartoon.homePage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.cartoon.catlog.CatlogActivity;
import com.example.cartoon.model.Bean.Cartoon;
import com.example.cartoon.model.Util.Const;
import com.example.cartoon.R;

import java.util.List;

public class SearchCartoonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Cartoon> cartoons;
    private Context context;

    public SearchCartoonAdapter(List<Cartoon> cartoons) {
        this.cartoons = cartoons;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null){
            context =parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_search,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.onbind(position);
    }

    @Override
    public int getItemCount() {
        return cartoons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private ImageView imageView;
        private TextView lastUpdate;
        private TextView from;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_title);
            imageView = itemView.findViewById(R.id.item_image);
            lastUpdate = itemView.findViewById(R.id.last_update);
            from = itemView.findViewById(R.id.from);
        }

        public void onbind(final int index){
            final Cartoon cartoon = cartoons.get(index);
            textView.setText(cartoon.getTitle());
            lastUpdate.setText(cartoon.getLastUpdates());
            from.setText(Const.comefrom(cartoon.getType()));
            RequestOptions options = new RequestOptions()
                    .fitCenter()
                    .error(R.drawable.clear)
                    .placeholder(R.drawable.back)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(itemView)
                    .load(cartoon.getCoverSrc())
                    .thumbnail(0.1f)
                    .apply(options)
                    .into(imageView);
            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,cartoon.getUrl(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, CatlogActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(CatlogActivity.CHOSECARTOON,cartoon);
                    intent.putExtra(CatlogActivity.CHOSECARTOON,bundle);
                    context.startActivity(intent);
                }
            });
        }
    }

}
