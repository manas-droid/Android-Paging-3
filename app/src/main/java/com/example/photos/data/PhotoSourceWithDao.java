package com.example.photos.data;

import androidx.paging.ListenableFutureRemoteMediator;
import androidx.paging.LoadType;
import androidx.paging.PagingState;

import com.example.photos.Dao.PhotosDao;
import com.example.photos.Dao.PhotosDatabase;
import com.example.photos.PackageRetrofit.GetDataService;
import com.example.photos.PackageRetrofit.RetrofitInstance;
import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PhotoSourceWithDao extends ListenableFutureRemoteMediator<Long , Photos>{
    GetDataService dataService;
    PhotosDatabase photosDatabase;

    public PhotoSourceWithDao(PhotosDatabase photosDatabase) {
        this.photosDatabase = photosDatabase;
    }

    @NotNull
    @Override
    public ListenableFuture<MediatorResult> loadFuture(@NotNull LoadType loadType, @NotNull PagingState<Long, Photos> pagingState) {
        dataService = RetrofitInstance.getInstance().create(GetDataService.class);
        Executor executor = Executors.newSingleThreadExecutor();
        Integer loadKey = null;
        PhotosDao photosDao = photosDatabase.photosDao();
        switch (loadType){

            case REFRESH:
                break;
            case PREPEND:
                return Futures.immediateFuture(new MediatorResult.Success(true));

            case APPEND:
                Photos lastItem = pagingState.lastItemOrNull();

                if(lastItem == null){
                    return Futures.immediateFuture(new MediatorResult.Success(true));
                }
                loadKey = lastItem.getAlbumId();
        }

        ListenableFuture<List<Photos>> photoResponse =  dataService.getAllPhotos(loadKey == null ? 1 : loadKey+1);

        ListenableFuture<MediatorResult> networkResult = Futures.transform(photoResponse, new Function<List<Photos>, MediatorResult>() {
            @NullableDecl
            @Override
            public MediatorResult apply(@NullableDecl List<Photos> input) {
                if(loadType == LoadType.REFRESH){
                    photosDao.deleteAllPhotos();
                }
                photosDao.insertPhotos(input);
                return new MediatorResult.Success(input.isEmpty());
            }
        }, executor);

        ListenableFuture<MediatorResult> ioCatchingNetworkResult = Futures.catching(networkResult, IOException.class, MediatorResult.Error::new , executor);
        return ioCatchingNetworkResult;
    }



}

