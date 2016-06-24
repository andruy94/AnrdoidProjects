package digitclock.urok.ru.examplewidget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TreeMap;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    Calendar c;
    protected Context mycontext;
    int hour, min, day, month, year,seconds;
    static int colorText, backgr;
    static SharedPreferences sp;
    static String time, date;
    static boolean flag1;



    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        sp = context.getSharedPreferences("pref_digitclock", Context.MODE_PRIVATE);
        c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
        seconds=c.get(Calendar.SECOND);
        String h = Integer.toString(hour);
        String m = Integer.toString(min);
        if(h.length()<2)h = "0" + h;
        if(m.length()<2)m = "0" + m;
        time = h + ":" + m +":"+((seconds>9) ? Integer.toString(seconds) :0+Integer.toString(seconds));
        date = Integer.toString(day) +" " + MonthAsString(month, context) + " " + Integer.toString(year);
        for (int id : appWidgetIds) {
            widgetUp(context, appWidgetManager, id);
        }
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        super.onEnabled(context);
        flag1=true;
        int mytime=60000;
        long time = (long) Math.rint(System.currentTimeMillis()/mytime);
        time = time*mytime + mytime;
        Intent intent = new Intent(context, NewAppWidget.class);
        intent.setAction("update_all_widgets");
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC, time, mytime, pIntent);
        mycontext=context;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(flag1){
                    try {
                        Intent intent = new Intent(mycontext, NewAppWidget.class);
                        intent.setAction("update_all_widgets");
                        PendingIntent pendingIntent= PendingIntent.getBroadcast(mycontext, 0, intent, 0);
                        try {
                            pendingIntent.send(mycontext,0,intent);
                        } catch (PendingIntent.CanceledException e) {
                            e.printStackTrace();
                        }

                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        super.onDisabled(context);
        Intent intent = new Intent(context, NewAppWidget.class);
        intent.setAction("update_all_widgets");
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pIntent);
        flag1=false;
    }


    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        SharedPreferences.Editor editor = context.getSharedPreferences("pref_digitclock", Context.MODE_PRIVATE).edit();
        for (int widgetID : appWidgetIds) {
            editor.remove("dg_color" + widgetID);
            editor.remove("dg_backgr" + widgetID);
        }
        editor.commit();
    }

    @Override

    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        mycontext=context;
        if(intent.getAction().equalsIgnoreCase("update_all_widgets")){

            ComponentName thisAppWidget = new ComponentName( context.getPackageName(), getClass().getName());
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int ids[] = appWidgetManager.getAppWidgetIds(thisAppWidget);
            onUpdate(context, appWidgetManager, ids);
        }else if(intent.getAction().equals("update_all_widgets0")){
            Toast.makeText(context,"Hello world",Toast.LENGTH_SHORT).show();
        }
    }

    static Bitmap convertToImg(String text, Context context, int colorText){
        Bitmap btmText = Bitmap.createBitmap(230, 60, Bitmap.Config.ARGB_4444);

        Canvas cnvText = new Canvas(btmText);
        // хороший пример для установки шрифта
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Lombardina Initial One.ttf");
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setSubpixelText(true);
        paint.setTypeface(tf);
        paint.setColor(colorText);
        paint.setTextSize(50);
        cnvText.drawText(text, 50, 50, paint);
        return btmText;
    }


    /**
     * @param month
     * @param context
     * @return
     */
    private String MonthAsString(int month, Context context){
        switch(month){
            case 0:
                return context.getString(R.string.Jan);
            case 1:
                return context.getString(R.string.Feb);
            case 2:
                return context.getString(R.string.Mar);
            case 3:
                return context.getString(R.string.Apr);
            case 4:
                return context.getString(R.string.May);
            case 5:
                return context.getString(R.string.Jun);
            case 6:
                return context.getString(R.string.Jul);
            case 7:
                return context.getString(R.string.Aug);
            case 8:
                return context.getString(R.string.Sep);
            case 9:
                return context.getString(R.string.Okt);
            case 10:
                return context.getString(R.string.Nov);
            case 11:
                return context.getString(R.string.Dec);
            default:
                return "Err";
        }
    }


    static void widgetUp(Context context, AppWidgetManager appWidgetManager, int widgetID){


        RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        colorText = sp.getInt("dg_color" /*+ widgetID*/, Color.WHITE);
        backgr = sp.getInt("dg_backgr" /*+ widgetID*/, R.drawable.fon1);
        backgr=R.drawable.pic;
        widgetView.setImageViewBitmap(R.id.ivTime, convertToImg(time, context, colorText));
        widgetView.setTextViewText(R.id.tvDate, date);
        widgetView.setTextColor(R.id.tvDate, colorText);
        backgr=R.drawable.monkeys;
        widgetView.setInt(R.id.llBack, "setBackgroundResource", backgr);
        Intent intent = new Intent(context, NewAppWidget.class);
        intent.setAction("update_all_widgets");
        widgetView.setOnClickPendingIntent(R.id.ivTime,PendingIntent.getBroadcast(context, 0, intent, 0));
        appWidgetManager.updateAppWidget(widgetID, widgetView);
        /**
        **/

    }

}

