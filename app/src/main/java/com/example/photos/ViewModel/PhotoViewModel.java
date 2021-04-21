package com.example.photos.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;
import androidx.paging.PagingSource;

import com.example.photos.Dao.PhotosDao;
import com.example.photos.Dao.PhotosDatabase;
import com.example.photos.data.PhotoSourceWithDao;
import com.example.photos.data.Photos;

import kotlin.jvm.functions.Function0;


public class PhotoViewModel extends AndroidViewModel {
    Lifecycle lifecycle;
    private static final String TAG = "PhotoDataSourceFactory";
    PhotosDatabase photosDatabase;

    public PhotoViewModel(@NonNull Application application) {
        super(application);
        this.photosDatabase = PhotosDatabase.getInstance(application);
    }

    public void setLifecycle(Lifecycle lifecycle){
        this.lifecycle = lifecycle;
    }

    public LiveData<PagingData<Photos>> getSearchResults(){
        PhotosDao photosDao = photosDatabase.photosDao();
        PagingConfig config = new PagingConfig(1, 2, false);
        Pager<Long , Photos> pager = new Pager(config, null, new PhotoSourceWithDao(this.photosDatabase), () -> photosDao.getAllPhotos());

        return PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), lifecycle);
    }


}