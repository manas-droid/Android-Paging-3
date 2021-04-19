package com.example.photos.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photos.R;

import org.jetbrains.annotations.NotNull;

public class PhotosLoadStateAdapter extends LoadStateAdapter<PhotosLoadStateAdapter.LoadStateViewHolder> {
    private View.OnClickListener retryCallback;

    private static final String TAG = "PhotosLoadStateAdapter";
    public PhotosLoadStateAdapter(View.OnClickListener retryCallback) {
        this.retryCallback = retryCallback;
    }

    @Override
    public void onBindViewHolder(
            @NotNull LoadStateViewHolder loadStateViewHolder
            , @NotNull LoadState loadState) {
        Log.d(TAG, "onBindViewHolder: "+loadState);
        loadStateViewHolder.progressBar.setVisibility(loadState instanceof LoadState.Loading? View.VISIBLE:View.GONE);

        loadStateViewHolder.retryButton.setVisibility((loadState instanceof LoadState.Error) ? View.VISIBLE : View.GONE);

        loadStateViewHolder.retryButton.setOnClickListener(retryCallback);
    }

    @NotNull
    @Override
    public LoadStateViewHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, @NotNull LoadState loadState) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.load_state_item,viewGroup,false);
        return new LoadStateViewHolder(view);
    }

    public static  class LoadStateViewHolder extends RecyclerView.ViewHolder{
        private ProgressBar progressBar;
        private Button retryButton;
        public LoadStateViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
            retryButton = itemView.findViewById(R.id.retryButton);
        }


    }


}