package com.example.photos.PackageRetrofit;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.adapter.guava.GuavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit;
    private static final String TAG = "RetrofitInstance";
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public static Retrofit getInstance(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(GuavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
