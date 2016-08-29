package com.example.workthisfile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main2Activity extends ActionBarActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button) findViewById(R.id.button1)).setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        File text1=new File("/sdcard/MyFolder/");
        File inputFile=new File(text1,"MyNigga.txt");
        FileInputStream fos;
        try {

            fos = new FileInputStream(inputFile);
        } catch (FileNotFoundException e) {
            Log.e("IO", "Fuck!");
            fos=null;
        }

        Scanner sc=new Scanner(fos);
        String str1="";
        while (sc.hasNext()) {
            str1 += sc.nextLine();
            str1+="\n";
        }
        str1=(str1==null) ? "null":str1;
        ((TextView) findViewById(R.id.textView1)).setText(str1);
    }
}
