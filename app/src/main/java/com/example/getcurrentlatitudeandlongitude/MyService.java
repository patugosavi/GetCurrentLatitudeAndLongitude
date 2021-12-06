package com.example.getcurrentlatitudeandlongitude;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyService extends Service {
    private Context ctx;
    protected static final int NOTIFICATION_ID = 1337;
    private static String TAG = "MyService";
    TimerCounter tc;
    private int counter = 0;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        tc = new TimerCounter();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        onTaskRemoved(intent);
        restartForeground();
        startService(new Intent(getApplicationContext(),MainActivity.class));
        tc.startTimer(counter);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
//        Intent restartServiceIntent = new Intent(getApplicationContext(),this.getClass());
//        restartServiceIntent.setPackage(getPackageName());
//        startService(restartServiceIntent);

        Intent intent = new Intent(getApplicationContext(), MyService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 5000, pendingIntent);


        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "ondestroy!");
        Intent broadcastIntent = new Intent(this, RestartBroadcastReceiver.class);
        sendBroadcast(broadcastIntent);
        tc.stopTimerTask();
    }

    public void restartForeground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.i(TAG, "restarting foreground");
            try {
                Notification notification = new Notification();
                startForeground(NOTIFICATION_ID, notification.setNotification(this, getString(R.string.app_name), "Location service is activated", R.drawable.placeholder                       ));
                Log.i(TAG, "restarting foreground successful");
//                startTimer();
            } catch (Exception e) {
                Log.e(TAG, "Error in notification " + e.getMessage());
            }
        }
    }
}

