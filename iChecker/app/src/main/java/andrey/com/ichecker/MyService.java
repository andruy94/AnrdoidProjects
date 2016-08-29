package andrey.com.ichecker;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import java.util.concurrent.TimeUnit;

public class MyService extends Service {
    NotificationManager manager;
    Notification myNotication;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    NotificationManager nm;

    @Override
    public void onCreate() {
        super.onCreate();
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sendNotif();
        return super.onStartCommand(intent, flags, startId);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    void sendNotif() {
        /*
        // 1-я часть
        Notification notif = new Notification(R.mipmap.ic_launcher, "Text in status bar",
                System.currentTimeMillis());

        // 3-я часть
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.FILE_NAME, "somefile");
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // 2-я часть
        notif.setLatestEventInfo(this, "Notification's title", "Notification's text", pIntent);

        // ставим флаг, чтобы уведомление пропало после нажатия
        notif.flags |= Notification.FLAG_AUTO_CANCEL;

        // отправляем
        nm.notify(1, notif);
        */


        Intent intent = new Intent("com.rj.notitfications.SECACTIVITY");

        PendingIntent pendingIntent = PendingIntent.getActivity(MyService.this, 1, intent, 0);

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Notification.Builder builder = new Notification.Builder(MyService.this);

        builder.setAutoCancel(false);
        builder.setTicker("this is ticker text");
        builder.setContentTitle("WhatsApp Notification");
        builder.setContentText("You have a new message");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(true);
        builder.setSubText("This is subtext...");   //API level 16
        builder.setNumber(100);
        builder.build();

        myNotication = builder.getNotification();
        manager.notify(11, myNotication);
    }


}
