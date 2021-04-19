package com.example.photos.data;
import androidx.paging.ListenableFuturePagingSource;
import androidx.paging.PagingState;

import com.example.photos.PackageRetrofit.GetDataService;
import com.example.photos.PackageRetrofit.RetrofitInstance;
import com.example.photos.data.Photos;
import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.HttpException;


public class PhotoSource extends ListenableFuturePagingSource<Long , Photos> {
    GetDataService dataService;
    private static final String TAG = "PhotoSource";
    private static long page;
    private static final long UNSPLASH_STARTING_PAGE_INDEX = 1;
    @NotNull
    @Override
    public ListenableFuture<LoadResult<Long, Photos>> loadFuture(@NotNull LoadParams<Long> loadParams) {
        dataService = RetrofitInstance.getInstance().create(GetDataService.class);
        Executor executor = Executors.newSingleThreadExecutor();
        page = loadParams.getKey()==null ? UNSPLASH_STARTING_PAGE_INDEX : loadParams.getKey();
        ListenableFuture<List<Photos>> photoResponse =  dataService.getAllPhotos(page);

        ListenableFuture<LoadResult<Long ,Photos>> loadResultListenableFuture = Futures.transform(photoResponse, new Function<List<Photos>, LoadResult<Long, Photos>>() {
            @NullableDecl
            @Override
            public LoadResult<Long, Photos> apply(@NullableDecl List<Photos> input) {
                return new LoadResult.Page<>(input, page==UNSPLASH_STARTING_PAGE_INDEX ? null:page-1, input.isEmpty()? null:page+1);
            }
        }, executor);
        ListenableFuture<LoadResult<Long ,Photos>> loadResultListenableFuture1 = Futures.catching(loadResultListenableFuture, HttpException.class, LoadResult.Error::new, executor);
        return Futures.catching(loadResultListenableFuture1, IOException.class, LoadResult.Error::new, executor);

    }

    @Nullable
    @Override
    public Long getRefreshKey(@NotNull PagingState<Long, Photos> pagingState) {

        return null;
    }
}
