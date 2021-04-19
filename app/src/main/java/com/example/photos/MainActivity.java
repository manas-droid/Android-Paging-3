package com.example.photos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.example.photos.Adapter.PhotosAdapter;
import com.example.photos.Adapter.PhotosLoadStateAdapter;
import com.example.photos.ViewModel.PhotoViewModel;

public class MainActivity extends AppCompatActivity {

    Button retry;
     PhotoViewModel viewModel;
    private static final String TAG = "MainActivity";
     RecyclerView recyclerView;
    PhotosAdapter adapter;
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
         adapter = new PhotosAdapter();
        initRecyclerViewAndAdapter();

        viewModel.getSearchResults().observe(this, photosPagingData -> adapter.submitData(lifecycle, photosPagingData));
    }

    private void initRecyclerViewAndAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this , 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter.withLoadStateFooter(new PhotosLoadStateAdapter(v->adapter.retry())));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}