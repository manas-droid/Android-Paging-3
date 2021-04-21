package com.example.photos.Dao;

import androidx.lifecycle.LiveData;
import androidx.paging.PagingSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.photos.data.Photos;

import java.util.List;

@Dao
public interface PhotosDao {

    @Query("SELECT * FROM Photos")
    PagingSource<Integer, Photos> getAllPhotos();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPhotos(List<Photos> photos);

    @Query("DELETE FROM Photos")
    void deleteAllPhotos();



}
