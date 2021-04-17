package com.example.photos;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import org.jetbrains.annotations.NotNull;


public class PhotosAdapter extends PagingDataAdapter<Photos,PhotosAdapter.PhotoViewHolder> {
    private static final String TAG = "PhotosAdapter";

    public PhotosAdapter() {
        super(Photos.CALLBACK);
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_photos,parent,false);
        return new PhotoViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(getItem(position).getUrl()+".png")
                .error(R.drawable.ic_launcher_background)
                .into(holder.photo);
        holder.textView.setText(getItem(position).getTitle());
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder{
        ImageView photo;
        TextView textView;
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
        }

    }
}
