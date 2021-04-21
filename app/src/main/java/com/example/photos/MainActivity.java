package com.example.photos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.CombinedLoadStates;
import androidx.paging.LoadState;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.photos.Adapter.PhotosAdapter;
import com.example.photos.Adapter.PhotosLoadStateAdapter;
import com.example.photos.ViewModel.PhotoViewModel;
import com.example.photos.exception.AppException;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {

    Button retry;
     PhotoViewModel viewModel;
    private static final String TAG = "MainActivity";
     RecyclerView recyclerView;
    PhotosAdapter adapter;
    Button retryButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this ,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                .get(PhotoViewModel.class);
        Lifecycle lifecycle = getLifecycle();
        viewModel.setLifecycle(lifecycle);
        recyclerView = findViewById(R.id.recyclerView);
        retryButton = findViewById(R.id.retryButton);
        Log.d(TAG, "onCreate: "+retryButton);
         adapter = new PhotosAdapter();
        initRecyclerViewAndAdapter();
        viewModel.getSearchResults().observe(this, photosPagingData -> adapter.submitData(lifecycle, photosPagingData));
    }


    private void initRecyclerViewAndAdapter() {
        Log.d(TAG, "initRecyclerViewAndAdapter: here");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.addLoadStateListener(combinedLoadStates -> {
            Log.d(TAG, "initRecyclerViewAndAdapter: "+(combinedLoadStates.getRefresh() instanceof LoadState.Error));
            Log.d(TAG, "initRecyclerViewAndAdapter: "+combinedLoadStates);
            retryButton.setVisibility((combinedLoadStates.getAppend() instanceof LoadState.Error)? View.VISIBLE : View.INVISIBLE);
            retryButton.setOnClickListener(v->adapter.retry());
            return null;
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}