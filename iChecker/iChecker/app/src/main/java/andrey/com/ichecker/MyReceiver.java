package andrey.com.ichecker;




import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.Date;
import java.util.List;

public class MyReceiver extends BroadcastReceiver {
    //Context context1;
    //Intent intent1;
    public MyReceiver() {
    }

    NotificationManager manager;
    Notification myNotication;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        final Context context1=context;
        //if (intent.getAction().equals("msg1")) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i=0;i<4;i++) {
                        Intent intent2 = new Intent();
                        intent2.setAction("msg2");
                        PendingIntent pendingIntent = PendingIntent.getActivity(context1, 1, intent2, 0);
                        manager = (NotificationManager) context1.getSystemService(Service.NOTIFICATION_SERVICE);
                        Notification.Builder builder = new Notification.Builder(context1);
                        builder.setAutoCancel(false);
                        if (isConnected(context1)) {
                            builder.setSmallIcon(R.drawable.online);
                            builder.setSubText("Нас ничто не остановит");
                            builder.setContentText(new Date().toString());
                            builder.setNumber(1);
                        } else {
                            builder.setContentText(new Date().toString());
                            builder.setSmallIcon(R.drawable.offline);
                            builder.setSubText("Милорд мы в западне");
                            builder.setNumber(1);
                        }
                        builder.setContentIntent(pendingIntent);
                        builder.setOngoing(true);
                        //API level 16
                        builder.build();
                        myNotication = builder.getNotification();
                        manager.notify(11, myNotication);
                        try {
                            Thread.sleep(15 * 1000);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

    }


    public boolean isConnected(Context context){
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){

            ConnectivityManager cm = (ConnectivityManager)context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnected()) {
                try {
                    URL url = new URL("http://www.google.com/");
                    HttpURLConnection urlc = (HttpURLConnection)url.openConnection();
                    urlc.setRequestProperty("User-Agent", "test");
                    urlc.setRequestProperty("Connection", "close");
                    urlc.setConnectTimeout(1000); // mTimeout is in seconds
                    urlc.connect();
                    if (urlc.getResponseCode() == 200) {
                        return true;
                    } else {
                        return false;
                    }
                } catch (IOException e) {
                    Log.i("warning", "Error checking internet connection", e);
                    return false;
                }
            }

            return false;
        }


        else
            return false;
    }

    }

