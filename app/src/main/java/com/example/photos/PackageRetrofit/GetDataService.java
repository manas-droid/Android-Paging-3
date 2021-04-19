package com.example.photos.PackageRetrofit;

import com.example.photos.data.Photos;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {
    @GET("/photos")
    ListenableFuture<List<Photos>> getAllPhotos(@Query("albumId") long albumId);
}
