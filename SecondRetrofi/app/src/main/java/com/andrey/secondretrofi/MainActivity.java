package com.andrey.secondretrofi;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.andrey.secondretrofi.interfaces.AuthEndpoint;
import com.andrey.secondretrofi.models.AnswerTest;
import com.andrey.secondretrofi.models.ApiRequest2;
import com.andrey.secondretrofi.models.Rebus;
import com.andrey.secondretrofi.retrofit.ServiceGenerator;
import com.andrey.secondretrofi.retrofit.ServiceGenerator2;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private AuthEndpoint client;//Интерфейс адаптер
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

        findViewById(R.id.btnTest).setOnClickListener(this);
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

    List<Integer> pics_id=new ArrayList<Integer>(1);
        pics_id.add(1);
        List<Integer> points=new ArrayList<Integer>(1);
        points.add(666);
        ServiceGenerator.getInstance().getAnswerTest(new ApiRequest2(
                "Admin",
                "123456789",
                pics_id,
              points
        )
        ).enqueue(new Callback<Rebus>() {
            @Override
            public void onResponse(Call<Rebus> call, Response<Rebus> response) {
                Log.e("TAG",response.body().toString());
            }

            @Override
            public void onFailure(Call<Rebus> call, Throwable t) {
                Log.e("TAG",t.toString()+ t.getMessage());
            }
        });
    }
}
