package andrey.com.ichecker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends ActionBarActivity {

    public final static String FILE_NAME = "filename";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }

    public void onClickStart(View v) {


        int mytime=60000;
        long time = (long) Math.rint(System.currentTimeMillis()/mytime);
        time = time*mytime + mytime;
        Intent intent = new Intent(this, MyReceiver.class);
        intent.setAction("msg1");
        PendingIntent pIntent = PendingIntent.getBroadcast(getApplicationContext(), 128, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC, time, mytime, pIntent);
        // принудитлеьно показавыем результат
        Intent intent1 = new Intent(this, MyReceiver.class);
        intent.setAction("msg1");
        try {
            PendingIntent pIntent1= PendingIntent.getBroadcast(getApplicationContext(), 128, intent1, 0);
            pIntent1.send(getApplicationContext(),128,intent1);
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }


    public void onClickStop(View v) {

        Intent intentstop = new Intent(getApplicationContext(), MyReceiver.class);
        PendingIntent senderstop = PendingIntent.getBroadcast(getApplicationContext(),
                128, intentstop, 0);
        AlarmManager alarmManagerstop = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManagerstop.cancel(senderstop);
        Log.d("TAG", "STOP");
    }



}
