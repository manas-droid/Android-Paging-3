package com.example.photos.ViewModel;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import com.example.photos.data.PhotoSource;
import com.example.photos.data.Photos;


public class PhotoViewModel extends ViewModel {
    Lifecycle lifecycle;
    private static final String TAG = "PhotoDataSourceFactory";
    public PhotoViewModel() {
    }

    public void setLifecycle(Lifecycle lifecycle){
        this.lifecycle = lifecycle;
    }

    public LiveData<PagingData<Photos>> getSearchResults(){
        PagingConfig config = new PagingConfig(1, 2, false);
        Pager<Long , Photos> pager = new Pager<>(config, () -> new PhotoSource());
        Log.d(TAG, "getSearchResults: "+pager.toString());

        return PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), lifecycle);
    }


}