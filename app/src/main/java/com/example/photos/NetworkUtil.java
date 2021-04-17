package com.example.photos;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

public class NetworkUtil {
    private static final String TAG = "NetworkUtil";

    public static void checkNetworkInfo(Context context , OnConnectionStatusChange onConnectionStatusChange){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());


            connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback(){
                @Override
                public void onAvailable(@NonNull Network network) {
                    onConnectionStatusChange.onChange(true);
                }
                @Override
                public void onLost(@NonNull Network network) {
                    Log.d(TAG, "onLost: "+network.toString());
                    onConnectionStatusChange.onChange(false);
                }
            });
        }
        else{
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            Log.d(TAG, "checkNetworkInfo: "+networkInfo.toString());
            onConnectionStatusChange.onChange(networkInfo!=null && networkInfo.isConnectedOrConnecting());
        }
    }
    interface  OnConnectionStatusChange{
        void onChange(boolean type);
    }
}


