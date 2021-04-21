package com.example.photos.Dao;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.photos.data.Photos;

@Database(version=1,
        entities=Photos.class)
public abstract class PhotosDatabase
        extends RoomDatabase {

    private static PhotosDatabase instance;
    public abstract PhotosDao photosDao();

    public static synchronized PhotosDatabase getInstance(Context context){
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PhotosDatabase.class, "photo_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }



}
