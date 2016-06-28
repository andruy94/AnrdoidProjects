package com.krabd.klient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TeachersMenu extends Activity {
    TextView tvGreatings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_menu);
        tvGreatings=(TextView) findViewById(R.id.tvGreatings);
        tvGreatings.setText(String.format(this.getString(R.string.Greatings),Variable.login));
        findViewById(R.id.btnMarks).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), ResultForTeachersActivity.class));
            }
        });
        findViewById(R.id.btnGraph).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Пока делается",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), StudentsGraph.class));
            }
        });
        findViewById(R.id.btnGraph2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ListOfTestActivity.class));
            }
        });
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
    }
}
