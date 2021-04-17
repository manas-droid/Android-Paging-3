package com.example.photos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

     PhotoViewModel viewModel;
    private static final String TAG = "MainActivity";
     RecyclerView recyclerView;
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
        PhotosAdapter adapter = new PhotosAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        viewModel.getSearchResults().observe(this, photosPagingData -> adapter.submitData(lifecycle, photosPagingData));

        NetworkUtil.checkNetworkInfo(this, type -> {
            if(type){
                Toast.makeText(MainActivity.this, "Connection Available", Toast.LENGTH_SHORT).show();
            }else{
                
                Toast.makeText(MainActivity.this, "No Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}