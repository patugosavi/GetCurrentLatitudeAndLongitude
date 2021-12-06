package com.example.getcurrentlatitudeandlongitude;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RestartBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = RestartBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(RestartBroadcastReceiver.class.getSimpleName(), "Service Stops! Oooooooooooooppppssssss!!!!");
        context.startService(new Intent(context, MyService.class));;
    }
}
