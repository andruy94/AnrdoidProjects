package com.example.workthisfile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        editText=(EditText) findViewById(R.id.editText);
        ((Button) findViewById(R.id.button)).setOnClickListener(this);
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
    public void onClick(View view) {
        String stringBuilder=editText.getText().toString();
        File text1=new File("/sdcard/MyFolder/");
        text1.mkdirs();
        File outputFile=new File(text1,"MyNigga.txt");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(outputFile);
            fos.write(stringBuilder.getBytes());
        } catch (IOException e) {
            Log.e("IO","WTF?");
        }


        Intent intent=new Intent();
        intent.putExtra("String",stringBuilder);
        intent.setClass(this,Main2Activity.class);
        startActivity(intent);
    }

}
