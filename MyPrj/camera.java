package com.example.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener ,View.OnTouchListener{
    final int CAMERA_CAPTURE = 1;
    final int PIC_CROP = 2;
    public Uri picUri;
    ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        View v=(TextView) findViewById(R.id.textView);
        v.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                try {
                    // Намерение для запуска камеры
                    Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(captureIntent, CAMERA_CAPTURE);
                } catch (ActivityNotFoundException e) {
                    // Выводим сообщение об ошибке
                    //String errorMessage = "Ваше устройство не поддерживает съемку";
                    Toast toast = Toast
                            .makeText(MainActivity.this, "errorMessage", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Вернулись от приложения Камера
            if (requestCode == CAMERA_CAPTURE) {
                // Получим Uri снимка
                picUri = data.getData();
                // кадрируем его
            }
            // Вернулись из операции кадрирования
            //else if(requestCode == PIC_CROP){
            Bundle extras = data.getExtras();
            // Получим кадрированное изображение
            Bitmap thePic = extras.getParcelable("data");
            // передаём его в ImageView
            ImageView picView = (ImageView)findViewById(R.id.picture);
            picView.setImageBitmap(thePic);

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        //Toast.makeText(this,width +"",Toast.LENGTH_SHORT).show();
/*
                c.drawCircle(event.getY(), event.getX(), 50, paint);

        if(event.getAction()==MotionEvent.ACTION_MOVE)
        {

                paint.setStyle(Paint.Style.FILL);
                c.drawCircle(fx, fy, 2, paint);
                paint.setStyle(Paint.Style.STROKE);
            }

            mImageView.setImageBitmap(newImage);
*/
        return true;
    }
}
